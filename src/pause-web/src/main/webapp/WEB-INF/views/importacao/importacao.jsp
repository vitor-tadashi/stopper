

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"
	prefix="layout"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link href='<c:url value="/css/custom/upload-file.css"/>'
			rel="stylesheet">
	</layout:put>

	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Importação de apontamentos
				eletrônicos</h1>
		</div>
		
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Importar</li>
		</ol>
		
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<form:form id="formValidar" action="importacao/salvar" method="post">
					<div id="textDiv" class=""></div>
					<c:if test="${not empty log}">
						<div id="msg-sucesso" class="alert alert-success msg-margin">
							<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
							<p>${log }</p>
						</div>
					</c:if>
					<input type="hidden" id="token" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
					<div class="panel-body">
						<div class="row">
						
						
							<div class="col-sm-2">
								<label class="control-label">Data importação</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="date" name="dataImportacao" id="dataImportacao" class="form-control periodo" name="periodo" placeholder="dd/mm/aaaa" data-bv-field="periodo">
								</div>
							</div>
						
							<div class="form-group col-md-4">
							<label class="control-label" style="margin-bottom: 11px;" >Arquivo:</label>
								<div class="upload-file">
								
									<input type="file" accept=".txt"
										name="file" id="upload-arquivo" class="upload-demo" /> 
									<label data-title="Selecione" for="upload-arquivo"> 
									<span id="caminho"></span>
									</label>
								</div>
							</div>
							
							<div class="col-sm-1" style="margin-top: 26px;">
								<input type="button" class="btn btn-info" value="Importar" onclick="importarArquivo()"/>
							</div>
						</div>
						<div id="tbHoras" class="bootstrap-table hide">
							<div class="fixed-table-container" style="padding-bottom: 0px;">
								<div class="fixed-table-body">
									<table id="demo-dt-basic" aria-describedby="demo-dt-basic_info"
										style="width: 100%"
										class="table table-striped table-bordered dataTable no-footer dtr-inline">
										<thead>
											<tr class="">
												<th class="text-center">PIS</th>
												<th class="text-center">Funcionário</th>
												<th class="text-center">Data</th>
												<th class="text-center">Entrada 1</th>
												<th class="text-center">Saída 1</th>
												<th class="text-center">Entrada 2</th>
												<th class="text-center">Saída 2</th>
												<th class="text-center">Entrada 3</th>
												<th class="text-center">Saída 3</th>
												<th class="text-center">Entrada 4</th>
												<th class="text-center">Saída 4</th>
											</tr>
										</thead>
										<tbody id="list">
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div id="botoes" class="pull-right hide">
							<button type="reset" onClick="cancelar()" class="btn btn-danger">Cancelar</button>
							<button id="submitbutton" type="submit" class="btn btn-success">Salvar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>

		<div id="modal-confirmacao" class="modal fade">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title text-center" id="mySmallModalLabel">Alerta!</h4>
					</div>
					<div class="modal-body">
						<div class="text-center">
							<span>Este arquivo já foi importado. Deseja substituí-lo?</span>
							<br> <br>
						</div>
						<div class="text-right">
							<button onClick="cancelar()" class="btn btn-danger" type="button">Não</button>
							<button onClick="exibirImportacao(true)" type="button"
								class="btn btn-success">Sim</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="modal-loader" class="modal fade" data-backdrop="static" data-keyboard="false">
			<div class="modal-dialog">
				<div style="text-align: center; margin-top:12%">
					<i class="fa fa-spinner fa-pulse fa-5x fa-fw" style="color: white"></i> 
					<br/>
					<br/>
					<span id="carregar-mensagem" class="hide"><font size="4" color="white">Aguarde enquanto carregamos as informações. . .</font></span>
					<span id="salvar-mensagem" class="hide"><font size="4" color="white">Aguarde enquanto salvamos as informações. . .</font></span>
				</div>
			</div>
		</div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="/js/custom/upload-file.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script src='<c:url value="/js/custom/importacao.js"/>'></script>
	</layout:put>
</layout:extends>