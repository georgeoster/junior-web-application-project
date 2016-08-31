package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dbUtils.*;
import java.math.BigDecimal;

public class WebUserView {

    /* This method returns a HTML table displaying all the records of the web_user table. 
     * cssClassForResultSetTable: the name of a CSS style that will be applied to the HTML table.
     *   (This style should be defined in the JSP page (header or style sheet referenced by the page).
     * dbc: an open database connection.
     */
    public static String listAllUsers(String cssTableClass, DbConn dbc) {

        // String type could have been used, but StringBuilder is more efficient 
        // in this case where we are just appending
        StringBuilder sb = new StringBuilder("");

        PreparedStatement stmt = null;
        ResultSet results = null;
        try {
            //sb.append("ready to create the statement & execute query " + "<br/>");
            String sql = "SELECT username, password, customerID, memberSince, roleName FROM customer, siteRole WHERE customer.roleID = siteRole.roleID ORDER BY username";
            stmt = dbc.getConn().prepareStatement(sql);
            results = stmt.executeQuery();
            //sb.append("executed the query " + "<br/><br/>");
            sb.append("<table class='");
            sb.append(cssTableClass);
            sb.append("'>");
            sb.append("<tr>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'></th>");
            sb.append("<th style='text-align:left'>Username</th>");
            sb.append("<th style='text-align:left'>Password</th>");
            sb.append("<th style='text-align:right'>Customer ID</th>");
            sb.append("<th style='text-align:center'>Member Since</th>");
            sb.append("<th style='text-align:center'>Role</th></tr>");
            while (results.next()) {
                sb.append("<tr>");
                sb.append("<td><a href = './updateUser.jsp?customerID=" + results.getString("customerID") + "'><img src='./update.png'></a></td>");
                sb.append("<td><a href = 'javascript:deleteRow(" + results.getString("customerID") + ")'><img src='./delete.png'></a></td>");
                sb.append(FormatUtils.formatStringTd(results.getObject("username")));
                sb.append(FormatUtils.formatStringTd(results.getObject("password")));
                sb.append(FormatUtils.formatIntegerTd(results.getObject("customerID")));
                sb.append(FormatUtils.formatDateTd(results.getObject("memberSince")));
                sb.append(FormatUtils.formatStringTd(results.getObject("roleName")));
                sb.append("</tr>\n");
            }
            sb.append("</table>");
            results.close();
            stmt.close();
            return sb.toString();
        } catch (Exception e) {
            return "Exception thrown in WebUserSql.listAllUsers(): " + e.getMessage()
                    + "<br/> partial output: <br/>" + sb.toString();
        }
    }
}
