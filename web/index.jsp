<%-- 
    Document   : index
    Created on : 21-nov-2016, 19:20:07
    Author     : Ismael
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- 
1. Sistema de enregistrament básic (usuari i contrasenya, cookies)
2. Registre de partides jugades (data inici, data fi de cada partida).
3. Página de llistat de jugadors amb més partides (top 10 viciats)
4. Página de jugadors online (comprovar la data inici) -->

<!DOCTYPE html>
<!DOCTYPE html>
<html>
    <head>
        <%
            String contextPath = request.getContextPath();
        %>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="<%= contextPath%>/css/maqIndex.css">
        <title>Hangar Login</title>
        <script type="text/javascript">

            function signUp() {
                document.dataform.action.value = 'signup'
                document.dataform.submit();
            }

            function login() {
                alias = document.getElementById("alias").value;
                password = document.getElementById("password").value;
                if (alias.length == 0 || password.length == 0) {
                    alert("The fields are empty");
                } else {
                    document.datacookie.action.value = 'login';
                    document.datacookie.submit();
                }
            }

        </script>
    </head>
    <body background="imagenes/fondo.png">
        <header>
            <h1>WELCOME TO THE HANGAR</h1>
        </header>
        <section>
            <form name="datacookie" action="<%=contextPath%>/Servlet_Cookie" method="post">
                <input type="hidden" name="action" value="">

                <label class="label">ALIAS</label>
                <input type="text" class="text" id="alias" name="alias">
                <br>
                <label class="label">PASSWORD</label>
                <input type="password" class="password" id="password" name="password">
                <br>
                <br>
                <input type="button" value="LOG IN" onclick="login();">
            </form>
            <br>
            <form name="dataform" action="<%=contextPath%>/Servlet_Nave" method="post">
                <input type="hidden" name="action" value="">
                
                <input type="button" value="SIGN UP" onclick="signUp();">
            </form>
        </section>
    </body>
</html>
