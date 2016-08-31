<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@page import="dbUtils.FormatUtils"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="view.PurchaseView"%>
<%@page import="dbUtils.MakeSelectTag"%>
<%@page import="model.StringData"%>
<%@page import="model.Logon"%>
<%@page import="dbUtils.DbConn"%>

<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />

<%
    DbConn dbc = new DbConn(); // get database connection wrapper object

    String errorMsg = "";
    String userSelect = ""; //for persistence
    String otherSelect = "";//for persistence
    String assocSelect = "";//for persistence
    String output = "";//result set in a table
    String usersql = "SELECT username, customerID FROM customer ORDER BY customerID";
    String othersql = "SELECT name, productID FROM product ORDER BY productID";
    String assocsql = "SELECT datePurchased, purchasedID FROM purchased ORDER BY datePurchased";
    int intuser = 0;
    int intother = 0;
    String struser = "";
    String strother = "";
    String strassoc = "yyyy-mm-dd";
    String dateError = "";

    errorMsg = dbc.getErr();
    PreparedStatement stmt = null;
    ResultSet results = null;

    if (request.getParameter("user") != null) {
        intuser = Integer.parseInt(request.getParameter("user"));
        intother = Integer.parseInt(request.getParameter("other"));
        strassoc = request.getParameter("assoc");

//************************************************************************************
        String getuser = "SELECT username FROM customer WHERE customerID =" + intuser;
        stmt = dbc.getConn().prepareStatement(getuser);
        results = stmt.executeQuery();
        while (results.next()) {
            struser = (String) results.getObject("username");
        }

        String getother = "SELECT name FROM product WHERE productID =" + intother;
        stmt = dbc.getConn().prepareStatement(getother);
        results = stmt.executeQuery();
        while (results.next()) {
            strother = (String) results.getObject("name");
        }

        if (PurchaseView.isValidDate(strassoc) == false) {
            dateError = "please enter date as yyyy-mm-dd";
            strassoc = "1900-01-01";
        }

        String getassoc = "SELECT datePurchased FROM purchased WHERE datePurchased =" + strassoc;
        stmt = dbc.getConn().prepareStatement(getassoc);
        results = stmt.executeQuery();
        while (results.next()) {
            strassoc = (String) FormatUtils.searchformatDate(results.getObject("datePurchased"));
        }
//************************************************************************************

        output = PurchaseView.search(dbc, struser, strother, strassoc, "table");
        if (strassoc.equalsIgnoreCase("1900-01-01")) {
            strassoc = "yyyy-mm-dd";
        }
    }

    userSelect = MakeSelectTag.makeSelect(dbc, usersql, "user", intuser, "member");
    otherSelect = MakeSelectTag.makeSelect(dbc, othersql, "other", intother, "product");

    dbc.close();
%>
<form action="search.jsp" method="get" style = "text-align: center">
    <br/>
    <%=errorMsg%>
    <%=userSelect%>
    <%=otherSelect%><br>
    purchased after: 
    <input type="text" name="assoc" size="20" style="border:1px solid black" value="<%=strassoc%>"/><br><%=dateError%><br>
    <input type="submit" value="search"><br/>

</form>
<center>
    <br>
    <%=output%>
</center>

<jsp:include page="postContent.jsp" />