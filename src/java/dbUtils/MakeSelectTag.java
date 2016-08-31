package dbUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * @author george oster
 */
public class MakeSelectTag {

    public static String makeSelect(DbConn dbc, String sql, String tagName, int selected, String optionZero) {

        int optionIndex = 0;
        String persist = "";

        String out = "\n\n<select name = '" + tagName + "'>\n";
        out += "<option value = '" + optionIndex + "'>" + optionZero + "</option>\n";
        
        try {
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            ResultSet results = stmt.executeQuery();
            while (results.next()) {

                if (results.getInt(2) == selected) {
                    persist = "selected = 'selected'";
                }

                out += " <option value='" + results.getInt(2) + "'" + persist + "   >"
                        + results.getString(1) + "</option>\n";
                // optionIndex++;
                persist = "";
            }
            out += "</select>\n\n";
            return out;
        } catch (Exception e) {
            return "Exception in MakeSelectTag.makeSelect(). Partial output: " + out + ". Error: " + e.getMessage();
        }
    }//end makeSelect

    public static String getCustomerNameFromCustomerId(DbConn dbc, String id) {

        PreparedStatement stmt = null;
        ResultSet results = null;
        String toReturn = null;
        String customerName = "SELECT username FROM customer WHERE customerID =" + id;
        try {
            stmt = dbc.getConn().prepareStatement(customerName);
            results = stmt.executeQuery();
            while (results.next()) {
                toReturn = (String) results.getObject("username");
            }
        } catch (SQLException ex) {
            Logger.getLogger(MakeSelectTag.class.getName()).log(Level.SEVERE, null, ex);
        }
        return toReturn;
    }

}
