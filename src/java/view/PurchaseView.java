package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbUtils.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PurchaseView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllPurchases(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "SELECT purchasedID, username, datePurchased, product.name AS purchased, price FROM customer, purchased, product WHERE purchased.customerID = customer.customerID AND purchased.productID = product.productID ORDER BY username, datePurchased";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'>User</th>");
            sb.append("<th style='text-align:center'>Date Purchased</th>");
            sb.append("<th style='text-align:left'>Purchased</th>");
            sb.append("<th style='text-align:center'>Price</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append("<td><a href = './updateAssoc.jsp?purchasedID=" + results.getString("purchasedID") + "'><img src='./update.png'></a></td>");
                sb.append("<td><a href = 'javascript:deleteRow(" + results.getString("purchasedID") + ")'><img src='./delete.png'></a></td>");
                sb.append(FormatUtils.formatStringTd(results.getObject("username")));
                sb.append(FormatUtils.formatDateTd(results.getObject("datePurchased")));
                sb.append(FormatUtils.formatStringTd(results.getObject("purchased")));
                sb.append(FormatUtils.formatDollarTd(results.getObject("price")));
                sb.append("</tr>\n");
            }//a href='javascript:deleteRow(246)'
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserSql.listAllProductss(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }

    public static String search(DbConn dbc, String user, String product, String purchasedAfter, String cssTableClass) {
        StringBuilder toreturn = new StringBuilder("");
        PreparedStatement stmt = null;
        ResultSet results = null;
        String out = "SELECT * FROM customer, purchased, product";
        out += " WHERE purchased.customerID = customer.customerID";
        out += " AND   purchased.productID  = product.productID";

        if (user.length() != 0) {
            out += " AND customer.username = ?";
        }

        if (product.length() != 0) {
            out += " AND product.name = ?";
        }

        if (purchasedAfter.length() != 0) {
            out += " AND purchased.datePurchased > ?";
        }
        try {
            stmt = dbc.getConn().prepareStatement(out);

            int count = 1;

            if (user.length() != 0) {
                stmt.setString(count, user);
                count++;
            }

            if (product.length() != 0) {
                stmt.setString(count, product);
                count++;
            }

            if (purchasedAfter.length() != 0) {
                stmt.setString(count, purchasedAfter);
                count++;
            }

            results = stmt.executeQuery();

            toreturn.append("<table class='");
            toreturn.append(cssTableClass);
            toreturn.append("'>");
            toreturn.append("<tr>");
            toreturn.append("<th style='text-align:left'>User</th>");
            toreturn.append("<th style='text-align:center'>Purchased</th>");
            toreturn.append("<th style='text-align:left'>Date Purchased</th>");
            while (results.next()) {
                toreturn.append("<tr>");
                toreturn.append(FormatUtils.formatStringTd(results.getObject("username")));
                toreturn.append(FormatUtils.formatStringTd(results.getObject("name")));
                toreturn.append(FormatUtils.formatDateTd(results.getObject("datePurchased")));
                toreturn.append("</tr>\n");
            }
            toreturn.append("</table>");
            results.close();
            stmt.close();
        } catch (Exception e) {
            return "Exception in PurchaseView.search(). Partial output: " + out + ". Error: " + e.getMessage();
        }

        return toreturn.toString();
    }

    public static boolean isValidDate(String value) {
        boolean toreturn = false;
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            date = sdf.parse(value);
            if (value.equals(sdf.format(date))) {
                toreturn = true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return toreturn;
    }

}
