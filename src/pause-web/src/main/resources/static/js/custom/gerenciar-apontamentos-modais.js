var obrigatorio = 'Campo obrigatório.';
var cor = '#a94442';
var valorQueTava;
var submit = false;
var dataBloqueio = '01/04/2018';
var pivotApontamentoDiaSemana;
var horaInit1, horaInit2, horaInit3, horaInit4, horaInit5, horaInit6, horaInit7, horaInit8;
var erroAnteriorFoiHoraIgual = 0;
var indiceErro1, indiceErro2;
var horasParaSalvar, idSemanaEscolhida, dataEscolhidaFim; 
var colocouApontamentos = 0;

$(document).ready(function(){
	exibirAviso();
	
    $('.clock').timepicker({
		defaultTime: false,
		showMeridian: false,
		template: false
    });
	$(".clock").mask("99:99")
	
	$('#apontamento-funcionario').val($( "#select-funcionario option:selected" ).val());
});

function setarFinalDoDia (id) {
	
	if ($(id).val() != '23:59:59') {
		
		valorQueTava = $(id).val();
		
		$(id).mask('00:00:00');
		$(id).val('23:59:59');
		
	} else {
		
		$(id).mask('00:00');
		$(id).val(valorQueTava);
		
	}
	
	habilitarSalvarApontamento();
}

$('#apontamento-time').change(function () {
	
	var string = $('#apontamento-time').val();
	var tamanho = string.length;
	
	if (tamanho !=8 && tamanho != 5) {
		
		desabilitarSalvarApontamento();
		
	}  else if ( tamanho == 5 ) {
		var horas = string.substring(0, 2);
		var minutos = string.substring(3, 5);
		
		if ( parseInt(horas) > 23 || parseInt(minutos) > 59) {
			desabilitarSalvarApontamento();
		} else {
			habilitarSalvarApontamento();
		}
	} else if (tamanho == 8) {
		
		$('#apontamento-time').val('23:59:59')
		
		habilitarSalvarApontamento();
		
	}
});

function habilitarSalvarApontamento () {
	$('#apontamento-time').css('border-color', "");
	$('#btn-form-time').attr('disabled', false);
}

function desabilitarSalvarApontamento () {
	$('#apontamento-time').css('border-color', cor);
	$('#btn-form-time').attr('disabled', true);
}

function inserirSA(){
	var dataSA = formatardataHtml($('#dt-sa').val());
	var horaEntrada = $('#hora-sa-e').val();
	var horaSaida = $('#hora-sa-s').val();
	
	if (horaEntrada == "" || horaSaida == "") {
		$('#hora-sa-e').css({ "border-color": cor });
		$('#hora-sa-s').css({ "border-color": cor });
		$('#mensagemHora-js').html(obrigatorio).removeClass("hide");
		
		if (dataSA == "") {
			$('#dt-sa').css({ "border-color": cor });
			$('#mensagemData-js').html(obrigatorio).removeClass("hide");
		} else {
			$('#dt-sa').css({ "border-color": "" });
			$('#mensagemData-js').html(obrigatorio).addClass("hide");
		}
		
	} else if (dataSA == "") {
		
		$('#mensagemHora-js').html(obrigatorio).addClass("hide");
		$('#hora-sa-e').css({ "border-color": ""});
		$('#hora-sa-s').css({ "border-color": ""});
		
		$('#dt-sa').css({ "border-color": cor });
		$('#mensagemData-js').html(obrigatorio).removeClass("hide");
		
	}else if(CompararHoras(horaEntrada, horaSaida) === -1){
		$('#hora-sa-e').css({ "border-color": cor });
		$('#hora-sa-s').css({ "border-color": cor });
		$('#mensagemHora-js').html("A hora de saída deve ser superior à primeira.").removeClass("hide");
	} 
	else {
		
		$('#hora-sa-e').css({ "border-color": ""});
		$('#hora-sa-s').css({ "border-color": ""});
		$('#dt-sa').css({ "border-color": "" });
		
		$('#mensagemHora-js').html(obrigatorio).addClass("hide");
		$('#mensagemData-js').html(obrigatorio).addClass("hide");
		
		if(!submit){
			submit = true;
			inserirSA_ajax(dataSA, horaEntrada, horaSaida);
		}
	}
}

