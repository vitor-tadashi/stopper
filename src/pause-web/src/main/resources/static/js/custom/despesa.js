function abrirModal(){
	$("#span-msg").html("Modal aberta");
	$('#add-despesa-modal').modal();
}

function submiterDespesa() {
	var despesa = {
			data : $('#dataDespesa').val(),
			valor : $('#valorDespesa').val(),
			tipoDespesa : $('select-tipo-despesa').val(),
			centroCusto : $('select-centro-custo').val(),
			idFuncionario : $('#funcionario').val(),
			justificativa : $('#justificativaDespesa').val(),
			comprovanteDespesa: $("#comprovanteDespesa")
		};
	$.ajax({
		url: 'despesa',
		type : 'POST',
		contentType : 'application/json',
		data: JSON.stringify(despesa),
		cache: false,
		success: function(data){
			$("#span-msg").html(data);
			$('#add-despesa-modal').modal("hide");
		},
		error: function(erro){
			if(erro.status === 403){
				location.reload();
			}else{
				$("#span-msg").html("Erro!" + erro.status);
				$('#add-despesa-modal').modal("hide");
			}
		}
	});
}