<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
    
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <link href='<c:url value="/plugins/bootstrap-validator/bootstrapValidator.min.css"/>' rel="stylesheet">
    
    <link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
    
    <script src='<c:url value="/js/jquery.min.js"/>'></script>
    <script src='<c:url value="/js/bootstrap.min.js"/>'></script>
    <script src='<c:url value="plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
    <script src='<c:url value="/js/nifty.min.js"/>'></script>
    
    <!--Bootstrap Validator [ OPTIONAL ]-->
    <script src='<c:url value="/plugins/bootstrap-validator/bootstrapValidator.min.js"/>'></script>
    

</head>
<body>
	<div class="cls-container">
		<!-- LOGIN FORM -->
		<!--===================================================-->
		<div class="cls-content">
		    <div class="cls-content-sm panel">
	            <div class="pad-btm">
	            	<img src='<c:url value="/img/logo.jpg"/>' style="width: 250px;" alt="" class="">
	            </div>
	            <form action='<c:url value="/selecionaMultiEmpresa"/>' method="post" id="bv-form">
					<div class="panel">
						<div class="panel-body">
							<div class="form-group text-left">
								<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								<label class="control-label">Selecione a empresa:</label>
								<select class="selectpicker" name="idEmpresaSessao" data-live-search="true" data-width="100%" id="idEmpresa">
									<c:forEach items="${empresas}" var="empresa">
										<option value="${empresa.id}">${empresa.nomeFantasia}</option>
									</c:forEach>
								</select>
							</div>
							<div class="pad-top">
                               	<input type="submit"
                                   	class="btn btn-block btn-primary btn-default" value="Acessar">
                           	</div>
						</div>
					</div>
	            </form>
		    </div>
		</div>
		<!--===================================================-->
	</div>
	<!--===================================================-->
	<!-- END OF CONTAINER -->
</body>
</html>