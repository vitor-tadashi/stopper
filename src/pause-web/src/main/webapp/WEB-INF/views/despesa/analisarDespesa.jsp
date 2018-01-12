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
			<h1 class="page-header text-overflow">Análise de Despesas</h1>
		</div>
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Análise de Despesas</li>
		</ol>
		<sec:authentication property="principal" var="user" />
		<input type="hidden" id="funcionario" value="${user.funcionario.id }" />
		<div id="page-content">
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<span id="span-msg">${erroMsg}</span>
					<div class="table-responsive" style="margin-top: 50px">
						<table class="table table-striped table-bordered" id="id-despesas">
							<thead>
								<tr>
									<th class="text-center">Solicitante</th>
									<th class="text-center">Data Ocorrência</th>
									<th class="text-center">Tipo Despesa</th>
									<th class="text-center">Projeto</th>
									<th class="text-center">Valor</th>
									<th class="text-center">Detalhes</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<c:choose>
									<c:when test="${not empty despesas }">
										<c:forEach items="${despesas}" var="despesa">
											<tr>
												<td>${despesa.nomeFuncionario }</td>
												<td>${despesa.dataOcorrencia }</td>
												<td>${despesa.nomeTipoDespesa }</td>
												<td>${despesa.idProjeto}</td>
												<td><fmt:formatNumber value="${despesa.valor }"
														type="currency" currencyCode="BRL"></fmt:formatNumber></td>
												<td><a href="#" onclick="abrirModalVisualizacaoGestor(${despesa.id })">
													<i class="fa fa-search" aria-hidden="true"></i></a></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<td colspan="6">
											Não há despesas para análise!
										<td>
									</c:otherwise>
								</c:choose>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

	</layout:put>
	<layout:put block="scripts">
		<div class="modal fade" id="detalhe-despesa-modal" role="dialog"
			tabindex="-1" aria-labelledby="detalhe-despesa-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title">Detalhes</h4>
					</div>

					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-despesa" name="form-despesa"
							class="clear-form">
							<div class="row">
								<div class="panel">
									<!--Data Table-->
									<!--===================================================-->
									<div class="panel-body">
										<label class="control-label" >Solicitante</label>
										<div class="input-group" id="solicitanteDiv"
											style="width: 100% !important">
											<input type="text" id="solicitante"
												class="form-control solicitante" name="solicitante"
												disabled="disabled"/>
										</div>
										<label class="control-label" >Data Ocorrência</label>
										<div class="input-group" id="dataOcorrenciaDiv"
											style="width: 100% !important">
											<input type="text" id="dataOcorrencia"
												class="form-control dataOcorrencia" name="dataOcorrencia"
												disabled="disabled"/>
										</div>
										<label class="control-label" >Valor</label>
										<div class="input-group" id="valorDespesaDiv"
											style="width: 100% !important">
											<input type="text" id="valorDespesa"
												class="form-control valorDespesa" name="valor"
												disabled="disabled"/>
										</div>
										<label class="control-label" >Tipo
											de despesa</label>
										<div class="input-group" id="tipoDespesaDiv" style="width: 100% !important">
											<input type="text" id="tipoDespesa"
												class="form-control tipoDespesa" name="tipoDespesa" disabled="disabled"
												/>
										</div>
										<label class="control-label" >Projeto</label>
										<div class="input-group" id="centroCustoDiv" style="width: 100% !important">
											<input type="text" id="projeto"
												class="form-control projeto" name="projeto" disabled="disabled"
												/>
										</div>
										<label class="control-label" >Justificativa</label>
										<div class="input-group" id="justificativaDespesaDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesa" disabled="disabled"
												class="form-control justificativaDespesa"
												name="justificativa" ></textarea>
										</div>
										<br/>
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<button id="comprovanteDespesa"  style="width: 100% !important"
												class="btn btn-info" name="comprovanteDespesa" type="button"
												>Download Comprovante</button>
										</div>
										<br/>
										<div class="input-group" id="buttonSubmitDiv"
											style="width: 100% !important">
											<button class="btn btn-success" id="aprovarDespesa" type="button">Aprovar</button>
											<button class="btn btn-danger" id="rejeitarDespesa"  type="button">Rejeitar</button>
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
		<script src='<c:url value="/js/custom/despesa.js"/>'></script>
	</layout:put>
</layout:extends>