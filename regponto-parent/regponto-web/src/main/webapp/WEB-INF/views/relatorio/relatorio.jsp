<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">

	<layout:put block="css">
		<link href='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.css"/>' rel="stylesheet">
	</layout:put>

	<layout:put block="contents">
		<div id="page-title">
			<h1 class="page-header text-overflow">Relatórios</h1>
		</div>
		<!--Page content-->
		<div class="col-md-12">
			<div class="panel">
				<div class="panel-heading">
					<div class="panel-title">
						<h4>Extrato de horas</h4>
					</div>
				</div>
				<form>
					<div class="panel-body">
						<div class="row">
							<div class="col-sm-3">
								<div class="form-group">
									<label class="control-label">Nome do funcionário:</label> 
									<select class="selectpicker" data-live-search="true" data-width="100%">
										<option value="">Selecione</option>
										<option value="">Igor Cunha</option>
										<option value="">Guilherme de Oliveira</option>
									</select>
								</div>
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">Período:</label> 
								<input type="text" class="form-control" />
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label> 
								<input type="text" class="form-control" />
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label>
								<button type="submit" class="btn-primary form-control">Gerar extrato</button>
							</div>
						</div>
					</div>
				</form>
				<div class="panel-heading">
					<br>
					<div class="panel-title">
						<h4>Relatório consolidado de horas extras</h4>
					</div>
				</div>
				<form>
					<div class="panel-body">
						<div class="row">
							<div class="form-group col-md-2">
								<label class="control-label">Período:</label> 
								<input type="text" class="form-control" />
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label> 
								<input type="text" class="form-control" />
							</div>
							<div class="form-group col-md-2">
								<label class="control-label">&nbsp;</label>
								<button type="submit" class="btn-primary form-control">Gerar extrato</button>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</layout:put>

	<layout:put block="scripts" type="REPLACE">
		<script src='<c:url value="/plugins/bootstrap-select/bootstrap-select.min.js"/>'></script>
	</layout:put>

</layout:extends>