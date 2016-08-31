<%@page import="view.ProductView"%>
<%@page import="view.WebUserView"%>
<%@page import="dbUtils.DbConn"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />

<center>
    <%
        DbConn dbc = new DbConn();
        String msg = dbc.getErr(); // returns "" if connection is good, else error msg.
        String potentialError = "";
        if (msg.length() == 0) { // got open connection
            String ID = request.getParameter("productID");
            if (ID != null && ID.length() != 0) {
                int i = model.Product.DbMods.delete(dbc, ID);
                if (i == -1) {
                    potentialError = "product # " + ID + " could not be deleted";
                }
            }
            msg = ProductView.listAllProducts("table", dbc);
        }

        dbc.close();
    %>

    <a href ="./insertOther.jsp">insert product</a><br><br>
    <%=potentialError%><br>
    <% out.print(msg);%>
</center>

<div style ="visibility:hidden">
    <form name="deleteForm" action="other.jsp" method="get">
        <input type="text" name="productID">
        <input type="submit"/>
    </form> 
</div>

<script language="Javascript" type="text/javascript">
    function deleteRow(ID) {
        if (confirm("Do you really want to delete product #" + ID + "?")) {
            document.deleteForm.productID.value = ID;
            document.deleteForm.submit();
        }
    }
</script>

<jsp:include page="postContent.jsp" />