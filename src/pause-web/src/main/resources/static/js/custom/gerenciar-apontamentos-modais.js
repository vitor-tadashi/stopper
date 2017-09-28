var obrigatorio = 'Campo obrigatório.';
var cor = '#a94442';

$(document).ready(function(){
    $('.clock').timepicker({
		defaultTime: false,
		showMeridian: false,
		template: false
    });
	$(".clock").mask("99:99")
	
	$('#apontamento-funcionario').val($( "#select-funcionario option:selected" ).val());
});

function setarFinalDoDia () {
	
	id = '#apontamento-time';
	
	if ($(id).val() != '23:59:59') {
		
		$(id).mask('00:00:00');
		$(id).val('23:59:59');
		
	} else {
		
		$(id).mask('00:00');
		$(id).val('');
		
	}
	
	habilitarSalvarApontamento();
}

$('#apontamento-time').change(function () {
	var string = $('#apontamento-time').val();
	var tamanho = string.length;
	
	if (tamanho !=8 && tamanho != 5) {
		
		desabilitarSalvarApontamento();
		
	} else if (tamanho == 8) {
		
		$('#apontamento-time').val('23:59:59')
		
		habilitarSalvarApontamento();
		
	} else if ( tamanho == 5 ) {
		var horas = string.substring(0, 2);
		var minutos = string.substring(3, 5);
		
		if ( parseInt(horas) > 23 || parseInt(minutos) > 59) {
			desabilitarSalvarApontamento();
		} else {
			habilitarSalvarApontamento();
		}
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
		
	} else {
		
		$('#hora-sa-e').css({ "border-color": ""});
		$('#hora-sa-s').css({ "border-color": ""});
		$('#dt-sa').css({ "border-color": "" });
		
		$('#mensagemHora-js').html(obrigatorio).addClass("hide");
		$('#mensagemData-js').html(obrigatorio).addClass("hide");
		
		inserirSA_ajax(dataSA, horaEntrada, horaSaida);
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
					$(this).find('td:last').html('0.0');
				}
				
			});
			
			$(click).parent().parent().remove();
		},
		error: function(erro){
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
		}
	});
}
function inserirSA_ajax(dataSA, horaSAe, horaSAs){
	var sobreAviso={	
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
			
			$('.linha').each(function() {
				
				var string = $(this).find("#infoDia").val().substring(0, 10); // 22/09/2017
				var ano = string.substring(6, 10);
				var mes = string.substring(3, 5);
				var dia = string.substring(0, 2);
				
				string = ano + '-' + mes + '-' + dia;
				
				var dataSobreAvisoInserido = data.data.substring(0, 10); // 2017-09-22
				
				if (dataSobreAvisoInserido == string) {
					$(this).find('td:last').html(valorSobreAviso);
				}
				
			});
			
			clearForm(1);
		},
		error: function(erro){
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
		}
	});
}
function inserirAfastamento(){
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
		
		inserirAfastamento_ajax(de, ate, tpId, tpDesc);
	}
}
function inserirAfastamento_ajax(de, ate, tpId, tpDesc){
	var afastamento={	
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
			clearForm(2);
		},
		error: function(erro){
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
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
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
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
		
		inserirAtestado_ajax(data, qtd_hr, tpId, tpDesc);
		
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
					.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerAfastamento(this ,'+ data.id +')'))
				)
			);
			clearForm(3);
		},
		error: function(erro){
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
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
			$('#erro-label').text(erro.responseText);
			$('#erro-sm-modal').modal();
		}
	});
}
function clearForm(i){
	$('.clear-form')[i].reset();
	$('.clear-select').prop('selectedIndex',0);
	$('.clear-select').selectpicker('refresh');
}