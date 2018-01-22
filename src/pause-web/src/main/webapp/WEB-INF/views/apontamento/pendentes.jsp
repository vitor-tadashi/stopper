<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance"
	prefix="layout"%>
<layout:extends name="../shared/base.jsp">
	<layout:put block="css">
		<link
			href='<c:url value="/plugins/datatables/media/css/dataTables.bootstrap.css"/>'
			rel="stylesheet">
	</layout:put>
	<layout:put block="contents">
		<!--Page Title-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<div id="page-title">
			<h1 class="page-header text-overflow">Consultar apontamentos
				pendentes</h1>
		</div>
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<!--End page title-->
		<!--Breadcrumb-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Consultar apontamentos pendentes</li>
		</ol>
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<!--End breadcrumb-->

		<!--Page content-->
		<!--===================================================-->
		<div id="page-content">
			<div class="panel">

				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<div class="table-respon">
						<table id="dt-basic-desc"
							class="table table-striped table-bordered">
							<thead>
								<tr>
									<th class="text-center">Funcionário</th>
									<th class="text-center">Dias corridos sem apontamento</th>
									<th class="text-center">Ações</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<c:forEach items="${pendentes}" var="p">
									<tr>
										<td>${p.nomeFuncionario}</td>
										<td>${p.diasSemApontar}</td>
										<td><div id="btn-sobreaviso" class="center">
												<button id="botaoEmail"
													onClick="abrirModalEmail('${p.idFuncionario}')"
													class="btn btn-trans" style="padding: 0" type="button">
													<ins>
														<font onmouseover="this.color='#8c8c8c';"
															onmouseout="this.color='#429bd1';" color=#429bd1>Notificar</font>
													</ins>
												</button>
											</div></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
				<!--===================================================-->
				<!--End Data Table-->
			</div>
		</div>
		<!--===================================================-->
		<!--End page content-->

		<!-- Enviar Email Bootstrap Modal -->
		<!--===================================================-->
		<div class="modal fade" id="enviar-email-modal" role="dialog"
			tabindex="-1" aria-labelledby="enviar-email-modal" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i id="closex" class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title">Enviar e-mail de notificação</h4>
					</div>
					<!--Modal body-->
					<div class="modal-body">
						<div class="row">
							<div class="form-group sa-data">
								<label id="msgConfirmacao" class="control-label"
									style="padding-left: 8px;">Deseja enviar o e-mail de
									notificação sobre apontamentos pendentes para este funcionário?</label>
							</div>
						</div>
					</div>
					<form action="" id="form-sa" class="clear-form" method="post">
						<div class="modal-footer">
							<button class="btn btn-default" data-dismiss="modal"
								type="button" id="btn-cancelar-envio">Cancelar</button>
							<button class="btn btn-success" id="btn-confirmar-envio"
								onClick="confirmarEnvioEmail()" type="button">Enviar
								e-mail</button>
						</div>
					</form>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--End Enviar Email Bootstrap Modal-->

	</layout:put>
	<layout:put block="scripts">
		<script>
			var id;

			function abrirModalEmail(idFuncionarioEscolhido) {
				id = idFuncionarioEscolhido;
				$('#enviar-email-modal').modal('show');
			}

			function confirmarEnvioEmail() {
				document.getElementById("btn-confirmar-envio").disabled = true;
				$('#btn-cancelar-envio').text("Fechar");
				$('#msgConfirmacao').text("E-mail enviado com sucesso!");
				$
						.ajax({
							url : 'apontamentos-pendentes/enviar-email',
							type : 'POST',
							contentType : 'application/json',
							data : {
								'idFuncionario' : id
							},
							cache : false,
							success : function(data) {
								$('#msgConfirmacao').text(data);
							},
							error : function(erro) {
								$('#msgConfirmacao').text("ERRO " + erro);
								document.getElementById("btn-confirmar-envio").disabled = false;
								console.log(erro);
							}
						});
			}

			$('#enviar-email-modal')
					.on(
							'hidden.bs.modal',
							function() {
								document.getElementById("btn-confirmar-envio").disabled = false;
								$('#btn-cancelar-envio').text("Cancelar");
								$('#msgConfirmacao')
										.text(
												"Deseja enviar o e-mail de notificação sobre apontamentos pendentes para este funcionário?");
							});
		</script>
		<script
			src='<c:url value="/plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script
			src='<c:url value="/plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value='/js/custom/datatable-custom.js'/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
	</layout:put>
</layout:extends>