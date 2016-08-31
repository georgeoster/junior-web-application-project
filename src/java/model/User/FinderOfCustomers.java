package model.User;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FinderOfCustomers {

    public static model.User.StringData find(DbConn dbc, String customerID) {

        model.User.StringData foundCust = new model.User.StringData(); // default constructor sets all fields to "" (empty string) 
        //foundCust.customerID = null;
        try {

            String sql = "select customerID, username, password, roleID, memberSince from customer where customerID = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, customerID);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                foundCust.customerID = results.getObject("customerID").toString();
                foundCust.username = results.getObject("username").toString();
                foundCust.password = results.getObject("password").toString();
                foundCust.roleID = results.getObject("roleID").toString();
                foundCust.memberSince = FormatUtils.formatDate(results.getObject("memberSince"));
                return foundCust;
            } else {
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundCust.errorMsg = "Exception thrown in Logon.find(): " + e.getMessage();
            return foundCust;
        }
    }

}
