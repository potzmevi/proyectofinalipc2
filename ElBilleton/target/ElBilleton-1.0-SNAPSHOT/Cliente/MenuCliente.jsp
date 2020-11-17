<%-- 
    Document   : MenuCliente
    Created on : Nov 12, 2020, 12:58:18 AM
    Author     : potz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
   <head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <script src="https://kit.fontawesome.com/64d58efce2.js" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/pacienteindexstyle.css?4.1">
    <title>Menu Cliente</title>
</head>

<body style="background: url('${pageContext.request.contextPath}/img/cliente.png') no-repeat center ; background-attachment: fixed;">
    <%@include file="header.jsp" %>
    <main>
        <section>
            <div class="overlay">
                <div class="contenedor">
                    <h1 class="texto" >Bienvenido ${Cliente.getNombre()}</h1>
                    </div>
                 </div>
        </section>
    </main>
            
</body>

</html>