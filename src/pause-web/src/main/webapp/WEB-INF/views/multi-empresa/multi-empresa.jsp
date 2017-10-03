<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	<title>Pause</title>

	<!--STYLESHEET-->
    <!--=================================================-->
    <!--Open Sans Font [ OPTIONAL ]-->
    <link href='https://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700' rel='stylesheet' type='text/css'>

    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href='<c:url value="/css/bootstrap.min.css"/>' rel="stylesheet">

    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href='<c:url value="/css/nifty.min.css"/>' rel="stylesheet">
    
    <link href='<c:url value="/plugins/ionicons/css/ionicons.min.css"/>' rel="stylesheet">
    
    
    <script src='<c:url value="/js/jquery.min.js"/>'></script>
    <script src='<c:url value="/js/bootstrap.min.js"/>'></script>
    <script src='<c:url value="/js/nifty.min.js"/>'></script>
    
</head>
<body style="background: white;">
<sec:authentication property="principal" var="user"/>
	<div class="cls-container" style="background: white;">
		<div class="cls-content">
		<div class="panel-body">
		<div class="row">
		
           	<h3>Olá, ${user.funcionario.nome }!</h3>
           	<h3>Qual empresa deseja acessar hoje?</h3>
            <form action='<c:url value="/selecionaMultiEmpresa/2"/>' method="post" id="bv-form">
           		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
            	<div class="panel col-sm-offset-2 col-sm-3">
            	
              		<button type="image" class="text-center" src='<c:url value="/img/verity.png"/>' style="width:300px; height: 120px;"></button>
				</div>
			 </form>
			 <form action='<c:url value="/selecionaMultiEmpresa/65"/>' method="post" id="bv-form">
			 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="panel col-sm-3 col-sm-offset-2">
					<button type="image" class="text-center" src='<c:url value="/img/qa360.png"/>' style="width: 300px;  height: 120px;"></button>
				</div>
            </form>
		</div>
		</div>
	</div>
	</div>
	<!--===================================================-->
	<!-- END OF CONTAINER -->
</body>
</html>