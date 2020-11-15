<%-- 
    Document   : IngresarIntervalo
    Created on : Nov 15, 2020, 6:46:12 AM
    Author     : potz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css?3.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/textstyle.css?3.1">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/select.css?3.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/button.css?3.0">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/searchbar.css">
        <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:400,500,700'>
        <title>Ingresar intervalo</title>
    </head>
    <body style="background: url('${pageContext.request.contextPath}/img/bgformulario.png') left ; background-attachment: fixed;">
        <%@include file="header.jsp" %>
        <form action="ObtenerReporte5" method="post" >
            <div class="caja">
                <h4 style="text-align: left;">Fecha inicio:</h4>
                <div class="inputAnimate">
                    <input type="date" id="start" name="fecha1"
                           min="1900-01-01" max="2022-12-31" required >
                </div>
                <h4 style="text-align: left;">Fecha final:</h4>
                <div class="inputAnimate">
                    <input type="date" id="start" name="fecha2"
                           min="1900-01-01" max="2022-12-31"  required >
                </div>
                <button class="draw" type="submit" name="gen">Generar</button>
                <c:if test="${no == 1}">
                <p id="error" style="color: red;">La fecha de inicio tiene que ser antes que la fecha final</p>
            </c:if>
            </div>
            


        </form>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script><script  src="${pageContext.request.contextPath}/js/text.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
    </body>
</html>
