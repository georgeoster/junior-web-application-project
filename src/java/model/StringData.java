package model;

import model.Product.*;

/* The purpose of this class is just to "bundle together" all the 
 * character data that the user might type in when they want to 
 * add a new Customer or edit an existing customer.  This String
 * data is "pre-validated" data, meaning they might have typed 
 * in a character string where a number was expected.
 * 
 * There are no getter or setter methods since we are not trying to
 * protect this data in any way.  We want to let the JSP page have
 * free access to put data in or take it out. */
public class StringData {

    public String customerID = "";
    public String username = "";
    public String password = "";
    public String roleID = "";
    public String memberSince = "";
    public String errorMsg = ""; // this field is used, for example, to hold db error while attempting login.

    @Override
    public String toString() { // this method is mostly just for debugging 
        return "Customer ID:" + this.customerID
                + ", username:" + this.username
                + ", password:" + this.password
                + ", role ID:" + this.roleID
                + ", member since:" + this.memberSince
                + ", errorMsg:" + this.errorMsg;
    }

    public int getCharacterCount() {
        String s = this.customerID + this.username + this.password + this.roleID
                + this.memberSince + this.errorMsg;
        return s.length();
    }

}
