function abrirModal(){
	$('#add-despesa-modal').modal();
	var today = new Date().toISOString().split('T')[0];
	$("#dataDespesa").attr('max', today);
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
	
	var regexNotEmpty = /\S/;

	if(!regexNotEmpty.test(dataDespesa)) {
		$('#dataDespesa').css('border-color', 'red');
		erro = true;
	} else {
		$('#dataDespesa').css('border-color', '');
	}
	
	var regexCurrency = /^\d+([.,]\d{1,2})?$/;
	
	if(!regexCurrency.test(valorDespesa)) {
		$('#valorDespesa').css('border-color', 'red');
		erro = true;
	} else {
		$('#valorDespesa').css('border-color', '');
	}
	
	if(!regexNotEmpty.test(tipoDespesa)) {
		$('#tipoDespesaDiv').css({'border': '1px solid red', 'padding-left': '1px'});
		erro = true;
	} else {
		$('#tipoDespesaDiv').css({'border': '', 'padding-left': ''});
	}
	
	if(!regexNotEmpty.test(centroCusto)) {
		$('#centroCustoDiv').css({'border': '1px solid red', 'padding-left': '1px'});
		erro = true;
	} else {
		$('#centroCustoDiv').css({'border': '', 'padding-left': ''});
	}
	
	if(!regexNotEmpty.test(justificativa)) {
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
	oMyForm.append("dataOcorrencia", $('#dataDespesa').val());
	oMyForm.append("valor", $('#valorDespesa').val().replace(/,/g, '.'));
	oMyForm.append("idTipoDespesa", $('#select-tipo-despesa').val()  );
	oMyForm.append("idProjeto", $('#select-centro-custo').val()  );
	oMyForm.append("idFuncionario", $('#funcionario').val()         );
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
			
			resetForm();
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

function resetForm() {
	$('#form-despesa')[0].reset();
	
	$("#select-tipo-despesa").val('default');
	$("#select-tipo-despesa").selectpicker("refresh");
	$("#select-centro-custo").val('default');
	$("#select-centro-custo").selectpicker("refresh");
}
