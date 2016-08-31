/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.Product;

import dbUtils.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbMods {

    /*
    Returns a "StringData" object that is full of field level validation
    error messages (or it is full of all empty strings if inputData
    totally passed validation.  
     */
    public static StringData validate(StringData inputData) {

        StringData errorMsgs = new StringData();

        // Validation
        errorMsgs.name = ValidationUtils.stringValidationMsg(inputData.name, 45, true);
        errorMsgs.description = ValidationUtils.stringValidationMsg(inputData.description, 45, true);
        errorMsgs.dateMade = ValidationUtils.dateValidationMsg(inputData.dateMade, true);
        errorMsgs.price = ValidationUtils.decimalValidationMsg(inputData.price, false);
        errorMsgs.madeBy = ValidationUtils.stringValidationMsg(inputData.madeBy, 20, true);

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
            String sql = "INSERT INTO product (name, description, dateMade, price, madeBy) "
                    + "values (?,?,?,?,?)";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.name);
            pStatement.setString(2, inputData.description);
            pStatement.setString(3, inputData.dateMade);
            pStatement.setBigDecimal(4, ValidationUtils.decimalConversion(inputData.price));
            pStatement.setString(5, inputData.madeBy);

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

    public static StringData update(StringData inputData, DbConn dbc) {

        StringData errorMsgs = new StringData();
        errorMsgs = validate(inputData);
        if (errorMsgs.getCharacterCount() > 0) {  // at least one field has an error, don't go any further.
            errorMsgs.errorMsg = "Please try again";
            return errorMsgs;

        } else { // all fields passed validation

            // Start preparing SQL statement
            String sql = "UPDATE product "
                    + "SET name=?, description=?, dateMade=?, price=?, madeBy=? "
                    + "WHERE productId = ?";

            // PrepStatement is Sally's wrapper class for java.sql.PreparedStatement
            // Only difference is that Sally's class takes care of encoding null 
            // when necessary. And it also System.out.prints exception error messages.
            PrepStatement pStatement = new PrepStatement(dbc, sql);

            // Encode string values into the prepared statement (wrapper class).
            pStatement.setString(1, inputData.name);
            pStatement.setString(2, inputData.description);
            pStatement.setString(3, inputData.dateMade);
            pStatement.setBigDecimal(4, ValidationUtils.decimalConversion(inputData.price));
            pStatement.setString(5, inputData.madeBy);
            pStatement.setString(6, inputData.productID);

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

        String sql = "DELETE FROM product WHERE productID = ?";
        PrepStatement pStatement = new PrepStatement(dbc, sql);
        pStatement.setString(1, ID);
        return pStatement.executeUpdate();

    }

}
