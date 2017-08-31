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
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<div class="panel-heading">
					<div class="panel-title">
						<h4>Extrato de horas</h4>
					</div>
				</div>
				<form>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3">
								<label class="control-label">Nome do funcionário:</label> 
								<select class="selectpicker" data-live-search="true" data-width="100%" id="idFunc">
									<option value="">Selecione</option>
									<c:forEach items="${funcionarios}" var="funcionario">
										<option value="${funcionario.id}">${funcionario.nome}</option>
									</c:forEach>
								</select>
							</div>
							<div class="col-md-2">
								<label class="control-label">Período:</label> 
								<input id="de" type="date" onBlur="permitirData()" class="form-control" style="padding: 5px 12px"/>
							</div>
							<div class="col-md-2">
								<label class="control-label">&nbsp;</label> 
								<input id="ate" type="date" onBlur="permitirData()" class="form-control" style="padding: 5px 12px"/>
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label>
								<button type="button" onclick="gerarRelatorio()" class="btn-primary form-control">Gerar extrato</button>
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
		<script>
		function permitirData(){
			$("#ate").attr("min", $("#de").val());
			$("#de").attr("max", $("#ate").val());
		}
		
		function gerarRelatorio(){
			$.ajax({
				url: "relatorio/gerar-relatorio",
				type : 'POST',
				data : {'idFuncionario': $("#idFunc").val(),
						'ate' : $("#de").val(),
						'de' : $("#ate").val()},
				sucess: function(){
					alert("foi");
				}
			})
		}
		</script>
	</layout:put>

</layout:extends>