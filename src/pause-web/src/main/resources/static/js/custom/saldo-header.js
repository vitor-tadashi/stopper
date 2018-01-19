$(document).ready(function() {
	atualizarSaldosConsolidados();
});
function atualizarSaldosConsolidados() {
	$.ajax({
		url : 'gerenciar-apontamento/atualizar-saldos-consolidados',
		type : 'GET',
		contentType : 'application/json',
		success : function(data) {
			$("#saldoMensal").text(decimalParaHorasMinutos(data[0]));
			$("#trimestreAnterior").attr(
					'data-original-title',
					"Saldo trimestre anterior: "
							+ decimalParaHorasMinutos(data[1]));
			$("#trimestreAtual").text(decimalParaHorasMinutos(data[2]));
		},
		error : function(erro) {
			if (erro.status === 403) {
				location.reload();
			}
		}
	});
}
function decimalParaHorasMinutos(tempoDecimal) {
	var hora, minutos;
	var negativo = 0;
	

	if (tempoDecimal < 0)
		negativo = 1;

	minutos = tempoDecimal % 1;
	hora = tempoDecimal - minutos;
	minutos *= 60;
	minutos = Math.round(minutos);

	if (negativo == 1)
		minutos = minutos * (-1);

	if (minutos < 10)
		return hora + 'h0' + minutos + 'min';
	else
		return hora + 'h' + minutos + 'min';
}