function removerSA(click, id){
	$.ajax({
		url: 'sobre-aviso/remover/'+id,
		type : 'DELETE',
		contentType : 'application/json',
		dataType : 'json',
		success: function(data){
			
			var dataSobreavisoRemovido = $(click).parent().parent().find('td:first').html();
			
			$('.linha').each(function() {
				
				var string = $(this).find("#infoDia").val().substring(0, 10); // 22/09/2017
				
				if (dataSobreavisoRemovido == string) {
					$(this).find('.sa-js').html('0.0');
					$(this).find('.sat-js').html('0.0');
				}
				
			});
			$(click).parent().parent().remove();
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
		}
	});
}

function inserirSA_ajax(dataSA, horaSAe, horaSAs){
	var sobreAviso = {	
			'data' : dataSA,
			'entrada' : horaSAe,
			'saida' : horaSAs,
			'idFuncionario' : $('#apontamento-funcionario').val()
		}
	$.ajax({
		url: 'sobre-aviso/inserir-sa',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(sobreAviso),
		cache: false,
		success: function(data){
			$('#body-sa')
			.append($('<tr>')
				.append($('<td>').text(dataSA))
				.append($('<td>').text(horaSAe))
				.append($('<td>').text(horaSAs))
				.append($('<td>')
					.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerSA(this ,'+ data.id +')'))
				)
			);
			
			var valorSobreAviso = data.controleDiario.sobreAviso;
			var valorSobreTrabalhado = data.controleDiario.sobreAvisoTrabalhado;
			
			valorSobreAviso = valorSobreAviso.toString().replace('.', ',');
			valorSobreTrabalhado = valorSobreTrabalhado.toString().replace('.', ',');
			
			$('.linha').each(function() {
				
				var string = $(this).find("#infoDia").val().substring(0, 10); // 22/09/2017
				var ano = string.substring(6, 10);
				var mes = string.substring(3, 5);
				var dia = string.substring(0, 2);
				
				string = ano + '-' + mes + '-' + dia;
				
				var dataSobreAvisoInserido = data.data.substring(0, 10); // 2017-09-22
				
				if (dataSobreAvisoInserido == string) {
					$(this).find('.sa-js').html(valorSobreAviso);
					$(this).find('.sat-js').html(valorSobreTrabalhado);
				}
				
			});
			submit = false;
			clearForm(1);
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
			submit = false;
		}
	});
}

function inserirAfastamento (){
	var de = formatardataHtml($('#afastamentoDe').val());
	var ate = formatardataHtml($('#afastamentoAte').val());
	var tpDesc = $('#afastamentoJus option:selected').text();
	var tpId = $('#afastamentoJus option:selected').val();
	
	if ( tpId == "" ) {
		$('.mudar-cor').css({ "border-color" : cor });
		$('#mensagemSelectAfastamento-js').html(obrigatorio).removeClass("hide");
		
		if ( de == "" || ate == "") {
			$('#afastamentoDe').css({ "border-color" : cor });
			$('#afastamentoAte').css({ "border-color" : cor });
			$('#mensagemDataAfastamento-js').html(obrigatorio).removeClass("hide");
		} else {
			$('#afastamentoDe').css({ "border-color" : "" });
			$('#afastamentoAte').css({ "border-color" : "" });
			$('#mensagemDataAfastamento-js').html(obrigatorio).addClass("hide");
		}
		
	} else if ( de == "" || ate == "") {
		$('.mudar-cor').css({ "border-color" : "" });
		$('#mensagemSelectAfastamento-js').html(obrigatorio).addClass("hide");
		
		$('#afastamentoDe').css({ "border-color" : cor });
		$('#afastamentoAte').css({ "border-color" : cor });
		$('#mensagemDataAfastamento-js').html(obrigatorio).removeClass("hide");
		
	} else {
		
		$('#afastamentoDe').css({ "border-color" : "" });
		$('#afastamentoAte').css({ "border-color" : "" });
		$('#mensagemDataAfastamento-js').html(obrigatorio).addClass("hide");
		
		$('.mudar-cor').css({ "border-color" : "" });
		$('#mensagemSelectAfastamento-js').html(obrigatorio).addClass("hide");
		
		if(!submit){
			submit = true;
			inserirAfastamento_ajax(de, ate, tpId, tpDesc);
		}
	}
}

