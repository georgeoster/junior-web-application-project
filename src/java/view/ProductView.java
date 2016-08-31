package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

// classes in my project
import dbUtils.*;
import java.math.BigDecimal;

public class ProductView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllProducts(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "SELECT madeBy, name, productID, description, dateMade, price FROM product ORDER BY madeBy";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'>Maker</th>");
            sb.append("<th style='text-align:left'>Name</th>");
            sb.append("<th style='text-align:right'>ID</th>");
            sb.append("<th style='text-align:left'>Description</th>");
            sb.append("<th style='text-align:center'>Made</th>");
            sb.append("<th style='text-align:left'>Price</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append("<td><a href = './updateOther.jsp?productId=" + results.getString("productID") + "'><img src='./update.png'></a></td>");
                sb.append("<td><a href = 'javascript:deleteRow(" + results.getString("productID") + ")'><img src='./delete.png'></a></td>");
                sb.append(FormatUtils.formatStringTd(results.getObject("madeBy")));
                sb.append(FormatUtils.formatStringTd(results.getObject("name")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("productID")));
                sb.append(FormatUtils.formatStringTd(results.getObject("description")));
                sb.append(FormatUtils.formatDateTd(results.getObject("dateMade")));
                sb.append(FormatUtils.formatDollarTd(results.getObject("price")));

                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserSql.listAllProductss(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}
