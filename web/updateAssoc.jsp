<%@page import="model.Purchased.FinderOfPurchases"%>
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

    String strpurchasedID = request.getParameter("purchasedID");
    String strcustomerID = "";
    String strproductID = "";
    String strdatePurchased = "";

    String strcustomerIDError = "";
    String strproductIDError = "";
    String strdatePurchasedError = "";

    String successOrFail = "";

    model.Purchased.StringData toUpdate = FinderOfPurchases.find(dbc, strpurchasedID);

    String customerIDsql = "SELECT username, customerID FROM customer ORDER BY customerID";
    int intcustomerID = Integer.parseInt(toUpdate.customerID);
    String customerIDSelect = MakeSelectTag.makeSelect(dbc, customerIDsql, "customer", intcustomerID, "customer");

    String productIDsql = "SELECT name, productID FROM product ORDER BY productID";
    int intproductID = Integer.parseInt(toUpdate.productID);
    String productIDSelect = MakeSelectTag.makeSelect(dbc, productIDsql, "product", intproductID, "product");

    strdatePurchased = toUpdate.datePurchased;

    if (request.getParameter("customer") != null) { // this is postback

        strcustomerID = request.getParameter("customer");
        strproductID = request.getParameter("product");
        customerIDSelect = MakeSelectTag.makeSelect(dbc, customerIDsql, "customer", Integer.parseInt(request.getParameter("customer")), "customer");
        productIDSelect = MakeSelectTag.makeSelect(dbc, productIDsql, "product", Integer.parseInt(request.getParameter("product")), "product");
        strdatePurchased = request.getParameter("datePurchased");

        model.Purchased.StringData errorChecking = new model.Purchased.StringData();
        model.Purchased.StringData changes = new model.Purchased.StringData();

        changes.purchasedID = strpurchasedID;
        changes.customerID = strcustomerID;
        changes.productID = strproductID;
        changes.datePurchased = strdatePurchased;

        errorChecking = DbMods.validate(changes);

        if (errorChecking.datePurchased.length() > 0) {
            strdatePurchasedError = "date made must be in yyyy-MM-dd format";
        }

        if (errorChecking.getCharacterCount() == 0) {
            successOrFail = "Record Successfully Updated";
            DbMods.update(changes, dbc);
        }

    }
    dbc.close();
%>
<br>
<form action="updateAssoc.jsp" method="get" style = "text-align: center">

    Purchased ID  <input type="text" name="purchasedID" size="20" style="border:1px solid black" value="<%=strpurchasedID%>"/><br>
    customer <%=customerIDSelect%><%=strcustomerIDError%><br>
    product <%=productIDSelect%><%=strproductIDError%><br>
    date purchased  <input type="text" name="datePurchased" size="20" style="border:1px solid black" value="<%=strdatePurchased%>"/><%=strdatePurchasedError%><br>

    <input type="submit" value="insert"><br/>

    <%=successOrFail%>

</form>

<jsp:include page="postContent.jsp" />