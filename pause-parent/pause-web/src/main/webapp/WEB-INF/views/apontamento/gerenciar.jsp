<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">
	<layout:put block="css">
		<link href='<c:url value="plugins/datatables/media/css/dataTables.bootstrap.css"/>' rel="stylesheet">
		<link href='<c:url value="plugins/bootstrap-timepicker/bootstrap-timepicker.min.css"/>' rel="stylesheet">
	</layout:put>
	<layout:put block="contents">
		<!--Page Title-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<div id="page-title">
			<h1 class="page-header text-overflow">Gerenciar apontamentos</h1>
		</div>
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<!--End page title-->
		
		
		<!--Breadcrumb-->
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<ol class="breadcrumb">
			<li><a href="#">Home</a></li>
			<li class="active">Gerenciar apontamentos</li>
		</ol>
		<!--~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~-->
		<!--End breadcrumb-->
		<input type="hidden" id="apontamento-funcionario" value="" >
		<!--Page content-->
		<!--===================================================-->
		<div id="page-content">
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<c:url value="/gerenciar-apontamento" var="url"/>
					<form action="${url }" method="get" id="bv-form">
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Nome do funcionário</label>
									<select class="selectpicker" data-live-search="true" data-width="100%" id="select-funcionario" name="pis">
										<option value="">Selecione</option>
										<c:forEach items="${funcionarios }" var="funcionario">
											<option value="${funcionario.pis }" ${funcionario.pis eq pis? 'selected="true"' : ''}>${funcionario.nome }</option>
										</c:forEach>
									</select>
								</div>
							</div>
							<div class="col-sm-5">
								<label class="control-label">Período</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="date" id="periodoDe" class="form-control periodo" name="periodo" placeholder="dd/mm/aaaa" value="${periodo[0] }" min="2010-03-01" max=""/>
									<span class="input-group-addon">até</span>
									<input type="date" id="periodoAte" class="form-control periodo" name="periodo" placeholder="dd/mm/aaaa" value="${periodo[1] }" min="2010-03-01" max=""/>
								</div>
							</div>
							<div class="col-sm-1" style="margin-top: 24px;">
								<button class="btn btn-info" type="submit">Filtrar</button>
							</div>
						</div>
					</form>
					<div class="row">
						<div class="col-md-12 pad-btm">
							<div class="left">
								<button class="btn btn-purple" type="button" data-target="#sobre-aviso-modal" data-toggle="modal"><span class="pli-add"></span> Informar sobre aviso</button>
							</div>
							<div class="left">
								<button class="btn btn-success" type="button" data-target="#afastamento-modal" data-toggle="modal"><span class="pli-add"></span> Informar afastamento</button>
							</div>
							<div>
								<button class="btn btn-pink" type="button" data-target="#atestado-modal" data-toggle="modal"><span class="pli-add"></span> Informar atestado</button>
							</div>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-striped table-bordered " id="dt-apontamentos">
							<thead>
								<tr>
									<th class="text-center">Data</th>
									<th class="text-center">Dia da semana</th>
									<th class="text-center">1-Entr.</th>
									<th class="text-center">1-Saída</th>
									<th class="text-center">2-Entr.</th>
									<th class="text-center">2-Saída</th>
									<th class="text-center">3-Entr.</th>
									<th class="text-center">3-Saída</th>
									<th class="text-center">4-Entr.</th>
									<th class="text-center">4-Saída</th>
									<th class="text-center">Atestado</th>
									<th class="text-center">Horas</th>
									<th class="text-center">Banco</th>
									<th class="text-center">Ad. Not.</th>
									<th class="text-center">S. A.</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<c:forEach items="${dias }" var="dia" varStatus="i">
									<tr>
										<fmt:formatDate value="${dia.data }" pattern="dd/MM/yyyy" var="data"/>
									
										<input type="hidden" id="infoDia" value="${data }, ${dia.diaDaSemana}"/>
										<td>${data }</td>
										<td class="text-left">${dia.diaDaSemana }</td>
										<c:forEach begin="0" end="7" varStatus="cont">
											<c:if test="${not empty dia.apontamentos[cont.index] && not empty dia.apontamentos[cont.index].horario}">
												<c:choose>
													<c:when test="${dia.apontamentos[cont.index].tipoImportacao || dia.mesFechado}">
														<td id="apontamento${cont.count + 8 * (i.count - 1)}" class="text-muted pli-clock">${dia.apontamentos[cont.index].horario }${!dia.mesFechado? 'E':''}</td>
													</c:when>
													<c:otherwise>
														<td id="apontamento${cont.count + 8 * (i.count - 1)}" style="cursor:pointer;" onclick="dialogApontamentoHora(this, ${dia.apontamentos[cont.index].id });">${dia.apontamentos[cont.index].horario }</td>
													</c:otherwise>
												</c:choose>
											</c:if>
											<c:if test="${empty dia.apontamentos[cont.index] || empty dia.apontamentos[cont.index].horario}">
												<td id="apontamento${cont.count + 8 * (i.count - 1)}" ${!dia.mesFechado? 'style="cursor:pointer;" onclick="dialogApontamentoHora(this);"':''}>--:--</td>
											</c:if>
										</c:forEach>
										<td id="#">${dia.qtdAtestadoHoras }</td>
										<td id="total-hora${i.count }">${dia.horaTotal }</td>
										<td id="#">${dia.bancoHora }</td>
										<td id="#">${dia.adicNoturno }</td>
										<td id="#">${dia.sobreAviso }</td>
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
		<!--Default Bootstrap Modal-->
		<!--===================================================-->
		<div class="modal" id="demo-default-modal" role="dialog" tabindex="-1" aria-labelledby="demo-default-modal" aria-hidden="true">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
		
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
						<h4 class="modal-title" id="title-modal-apontamento">01/08/2017, terça-feira</h4>
					</div>
		
					<!--Modal body-->
					<div class="modal-body">
						<p class="text-semibold text-main">Informe o horário:</p>
						<form action="" id="form-time" class="clear-form">
							<input type="hidden" id="apontamento-id" />
							<input type="hidden" id="idApontamento" />
							<!--Bootstrap Timepicker : Component-->
							<!--===================================================-->
							<div class="input-group date">
								<input id="apontamento-time" type="text-center" class="form-control clock" placeholder="--:--">
								<span class="input-group-addon"><i class="pli-clock"></i></span>
							</div>
							<div class="form-group pad-top">
								<label class="control-label">Justificativa</label>
								<select class="selectpicker clear-select" data-live-search="true" data-width="100%" id="apontamento-jus">
									<option value="0">Selecione</option>
									<c:forEach items="${justificativas }" var="jus">
										<option value="${jus.id }">${jus.descricao }</option>
									</c:forEach>
								</select>
							</div>
							<div class="form-group">
								<label class="control-label">Observações</label>
								<textarea id="apontamento-obs" rows="2" maxlength="200" class="form-control"></textarea>
							</div>
							<!--===================================================-->
						</form>
					</div>
		
					<!--Modal footer-->
					<div class="modal-footer">
						<button data-dismiss="modal" class="btn btn-default" type="button">Cancelar</button>
						<button class="btn btn-primary" onclick="informarHorario();">Salvar</button>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--End Default Bootstrap Modal-->
		<!--sobre aviso Modal-->
		<!--===================================================-->
		<div class="modal fade" id="sobre-aviso-modal" role="dialog" tabindex="-1" aria-labelledby="sobre-aviso-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
		
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
						<h4 class="modal-title">Gerenciar horas sobre aviso</h4>
					</div>
		
					<!--Modal body-->
					<div class="modal-body">
						<form action="" id="form-sa" class="clear-form">
							<div class="row">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Data</label>
										<input type="text" name="data" pattern="\d{1,2}/\d{1,2}/\d{4}" class="form-control data" id="dt-sa" placeholder="dd/mm/yyyy"/>
									</div>
								</div>
								<div class="col-sm-6">
									<label class="control-label">Período</label>
									<div class="input-daterange input-group" id="datepicker">
										<input id="hora-sa-e" name="entrada" type="text" class="form-control clock" placeholder="--:--">
										<span class="input-group-addon">até</span>
										<div class="input-group date">
											<input id="hora-sa-s" name="saida" type="text" class="form-control clock" placeholder="--:--">
											<span class="input-group-addon"><i class="pli-clock"></i></span>
										</div>
									</div>
								</div>
								<div class="col-sm-1" style="margin-top: 23px;">
									<button class="btn btn-info" type="button" onclick="inserirSA()">Inserir</button>
								</div>
							</div>
						</form>
						<div class="table-responsive">
							<table class="table table-striped table-bordered">
								<thead>
									<tr class="">
										<th class="text-center">Data</th>
										<th class="text-center">Entrada</th>
										<th class="text-center">Saída</th>
										<th class="text-center">Ação</th>
									</tr>
								</thead>
								<tbody class="text-center" id="body-sa">
									<tr>
										<td>13/03/2017</td>
										<td>10:00</td>
										<td>17:00</td>
										<td><a class="text-danger" href="#">Remover</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--End sobre aviso Modal-->
		<!--afastamento Modal-->
		<!--===================================================-->
		<div class="modal fade" id="afastamento-modal" role="dialog" tabindex="-1" aria-labelledby="afastamento-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
		
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
						<h4 class="modal-title">Gerenciar afastamento</h4>
					</div>
		
					<!--Modal body-->
					<div class="modal-body">
						<form action="" class="clear-form">
							<div class="row pad-btm">
								<div class="col-sm-7">
									<label class="control-label">Período</label>
									<div class="input-daterange input-group" id="datepicker">
										<input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="afastamentoDe" class="form-control data" name="start" placeholder="dd/mm/yyyy"/>
										<span class="input-group-addon">até</span>
										<input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" id="afastamentoAte" class="form-control data" name="end" placeholder="dd/mm/yyyy"/>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-sm-8">
									<div class="form-group">
										<label class="control-label">Justificativa</label>
										<select class="selectpicker clear-select" data-live-search="true" data-width="100%" id="afastamentoJus">
											<option value="">Selecione</option>
										</select>
									</div>
								</div>
								<div class="col-sm-1" style="margin-top: 23px;">
									<button class="btn btn-info" type="button" onclick="inserirAfastamento()">Inserir</button>
								</div>
							</div>
						</form>
						<div class="table-responsive">
							<table class="table table-striped table-bordered">
								<thead>
									<tr class="">
										<th class="text-center">De</th>
										<th class="text-center">Até</th>
										<th class="text-center">Justificativa</th>
										<th class="text-center">Ação</th>
									</tr>
								</thead>
								<tbody class="text-center" id="body-afastamento">
									<tr>
										<td>13/03/2017</td>
										<td>13/03/2017</td>
										<td>Férias</td>
										<td><a class="text-danger" href="#">Remover</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--End afastamento Modal-->
		<!--atestado Modal-->
		<!--===================================================-->
		<div class="modal fade" id="atestado-modal" role="dialog" tabindex="-1" aria-labelledby="atestado-modal" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
		
					<!--Modal header-->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
						<h4 class="modal-title">Gerenciar atestados</h4>
					</div>
		
					<!--Modal body-->
					<div class="modal-body">
						<form action="" class="clear-form">
							<div class="row pad-btm">
								<div class="col-sm-3">
									<label class="control-label">Quantidade de horas</label>
									<input id="qtd-hr-atestado" type="text" class="form-control clock" placeholder="--:--">
								</div>
								<div class="col-sm-7">
									<div class="form-group">
										<label class="control-label">Justificativa</label>
										<select class="selectpicker clear-select" data-live-search="true" data-width="100%" id="atestadoJus">
											<option value="">Selecione</option>
										</select>
									</div>
								</div>
								<div class="col-sm-1" style="margin-top: 23px;">
									<button class="btn btn-info" type="button" onclick="inserirAtestado()">Inserir</button>
								</div>
							</div>
						</form>
						<div class="table-responsive">
							<table class="table table-striped table-bordered">
								<thead>
									<tr class="">
										<th class="text-center">Qtd de horas</th>
										<th class="text-center">Justificativa</th>
										<th class="text-center">Ação</th>
									</tr>
								</thead>
								<tbody class="text-center" id="body-atestado">
									<tr>
										<td>3:30</td>
										<td>Férias</td>
										<td><a class="text-danger" href="#">Remover</a></td>
									</tr>
								</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--End atestado Modal-->	
	
		<script src='<c:url value="plugins/bootstrap-timepicker/bootstrap-timepicker.min.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value="js/custom/datatable-custom.js"/>'></script>
		<script src='<c:url value="/plugins/bootstrap-validator/bootstrapValidator.min.js"/>'></script>
		<script src='<c:url value="/js/custom/bootstrap-validator-data-periodo.js"/>'></script>
		<script src='<c:url value="/plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script src='<c:url value="js/custom/gerenciar-apontamentos-modais.js"/>'></script>
		<script src='<c:url value="js/custom/gerenciar-apontamentos-core.js"/>'></script>
		<script>
		$(document).ready(function() {
			$('.periodo').prop('max',function(){
		        return new Date().toJSON().split('T')[0];
		    });
			
			$('#bv-form').bootstrapValidator({
				excluded : [ ':disabled' ],
				fields : {
					periodo : {
						validators : {
							range : {
								message : 'A segunda data deve ser superior ou igual à primeira.'
							},
							date: {
								min: $("#periodoAte").prop('min'),
								max: $("#periodoAte").prop('max'),
								message:'Data inválida.'
							}
						}
					},
					pis : {
						validators : {
							notEmpty : {
								message : 'O campo é obrigatório.'
							}
						}
					}
				}
			});
		});
		</script>
	</layout:put>
</layout:extends>