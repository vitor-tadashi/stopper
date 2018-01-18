<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">
	<layout:put block="contents">
		<div id="container" class="cls-container">
			<div class="cls-content">
			    <h1 class="error-code text-info">500</h1>
			    <p class="text-main text-semibold text-lg">Erro interno do servidor.</p>
			    <div class="pad-btm text-muted">
					Algo deu errado e o servidor não conseguiu processar seu pedido.
			    </div>
			    <hr class="new-section-sm bord-no">
			    <div class="pad-top"><a class="btn-link" href='<c:url value="/"/>'>Voltar para a página inicial.</a></div>
			</div>
		</div>
	</layout:put>
</layout:extends>