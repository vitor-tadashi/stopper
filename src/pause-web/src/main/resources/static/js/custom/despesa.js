function abrirModal(){
	$('#add-despesa-modal').modal();
}

function submiterDespesa() {
	
	validarFormDespesa();
	
}

function validarFormDespesa() {
	var erro = false;
	
	var dataDespesa = $('#dataDespesa').val();
	var valorDespesa = $('#valorDespesa').val();
	var tipoDespesa = $('#select-tipo-despesa').val()  ;
	var centroCusto = $('#select-centro-custo').val()  ;
	var justificativa = $('#justificativaDespesa').val();

	if(!dataDespesa && dataDespesa == "") {
		$('#dataDespesa').css('border-color', 'red');
		erro = true;
	} else {
		$('#dataDespesa').css('border-color', '');
	}
	
	if(!valorDespesa && valorDespesa == "") {
		$('#valorDespesa').css('border-color', 'red');
		erro = true;
	} else {
		$('#valorDespesa').css('border-color', '');
	}
	
	if(!tipoDespesa && tipoDespesa == "") {
		$('#tipoDespesaDiv').css({'border': '1px solid red', 'padding-left': '1px'});
		erro = true;
	} else {
		$('#tipoDespesaDiv').css({'border': '', 'padding-left': ''});
	}
	
	if(!centroCusto && centroCusto == "") {
		$('#centroCustoDiv').css({'border': '1px solid red', 'padding-left': '1px'});
		erro = true;
	} else {
		$('#centroCustoDiv').css({'border': '', 'padding-left': ''});
	}
	
	if(!justificativa && justificativa == "") {
		$('#justificativaDespesa').css('border-color', 'red');
		erro = true;
	} else {
		$('#justificativaDespesa').css('border-color', '');
	}
	
	if (!erro) {
		enviarFormDespesa();
	}
}

function enviarFormDespesa() {
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
			if (erro.status === 403) {
				location.reload();
			} else {
				$("#span-msg").html("Erro!" + erro.status);
				$('#add-despesa-modal').modal("hide");
			}
		}
	});
}