function inserirAfastamento_ajax (de, ate, tpId, tpDesc) {
	
	var afastamento = {	
			'dataInicio' : de,
			'dataFim' : ate,
			'tipoAfastamento' : {
				'id' : tpId
			},
			'idFuncionario' : $('#apontamento-funcionario').val()
		}
	
	$.ajax({
		url: 'afastamento/inserir',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(afastamento),
		cache: false,
		success: function(data){
			$('#body-afastamento')
			.append($('<tr>')
				.append($('<td>').text(de))
				.append($('<td>').text(ate))
				.append($('<td>').text(tpDesc))
				.append($('<td>')
					.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerAfastamento(this ,'+ data.id +')'))
				)
			);
			submit = false;
			clearForm(2);
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
			submit = false;
		}
	});
}

function removerAfastamento(click, id){
	$.ajax({
		url: 'afastamento/remover/'+id,
		type : 'DELETE',
		contentType : 'application/json',
		dataType : 'json',
		success: function(data){
			$(click).parent().parent().remove();
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
		}
	});
}

function inserirAtestado(){
	var data = formatardataHtml($('#atestadoData').val());
	var qtd_hr = $('#qtd-hr-atestado').val();
	var tpDesc = $('#atestadoJus option:selected').text();
	var tpId = $('#atestadoJus option:selected').val();
	
	if ( data == "" ) {
		
		$('#atestadoData').css({ "border-color" : cor });
		$('#mensagemDataAtestado-js').html(obrigatorio).removeClass("hide");
		
		if ( qtd_hr == "" ) {
			
			$('#qtd-hr-atestado').css({ "border-color" : cor });
			$('#mensagemHoraAtestado-js').html(obrigatorio).removeClass("hide");
			
			if ( tpId == "" ){
				
				$('.mudar-cor').css({ "border-color" : cor });
				$('#mensagemSelectAtestado-js').html(obrigatorio).removeClass("hide");
				
			} else {
				$('.mudar-cor').css({ "border-color" : "" });
				$('#mensagemSelectAtestado-js').html(obrigatorio).addClass("hide");
			}
			
		} else {
			
			$('#qtd-hr-atestado').css({ "border-color" : "" });
			$('#mensagemHoraAtestado-js').html(obrigatorio).addClass("hide");
			
		}
		
	} else if ( qtd_hr == "" ) {
		
		$('#atestadoData').css({ "border-color" : "" });
		$('#mensagemDataAtestado-js').html(obrigatorio).addClass("hide");
		
		$('#qtd-hr-atestado').css({ "border-color" : cor });
		$('#mensagemHoraAtestado-js').html(obrigatorio).removeClass("hide");
		
		if ( tpId == "" ){
			
			$('.mudar-cor').css({ "border-color" : cor });
			$('#mensagemSelectAtestado-js').html(obrigatorio).removeClass("hide");
			
		} else {
			$('.mudar-cor').css({ "border-color" : "" });
			$('#mensagemSelectAtestado-js').html(obrigatorio).addClass("hide");
		}
		
	} else if ( tpId == "" ){
		
		$('#atestadoData').css({ "border-color" : "" });
		$('#mensagemDataAtestado-js').html(obrigatorio).addClass("hide");
		
		$('#qtd-hr-atestado').css({ "border-color" : "" });
		$('#mensagemHoraAtestado-js').html(obrigatorio).addClass("hide");
		
		$('.mudar-cor').css({ "border-color" : cor });
		$('#mensagemSelectAtestado-js').html(obrigatorio).removeClass("hide");
	} else {
		
		$('#atestadoData').css({ "border-color" : "" });
		$('#mensagemDataAtestado-js').html(obrigatorio).addClass("hide");
		
		$('#qtd-hr-atestado').css({ "border-color" : "" });
		$('#mensagemHoraAtestado-js').html(obrigatorio).addClass("hide");
		
		$('.mudar-cor').css({ "border-color" : "" });
		$('#mensagemSelectAtestado-js').html(obrigatorio).addClass("hide");
		
		if(!submit){
			submit = true;
			inserirAtestado_ajax(data, qtd_hr, tpId, tpDesc);
		}
		
	}
}

