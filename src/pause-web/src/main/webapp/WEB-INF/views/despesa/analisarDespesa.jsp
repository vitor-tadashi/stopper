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
			<div id="div-alert" style="display: none;">
    			<a href="#" class="close" onclick="hideDiv()" >×</a>
    			<span id="span-msg"></span>
  			</div>
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<div class="table-responsive" style="margin-top: 50px">
						<table class="table table-striped table-bordered" id="table-despesas">
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
											<tr id="despesa${despesa.id}">
												<td>${despesa.nomeFuncionario }</td>
												<td>${despesa.dataOcorrencia }</td>
												<td>${despesa.nomeTipoDespesa }</td>
												<td>${despesa.descricaoProjeto }</td>
												<td><fmt:formatNumber value="${despesa.valor }"
														type="currency" currencyCode="BRL"></fmt:formatNumber></td>
												<td><a href="#" onclick="abrirModalVisualizacaoDespesa(${despesa.id })">
													<i class="fa fa-search" aria-hidden="true"></i></a></td>
											</tr>
										</c:forEach>
									</c:when>
									<c:otherwise>
										<tr>
											<td colspan="6">
												Não há despesas para análise!
											<td>
										</tr>
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
										<label class="control-label" >Descrição</label>
										<div class="input-group" id="justificativaDespesaDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesa" disabled="disabled"
												class="form-control justificativaDespesa"
												name="justificativa" ></textarea>
										</div>
										<br/>
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<a class="btn btn-info" href="#" id="btnDownloadArquivo" style="text-align: center; width: 100%;"  target="_blank">Download arquivo</a>
										</div>
										<br/>
										<sec:authorize access="hasRole('GP_APROVAR_DESPESAS')">
											<div class="input-group" id="buttonSubmitGestorDiv"
												style="width: 100% !important">
												<button class="btn btn-success" id="aprovarDespesa" type="button">Aprovar</button>
												<button class="btn btn-danger" id="rejeitarDespesa"  type="button">Rejeitar</button>
											</div>
										</sec:authorize>
										<sec:authorize access="hasRole('FINANCEIRO_APROVAR_DESPESAS')">
											<div class="input-group" id="buttonSubmitFinanceiroDiv"
												style="width: 100% !important">
												<button class="btn btn-success" id="aprovarDespesa" type="button">Aprovar</button>
												<button class="btn btn-danger" id="rejeitarDespesa"  type="button">Rejeitar</button>
											</div>
										</sec:authorize>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal" id="justificativa-modal" role="dialog"
			tabindex="-1" aria-labelledby="justificativa-modal"
			aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title">Descrição</h4>
					</div>

					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-justificativa" name="form-justificativa"
							class="clear-form">
							<div class="row">
								<div class="panel">
									<!--Data Table-->
									<!--===================================================-->
									<div class="panel-body">
										<div id="div-alert-just">
    										<span id="span-msg-just"></span>
  										</div>

										<!--Modal body-->
										<div class="modal-body">
											<div class="input-group" id="justificativaRejectDiv"
												style="width: 100% !important">
												<textarea rows="6" id="justificativaReject"
													class="form-control justificativaDespesa"
													name="justificativaReject"></textarea>
											</div>
											</br>
											<sec:authorize access="hasRole('GP_APROVAR_DESPESAS')">
												<div class="input-group" id="buttonJustRejectGestor"
													style="width: 100% !important">
													<button class="btn btn-info" id="enviarJustificativaReject"
														type="button">Enviar</button>
												</div>
											</sec:authorize>
											<sec:authorize
												access="hasRole('FINANCEIRO_APROVAR_DESPESAS')">
												<div class="input-group" id="buttonJustRejectFin"
													style="width: 100% !important">
													<button class="btn btn-info" id="enviarJustificativaReject"
														type="button">Enviar</button>
												</div>
											</sec:authorize>
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