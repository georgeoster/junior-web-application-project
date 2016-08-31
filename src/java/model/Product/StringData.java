package model.Product;

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

    public String productID = "";
    public String name = "";
    public String description = "";
    public String dateMade = "yyyy-MM-dd";
    public String price = "";
    public String madeBy = "";
    public String errorMsg = ""; // this field is used, for example, to hold db error while attempting login.

    @Override
    public String toString() { // this method is mostly just for debugging 
        return "Product ID:" + this.productID
                + ", name:" + this.name
                + ", dexciption:" + this.description
                + ", date Made:" + this.dateMade
                + ", price:" + this.price
                + ", made by:" + this.madeBy
                + ", errorMsg:" + this.errorMsg;
    }

    public int getCharacterCount() {
        String s = this.productID + this.name + this.description + this.dateMade
                + this.price + this.madeBy + this.errorMsg;
        return s.length();
    }

}
