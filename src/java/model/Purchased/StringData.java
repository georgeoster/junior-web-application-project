package model.Purchased;

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

    public String purchasedID = "";
    public String customerID = "";
    public String productID = "";
    public String datePurchased = "yyyy-MM-dd";
    public String errorMsg = ""; // this field is used, for example, to hold db error while attempting login.

    @Override
    public String toString() { // this method is mostly just for debugging 
        return "Purchased ID:" + this.purchasedID
                + ", customerID:" + this.customerID
                + ", productID:" + this.productID
                + ", date purchased:" + this.datePurchased
                + ", errorMsg:" + this.errorMsg;
    }

    public int getCharacterCount() {
        String s = this.purchasedID + this.customerID + this.productID + this.datePurchased
                + this.errorMsg;
        return s.length();
    }

}
