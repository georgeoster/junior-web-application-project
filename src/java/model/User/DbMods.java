package model.User;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import static model.Product.DbMods.validate;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    public static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

        errorMsgs.username = ValidationUtils.stringValidationMsg(inputData.username, 45, true);
        errorMsgs.password = ValidationUtils.stringValidationMsg(inputData.password, 45, true);
        errorMsgs.roleID = ValidationUtils.stringValidationMsg(inputData.roleID, 45, true);
        errorMsgs.memberSince = ValidationUtils.dateValidationMsg(inputData.memberSince, false);

        return errorMsgs;
    } // validate 

    public static StringData insert(StringData inputData, DbConn dbc) {

        StringData errorMsgs = new StringData();
        errorMsgs = validate(inputData);
        if (errorMsgs.getCharacterCount() > 0) {  // at least one field has an error, don't go any further.
            errorMsgs.errorMsg = "Please try again";
            return errorMsgs;

        } else { // all fields passed validation

            // Start preparing SQL statement
            String sql = "INSERT INTO customer (username, password, roleID, memberSince) "
                    + "values (?,?,?,?)";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.username);
            pStatement.setString(2, inputData.password);
            pStatement.setString(3, inputData.roleID);
            pStatement.setString(4, inputData.memberSince);

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the JSP page decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
                }
            }
        } // customerId is not null and not empty string.
        return errorMsgs;
    } // insert

    public static model.User.StringData update(model.User.StringData inputData, DbConn dbc) {

        model.User.StringData errorMsgs = new model.User.StringData();
        errorMsgs = validate(inputData);
        if (errorMsgs.getCharacterCount() > 0) {  // at least one field has an error, don't go any further.
            errorMsgs.errorMsg = "Please try again";
            return errorMsgs;

        } else { // all fields passed validation

            // Start preparing SQL statement
            String sql = "UPDATE customer "
                    + "SET username=?, password=?, roleID=?, memberSince=? "
                    + "WHERE customerID = ?";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.username);
            pStatement.setString(2, inputData.password);
            pStatement.setString(3, inputData.roleID);
            pStatement.setString(4, inputData.memberSince);
            pStatement.setString(5, inputData.customerID);

            // here the SQL statement is actually executed
            int numRows = pStatement.executeUpdate();

            // This will return empty string if all went well, else all error messages.
            errorMsgs.errorMsg = pStatement.getErrorMsg();
            if (errorMsgs.errorMsg.length() == 0) {
                if (numRows == 1) {
                    errorMsgs.errorMsg = ""; // This means SUCCESS. Let the JSP page decide how to tell this to the user.
                } else {
                    // probably never get here unless you forgot your WHERE clause and did a bulk sql update.
                    errorMsgs.errorMsg = numRows + " records were inserted when exactly 1 was expected.";
                }
            }
        } // all fields passed validation
        return errorMsgs;
    } // insert

    public static int delete(DbConn dbc, String ID) {
        String sql = "DELETE FROM customer WHERE customerID = ?";
        PrepStatement pStatement = new PrepStatement(dbc, sql);
        pStatement.setString(1, ID);
        return pStatement.executeUpdate();
    }

}
