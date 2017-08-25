<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
	
	</layout:put>
	<layout:put block="contents">
		<!--Page Title-->
		<div id="page-title">
			<h1 class="page-header text-overflow">Erro 404</h1>
		</div>
		<!--End page title-->
	</layout:put>
	<layout:put block="scripts">
		
	</layout:put>
</layout:extends>