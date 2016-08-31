package model;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Logon {

    public static StringData find(DbConn dbc, String username, String password) {
        StringData foundCust = new StringData(); // default constructor sets all fields to "" (empty string) 
        //foundCust.customerID = null;
        try {

            String sql = "select customerID, memberSince from customer where username = ? and password = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                foundCust.customerID = results.getObject("customerID").toString();
                foundCust.username = username; // we can take this from input parameter instead of db. 
                foundCust.password = password;
                foundCust.memberSince = FormatUtils.formatDate(results.getObject("memberSince"));
                //System.out.println("*** 3 fields extracted from result set");
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
