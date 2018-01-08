function abrirModal(){
	$("#span-msg").html("Modal aberta");
	$('#add-despesa-modal').modal();
}

function submiterDespesa() {
	var oMyForm = new FormData();
	oMyForm.append("comprovante", $('input[type=file]')[0].files[0]);
	oMyForm.append("data", $('#dataDespesa').val()         );
	oMyForm.append("valor", $('#valorDespesa').val()        );
	oMyForm.append("tipoDespesa", $('#select-tipo-despesa').val()  );
	oMyForm.append("centroCusto", $('#select-centro-custo').val()  );
	oMyForm.append("funcionario", $('#funcionario').val()         );
	oMyForm.append("justificativa", $('#justificativaDespesa').val());
	
	$.ajax({
		 url : 'despesa',
	    type: "POST",
	    data : oMyForm,
	    processData: false,
	    contentType: false,
	    enctype: 'multipart/form-data',
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
