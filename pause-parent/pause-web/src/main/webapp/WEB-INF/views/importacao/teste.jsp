<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
		<style>
	.upload-file {
  position: relative;
  height: 20px;
  padding: 4px 6px;
  line-height: 20px;
}
.upload-file input[type="file"] {
  position: absolute;
  opacity: 0;
}
.upload-file label {
  display: block;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  border: 1px solid #ccc;
  max-height: 28px;
  box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -moz-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  -webkit-box-shadow: inset 0 1px 1px rgba(0, 0, 0, 0.075);
  border-radius: 4px;
  -moz-border-radius: 4px;
  -webkit-border-radius: 4px;
  transition: all 0.2s linear;
  -webkit-transition: all 0.2s linear;
  -moz-transition: all 0.2s linear;
  -ms-transition: all 0.2s linear;
  -o-transition: all 0.2s linear;
}
.upload-file label:before {
  display: inline-block;
  content: attr(data-title);
  position: absolute;
  right: 0;
  top: 0;
  bottom: 0;
  padding: 0 8px;
  line-height: 26px;
  text-align: center;
  border-left: 1px solid #ccc;
  border-radius: 0 4px 4px 0;
  -moz-border-radius: 0 4px 4px 0;
  -webkit-border-radius: 0 4px 4px 0;
  background-color: #fff;
}
.upload-file label [class*="icon-"] {
  display: inline-block;
  position: absolute;
  left: 0;
  top: 0;
  bottom: 0;
  text-align: center;
  border-radius: 4px 0 0 4px;
  -moz-border-radius: 4px 0 0 4px;
  -webkit-border-radius: 4px 0 0 4px;
  padding: 5px;
  line-height: 13px;
  color: #fff;
  width: auto;
}
.upload-file label span {
  display: inline-block;
  height: 26px;
  white-space: nowrap;
  overflow: hidden;
  line-height: 26px;
  color: #777;
  padding-left: 10px;
}
.upload-file label span:before {
  content: attr(data-title);
}
	</style>
	</layout:put>
	
	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Importação de apontamentos eletrônicos</h1>
		</div>
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<form:form id="formValidar" action="importacao/salvar" method="post" modelAttribute="horas">
					<div id="textDiv" class=""></div>
					<input type="hidden" id="token" name="${_csrf.parameterName}" value="${_csrf.token}"/>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3">
								<div class="form-group" id="divEmpresa">
									<label class="control-label">Empresa:</label> 
									<select id="empresa" class="selectpicker" data-live-search="true" data-width="100%">
										<option value="0">Selecione</option>
										<option value="QA360">QA360</option>
										<option value="Verity">Verity</option>
									</select>
								</div>
							</div>
							<div class="form-group col-md-4">
								<label class="control-label">Arquivo:</label>
								<div class="upload-file">
									<input type="file" onchange="importarArquivo();" accept=".txt" name="file" id="upload-arquivo"  class="upload-demo" />
									<label data-title="Selecione" for="upload-arquivo">
										<span id="caminho"></span>
									</label>
								</div>
							</div>
						</div>
						<div id="tbHoras" class="bootstrap-table hide">
							<div class="fixed-table-container" style="padding-bottom: 0px;">
								<div class="fixed-table-body">
									<table id="demo-dt-basic" aria-describedby="demo-dt-basic_info" style="width: 100%"
										class="table table-striped table-bordered dataTable no-footer dtr-inline">
										<thead>
											<tr class="">
												<th class="sorting_asc text-center" 
													tabindex="0" aria-controls="demo-dt-basic" aria-sort="ascending"
													aria-label="PIS: activate to sort column descending">
													PIS</th>
												<th class="sorting_asc text-center"
													tabindex="0" aria-controls="demo-dt-basic" aria-sort="ascending"
													aria-label="Funcionário: activate to sort column descending">
													Funcionário</th>
												<th class="min-tablet sorting text-center"
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="Data: activate to sort column ascending">
													Data</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="1 - Entrada: activate to sort column ascending">
													Entrada 1</th>
												<th class="min-tablet sorting text-center" tabindex="0"
													aria-controls="demo-dt-basic"
													aria-label="1 - Saída: activate to sort column ascending">
													Saída 1</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="2 - Entrada: activate to sort column ascending">
													Entrada 2</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="2 - Saída: activate to sort column ascending">
													Saída 2</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="3 - Entrada: activate to sort column ascending">
													Entrada 3</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="3 - Saída: activate to sort column ascending">
													Saída 3</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic"
													aria-label="4 - Entrada: activate to sort column ascending">
													Entrada 4</th>
												<th class="min-tablet sorting text-center" 
													tabindex="0" aria-controls="demo-dt-basic" 
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
						<div id="botoes" class="pull-right hide">
							<button type="submit" class="btn btn-success">Salvar</button>
							<button type="reset" onClick="cancelar()" class="btn btn-danger">Cancelar</button>
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
		<script src='<c:url value="/plugins/masked-input/jquery.mask.js"/>'></script>
		<script>
			function cancelar(){
				location.reload();
			}
		
			$('.upload-demo').change(function()	{
				var filename = $(this).val().split('\\').pop();
				$(this).parent().find('span').attr('data-title',filename);
				$(this).parent().find('label').attr('data-title','Selecione');
				$(this).parent().find('label').addClass('selected');
			});
			
			$(function () {
				var token = $("meta[name='_csrf']").attr("content");
				var header = $("meta[name='_csrf_header']").attr("content");
				$(document).ajaxSend(function(e, xhr, options) {
			    	xhr.setRequestHeader(header, token);
				});
			});
			
			function importarArquivo() {
				var div = document.getElementById("textDiv");
				$("#textDiv").removeClass("alert alert-danger");
				$("#textDiv").text("");
				$(".rmvLinha").remove();
				if ($("#upload-arquivo").val() && $("#empresa").val() != 0) {
					var aux = 0;
					var date;
					var form = document.getElementById("formValidar");
					var formData = new FormData(form);
					$.ajax({
						url : 'importacao/importar-arquivo/'+$("#empresa option:selected").text(),
						enctype : 'multipart/form-data',
						type : 'POST',
						data : formData,
						processData : false,
						contentType : false,
						cache : false,
						success : function(data) {
							if(data != ''){
								$(data).each(function(index, value) {
									var campos = "<tr role='row' class='odd text-center rmvLinha'>"+
									"<td id='pis"+index+"' class='pis'></td>" +
									"<input id='pisFunc"+index+"' type='hidden'>"+
									"<td id='funcionario"+index+"'></td>" +
									"<input id='nmFunc"+index+"' type='hidden'>"+
									"<td id='data"+index+"' class='data'></td>" +
									"<td id='hora0"+index+"' class='hora'></td>" +
									"<input id='pisHoras0"+index+"' type='hidden' name='horas["+aux+"].pis'>"+
									"<input id='horaHoras0"+index+"' type='hidden' name='horas["+aux+"].hora'>"+
									"<input id='dataHoras0"+index+"' type='hidden' name='horas["+aux+"].dataImportacao'>"+
									"<td id='hora1"+index+"' class='hora'></td>" +
									"<input id='pisHoras1"+index+"' type='hidden' name='horas["+(aux+1)+"].pis'>"+
									"<input id='horaHoras1"+index+"' type='hidden' name='horas["+(aux+1)+"].hora'>"+
									"<input id='dataHoras1"+index+"' type='hidden' name='horas["+(aux+1)+"].dataImportacao'>"+
									"<td id='hora2"+index+"' class='hora'></td>" +
									"<input id='pisHoras2"+index+"' type='hidden' name='horas["+(aux+2)+"].pis'>"+
									"<input id='horaHoras2"+index+"' type='hidden' name='horas["+(aux+2)+"].hora'>"+
									"<input id='dataHoras2"+index+"' type='hidden' name='horas["+(aux+2)+"].dataImportacao'>"+
									"<td id='hora3"+index+"' class='hora'></td>" +
									"<input id='pisHoras3"+index+"' type='hidden' name='horas["+(aux+3)+"].pis'>"+
									"<input id='horaHoras3"+index+"' type='hidden' name='horas["+(aux+3)+"].hora'>"+
									"<input id='dataHoras3"+index+"' type='hidden' name='horas["+(aux+3)+"].dataImportacao'>"+
									"<td id='hora4"+index+"' class='hora'></td>" +
									"<input id='pisHoras4"+index+"' type='hidden' name='horas["+(aux+4)+"].pis'>"+
									"<input id='horaHoras4"+index+"' type='hidden' name='horas["+(aux+4)+"].hora'>"+
									"<input id='dataHoras4"+index+"' type='hidden' name='horas["+(aux+4)+"].dataImportacao'>"+
									"<td id='hora5"+index+"' class='hora'></td>" +
									"<input id='pisHoras5"+index+"' type='hidden' name='horas["+(aux+5)+"].pis'>"+
									"<input id='horaHoras5"+index+"' type='hidden' name='horas["+(aux+5)+"].hora'>"+
									"<input id='dataHoras5"+index+"' type='hidden' name='horas["+(aux+5)+"].dataImportacao'>"+
									"<td id='hora6"+index+"' class='hora'></td>" +
									"<input id='pisHoras6"+index+"' type='hidden' name='horas["+(aux+6)+"].pis'>"+
									"<input id='horaHoras6"+index+"' type='hidden' name='horas["+(aux+6)+"].hora'>"+
									"<input id='dataHoras6"+index+"' type='hidden' name='horas["+(aux+6)+"].dataImportacao'>"+
									"<td id='hora7"+index+"' class='hora'>"+
									"</td>" +
									"</tr>";
	
									$("#list").append(campos);
	
	
									$('#pis'+index).text(value.pis);
									$('#funcionario'+index).text(value.nome);
									$(value.horas).each(function(i, value) {
										$('#hora'+i+index).text(value.hora);
										$('#pisHoras'+i+index).val(value.pis);
										$('#horaHoras'+i+index).val(value.hora);
										
										date = new Date(value.dataImportacao);
										
										$('#dataHoras'+i+index).val(date.toLocaleDateString());
									})
									date = new Date(value.horas[0].dataImportacao);
									$('#data'+index).text(date.toLocaleDateString());
									$('.pis').mask('999.9999.999-9');
									$('.data').mask('99/99/9999');
									$('.hora').mask('99:99');
									aux += 8;
								});
								$("#tbHoras").removeClass("hide");
								$("#botoes").removeClass("hide");
							}else{
								textDiv.className = "alert alert-danger";
								textDiv.textContent = "Por favor, importe um arquivo de uma só data.";
								var text = "[" + div.textContent + "]";
							}
						},
					});
				}else{
					textDiv.className = "alert alert-danger";
					textDiv.textContent = "Por favor, selecione uma empresa.";
					var text = "[" + div.textContent + "]";
					$("#divEmpresa").attr("onchange", "importarArquivo();");
				}
			};
		</script>
	</layout:put>

</layout:extends>