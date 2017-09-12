<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">
	<layout:put block="css">
		<link href='<c:url value="plugins/datatables/media/css/dataTables.bootstrap.css"/>' rel="stylesheet">
	</layout:put>
	<layout:put block="contents">
		<!--Page Title-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<div id="page-title">
			<h1 class="page-header text-overflow">Consultar apontamentos</h1>
		</div>
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<!--End page title-->
		<!--Breadcrumb-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Consultar apontamentos</li>
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
					<form>
						<div class="row">
							<div class="col-sm-3">
								<div class="form-group">
									<label class="control-label">Nome do funcionário</label>
									<select class="selectpicker" data-live-search="true" data-width="100%" id="idFunc">
										<option value="">Selecione</option>
										<c:forEach items="${funcionarios}" var="funcionario">
											<option value="${funcionario.id}">${funcionario.nome}</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5">
								<label class="control-label">Período</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="date"class="form-control" name="start" />
									<span class="input-group-addon">até</span>
									<input type="date" class="form-control" name="end"/>
								</div>
							</div>
							<div class="col-sm-1" style="margin-top: 23px;">
								<button class="btn btn-info" type="submit">Filtrar</button>
							</div>
							<div class="col-sm-2 pull-right" style="margin-top: 23px;">
								<a class="btn btn-success pull-right" href="#"><i class="fa fa-file-excel-o"></i> Gerar relatório</a>
							</div>
						</div>
					</form>
					<div class="table-respon">
						<table class="table table-striped table-bordered dt-pag">
							<thead>
								<tr>
									<th class="text-center">Funcionário</th>
									<th class="text-center">Total de horas</th>
									<th class="text-center">Banco de horas</th>
									<th class="text-center">Adicional noturno</th>
									<th class="text-center">Horas SA</th>
									<th class="text-center">Horas ST</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<tr>
									<td>Steve N. Horton</td>
									<td>9,00</td>
									<td>1,00</td>
									<td>0</td>
									<td>0</td>
									<td>0</td>
								</tr>
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
	</layout:put>
	<layout:put block="scripts">
		<script src='<c:url value="plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value='js/custom/datatable-custom.js'/>'></script>
		<script src='<c:url value='js/custom/masks.js'/>'></script>
	</layout:put>
</layout:extends>