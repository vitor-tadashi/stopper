$(document).ready(function(){
    $('.clock').timepicker({
		defaultTime: false,
		showMeridian: false,
		template: false
    });
	$(".clock").mask("99:99")
	
	$('#apontamento-funcionario').val($( "#select-funcionario option:selected" ).val());
});
function inserirSA(){
	var dataSA = formatardataHtml($('#dt-sa').val());
	var horaSAe = $('#hora-sa-e').val();
	var horaSAs = $('#hora-sa-s').val();
	
	inserirSA_ajax(dataSA, horaSAe, horaSAs);
	
}
function removerSA(click, id){
	$.ajax({
		url: 'sobre-aviso/remover',
		type : 'GET',
		contentType : 'application/json',
		data: {'id' : id},
		cache: true,
		success: function(data){
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
	
	inserirAfastamento_ajax(de, ate, tpId, tpDesc)
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
	var qtd_hr = $('#qtd-hr-atestado').val();
	var justificativa = $('#atestadoJus').text();
	
	$('#body-atestado')
		.append($('<tr>')
			.append($('<td>').text(qtd_hr))
			.append($('<td>').text(justificativa))
			.append($('<td>')
				.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerTr(this)'))
			)
		);
}
function clearForm(i){
	$('.clear-form')[i].reset();
	$('.clear-select').prop('selectedIndex',0);
	$('.clear-select').selectpicker('refresh');
}