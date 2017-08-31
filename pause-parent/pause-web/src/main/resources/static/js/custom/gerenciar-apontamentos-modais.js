$(document).ready(function(){
    $('.clock').timepicker({
		defaultTime: false,
		showMeridian: false,
		template: false
    });
	$(".clock").mask("99:99")
});
function inserirSA(){
	var dataSA = $('#dt-sa').val();
	var horaSAe = $('#hora-sa-e').val();
	var horaSAs = $('#hora-sa-s').val();
	
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