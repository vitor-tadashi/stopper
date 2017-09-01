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
		
		<!--Page content-->
		<!--===================================================-->
		<div id="page-content">
			<div class="panel">
				<!--Data Table-->
				<!--===================================================-->
				<div class="panel-body">
					<form>
						<div class="row">
							<div class="col-sm-4">
								<div class="form-group">
									<label class="control-label">Nome do funcionário</label>
									<select class="selectpicker" data-live-search="true" data-width="100%" id="#">
										<option value="">Selecione</option>
									</select>
								</div>
							</div>
							<div class="col-sm-4">
								<label class="control-label">Período</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" class="form-control data" name="start" placeholder="dd/mm/yyyy"/>
									<span class="input-group-addon">até</span>
									<input type="text" pattern="\d{1,2}/\d{1,2}/\d{4}" class="form-control data" name="end" placeholder="dd/mm/yyyy"/>
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
								<tr>
									<input type="hidden" id="infoDia" value="01/08/2017, terça-feira"/>
									<td>01/08/2017</td>
									<td class="text-left">Terça-feira</td>
									<td id="apontamento01" class="text-muted pli-clock">09:00E</td>
									<td id="apontamento02" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">12:00</td>
									<td id="apontamento03" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">13:00</td>
									<td id="apontamento04" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">19:51</td>
									<td id="apontamento05" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento06" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento07" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento08" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora01">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="02/08/2017, quarta-feira"/>
									<td>02/08/2017</td>
									<td class="text-left">Quarta-feira</td>
									<td id="apontamento09" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">07:00</td>
									<td id="apontamento10" class="text-muted pli-clock">12:00E</td>
									<td id="apontamento11" class="text-muted pli-clock">13:40E</td>
									<td id="apontamento12" class="text-muted pli-clock">18:17E</td>
									<td id="apontamento13" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento14" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento15" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento16" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora02">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="03/08/2017, quinta-feira"/>
									<td>03/08/2017</td>
									<td class="text-left">Quinta-feira</td>
									<td id="apontamento17" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">09:22</td>
									<td id="apontamento18" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">12:10</td>
									<td id="apontamento19" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">13:36</td>
									<td id="apontamento20" class="text-muted pli-clock">19:07E</td>
									<td id="apontamento21" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento22" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento23" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento24" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora03">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="04/08/2017, sexta-feira"/>
									<td>04/08/2017</td>
									<td class="text-left">Sexta-feira</td>
									<td id="apontamento25" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">08:50</td>
									<td id="apontamento26" class="text-muted pli-clock">11:58E</td>
									<td id="apontamento27" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">13:31</td>
									<td id="apontamento28" class="text-muted pli-clock">18:16E</td>
									<td id="apontamento29" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento30" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento31" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento32" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora04">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="05/08/2017, sábado"/>
									<td>05/08/2017</td>
									<td class="text-left">Sábado</td>
									<td id="apontamento33" class="text-muted pli-clock">09:21E</td>
									<td id="apontamento34" class="text-muted pli-clock">12:10E</td>
									<td id="apontamento36" class="text-muted pli-clock">18:03E</td>
									<td id="apontamento37" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento37" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento38" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento39" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento40" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora05">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="06/08/2017, domingo"/>
									<td>06/08/2017</td>
									<td class="text-left">Domingo</td>
									<td id="apontamento41" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">09:10</td>
									<td id="apontamento42" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">12:00</td>
									<td id="apontamento43" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">13:00</td>
									<td id="apontamento44" class="text-muted pli-clock">18:00E</td>
									<td id="apontamento45" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento46" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento47" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento48" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora06">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
								</tr>
								<tr>
									<input type="hidden" id="infoDia" value="07/08/2017, segunda-feira"/>
									<td>07/08/2017</td>
									<td class="text-left">Segunda-feira</td>
									<td id="apontamento49" class="text-muted pli-clock">09:00E</td>
									<td id="apontamento50" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">12:00</td>
									<td id="apontamento27" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">13:31</td>
									<td id="apontamento53" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento54" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento55" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento56" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="apontamento56" style="cursor:pointer;" onclick="dialogApontamentoHora(this);">--:--</td>
									<td id="#">0</td>
									<td id="total-hora07">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
									<td id="#">0</td>
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
						<input type="hidden" id="apontamento-id" />
						<!--Bootstrap Timepicker : Component-->
						<!--===================================================-->
						<div class="input-group date">
							<input id="apontamento-time" type="text-center" class="form-control">
							<span class="input-group-addon"><i class="pli-clock"></i></span>
						</div>
						<!--===================================================-->
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
						<form action="" id="form-sa">
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
						<form action="">
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
										<select class="selectpicker" data-live-search="true" data-width="100%" id="afastamentoJus">
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
						<form action="">
							<div class="row pad-btm">
								<div class="col-sm-3">
									<label class="control-label">Quantidade de horas</label>
									<input id="qtd-hr-atestado" type="text" class="form-control clock" placeholder="--:--">
								</div>
								<div class="col-sm-7">
									<div class="form-group">
										<label class="control-label">Justificativa</label>
										<select class="selectpicker" data-live-search="true" data-width="100%" id="atestadoJus">
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
		<script src='<c:url value="plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script src='<c:url value="plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value="js/custom/datatable-custom.js"/>'></script>
		<script src='<c:url value="js/custom/masks.js"/>'></script>
		<script src='<c:url value="js/custom/gerenciar-apontamentos-core.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script src='<c:url value="js/custom/gerenciar-apontamentos-modais.js"/>'></script>
	</layout:put>
</layout:extends>