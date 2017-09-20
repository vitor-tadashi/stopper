<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
		<style>
		input[type=date].form-control{
		    line-height: 10px !important;;
		}
		</style>
	</layout:put>

	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Relatórios</h1>
		</div>
		
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Relatório</li>
		</ol>
		
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<form>
					<div class="panel-body">
						<div class="row">
							<div style="margin-top: -10px"> 
								<h4 style="margin-left: 8px">Extrato de horas</h4>
							</div>
							<div class="col-sm-3">
								<label class="control-label">Nome do funcionário:</label> 
								<select class="selectpicker" data-live-search="true" data-width="100%" id="idFunc">
									<option value="">Selecione</option>
									<c:forEach items="${funcionarios}" var="funcionario">
										<option value="${funcionario.id}">${funcionario.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-sm-4">
								<label class="control-label">Período</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="date" class="form-control" id="dtDe" onBlur="permitirData()" placeholder="dd/mm/yyyy" />
									<span class="input-group-addon">até</span>
									<input type="date" class="form-control" id="dtAte" onBlur="permitirData()" placeholder="dd/mm/yyyy"/>
								</div>
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label>
								<button type="button" onclick="gerarRelatorio()" class="btn-primary form-control">Gerar extrato</button>
		                        <a href="${url }" target="_blank" id="download" class="hide"></a>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script src='<c:url value="/js/custom/relatorio.js"/>'></script>
	</layout:put>

</layout:extends>