function inserirAtestado_ajax(dt, qtdHora, tpId, tpDesc){
	var atestado={
			'controleDiario' : {
				'dataJson' : dt
			},
			'quantidadeHora' : qtdHora,
			'tipoAtestado' : {
				'id' : tpId
			},
			'idFuncionario' : $('#apontamento-funcionario').val()
		}
	$.ajax({
		url: 'atestado/inserir',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(atestado),
		cache: false,
		success: function(data){
			$('#body-atestado')
			.append($('<tr>')
				.append($('<td>').text(dt))
				.append($('<td>').text(qtdHora))
				.append($('<td>').text(tpDesc))
				.append($('<td>')
					.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerAtestado(this ,'+ data.id +')'))
				)
			);
			submit = false;
			clearForm(3);
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
			submit = false;
		}
	});
}

function removerAtestado(click, id){
	$.ajax({
		url: 'atestado/remover/'+id,
		type : 'DELETE',
		contentType : 'application/json',
		dataType : 'json',
		success: function(data){
			$(click).parent().parent().remove();
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
		}
	});
}

function clearForm(i){
	$('.clear-form')[i].reset();
	$('.clear-select').prop('selectedIndex',0);
	$('.clear-select').selectpicker('refresh');
}

function CompararHoras(sHora1, sHora2) { 
    var arHora1 = sHora1.split(":"); 
    var arHora2 = sHora2.split(":"); 
    
    var hh1 = parseInt(arHora1[0],10); 
    var mm1 = parseInt(arHora1[1],10); 
    
    var hh2 = parseInt(arHora2[0],10); 
    var mm2 = parseInt(arHora2[1],10); 

    if (hh1<hh2 || (hh1==hh2 && mm1<mm2)) 
        return 1; 
    else if (hh1>hh2 || (hh1==hh2 && mm1>mm2)) 
        return -1; 
    else  
        return 0; 
}

function exibirAviso(){
	var atual = new Date();
	var final = new Date(dataBloqueio);
	if(atual < final){
		if(document.referrer.match("/login") && localStorage.getItem("refresh") != 1){
			$("#fechamento-apontamento-modal").modal("show");
			localStorage.setItem('refresh', 1);
		}
	}
}

function abrirModalDiaSemana(dataSemana, diaSemana, td) {
	var idFuncionario;	
	var idTd = $(td).attr('id');
	$('#apontamento-idDiaDaSemana').val(idTd);	
	
	if (document.getElementById("select-funcionario") == null) idFuncionario = null;
	else idFuncionario = document.getElementById("select-funcionario").value;
	
	ajaxPreencherModalDiaDaSemana(idFuncionario, dataSemana);
	$('#dataSemanaEscolhida').text(dataSemana + ", " + diaSemana);
}

function ajaxPreencherModalDiaDaSemana(idFuncionario, dataSemana) {	
	$.ajax({
		url : 'gerenciar-apontamento/carregarApontamentosDia',
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify({
			'idFuncionario' : idFuncionario,
			'dataSemana' : dataSemana
		}),
		cache : false,
		success : function(data) {
			pivotApontamentoDiaSemana = data;

			if (data.entrada1)
				$('#apDia1').val(data.entrada1.substring(0, 5));
			if (data.saida1)
				$('#apDia2').val(data.saida1.substring(0, 5));
			if (data.entrada2)
				$('#apDia3').val(data.entrada2.substring(0, 5));
			if (data.saida2)
				$('#apDia4').val(data.saida2.substring(0, 5));
			if (data.entrada3)
				$('#apDia5').val(data.entrada3.substring(0, 5));
			if (data.saida3)
				$('#apDia6').val(data.saida3.substring(0, 5));
			if (data.entrada4)
				$('#apDia7').val(data.entrada4.substring(0, 5));
			if (data.saida4)
				$('#apDia8').val(data.saida4.substring(0, 5));
			
			horaInit1 = document.getElementById('apDia1').value;
			horaInit2 = document.getElementById('apDia2').value;
			horaInit3 = document.getElementById('apDia3').value;
			horaInit4 = document.getElementById('apDia4').value;
			horaInit5 = document.getElementById('apDia5').value;
			horaInit6 = document.getElementById('apDia6').value;
			horaInit7 = document.getElementById('apDia7').value;
			horaInit8 = document.getElementById('apDia8').value;	
			
			if (data.tipoEntrada1 == 1){
				$('#apDia1').attr('disabled', true); $('#apDia1').val(data.entrada1.substring(0, 5).concat("E"));}
			if (data.tipoSaida1 == 1) {
				$('#apDia2').attr('disabled', true); $('#apDia2').val(data.saida1.substring(0, 5).concat("E"));}
			if (data.tipoEntrada2 == 1) {
				$('#apDia3').attr('disabled', true); $('#apDia3').val(data.entrada2.substring(0, 5).concat("E"));}
			if (data.tipoSaida2 == 1) {
				$('#apDia4').attr('disabled', true); $('#apDia4').val(data.saida2.substring(0, 5).concat("E"));}
			if (data.tipoEntrada3 == 1) {
				$('#apDia5').attr('disabled', true); $('#apDia5').val(data.entrada3.substring(0, 5).concat("E"));}
			if (data.tipoSaida3 == 1) {
				$('#apDia6').attr('disabled', true); $('#apDia6').val(data.saida3.substring(0, 5).concat("E"));}
			if (data.tipoEntrada4 == 1) {
				$('#apDia7').attr('disabled', true); $('#apDia7').val(data.entrada4.substring(0, 5).concat("E"));}
			if (data.tipoSaida4 == 1) {
				$('#apDia8').attr('disabled', true); $('#apDia8').val(data.saida4.substring(0, 5).concat("E"));}
			
			$('#apontamentos-DiaSemana-modal').modal('show');
		},
		error : function(erro) {
			alert("ERRO PREENCHER MODAL " + erro);
			console.log(erro);
		}
	});
}

