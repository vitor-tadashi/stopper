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
						<table id="dt-basic-desc" class="table table-striped table-bordered">
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
										<td><a
											href="mailto:${p.emailFuncionario}?Subject=Apontamentos%20pendentes"
											target="_top"><i class="pli-mail"></i></a></td>
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

	</layout:put>
	<layout:put block="scripts">
		<script
			src='<c:url value="/plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script
			src='<c:url value="/plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value='/js/custom/datatable-custom.js'/>'></script>
	</layout:put>
</layout:extends>