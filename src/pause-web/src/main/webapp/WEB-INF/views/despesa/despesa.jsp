<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"
	prefix="layout"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link
			href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>'
			rel="stylesheet">
		<style>
			input[type=date].form-control {
				line-height: 10px !important;;
			}
		</style>
	</layout:put>
	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Despesa</h1>
		</div>
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Despesas</li>
		</ol>
		<sec:authentication property="principal" var="user"/>
		<input type="hidden" id="funcionario" value="${user.funcionario.id }" />
		<div id="page-content">
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<span id="span-msg" ></span>
					<button id="addDespesa" class="btn btn-info" type="button" style="float: right" onclick="abrirModal();">Adicionar</button>					
				</div>
			</div>
		</div>
	</layout:put>
	<layout:put block="scripts">
		<div class="modal fade" id="add-despesa-modal" role="dialog"
			tabindex="-1" aria-labelledby="add-despesa-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
	
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title">Adicionar Despesa</h4>
					</div>
	
					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-despesa" name="form-despesa" class="clear-form">
							<div class="row">
								<div class="panel">
									<!--Data Table-->
									<!--===================================================-->
									<div class="panel-body">
										<label class="control-label" style="margin: 4px 0 0 0">*Data</label>
										<div class="input-daterange input-group" id="datepicker"
											style="width: 100% !important">
											<input type="date" id="dataDespesa"
												class="form-control dataDespesa" name="data"
												placeholder="dd/mm/aaaa" />
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Valor</label>
										<div class="input-group" id="valorDespesaDiv"
											style="width: 100% !important">
											<input type="text" id="valorDespesa" 
												class="form-control valorDespesa" name="valor"
												placeholder="XXXXX.XX" />
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Tipo de despesa</label>
										<div class="input-group" id="tipoDespesaDiv">
											<select class="selectpicker" data-live-search="true"
												id="select-tipo-despesa" name="tipoDespesa">
												<option value="">Selecione</option>
												<c:forEach items="${tipoDespesas }" var="tipoDespesa">
													<option value="${tipoDespesa.id }">${tipoDespesa.nome }</option>
												</c:forEach>
											</select>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Centro de custo</label>
										<div class="input-group" id="centroCustoDiv">
											<select class="selectpicker" data-live-search="true"
												id="select-centro-custo" name="centroCusto">
												<option value="">Selecione</option>
												<option value="12">Teste</option>
												<%-- 							<c:forEach items="${centrosCusto }" var="centroCusto"> --%>
												<%-- 								<option value="${centrosCusto.id }">${centrosCusto.nome }</option>
												<%-- 							</c:forEach> --%>
											</select>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Justificativa</label>
										<div class="input-group" id="justificativaDespesaDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesa"
												class="form-control justificativaDespesa"
												name="justificativa"
												placeholder="Justificativa da despesa"></textarea>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">Comprovante</label>
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<input id="comprovanteDespesa" type="file"
												class="form-control comprovanteDespesa"
												name="comprovante"
												placeholder="Comprovante da despesa" value="" />
										</div>
										<br />
										<div class="input-group" id="buttonSubmitDiv"
											style="width: 100% !important">
											<button id="submit-js" class="btn btn-info" type="button" onclick="submiterDespesa();">Salvar</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script src='<c:url value="js/custom/despesa.js"/>'></script>
	</layout:put>
</layout:extends>