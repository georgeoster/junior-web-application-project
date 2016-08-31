<%@page import="model.Product.DbMods"%>
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
    String strnameError = "";
    String strdescriptionError = "";
    String strdateMadeError = "";
    String strmadeByError = "";

    String madeBySelect = ""; 
    String madeBysql = "SELECT username, customerID FROM customer ORDER BY customerID";
    int intmadeBy = 0;

    madeBySelect = MakeSelectTag.makeSelect(dbc, madeBysql, "madeBy", intmadeBy, "made by");

    // Declare all Strings and objects as they should be if it is first rendering
    model.Product.StringData inputData = new model.Product.StringData(); // all properties empty string, good for first rendering
    model.Product.StringData errorMsgs = new model.Product.StringData(); // all properties empty string, good for first rendering

    if (request.getParameter("name") != null) { // this is postback

        intmadeBy = Integer.parseInt(request.getParameter("madeBy"));
        madeBySelect = MakeSelectTag.makeSelect(dbc, madeBysql, "madeBy", intmadeBy, "made by");
       
        // package up Customer String data
        inputData.name = request.getParameter("name");
        inputData.description = request.getParameter("description");
        inputData.dateMade = request.getParameter("dateMade");
        inputData.price = request.getParameter("price");

//the next 10 lines are to get the name of the user that was selected from the SELECT dropdown menu
        PreparedStatement stmt = null;
        ResultSet results = null;
        String getmaker = "SELECT username FROM customer WHERE customerID =" + intmadeBy;
        stmt = dbc.getConn().prepareStatement(getmaker);
        results = stmt.executeQuery();
        while (results.next()) {
            inputData.madeBy = (String) results.getObject("username");
        }
        

        if (inputData.name.length() < 1) {
            strnameError = "name is a required field";
        }
        if (inputData.description.length() < 1) {
            strdescriptionError = "description is a required field";
        }
        if (PurchaseView.isValidDate(inputData.dateMade) == false) {
            strdateMadeError = "please enter date as yyyy-mm-dd";
        }
        if (Integer.parseInt(request.getParameter("madeBy")) == 0) {
            strmadeByError = "made by is a required field";
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
<form action="insertOther.jsp" method="get" style = "text-align: center">

    name       <input type="text" name="name" size="20" style="border:1px solid black"        value="<%=inputData.name%>"/><%=strnameError%><br>
    description<input type="text" name="description" size="20" style="border:1px solid black" value="<%=inputData.description%>"/><%=strdescriptionError%><br>
    date made  <input type="text" name="dateMade" size="20" style="border:1px solid black"    value="<%=inputData.dateMade%>"/><%=strdateMadeError%><br>
    price      <input type="text" name="price" size="20" style="border:1px solid black"       value="<%=inputData.price%>"/><br>
    made by    <%=madeBySelect%><%=strmadeByError%><br>

    <input type="submit" value="insert"><br/>

    <%=errorMsgs.errorMsg%>

</form>




<jsp:include page="postContent.jsp" />