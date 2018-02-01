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
			<h1 class="page-header text-overflow">Despesas</h1>
		</div>
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Despesas</li>
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
<!-- 					<div class="label-message" role="alert"> -->
<!-- 						<span id="span-msg"></span> -->
<!-- 					</div> -->
					<button id="addDespesa" class="btn btn-info" type="button"
						style="float: right" onclick="abrirModal();">Adicionar</button>
					<div class="table-responsive" style="margin-top: 50px">
						<table class="table table-striped table-bordered" id="table-despesas">
							<thead>
								<tr>
									<th class="text-center">Data Ocorrência</th>
									<th class="text-center">Despesa</th>
									<th class="text-center">Projeto</th>
									<th class="text-center">Valor</th>
									<th class="text-center">Status</th>
									<th class="text-center">Editar</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<c:forEach items="${despesasFuncionario }" var="despesa">
									<tr id="trDespesa${despesa.id }">
										<td id="tdDataOcorrencia${despesa.id }">${despesa.dataOcorrencia }</td>
										<td id="tdNomeTipoDespesa${despesa.id }">${despesa.nomeTipoDespesa }</td>
										<td id="tdDescProjeto${despesa.id }">${despesa.descricaoProjeto }</td>
										<td id="tdValor${despesa.id }"><fmt:formatNumber value="${despesa.valor }"
												type="currency" currencyCode="BRL"></fmt:formatNumber></td>
												
										<c:choose>
  											<c:when test="${despesa.idStatus eq 1}">
  												<td id="tdNomeStatus${despesa.id }"><span class="label-status label-status-analise"> ${despesa.nomeStatus }</span></td>
  											</c:when>
  											<c:when test="${despesa.idStatus eq 2}">
												<td id="tdNomeStatus${despesa.id }"><span class="label-status label-status-aprovado"> ${despesa.nomeStatus }</span></td>
											</c:when>
  											<c:otherwise>
  												<td id="tdNomeStatus${despesa.id }"><span class="label-status label-status-reprovado"> ${despesa.nomeStatus }</span></td>
  											</c:otherwise>
										</c:choose>		
										
										<c:choose>
  											<c:when test="${despesa.idGpAprovador eq 0 || despesa.idGpAprovador eq null}">
  												<td><a href="#" onclick="abrirModalEdicaoSolicitante(${despesa.id }, 1)">
													<i class="fa fa-pencil-square-o" aria-hidden="true"></i></a></td>
  											</c:when>
  											<c:otherwise>
  												<td><a href="#" onclick="abrirModalVisualizacaoSolicitante(${despesa.id })">
													<i class="fa fa-search" aria-hidden="true"></i></a></td>
  											</c:otherwise>
										</c:choose>
								</c:forEach>
							</tbody>
						</table>
					</div> 
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
						<h4 class="modal-title" id="modal-title"></h4>
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
										
										<input type="text" id="id" name="id" hidden="true"/>
										<input type="text" id="caminhoComprovante" name="caminhoComprovante" hidden="true"/>
									
										<label class="control-label" style="margin: 4px 0 0 0">*Data Ocorrência</label>
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
												data-thousands="" data-decimal=","
												placeholder="XXXXX.XX" />
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Tipo
											de despesa</label>
										<div class="input-group" id="tipoDespesaDiv">
											<select class="selectpicker" data-live-search="true"
												id="select-tipo-despesa" name="tipoDespesa">
												<option value="">Selecione</option>
												<c:forEach items="${tipoDespesas }" var="tipoDespesa">
													<option value="${tipoDespesa.id }">${tipoDespesa.nome }</option>
												</c:forEach>
											</select>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Projeto</label>
										<div class="input-group" id="centroCustoDiv">
											<select class="selectpicker" data-live-search="true"
												id="select-centro-custo" name="centroCusto">
												<option value="">Selecione</option>
												<c:forEach items="${projetos }" var="projeto">
													<option value="${projeto.id }">${projeto.nome }</option>
												</c:forEach>
											</select>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">*Descrição</label>
										<div class="input-group" id="justificativaDespesaDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesa"
												class="form-control justificativaDespesa"
												name="justificativa" placeholder="Descrição da despesa"></textarea>
										</div>
										<br />
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<a class="btn btn-info" href="#" id="btnDownloadArquivo" style="text-align: center; width: 100%;"  target="_blank">Download arquivo</a>
										</div>
										<br /> <label class="control-label" style="margin: 4px 0 0 0">Comprovante</label>
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<input id="comprovanteDespesa" type="file"
												class="form-control comprovanteDespesa" name="comprovante"
												placeholder="Comprovante da despesa" value="" />
										</div>
										<br />
										<div class="input-group" id="buttonSubmitDiv"
											style="width: 100% !important">
											<button id="submit-js" class="btn btn-info" type="button"
												onclick="submeterDespesa();">Salvar</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<div class="modal fade" id="exibe-despesa-modal" role="dialog"
			tabindex="-1" aria-labelledby="exibe-despesa-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">

					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title">Exibe despesa</h4>
					</div>

					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-despesa" name="form-despesa"
							class="clear-form" >
							<div class="row">
								<div class="panel">
									<!--Data Table-->
									<!--===================================================-->
									<div class="panel-body" >
										<label class="control-label" style="margin: 2px 0 0 0">Status</label>
										<div class="input-group" id="dataOcorrenciaExibDiv"
											style="width: 100% !important" >
											<span id="statusExib"></span>
										</div>
										<br/> 
										<label class="control-label" style="margin: 2px 0 0 0">Data Ocorrência</label>
										<div class="input-group" id="dataOcorrenciaExibDiv"
											style="width: 100% !important">
											<input type="text" id="dataOcorrenciaExib" class="form-control" name="dataOcorrenciaExib" readonly/>
										</div>
										<br/> 
										<label class="control-label" style="margin: 2px 0 0 0">Valor</label>
										<div class="input-group" id="valorDespesaExibDiv"
											style="width: 100% !important">
											<input type="text" id="valorDespesaExib" class="form-control" name="valorDespesaExib" readonly/>
										</div>
										<br/> 
										<label class="control-label" style="margin: 2px 0 0 0">Tipo de despesa</label>
										<div class="input-group" id="TipoDeDespesaExibDiv"
											style="width: 100% !important">
											<input type="text" id="TipoDespesaExib" class="form-control" name="TipoDespesaExib" readonly/>
										</div>
										<br/> 
										<label class="control-label" style="margin: 2px 0 0 0">Projeto</label>
										<div class="input-group" id="projetoExibDiv"
											style="width: 100% !important">
											<input type="text" id="projetoExib" class="form-control" name="projetoExib" readonly/>
										</div>
										<br/> 
										<label class="control-label" style="margin: 2px 0 0 0">Descrição</label>
										<div class="input-group" id="justificativaDespesaExibDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesaExib" class="form-control" name="justificativaDespesaExib" readonly></textarea>
										</div>
										<br/> 
										<div id="div-just-reject" style="display: none;">
											<label class="control-label" style="margin: 2px 0 0 0">Justificativa rejeição</label>
											<div class="input-group" id="justificativaRejecExibDiv"
												style="width: 100% !important">
												<textarea id="justificativaRejecExib" class="form-control" name="justificativaRejecExib" readonly></textarea>
											</div>
										</div>
										<br/>
										<div class="input-group" id="comprovanteDespesaDivExib"
											style="width: 100% !important">
											<a class="btn btn-info" href="#" id="btnDownloadArquivoExib" style="text-align: center; width: 100%;"  target="_blank">Download arquivo</a>
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
		<script src='<c:url value="/js/jquery.maskMoney.js"/>'></script>
	</layout:put>
</layout:extends>