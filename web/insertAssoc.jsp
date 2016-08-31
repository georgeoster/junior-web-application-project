<%@page import="model.Purchased.DbMods"%>
<%@page import="dbUtils.MakeSelectTag"%>
<%@page import="dbUtils.FormatUtils"%>
<%@page import="view.PurchaseView"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />

<%
    DbConn dbc = new DbConn();
    
    String strcustomerIDError = "";
    String strProductIDError = "";
    String datePurchasedError = "";

    String customerIDSelect = "";
    String customerIDsql = "SELECT username, customerID FROM customer ORDER BY customerID";
    int intcustomerID = 0;

    String productIDSelect = "";
    String productIDsql = "SELECT name, productID FROM product ORDER BY productID";
    int intproductID = 0;

    customerIDSelect = MakeSelectTag.makeSelect(dbc, customerIDsql, "customer", intcustomerID, "customer");
    productIDSelect = MakeSelectTag.makeSelect(dbc, productIDsql, "product", intproductID, "product");

    // Declare all Strings and objects as they should be if it is first rendering
    model.Purchased.StringData inputData = new model.Purchased.StringData(); // all properties empty string, good for first rendering
    model.Purchased.StringData errorMsgs = new model.Purchased.StringData(); // all properties empty string, good for first rendering

    if (request.getParameter("customer") != null) { // this is postback

        intcustomerID = Integer.parseInt(request.getParameter("customer"));
        intproductID = Integer.parseInt(request.getParameter("product"));

        customerIDSelect = MakeSelectTag.makeSelect(dbc, customerIDsql, "customer", intcustomerID, "customer");
        productIDSelect = MakeSelectTag.makeSelect(dbc, productIDsql, "product", intproductID, "product");

        // package up Customer String data
        inputData.customerID = request.getParameter("customer");
        inputData.productID = request.getParameter("product");
        inputData.datePurchased = request.getParameter("datePurchased");

        if (Integer.parseInt(inputData.customerID) == 0) {
            strcustomerIDError = "customer is a required field";
        }
        if (Integer.parseInt(inputData.productID) == 0) {
            strProductIDError = "product is a required field";
        }
        if (PurchaseView.isValidDate(inputData.datePurchased) == false) {
            datePurchasedError = "please enter date as yyyy-mm-dd";
        }

        errorMsgs.errorMsg = dbc.getErr();
        if (errorMsgs.errorMsg.length() == 0) { // DB connection is good
            errorMsgs = DbMods.insert(inputData, dbc); // errorMsgs will hold all validation messags
            if (errorMsgs.errorMsg.length() == 0) { // this is the form level error message
                // replace empty string with successful message
                errorMsgs.errorMsg = "Record successfully inserted !";
            }
        } // if db connection is good

        dbc.close(); // no database connection leaks !
    } // postback
%>
<br>
<form action="insertAssoc.jsp" method="get" style = "text-align: center">

    customer <%=customerIDSelect%><%=strcustomerIDError%><br>
    product <%=productIDSelect%><%=strProductIDError%><br>
    date purchased  <input type="text" name="datePurchased" size="20" style="border:1px solid black"    value="<%=inputData.datePurchased%>"/><br>

    <input type="submit" value="insert"><br/>

    <%=errorMsgs.errorMsg%>

</form>

<jsp:include page="postContent.jsp" />