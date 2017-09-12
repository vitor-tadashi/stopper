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
	var dataSA = $('#dt-sa').val();
	var horaSAe = $('#hora-sa-e').val();
	var horaSAs = $('#hora-sa-s').val();
	
	inserirSA_ajax(dataSA, horaSAe, horaSAs);
	
	$('#body-sa')
		.append($('<tr>')
			.append($('<td>').text(dataSA))
			.append($('<td>').text(horaSAe))
			.append($('<td>').text(horaSAs))
			.append($('<td>')
				.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerTr(this)'))
			)
		);
}
function inserirSA_ajax(dataSA, horaSAe, horaSAs){
	var sobreAviso={	
			'data' : dataSA,
			'entrada' : horaSAe,
			'saida' : horaSAs
		}
	$.ajax({
		url: 'sobre-aviso/inserir-sa',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(sobreAviso),
		cache: false,
		success: function(data){
			clearForm(1);
		}
	});
}
function inserirAfastamento(){
	var de = $('#afastamentoDe').val();
	var ate = $('#afastamentoAte').val();
	var justificativa = $('#afastamentoJus').text();
	
	$('#body-afastamento')
		.append($('<tr>')
			.append($('<td>').text(de))
			.append($('<td>').text(ate))
			.append($('<td>').text(justificativa))
			.append($('<td>')
				.append($('<a>').text('Remover').addClass('text-danger').attr('href',"#").attr('onclick','removerTr(this)'))
			)
		);
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
function removerTr(click){
	$(click).parent().parent().remove();
}
function clearForm(i){
	$('.clear-form')[i].reset();
	$('.clear-select').prop('selectedIndex',0);
	$('.clear-select').selectpicker('refresh');
}