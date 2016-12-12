<%-- 
    Document   : error
    Created on : 08-dic-2016, 9:45:59
    Author     : Ismael
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%= contextPath%>/css/maqIndex.css">
        <title>Error Log in</title>
        <script type="text/javascript">

            function retry() {
                document.datalog.action.value = 'retry'
                document.datalog.submit();
            }
        </script>
    </head>
    <body>
        <header>
            <h1>ERROR LOGIN</h1>
        </header>
        <section>
            <%  String alias = request.getParameter("alias");%>
            <h1>Are you really <%=alias%> ?</h1>
            <form name="datalog" action="<%=contextPath%>/Servlet_Nave" method="post">
                <input type="hidden" name="action" value="">

                <input type="button" value="RETRY" onclick="retry();">
            </form>
        </section>
    </body>
</html>