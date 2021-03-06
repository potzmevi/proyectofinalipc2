<%-- 
    Document   : VerCuentasAsociadas
    Created on : Nov 16, 2020, 4:17:52 PM
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabla.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/searchbar.css?3.0">
        <link rel='stylesheet' href='https://fonts.googleapis.com/css?family=Roboto:400,500,700'>
        <title>Ver cuentas</title>
    </head>
    <body style="background: url('${pageContext.request.contextPath}/img/fondo.png') center ; background-attachment: fixed;">
        <%@include file="header.jsp" %>
        <form action="BuscarCuenta" method="post" >
            <div class="cajatabla">
                <h2 >Cuentas asociadas de ${Cliente.getNombre()}:</h2>
                <table class="darkTable">
                    <tr>
                        <th class="text-center">Codigo</th>
                        <th class="text-center">Fecha Creacion</th>
                        <th class="text-center">Monto</th>
                        <th class="text-center">Codigo Cliente</th>
                    </tr>
                    <c:forEach items="${CuentasAso}" var="cliente">
                        <tr>
                            <td class="text-center">${cliente.getCodigo()}</td>
                            <td class="text-center">${cliente.getFechaCreacion()}</td>
                            <td class="text-center">${cliente.getMonto()}</td>
                            <td class="text-center">${cliente.getCliente_codigo()}</td>
                            <td>
                                <a class="btn solid" href="${pageContext.request.contextPath}/SeleccionarCuentaAso?cuenta=${cliente.getCodigo()}&&cliente=${cliente.getCliente_codigo()}">Transaferir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <h2 >Cuentas de ${Cliente.getNombre()}:</h2>
                <table class="darkTable">
                    <tr>
                        <th class="text-center">Codigo</th>
                        <th class="text-center">Fecha Creacion</th>
                        <th class="text-center">Monto</th>
                        <th class="text-center">Codigo Cliente</th>
                    </tr>
                    <c:forEach items="${Cuentas}" var="cliente">
                        <tr>
                            <td class="text-center">${cliente.getCodigo()}</td>
                            <td class="text-center">${cliente.getFechaCreacion()}</td>
                            <td class="text-center">${cliente.getMonto()}</td>
                            <td class="text-center">${cliente.getCliente_codigo()}</td>
                            <td>
                                <a class="btn solid" href="${pageContext.request.contextPath}/SeleccionarCuentaAso?cuenta=${cliente.getCodigo()}&&cliente=${cliente.getCliente_codigo()}">Transaferir</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </div>




        </form>
        <script src='https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script><script  src="${pageContext.request.contextPath}/js/text.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/select.js"></script>
    </body>
</html>