<%-- 
    Document   : Register
    Created on : 23-nov-2016, 11:21:46
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
        <title>Hangar Register</title>
        <script type="text/javascript">
            function finished() {
                name = document.getElementById("name").value;
                alias = document.getElementById("alias").value;
                password = document.getElementById("password").value;
                if (name.length == 0 || alias.length == 0 || password.length == 0) {
                    alert("The fields are empty");
                } else {
                    document.datareg.action.value = 'register'
                    document.datareg.submit();
                }
            }

        </script>
    </head>
    <body>
        <header>
            <h1>SIGN UP</h1>
        </header>
        <section>
            <form name="datareg" action="<%=contextPath%>/Servlet_Nave" method="post">
                <input type="hidden" name="action" value="register">

                <label class="label">NAME</label>
                <input type="text" class="text"  name="name" id="name">
                <br>

                <label class="label">ALIAS</label>
                <input type="text" class="text"  name="alias" id="alias">
                <br>

                <label class="label">PASSWORD</label>
                <input type="password" class="password" name="password" id="password">
                <br>
                <br>
                <input type="button" value="FINISHED" onclick="finished();">
                <a href="index.jsp">BACK</a>
            </form>
        </section>
    </body>
</html>
