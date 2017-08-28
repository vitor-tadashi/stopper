<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
		<link href='<c:url value="/css/custom/upload-file.css"/>' rel="stylesheet">
	</layout:put>
	
	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Importação de apontamentos eletrônicos</h1>
		</div>
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<form:form id="formValidar" action="importacao/salvar" method="post">
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
									<input type="file" onchange="importarArquivo();" accept=".txt" name="file" id="upload-arquivo" class="upload-demo" />
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
		
		<div id="modal-confirmacao" class="modal fade">
	        <div class="modal-dialog">
	            <div class="modal-content">
	                <div class="modal-header">
	                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
	                    <h4 class="modal-title text-center" id="mySmallModalLabel">Alerta!</h4>
	                </div>
	                <div class="modal-body">
	                	<div class="text-center">
	                		<span>Arquivo para está data já foi importado, se você continuar irá substituir o mesmo!</span>
	                		<br><br>
	                		<span>Deseja continuar?</span>
	                	</div>
	                    <div class="text-center">
	                    <br><br>
							<button  onClick="exibirImportacao(true)" type="button" class="btn btn-danger">Sim</button>
							<button onClick="cancelar()" class="btn btn-primary" type="button">Não</button>
						</div>
	                </div>
	            </div>
	        </div>
	    </div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
		<script src='<c:url value="/plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="/js/custom/upload-file.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script>
			function cancelar(){
				location.reload();
			}
		
			function importarArquivo() {
				var div = document.getElementById("textDiv");
				$("#textDiv").removeClass("alert alert-danger");
				$("#textDiv").text("");
				$(".rmvLinha").remove();
				if ($("#upload-arquivo").val() && $("#empresa").val() != 0) {
					var date;
					var form = document.getElementById("formValidar");
					var formData = new FormData(form);
					var exibir = true;
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
									if(value.id != null){
										preencherImportacao(value, index);
										exibirImportacao(exibir);
									}else{
										exibir = false;
										$('#modal-confirmacao').modal('show'); 
									}
								});
							}else{
								textDiv.className = "alert alert-danger";
								textDiv.textContent = "Nenhum funcionário coincide com o arquivo.";
								var text = "[" + div.textContent + "]";
								$("#divEmpresa").attr("onchange", "importarArquivo();");
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
			
			function exibirImportacao(bool){
				if(bool == true){
					$('#modal-confirmacao').modal('hide'); 
					$("#tbHoras").removeClass("hide");
					$("#botoes").removeClass("hide");
				}
			}
			
			function preencherImportacao(value, index){
				var campos = "<tr role='row' class='odd text-center rmvLinha'>"+
				"<td id='pis"+index+"' class='pis'></td>" +
				"<td id='funcionario"+index+"'></td>" +
				"<td id='data"+index+"' class='data'></td>" +
				"<td id='hora0"+index+"' class='hora'></td>" +
				"<td id='hora1"+index+"' class='hora'></td>" +
				"<td id='hora2"+index+"' class='hora'></td>" +
				"<td id='hora3"+index+"' class='hora'></td>" +
				"<td id='hora4"+index+"' class='hora'></td>" +
				"<td id='hora5"+index+"' class='hora'></td>" +
				"<td id='hora6"+index+"' class='hora'></td>" +
				"<td id='hora7"+index+"' class='hora'></td>"+
				"</tr>";

				$("#list").append(campos);


				$('#pis'+index).text(value.pis);
				$('#funcionario'+index).text(value.nome);
				$(value.apontamentos).each(function(i, value) {
					$('#hora'+i+index).text(value.hora);
				})
				date = new Date(value.apontamentos[0].data);
				$('#data'+index).text(date.toLocaleDateString());
				$('.pis').mask('999.9999.999-9');
				$('.data').mask('99/99/9999');
				$('.hora').mask('99:99');
			}
		</script>
	</layout:put>

</layout:extends>