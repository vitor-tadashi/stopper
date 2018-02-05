<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://kwonnam.pe.kr/jsp/template-inheritance" prefix="layout"%>
<layout:extends name="../shared/base.jsp">
	<layout:put block="css">
		<link href='<c:url value="plugins/datatables/media/css/dataTables.bootstrap.css"/>' rel="stylesheet">
		<link href='<c:url value="plugins/bootstrap-timepicker/bootstrap-timepicker.min.css"/>' rel="stylesheet">
	    <!--Bootstrap Validator [ OPTIONAL ]-->
	    <link href="plugins/bootstrap-validator/bootstrapValidator.min.css" rel="stylesheet">
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
							<sec:authorize access="hasAnyRole('ROLE_FILTRAR_APONTAMENTOS_POR_FUNCIONARIO', 'ROLE_MULTI_EMPRESA')">
								<div class="col-sm-4">
									<div class="form-group">
										<label class="control-label">Nome do funcionário</label>
										<select class="selectpicker" data-live-search="true" data-width="100%" id="select-funcionario" name="idFuncionario">
											<option value="">Selecione</option>
											<c:forEach items="${funcionarios }" var="funcionario">
												<option value="${funcionario.id }" ${funcionario.id eq idFuncionario? 'selected="true"' : ''}>${funcionario.nome }</option>
											</c:forEach>
										</select>
									</div>
								</div>
							</sec:authorize>	
							<input type="hidden" id="idFuncHidden" value="${idFuncionarioLogado}"/>			
							<div class="col-sm-5 pad-btm" id="periodo-js">
								<label class="control-label">Período</label>
								<div class="input-daterange input-group" id="datepicker">
									<input type="date" id="periodoDe" class="form-control periodo" name="periodo" placeholder="dd/mm/aaaa" value="${periodo[0] }" min="2010-03-01" max=""/>
									<span class="input-group-addon">até</span>
									<input type="date" id="periodoAte" class="form-control periodo" name="periodo" placeholder="dd/mm/aaaa" value="${periodo[1] }" min="2010-03-01" max=""/>
								</div>
							</div>
							<div class="col-sm-1" style="margin-top: 24px;">
								<button id="filtrar-js" class="btn btn-info" type="submit">Filtrar</button>
							</div>
						</div>
					</form>
					
					<sec:authorize access="hasRole('ROLE_MULTI-EMPRESA')" var="isMultiEmpresa"></sec:authorize>
					
					<c:if test="${((not empty idFuncionario) and isMultiEmpresa==true) or ((empty idFuncionario) and isMultiEmpresa==false)}">
					<div class="row">
						<div class="col-md-12 pad-btm">
							<sec:authorize access="hasRole('ROLE_INSERIR_SOBRE_AVISO')">
								<div id="btn-sobreaviso" class="left">
									<button class="btn btn-purple" type="button" data-target="#sobre-aviso-modal" data-toggle="modal"><span class="pli-add"></span> Informar sobre aviso</button>
								</div>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_INSERIR_AFASTAMENTO')">
								<div id="btn-afastamento" class="left">
									<button class="btn btn-success" type="button" data-target="#afastamento-modal" data-toggle="modal"><span class="pli-add"></span> Informar afastamento</button>
								</div>
							</sec:authorize>
							<sec:authorize access="hasRole('ROLE_INSERIR_ATESTADO')">
								<div id="btn-atestado" class="">
									<button class="btn btn-pink" type="button" data-target="#atestado-modal" data-toggle="modal"><span class="pli-add"></span> Informar atestado</button>
								</div>
							</sec:authorize>
						</div>
					</div>
					<div class="table-responsive">
						<table class="table table-striped table-bordered" id="dt-apontamentos">
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
									<th class="text-center table-color" style="color: white">Atestado</th>
									<th class="text-center table-color" style="color: white">Horas</th>
									<th class="text-center table-color" style="color: white">Banco</th>
									<th class="text-center table-color" style="color: white">Ad. Not.</th>
									<th class="text-center table-color" style="color: white">S.A</th>
									<th class="text-center table-color" style="color: white">S.T</th>
								</tr>
							</thead>
							<tbody class="text-center">
								<c:forEach items="${dias }" var="dia" varStatus="i">
									<tr class="linha">
										<fmt:formatDate value="${dia.data }" pattern="dd/MM/yyyy" var="data"/>
									
										<input type="hidden" id="infoDia" value="${data }, ${dia.diaDaSemana}"/>
										<td>${data }</td>
											<c:choose>
												<c:when test="${dia.mesFechado}">
													<td id="diaDaSemanaId${i.count}" class="text-left">${dia.diaDaSemana }</td>
												</c:when>
												<c:otherwise>
													<td id="diaDaSemanaId${i.count}" style="cursor: pointer;"
														onClick="abrirModalDiaSemana('${data }', '${dia.diaDaSemana }', this)"
														class="text-left">${dia.diaDaSemana }</td>
												</c:otherwise>
											</c:choose>
											<c:forEach begin="0" end="7" varStatus="cont">
											<c:if test="${not empty dia.apontamentos[cont.index] && not empty dia.apontamentos[cont.index].horario}">
												<c:choose>
													<c:when test="${dia.apontamentos[cont.index].tipoImportacao || dia.mesFechado}">
														<td id="apontamento${cont.count + 8 * (i.count - 1)}" class="">${dia.apontamentos[cont.index].horario }${dia.apontamentos[cont.index].tipoImportacao? 'E':''}</td>
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
										<td id="#">${dia.qtdAtestadoHorasString }</td>
										<td id="total-hora${i.count }">${dia.horaTotalString }</td>
										<td class="banco-hora-js">${dia.bancoHoraString }</td>
										<td class="adic-noturno-js">${dia.adicNoturnoString }</td>
										<td id="#" class="sa-js">${dia.sobreAvisoString }</td>
										<td id="#" class="sat-js">${dia.sobreAvisoTrabalhadoString }</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					</c:if>
				</div>
				<!--===================================================-->
				<!--End Data Table-->
			</div>
		</div>
		<!--===================================================-->
		<!--End page content-->
	</layout:put>
	<layout:put block="scripts">

		<sec:authorize access="hasRole('ROLE_INSERIR_APONTAMENTO')">
			<!--Apontamento POR DIA DA SEMANA Bootstrap Modal-->
			<!--===================================================-->
			<div class="modal" id="apontamentos-DiaSemana-modal" role="dialog"
				tabindex="-1" aria-labelledby="apontamentos-DiaSemana-modal"
				aria-hidden="true">
				<div class="modal-dialog modal-sm" style="width: 696px">
					<div class="modal-content">
						<form id="form-timeSemana" class="clear-form">
							<!--Modal header-->
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<i class="pci-cross pci-circle"></i>
								</button>
								<h4 class="modal-title" id="dataSemanaEscolhida">Dia da
									semana</h4>
							</div>

							<!--Modal body-->
							<div class="modal-body table-respon">
								<p class="text-main">Informe os horários a
									serem adicionados ou alterados:</p>
								<input type="hidden" id="apontamento-idDiaDaSemana" />
								<br />
								<!--Bootstrap Timepicker : Component-->
								<!--===================================================-->
								<table class="table table-striped table-bordered">
									<tr align="center" style="background-color:#eff3f7">
										<th style="background-color:#ECF0F5" align="center" >1-Entr.</th>
										<th style="background-color:#ECF0F5" align="center">1-Saída</th>
										<th align="center">2-Entr.</th>
										<th align="center">2-Saída</th>
										<th style="background-color:#ECF0F5" align="center">3-Entr.</th>
										<th style="background-color:#ECF0F5" align="center">3-Saída</th>
										<th align="center">4-Entr.</th>
										<th align="center">4-Saída</th>
									</tr>
									<tr style="background-color:#f7f7f7">
										<td><div style="width: 66px" class="input-group"
												id="apSemanaE1">
												<input data-mask="00:00" style="width: 66px" id="apDia1"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px" class="input-group"
												id="apSemanaS1">
												<input data-mask="00:00" style="width: 66px" id="apDia2"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px; class="input-group"
												id="apSemanaE2">
												<input data-mask="00:00" style="width: 66px"
													style="width:50px" id="apDia3" type="text"
													class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px; class="input-group"
												id="apSemanaS2">
												<input data-mask="00:00" style="width: 66px" id="apDia4"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px" class="input-group"
												id="apSemanaE3">
												<input data-mask="00:00" style="width: 66px" id="apDia5"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px" class="input-group"
												id="apSemanaS3">
												<input data-mask="00:00" style="width: 66px" id="apDia6"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px; class="input-group"
												id="apSemanaE4">
												<input data-mask="00:00" style="width: 66px" id="apDia7"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
										<td><div style="width: 66px; class="input-group"
												id="apSemanaS4">
												<input data-mask="00:00" style="width: 66px" id="apDia8"
													type="text" class="form-control time" placeholder="--:--">
											</div></td>
									</tr>
								</table>
							</div>
							<div class="modal-footer">
								<button class="btn btn-default" data-dismiss="modal"
									type="button" id="btn-cancelar-Semana">Cancelar</button>
								<button class="btn btn-success" id="btn-confirmar-DiaSemana"
									onClick="confirmarAlteracaoApontDiaSemana()" type="button">Salvar</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!--===================================================-->
			<!--End Default Bootstrap Modal-->
		</sec:authorize>

		<sec:authorize access="hasRole('ROLE_INSERIR_APONTAMENTO')">
			<!--Apontamento Bootstrap Modal-->
			<!--===================================================-->
			<div class="modal" id="demo-default-modal" role="dialog"
				tabindex="-1" aria-labelledby="demo-default-modal"
				aria-hidden="true">
				<div class="modal-dialog modal-sm">
					<div class="modal-content">
						<form id="form-time" class="clear-form">
							<!--Modal header-->
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">
									<i class="pci-cross pci-circle"></i>
								</button>
								<h4 class="modal-title" id="title-modal-apontamento">01/08/2017,
									terça-feira</h4>
							</div>

							<!--Modal body-->
							<div class="modal-body">
								<p class="text-semibold text-main">Informe o horário:</p>

								<input type="hidden" id="apontamento-id" /> <input
									type="hidden" id="idApontamento" />
								<!--Bootstrap Timepicker : Component-->
								<!--===================================================-->
								<div class="input-group" id="div-apontamento-hora">
									<input id="apontamento-time" name="hora" type="text"
										class="form-control time" placeholder="--:--"> <span
										class="input-group-btn">
										<button type="button"
											onclick="setarFinalDoDia('#apontamento-time')"
											class="btn btn-default" data-toggle="tooltip"
											title="final do dia">
											<i class="pli-clock"></i>
										</button>
									</span>
								</div>
								<div class="form-group pad-top">
									<label class="control-label">Justificativa</label> <select
										class="selectpicker clear-select" data-live-search="true"
										data-width="100%" id="apontamento-jus">
										<c:forEach items="${justificativas }" var="jus">
											<option value="${jus.id }">${jus.descricao }</option>
										</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label class="control-label">Observações</label>
									<textarea id="apontamento-obs" rows="2" maxlength="200"
										class="form-control"></textarea>
								</div>
							</div>
							<div class="modal-footer">
								<button class="btn btn-danger" type="button"
									onclick="confirmarRemover()" id="btn-cancelar-apontamento">Cancelar</button>
								<button class="btn btn-success" id="btn-form-time" type="button">Salvar</button>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!--===================================================-->
			<!--End Default Bootstrap Modal-->
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_INSERIR_SOBRE_AVISO')">
			<!--sobre aviso Modal-->
			<!--===================================================-->
			<div class="modal fade" id="sobre-aviso-modal" role="dialog"
				tabindex="-1" aria-labelledby="sobre-aviso-modal" aria-hidden="true">
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
									<div class="col-sm-4">
										<div class="form-group sa-data">
											<label class="control-label">Data</label> <input type="date"
												name="data" class="form-control data" id="dt-sa"
												placeholder="dd/mm/aaaa" min="${dataMinima }" /> <span
												id="mensagemData-js" class="hide" style="color: #a94442;"></span>
										</div>
									</div>
									<div class="col-sm-6">
										<label class="control-label">Período</label>
										<div class="input-daterange input-group sa-periodo"
											id="datepicker">
											<input id="hora-sa-e" name="entrada" type="text"
												class="form-control clock" placeholder="--:--"> <span
												class="input-group-addon">até</span>
											<div class="input-group date">
												<input id="hora-sa-s" name="saida" type="text"
													class="form-control clock" placeholder="--:--"> <span
													class="input-group-btn">
													<button type="button"
														onclick="setarFinalDoDia('#hora-sa-s')"
														class="btn btn-default" data-toggle="tooltip"
														data-placement="top" title="Apontar final do dia">
														<i class="pli-clock"></i>
													</button>
												</span>
											</div>
										</div>
										<span id="mensagemHora-js" class="hide"
											style="color: #a94442;"></span>
									</div>

									<div class="col-sm-1" style="margin-top: 23px;">
										<button class="btn btn-success" type="button"
											onclick="inserirSA()">Inserir</button>
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
										<c:forEach items="${sobreAvisos }" var="sa">
											<fmt:formatDate value="${sa.data }" pattern="dd/MM/yyyy"
												var="data" />
											<tr>
												<td>${data }</td>
												<td>${sa.entrada }</td>
												<td>${sa.saida }</td>

												<c:if test="${sa.mesFechado }">
													<td>Remover</td>
												</c:if>

												<c:if test="${!sa.mesFechado }">
													<td><a class="text-danger"
														onclick="removerSA(this, ${sa.id})" href="#">Remover</a></td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--===================================================-->
			<!--End sobre aviso Modal-->
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_INSERIR_AFASTAMENTO')">
			<!--afastamento Modal-->
			<!--===================================================-->
			<div class="modal fade" id="afastamento-modal" role="dialog"
				tabindex="-1" aria-labelledby="afastamento-modal" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">

						<!--Modal header-->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<i class="pci-cross pci-circle"></i>
							</button>
							<h4 class="modal-title">Gerenciar afastamento</h4>
						</div>

						<!--Modal body-->
						<div class="modal-body">
							<form action="" class="clear-form">
								<div class="row pad-btm">
									<div class="col-sm-7">
										<label class="control-label">Período</label>
										<div class="input-daterange input-group" id="datepicker">
											<input type="date" id="afastamentoDe"
												class="form-control data" name="start"
												placeholder="dd/mm/aaaa" min="${dataMinima }" /> <span
												class="input-group-addon">até</span> <input type="date"
												id="afastamentoAte" class="form-control data" name="end"
												placeholder="dd/mm/aaaa" min="${dataMinima }" />
										</div>
										<span id="mensagemDataAfastamento-js" class="hide"
											style="color: #a94442;"></span>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-8">
										<div>
											<label class="control-label">Justificativa</label> <select
												class="selectpicker clear-select" data-live-search="true"
												data-width="100%" id="afastamentoJus">
												<option class="mudar-cor" value="">Selecione</option>
												<c:forEach items="${tipoAfastamentos}" var="tp">
													<option value="${tp.id }">${tp.descricao }</option>
												</c:forEach>
											</select>
										</div>
										<span id="mensagemSelectAfastamento-js" class="hide"
											style="color: #a94442;"></span>
									</div>
									<div class="col-sm-1" style="margin-top: 23px;">
										<button class="btn btn-success" type="button"
											onclick="inserirAfastamento()">Inserir</button>
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
										<c:forEach items="${afastamentos }" var="af">
											<fmt:formatDate value="${af.dataInicio }"
												pattern="dd/MM/yyyy" var="inicio" />
											<fmt:formatDate value="${af.dataFim }" pattern="dd/MM/yyyy"
												var="fim" />
											<tr>
												<td>${inicio }</td>
												<td>${fim }</td>
												<td>${af.tipoAfastamento.descricao }</td>

												<c:if test="${af.mesFechado }">
													<td>Remover</td>
												</c:if>

												<c:if test="${!af.mesFechado }">
													<td><a class="text-danger"
														onclick="removerAfastamento(this, ${af.id})" href="#">Remover</a></td>
												</c:if>

											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--===================================================-->
			<!--End afastamento Modal-->
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_INSERIR_ATESTADO')">
			<!--atestado Modal-->
			<!--===================================================-->
			<div class="modal fade" id="atestado-modal" role="dialog"
				tabindex="-1" aria-labelledby="atestado-modal" aria-hidden="true">
				<div class="modal-dialog">
					<div class="modal-content">

						<!--Modal header-->
						<div class="modal-header">
							<button type="button" class="close" data-dismiss="modal">
								<i class="pci-cross pci-circle"></i>
							</button>
							<h4 class="modal-title">Gerenciar atestados</h4>
						</div>

						<!--Modal body-->
						<div class="modal-body">
							<form action="" class="clear-form">
								<div class="row mar-btm">
									<div class="col-sm-4">
										<label class="control-label">Data</label> <input type="date"
											id="atestadoData" class="form-control"
											placeholder="dd/mm/aaaa" min="${dataMinima }" /> <span
											id="mensagemDataAtestado-js" class="hide"
											style="color: #a94442;"></span>
									</div>
									<div class="col-sm-3">
										<label class="control-label">Quantidade de horas</label> <input
											id="qtd-hr-atestado" type="number" step="0.5"
											class="form-control" placeholder="00,00" min="0.5" max="8">
										<span id="mensagemHoraAtestado-js" class="hide"
											style="color: #a94442;"></span>
									</div>
								</div>
								<div class="row">
									<div class="col-sm-7">
										<div class="form-group">
											<label class="control-label">Justificativa</label> <select
												class="selectpicker clear-select mudar-cor"
												data-live-search="true" data-width="100%" id="atestadoJus">
												<c:forEach items="${tipoAtestado}" var="tp">
													<option value="${tp.id }">${tp.descricao }</option>
												</c:forEach>
											</select>
										</div>
									</div>
									<div class="col-sm-1" style="margin-top: 23px;">
										<button class="btn btn-success" type="button"
											onclick="inserirAtestado()">Inserir</button>
									</div>
								</div>
								<span id="mensagemSelectAtestado-js" class="hide"
									style="color: #a94442;"></span>
							</form>
							<div class="table-responsive">
								<table class="table table-striped table-bordered">
									<thead>
										<tr class="">
											<th class="text-center">Data</th>
											<th class="text-center">Qtd de horas</th>
											<th class="text-center">Justificativa</th>
											<th class="text-center">Ação</th>
										</tr>
									</thead>
									<tbody class="text-center" id="body-atestado">
										<c:forEach items="${atestados }" var="at">
											<fmt:formatDate value="${at.controleDiario.data }"
												pattern="dd/MM/yyyy" var="data" />
											<tr>
												<td>${data }</td>
												<td class="formatNumber">${at.quantidadeHora }</td>
												<td>${at.tipoAtestado.descricao }</td>

												<c:if test="${sa.mesFechado }">
													<td>Remover</td>
												</c:if>

												<c:if test="${!sa.mesFechado }">
													<td><a class="text-danger"
														onclick="removerAtestado(this, ${at.id})" href="#">Remover</a></td>
												</c:if>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--===================================================-->
			<!--End atestado Modal-->
		</sec:authorize>

		<!--erro Modal-->
		<!--===================================================-->
		<div id="erro-sm-modal" class="modal fade" tabindex="-1">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title text-center">Atenção!</h4>
					</div>
					<div class="content">
						<h4 class="text-center" id="erro-label"></h4>
					</div>
					<div class="modal-footer">
						<div class="pull-right">
							<button data-dismiss="modal" class="btn btn-primary"
								type="button">OK</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->
		<!--remover Modal-->
		<!--===================================================-->
		<div id="remover-sm-modal" class="modal fade" tabindex="-1">
			<div class="modal-dialog modal-sm">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">
							<i class="pci-cross pci-circle"></i>
						</button>
						<h4 class="modal-title text-center">Atenção!</h4>
					</div>
					<div class="content">
						<h4 class="text-center">Deseja remover este apontamento?</h4>
					</div>
					<div class="modal-footer">
						<div class="pull-right">
							<button data-dismiss="modal" class="btn btn-danger" type="button">Não</button>
							<button class="btn btn-success" type="button"
								id="btn-remover-apontamento" onclick="removerApontamento()">Sim</button>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!--===================================================-->

		<div id="fechamento-apontamento-modal" class="modal fade"
			tabindex="-1">
			<div class="modal-dialog modal-md">
				<div class="modal-content text-center">
					<div class="modal-header" style="background-color: #e95350">
						<div class="row">
							<button type="button" class="close" data-dismiss="modal">
								<i class="glyphicon glyphicon-remove"></i>
							</button>
						</div>
						<div class="" style="padding-top: 9%; padding-bottom: 9%;">
							<img src='/pause/img/icon-alert.png' width="150" height="150" />
						</div>
					</div>
					<div class="content" style="padding-bottom: 10%; padding-top: 10%;">
						<span class="text-center" id="vaiFecharBrother"
							style="color: black; font-size: 19px">No segundo dia útil
							de cada mês os apontamentos referentes ao mês anterior serão
							bloqueados. Regularize seus apontamentos.</span>
					</div>
				</div>
			</div>
		</div>

		<script
			src='<c:url value="plugins/bootstrap-timepicker/bootstrap-timepicker.min.js"/>'></script>
		<script
			src='<c:url value="plugins/datatables/media/js/jquery.dataTables.js"/>'></script>
		<script
			src='<c:url value="plugins/datatables/media/js/dataTables.bootstrap.js"/>'></script>
		<script src='<c:url value="js/custom/datatable-custom.js"/>'></script>
		<script
			src='<c:url value="/plugins/bootstrap-validator/bootstrapValidator.min.js"/>'></script>
		<script src='<c:url value="/plugins/masked-input/jquery.mask.js"/>'></script>
		<script src='<c:url value="/js/custom/send-ajax.js"/>'></script>
		<script
			src='<c:url value="js/custom/gerenciar-apontamentos-modais.js"/>'></script>
		<script
			src='<c:url value="js/custom/gerenciar-apontamentos-core.js"/>'></script>
		<script>
		
		var cor = '#a94442';
		$(document).ready(function() {
			$('#apontamento-time').mask('00:00');
			
			$('.periodo').prop('max',function(){
		        return new Date().toJSON().split('T')[0];
		    }); 
			
			$('#bv-form').bootstrapValidator({
				excluded : [ ':disabled' ],
				fields : {
					periodo : {
						validators : {
							date: {
								min: $("#periodoAte").prop('min'),
								max: $("#periodoAte").prop('max'),
								message:'Data inválida.'
							}
						}
					},
					idFuncionario : {
						validators : {
							notEmpty : {
								message : 'O campo é obrigatório.'
							}
						}
					}
				}
			});
			$('#filtrar-js').on('click', function () {
			    var startDate = document.getElementById("periodoDe").value;
				var endDate = document.getElementById("periodoAte").value;

				if ((Date.parse(endDate) < Date.parse(startDate))) {
					if ($("#erro-periodo").length == 0){
						$('#periodo-js')
							.append('<small class="help-block" id="erro-periodo">A segunda data deve ser superior ou igual à primeira.</small>')
					}
			        return false;
				}
			});
			$('#btn-form-time').on('click', function () {
			    var erro = false;
				if (!$('#apontamento-time').val() && $('#apontamento-time').val() == "") {
					if ($("#aponta-time-erro").length == 0)
						$('#div-apontamento-hora').after('<small class="help-block" id="aponta-time-erro">Campo obrigatório.</small>')
			        erro = true
			    }
			
				if(!erro){
					informarHorario();
				}
			});
			$('#apontamento-time').on('change', function () {
				if ($('#apontamento-time').val() && $('#apontamento-time').val() != "") {
					$('#div-apontamento-hora').next('small').remove();
			    }
			});
			$('#apontamento-jus').on('change', function () {
				if ($('#apontamento-jus').val() != "0") {
					$('#apontamento-jus').next('small').remove();
			    }
			});
			
			$('#qtd-hr-atestado').on('blur', function () {
				if ($('#qtd-hr-atestado').val() > 8) {
					$('#qtd-hr-atestado').val(8);
			    } else if ($('#qtd-hr-atestado').val() < 0.5) {
					$('#qtd-hr-atestado').val(0.5);
			    }
			});
			
		});
				
		$('#apDia1').change(function(){ validarMaskHora(); });
		$('#apDia2').change(function(){ validarMaskHora(); });
		$('#apDia3').change(function(){ validarMaskHora(); });
		$('#apDia4').change(function(){ validarMaskHora(); });
		$('#apDia5').change(function(){ validarMaskHora(); });
		$('#apDia6').change(function(){ validarMaskHora(); });
		$('#apDia7').change(function(){ validarMaskHora(); });
		$('#apDia8').change(function(){ validarMaskHora(); });
		
		$('#apontamentos-DiaSemana-modal').on('hidden.bs.modal', function() {
					document.getElementById("btn-confirmar-DiaSemana").disabled = false;
					for(var i = 1; i < 9; i++){ 
						$('#apDia' + i).css('border-color', "");
						$('#apDia' + i).val("");
						$('#apDia' + i).attr('placeholder', "--:--");
						$('#apDia' + i).attr('disabled', false);					
					}
		});					
		</script>

	</layout:put>
</layout:extends>