function confirmarAlteracaoApontDiaSemana() {
	var dataApontamento = $('#dataSemanaEscolhida').text().split(',');
	var hora1, hora2, hora3, hora4, hora5, hora6, hora7, hora8;
	document.getElementById("btn-confirmar-DiaSemana").disabled = true;

	hora1 = document.getElementById('apDia1').value;
	hora2 = document.getElementById('apDia2').value;
	hora3 = document.getElementById('apDia3').value;
	hora4 = document.getElementById('apDia4').value;
	hora5 = document.getElementById('apDia5').value;
	hora6 = document.getElementById('apDia6').value;
	hora7 = document.getElementById('apDia7').value;
	hora8 = document.getElementById('apDia8').value;
	horasParaSalvar = [ hora1, hora2, hora3, hora4, hora5, hora6, hora7, hora8 ];
	
	if (document.getElementById('apDia1').disabled == false && hora1 != ""
			&& hora1 != horaInit1
			&& pivotApontamentoDiaSemana.tipoEntrada1 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idEntrada1, hora1.substring(
				0, 5), dataApontamento[0]);
	if (document.getElementById('apDia2').disabled == false && hora2 != ""
			&& hora2 != horaInit2 && pivotApontamentoDiaSemana.tipoSaida1 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idSaida1, hora2.substring(0,
				5), dataApontamento[0]);
	if (document.getElementById('apDia3').disabled == false && hora3 != ""
			&& hora3 != horaInit3
			&& pivotApontamentoDiaSemana.tipoEntrada2 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idEntrada2, hora3.substring(
				0, 5), dataApontamento[0]);
	if (document.getElementById('apDia4').disabled == false && hora4 != ""
			&& hora4 != horaInit4 && pivotApontamentoDiaSemana.tipoSaida2 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idSaida2, hora4.substring(0,
				5), dataApontamento[0]);
	if (document.getElementById('apDia5').disabled == false && hora5 != ""
			&& hora5 != horaInit5
			&& pivotApontamentoDiaSemana.tipoEntrada3 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idEntrada3, hora5.substring(
				0, 5), dataApontamento[0]);
	if (document.getElementById('apDia6').disabled == false && hora6 != ""
			&& hora6 != horaInit6 && pivotApontamentoDiaSemana.tipoSaida3 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idSaida3, hora6.substring(0,
				5), dataApontamento[0]);
	if (document.getElementById('apDia7').disabled == false && hora7 != ""
			&& hora7 != horaInit7
			&& pivotApontamentoDiaSemana.tipoEntrada4 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idEntrada4, hora7.substring(
				0, 5), dataApontamento[0]);
	if (document.getElementById('apDia8').disabled == false && hora8 != ""
			&& hora8 != horaInit8 && pivotApontamentoDiaSemana.tipoSaida4 != 1)
		apontarDiaSemana(pivotApontamentoDiaSemana.idSaida4, hora8.substring(0,
				5), dataApontamento[0]);
	
	idSemanaEscolhida = $('#apontamento-idDiaDaSemana').val();
	var tr = $("#" + idSemanaEscolhida).parent();
	dataEscolhidaFim = dataApontamento[0];	
	
	setTimeout(function(){colocouApontamentos = 1; $('#apontamentos-DiaSemana-modal').modal('hide');}, 1200);		
}

