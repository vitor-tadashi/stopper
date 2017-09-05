$(document).ready(function() {
	calcularTotal();
});
function dialogApontamentoHora(td) {
	var id = $(td).attr('id');
	var infoDia = $(td).parent().find('input[id^="infoDia"]');
	$('#title-modal-apontamento').text(infoDia.val());
	$('#apontamento-id').val(id)
	$('#demo-default-modal').modal();
}

function informarHorario() {
	var id = $('#apontamento-id').val();
	var horario = $('#apontamento-time').val();
	var dtApontamento = $('#title-modal-apontamento').text().split(',');
	
	apontar(horario,dtApontamento[0]);
	
	var tr = $("#"+id).parent();
	var horarios = [];
	$("#"+id).text(horario);
	
	$(tr).find('td[id^="apontamento"], span[id^="apontamento"]').each (function() {
		if ($(this).html() != '--:--') {
			
			var horarioSetado = $(this).html();
			var pad = "00:00";
			var ans = pad.substring(0, pad.length - horarioSetado.length) + horarioSetado;
			horarios.push(ans);
		}
	});
	
	horarios.sort();
	
	$(tr).find('td[id^="apontamento"], span[id^="apontamento"]').each (function(index) {
		if (typeof horarios[index] == 'undefined') {
			$(this).html('--:--');
			$(this).attr("class", "");
			$(this).attr("style", "cursor:pointer;");
			$(this).attr("onclick", "dialogApontamentoHora(this);");
		}
		else {
			var horarioAlterado = horarios[index];
			
			$(this).html(horarioAlterado);
			if (horarioAlterado.indexOf('E') > -1) {
				$(this).attr("class", "text-muted demo-pli-clock");
				$(this).attr("style", "");
				$(this).attr("onclick", "");
			}
			else {
				$(this).attr("class", "");
				$(this).attr("style", "cursor:pointer;");
				$(this).attr("onclick", "dialogApontamentoHora(this);");
			}                
		}
	});
	calcularTotal();
	$('#demo-default-modal').modal("hide");
}
function apontar(hr,dt){
	var apontamento = {
		horarioJson : hr,
		dataJson : dt,
		observacao : $('#apontamento-obs').val(),
		tpJustificativa : {
			id : $('#apontamento-jus').val()
		}
	};
	if($('#apontamento-funcionario').val())
		apontamento['pis'] = $('#apontamento-funcionario').val();
	$.ajax({
		url: 'gerenciar-apontamento/apontar',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(apontamento),
		cache: false,
		success: function(data){
			clearForm(0);
		}
	});
}
function calcularTotal() {
	
	$("#dt-apontamentos").find('tr').each (function() {
		var horaTotal = 0;
		var entrada = 0;
		var saida = 0;
		
		$(this).find('td[id^="apontamento"], span[id^="apontamento"]').each (function() {
			if ($(this).html() != '--:--') {
				var apontamento = $(this).html().split(':');
				if (entrada==0) {
					entrada = (parseInt(apontamento[0])*60)+parseInt(apontamento[1].replace('E', ''));	 
				}
				else {
					saida = (parseInt(apontamento[0])*60)+parseInt(apontamento[1].replace('E', ''));
					horaTotal += (saida - entrada)/60;
					entrada=0;
					saida=0;
				}
			}
		});
		
		$(this).find('td[id^="total-hora"]').text(Math.round(horaTotal*100)/100);
		horaTotal = 0;
	});	
}