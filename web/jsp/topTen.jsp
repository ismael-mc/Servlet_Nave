<%-- 
    Document   : topTen
    Created on : 28-nov-2016, 10:55:08
    Author     : Ismael
--%>

<%@page import="services.MimcerUsersJpaController"%>
<%@page import="model.MimcerUsers"%>
<%@page import="javax.persistence.Persistence"%>
<%@page import="javax.persistence.EntityManagerFactory"%>
<%@page import="javax.persistence.EntityManager"%>
<%@page import="javax.persistence.TypedQuery"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Date"%>
<%@page import="model.MimcerGame"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ page import="javax.servlet.ServletContext" %>
<%@ page import="javax.servlet.http.Cookie" %>
<%
    String contextPath = request.getContextPath();
    Cookie[] cookies = request.getCookies();
    boolean autorizado = false;
    for (int i = 0; cookies != null && i < cookies.length; i++) {
        Cookie cookie = cookies[i];
        if ("cookie".equals(cookie.getName())) {
            autorizado = true;
            break;
        }
    }

    if (!autorizado) {
        response.sendRedirect(contextPath + "/index.jsp");
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%= contextPath%>/css/maqIndex.css">
        <title>Top 10</title>
        <script type="text/javascript">
            function menu() {
                document.dataform.action.value = 'menu'
                document.dataform.submit();
            }
        </script>
    </head>
    <body>
        <header>
            <h1>TOP 10 FREAKS</h1>
        </header>
        <br>
    <center>
        <table id="tabla">
            <tr><td bgcolor="#FFFFFF">ALIAS</td><td bgcolor="#FFFFFF">MATCHES</td></tr>
            <tr><td bgcolor="#FFFFFF"></td><td bgcolor="#FFFFFF"></td></tr>
        </table>
    </center>
    <br>
    <section>
        <form name="dataform" action="<%=contextPath%>/Servlet_Nave" method="post">
            <input type="hidden" name="action" value="">
            <input type="button" value="BACK MENU" onclick="menu();">
        </form>
    </section>
</body>
</html>
