<%-- 
    Document   : IngresarDeposito
    Created on : Nov 17, 2020, 6:35:09 AM
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
        <title>Ingresar deposito</title>
    </head>
    <body style="background: url('${pageContext.request.contextPath}/img/bgformulario.png') left ; background-attachment: fixed;">
        <%@include file="header.jsp" %>
        <form action="RealizarDeposito" method="post" >
            <div class="caja">
                <h2 >Cuenta que recibira el deposito:</h2>
                <div class="inputAnimate">
                    <input type="number" value="${cuenta.getCodigo()}" min="1" name="cuenta" readonly>
                </div> 
                <h4 >Propietario:</h4>
                <div class="inputAnimate">
                    <input type="text"  value="${cliente.getNombre()}" name="cliente" readonly>
                </div>
                <h4>Monto a depositar:</h4>
                <div class="inputAnimate">
                    <input type="number"  min="0"  step="0.1" name="monto">
                </div>
                <button class="draw" type="submit" name="gen">Depositar</button>
            </div>           
        </form>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script><script  src="${pageContext.request.contextPath}/js/text.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
    </body>
</html>
