<%@page import="model.StringData"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<jsp:include page="toHead.jsp" />
<jsp:include page="headToContent.jsp" />


<%

    StringData loggedOnCust = (StringData) session.getAttribute("cust"); // must type cast the object before use 

    if (loggedOnCust == null) { // means user is not logged in
        try {
            //out.println("you are not a member, away with you!!");
            response.sendRedirect("deny.jsp?denyMsg=You are not authorized to view the Members Only page.");
        } catch (Exception e) {
            out.println(" Exception was thrown: " + e.getMessage());
        }
    }

    if (loggedOnCust != null) { // means user is logged in
        try {
            out.println("YOU ARE A MEMBER");
            // response.sendRedirect("deny.jsp?denyMsg=You are not authorized to view the Members Only page.");
        } catch (Exception e) {
            out.println(" Exception was thrown: " + e.getMessage());
        }
    }

%>


<jsp:include page="postContent.jsp" />
