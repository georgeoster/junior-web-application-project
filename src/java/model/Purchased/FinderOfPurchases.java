package model.Purchased;

import dbUtils.DbConn;
import dbUtils.FormatUtils;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author george
 */
public class FinderOfPurchases {

    public static model.Purchased.StringData find(DbConn dbc, String purchasedID) {

        model.Purchased.StringData foundCust = new model.Purchased.StringData(); // default constructor sets all fields to "" (empty string) 

        try {

            String sql = "select purchasedID, customerID, productID, datePurchased from purchased where purchasedID = ?";
            PreparedStatement stmt = dbc.getConn().prepareStatement(sql);
            stmt.setString(1, purchasedID);
            ResultSet results = stmt.executeQuery();

            if (results.next()) {
                foundCust.purchasedID = results.getObject("purchasedID").toString();
                foundCust.customerID = results.getObject("customerID").toString();
                foundCust.productID = results.getObject("productID").toString();
                foundCust.datePurchased = FormatUtils.formatDate(results.getObject("datePurchased"));
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
