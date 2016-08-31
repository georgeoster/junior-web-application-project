<%@page import="model.Product.FinderOfProducts"%>
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

    String strproductId = request.getParameter("productId");
    String strname = "";
    String strdescription = "";
    String strdateMade = "";
    String strprice = "";
    String strmadeBy = "";

    String strnameError = "";
    String strdescriptionError = "";
    String strdateMadeError = "";
    String strmadeByError = "";

    String successOrFail = "";

    model.Product.StringData toUpdate = FinderOfProducts.find(dbc, strproductId);

    strname = toUpdate.name;
    strdescription = toUpdate.description;
    strdateMade = toUpdate.dateMade;
    strprice = toUpdate.price;
    strmadeBy = toUpdate.madeBy;

    if (request.getParameter("name") != null) {//postback

        strname = request.getParameter("name");
        strdescription = request.getParameter("description");
        strdateMade = request.getParameter("dateMade");
        strprice = request.getParameter("price");
        strmadeBy = request.getParameter("madeBy");

        model.Product.StringData errorChecking = new model.Product.StringData();
        model.Product.StringData changes = new model.Product.StringData();

        changes.productID = strproductId;
        changes.name = strname;
        changes.description = strdescription;
        changes.dateMade = strdateMade;
        changes.price = strprice;
        changes.madeBy = strmadeBy;

        errorChecking = DbMods.validate(changes);

        if (errorChecking.name.length() > 0) {
            strnameError = "name is a required field";
        }
        if (errorChecking.description.length() > 0) {
            strdescriptionError = "description is a required field";
        }
        if (errorChecking.dateMade.length() > 0) {
            strdateMadeError = "date made must be in yyyy-MM-dd format";
        }
        if (errorChecking.madeBy.length() > 0) {
            strmadeByError = "made by is a required field";
        }

        if (errorChecking.getCharacterCount() == 0) {
            successOrFail = "Record Successfully Updated";
            DbMods.update(changes, dbc);
        }

    } // end postback
    dbc.close();
%>
<br>
<form action="updateOther.jsp" method="get" style = "text-align: center">
    product ID <input type="text" name="productId" size="4" style="border:1px solid black"    value="<%=strproductId%>"/><br>
    name       <input type="text" name="name" size="20" style="border:1px solid black"        value="<%=strname%>"/><%=strnameError%><br>
    description<input type="text" name="description" size="20" style="border:1px solid black" value="<%=strdescription%>"/><%=strdescriptionError%><br>
    date made  <input type="text" name="dateMade" size="20" style="border:1px solid black"    value="<%=strdateMade%>"/><%=strdateMadeError%><br>
    price      <input type="text" name="price" size="20" style="border:1px solid black"       value="<%=strprice%>"/><br>
    made by    <input type="text" name="madeBy" size="20" style="border:1px solid black"       value="<%=strmadeBy%>"/><%=strmadeByError%><br>
    <%=successOrFail%>
    <input type="submit" value="update"><br/>


</form>




<jsp:include page="postContent.jsp" />