<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html lang="pt-br">
	<head>
	</head>
	<body>
		<div id="container" class="cls-container">
			<div class="cls-content">
			    <h1 class="error-code text-info">403</h1>
			    <p class="text-main text-semibold text-lg">Acesso não permitido.</p>
			    <div class="pad-btm text-muted">
					Você não tem permissão para acessar essa página.
			    </div>
			    <hr class="new-section-sm bord-no">
			    <div class="pad-top"><a class="btn-link" href='<c:url value="https://login.microsoftonline.com"/>'>Ir para Login Microsoft</a></div>
			</div>
		</div>
	</body>
</html>