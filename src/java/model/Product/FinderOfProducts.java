package model.Product;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FinderOfProducts {

    public static StringData find(DbConn dbc, String productID) {
        StringData foundCust = new StringData(); // default constructor sets all fields to "" (empty string) 

        try {

            String sql = "select productID, name, description, dateMade, price, madeBy from product where productID = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, productID);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                foundCust.productID = results.getObject("productID").toString();
                foundCust.name = results.getObject("name").toString();
                foundCust.description = results.getObject("description").toString();
                foundCust.dateMade = FormatUtils.formatDate(results.getObject("dateMade"));
                foundCust.price = results.getObject("price").toString();
                foundCust.madeBy = results.getObject("madeBy").toString();
                return foundCust;
            } else {
                return null; // means customer not found with given credentials.
            }
        } catch (Exception e) {
            foundCust.errorMsg = "Exception thrown in Logon.find(): " + e.getMessage();
            return foundCust;
        }
    }

}//end class FinderOfProducts
