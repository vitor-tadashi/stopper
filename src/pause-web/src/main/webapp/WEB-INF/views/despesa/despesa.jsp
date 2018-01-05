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
		<div id="page-content">
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
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
						<h4 class="modal-title">Gerenciar horas sobre aviso</h4>
					</div>
	
					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-sa" class="clear-form">
							<div class="row">
								<div class="panel">
									<!--Data Table-->
									<!--===================================================-->
									<div class="panel-body">
										<label class="control-label">Data</label>
										<div class="input-daterange input-group" id="datepicker"
											style="width: 100% !important">
											<input type="date" id="dataDespesa"
												class="form-control dataDespesa" name="dataDespesa"
												placeholder="dd/mm/aaaa" value="${periodo[0] }"
												min="2010-03-01" max="" />
										</div>
										<br /> <label class="control-label">Valor</label>
										<div class="input-group" id="valorDespesaDiv"
											style="width: 100% !important">
											<input type="number" id="valorDespesa" min="1" step="0.01"
												class="form-control valorDespesa" name="valorDespesa"
												placeholder="R$115,00" value="${periodo[0] }"
												pattern="([0-9]{1,3}\.)?[0-9]{1,3},[0-9]{2}$" />
										</div>
										<br /> <label class="control-label">Tipo de despesa</label>
										<div class="input-group">
											<select class="selectpicker" data-live-search="true"
												id="select-tipo-despesa" name="tipoDespesa">
												<option value="">Selecione</option>
												<%-- 							<c:forEach items="${funcionarios }" var="funcionario"> --%>
												<%-- 								<option value="${funcionario.id }" ${funcionario.id eq idFuncionario? 'selected="true"' : ''}>${funcionario.nome }</option> --%>
												<%-- 							</c:forEach> --%>
											</select>
										</div>
										<br /> <label class="control-label">Centro de custo</label>
										<div class="input-group">
											<select class="selectpicker" data-live-search="true"
												id="select-centro-custo" name="centroCusto">
												<option value="">Selecione</option>
												<%-- 							<c:forEach items="${funcionarios }" var="funcionario"> --%>
												<%-- 								<option value="${funcionario.id }" ${funcionario.id eq idFuncionario? 'selected="true"' : ''}>${funcionario.nome }</option> --%>
												<%-- 							</c:forEach> --%>
											</select>
										</div>
										<br /> <label class="control-label">Justificativa</label>
										<div class="input-group" id="justificativaDespesaDiv"
											style="width: 100% !important">
											<textarea id="justificativaDespesa"
												class="form-control justificativaDespesa"
												name="justificativaDespesa"
												placeholder="Justificativa da despesa" value="${periodo[0] }"></textarea>
										</div>
										<br /> <label class="control-label">Comprovante</label>
										<div class="input-group" id="comprovanteDespesaDiv"
											style="width: 100% !important">
											<input id="comprovanteDespesa" type="file"
												class="form-control comprovanteDespesa"
												name="comprovanteDespesa"
												placeholder="Comprovante da despesa" value="${periodo[0] }" />
										</div>
										<br />
										<div class="input-group" id="buttonSubmitDiv"
											style="width: 100% !important">
											<button id="submit-js" class="btn btn-info" type="submit">Salvar</button>
										</div>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
		<script>
			
			
			function abrirModal(){
				$('#add-despesa-modal').modal();
			}
		</script>
	</layout:put>
</layout:extends>