function apontarDiaSemana(id, horario, dataApontamento) {
	var idTd = $('#apontamento-idDiaDaSemana').val();
	if(id < 0) apontarModalDiaDaSemana(horario, dataApontamento, null, idTd, horasParaSalvar);
	else apontarModalDiaDaSemana(horario, dataApontamento, id, idTd, horasParaSalvar);	
}

function validarMaskHora() {
	var erro = 0;
	
	for (var i = 1; i < 9; i++) { if(erro == 1) break;
		var string = $('#apDia' + i).val();
		var tamanho;
		
		if (string.length == 5) tamanho = string.length;
		else tamanho = string.substring(0,5).length;
		
		for (var j = 1; j < 9; j++) {
			var valorI = $('#apDia' + j).val();
			if (j == i)
				continue;
			else if (tamanho != 5 && $('#apDia' + i).val() != "") {
				document.getElementById("btn-confirmar-DiaSemana").disabled = true;
				$('#apDia' + i).css('border-color', cor);
				erro = 1;
				break;
			} else if (tamanho == 5) {
				var horas = string.substring(0, 2);
				var minutos = string.substring(3, 5);
				if (parseInt(horas) > 23 || parseInt(minutos) > 59) {
					document.getElementById("btn-confirmar-DiaSemana").disabled = true;
					$('#apDia' + i).css('border-color', cor);
					erro = 1;
					break;
				} else if (string == valorI || string.concat("E") == valorI || string == valorI.concat("E")) {
					document.getElementById("btn-confirmar-DiaSemana").disabled = true;
					$('#apDia' + i).css('border-color', cor);
					$('#apDia' + j).css('border-color', cor);
					indiceErro1 = i;
					indiceErro2 = j;
					erroAnteriorFoiHoraIgual = 1;
					erro = 1;
					break;
				} else {
					document.getElementById("btn-confirmar-DiaSemana").disabled = false;
					$('#apDia' + i).css('border-color', "");
					if (indiceErro1 != 0 && indiceErro2 != 0
							&& erroAnteriorFoiHoraIgual == 1) {
						$('#apDia' + indiceErro1).css('border-color', "");
						$('#apDia' + indiceErro2).css('border-color', "");
						erroAnteriorFoiHoraIgual = 0;
						indiceErro1 = 0;
						indiceErro2 = 0;
					}
				}
			}
		}
		if (string == "") $('#apDia' + i).css('border-color', "");
	}
}

$(document).ajaxStop(
		function() {
			setTimeout(function() {
				if (colocouApontamentos == 1) {
					colocarIdsNosAps($("#" + idSemanaEscolhida).parent(),
							dataEscolhidaFim,
							pivotApontamentoDiaSemana.idFuncionario);
					colocouApontamentos = 0;
				}
			}, 300);
		});

function colocarIdsNosAps(tr, dataSemana, idFuncionario) {
	$.ajax({
		url : 'gerenciar-apontamento/carregarApontamentosDia',
		type : 'POST',
		contentType : 'application/json',
		data : JSON.stringify({
			'idFuncionario' : idFuncionario,
			'dataSemana' : dataSemana
		}),
		cache : false,
		success : function(data) {
			var idsAps = [ data.idEntrada1, data.idSaida1, data.idEntrada2,
					data.idSaida2, data.idEntrada3, data.idSaida3,
					data.idEntrada4, data.idSaida4 ];
			var horarios = new Array(8);
			var i = parseInt(idSemanaEscolhida.substring(13, idSemanaEscolhida.length));
			i = i - 1;
			var indice = 1 + 8*i;
			for(var j = 0; j < 8; j++) { 
				var idAp = indice + j;
				if (idsAps[j] != -1) $("#apontamento" + idAp).attr('onclick',
						"dialogApontamentoHora(this," + idsAps[j] + ")");
			}
		},
		error : function(erro) {
		}
	});
}