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
		<link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
		
	</layout:put>

	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Importação de apontamentos eletrônicos</h1>
		</div>
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<form id="formValidar" enctype="multipart/form-data" method="post">
					<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3">
								<div class="form-group">
									<label class="control-label">Empresa:</label> <select
										class="selectpicker" data-live-search="true" data-width="100%">
										<option value="">Selecione</option>
										<c:forEach items="${empresas}" var="empresa">
											<option value="${empresa.id }">${empresa.nome }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Arquivo:</label>
								<div class="upload-file">
									<input type="file" onchange="importarArquivo();" name="file" id="upload-arquivo"  class="upload-demo" />
								</div>
							</div>
						</div>
						<div class="bootstrap-table">
							<div class="fixed-table-container" style="padding-bottom: 0px;">
								<div class="fixed-table-body">
									<table id="demo-dt-basic" aria-describedby="demo-dt-basic_info" style="width: 100%"
										class="table table-striped table-bordered dataTable no-footer dtr-inline">
										<thead>
											<tr class="">
												<th class="sorting_asc text-center" tabindex="0"
													aria-controls="demo-dt-basic" aria-sort="ascending"
													aria-label="PIS: activate to sort column descending">
													PIS</th>
												<th class="sorting_asc text-center" tabindex="0"
													aria-controls="demo-dt-basic" aria-sort="ascending"
													aria-label="Funcionário: activate to sort column descending">
													Funcionário</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="1 - Entrada: activate to sort column ascending">
													Entrada 1</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="1 - Saída: activate to sort column ascending">
													Saída 1</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="2 - Entrada: activate to sort column ascending">
													Entrada 2</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="2 - Saída: activate to sort column ascending">
													Saída 2</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="3 - Entrada: activate to sort column ascending">
													Entrada 3</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="3 - Saída: activate to sort column ascending">
													Saída 3</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="4 - Entrada: activate to sort column ascending">
													Entrada 4</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="4 - Saída: activate to sort column ascending">
													Saída 4</th>
											</tr>
										</thead>
										<tbody id="list">
											
										</tbody>
									</table>
								</div>
							</div>
						</div>
						<div class="pull-right">
							<button type="submit" class="btn btn-success">Salvar</button>
							<button type="reset" class="btn btn-danger">Cancelar</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
		<script>
			function escolheu(){
				$('.upload-demo').change(function()	{
					var filename = $(this).val().split('\\').pop();
					$(this).parent().find('span').attr('data-title',filename);
					$(this).parent().find('label').attr('data-title','Selecione');
					$(this).parent().find('label').addClass('selected');
				});
			}
			
			$(function () {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$(document).ajaxSend(function(e, xhr, options) {
			    	xhr.setRequestHeader(header, token);
				});
			});
			
			function importarArquivo() {
				var paperElement = document.getElementById("upload-arquivo");

				if ($(paperElement).val()) {
					var form = document.getElementById("formValidar");
					var formData = new FormData(form);
					$.ajax({
						url : 'importar-arquivo',
						enctype : 'multipart/form-data',
						type : 'POST',
						data : formData,
						processData : false,
						contentType : false,
						cache : false,
						success : function(data) {
							$(data).each(function(index, value) {
								var campos = "<tr role='row' class='odd text-center'>"+
												"<td id='pis"+index+"'></td>" +
												"<td id='funcionario"+index+"'></td>" +
												"<td id='hora"+index+"'></td>" +
												"<td id='data"+index+"'></td>" +
												"<td></td>" +
												"<td></td>" +
												"<td></td>" +
												"<td></td>" +
												"<td></td>" +
												"<td></td>";

								$("#list").append(campos);


								$('#pis'+index).text(value.pis);
								$('#funcionario'+index).text(value.funcionario.nome);
								$('#hora'+index).text(value.hora);
								$('#data'+index).text(value.dataImportacao);

							});
						},
					});
				}
			};
		</script>
	</layout:put>

</layout:extends>