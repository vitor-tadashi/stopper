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
	oMyForm.append("id", $('#id').val());
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

function enviarFormAnalise(idDespesa, fgFinanceiroGP, despesaAprovada) {
	var oMyForm = new FormData();
	oMyForm.append("idDespesa", idDespesa);
	oMyForm.append("fgFinanceiroGP", fgFinanceiroGP);
	oMyForm.append("despesaAprovada", despesaAprovada);

	$.ajax({
		url : 'analisar',
		type: "POST",
		data : oMyForm,
		processData: false,
		contentType: false,
		success: function(data){
			$("#span-msg").html(data);
			$('#despesa' + idDespesa).remove();
			if($("#table-despesas tr td").length <= 0) {
				 $('#table-despesas tbody').append('<tr><td colspan="6">Não há despesas para análise</td></tr>');
			}
			$('#detalhe-despesa-modal').modal("hide");
		},
		error: function(erro){
			if (erro.status === 403) {
				location.reload();
			} else {
				$("#span-msg").html("Erro!" + erro.status);
				$('#detalhe-despesa-modal').modal("hide");
			}
		}
	});
}

function abrirModalVisualizacaoDespesa(idDespesa) {
	$.ajax({
		url: idDespesa,
		type: "get", 
		success: function(data) {
			$("#dataOcorrencia").val(data.dataOcorrencia);
			$("#solicitante").val(data.nomeFuncionario);
			$("#valorDespesa").val(data.valor);
			$("#tipoDespesa").val(data.nomeTipoDespesa);
			$("#projeto").val(data.descricaoProjeto);
			$("#justificativaDespesa").val(data.justificativa);
			if (data.caminhoComprovante != null) {
				$("#btnDownloadArquivo").show();
				$("#btnDownloadArquivo").attr("href", "http://" + window.location.host + "/pause/despesa/arquivo/" + data.id);
			} else {
				$("#btnDownloadArquivo").hide();
				$("#btnDownloadArquivo").hide();
			}
			
			$("#aprovarDespesa").off("click");
			$("#aprovarDespesa").click(function(){
				if ($("#buttonSubmitGestorDiv").length) {
					enviarFormAnalise(data.id, 'G', true);
				} else if ($("#buttonSubmitFinanceiroDiv").length) {
					enviarFormAnalise(data.id, 'F', true);
				}
			});
			$("#aprovarDespesa").on("click");
			
			$("#rejeitarDespesa").off("click");
			$("#rejeitarDespesa").click(function(){
				if ($("#buttonSubmitGestorDiv").length) {
					enviarFormAnalise(data.id, 'G', false);
				} else if ($("#buttonSubmitFinanceiroDiv").length) {
					enviarFormAnalise(data.id, 'F', false);
				}
			});
			$("#rejeitarDespesa").on("click");
			$('#detalhe-despesa-modal').modal();
		},
		error: function(erro) {
			if (erro.status === 403) {                        
				location.reload();                            
			} else {                                          
				$("#span-msg").html("Erro!" + erro.status);   
			}                                                 
		}
	});
}

function abrirModalEdicaoSolicitante(idDespesa) {
	$.ajax({
		url: "despesa/"+idDespesa,
		type: "get", 
		success: function(data) {
			$("#id").val(data.id);
			$("#dataDespesa").val(data.dataOcorrencia.split("/").reverse().join("-"));
			$("#valorDespesa").val(Number(data.valor).toFixed(2).replace(".",","));
			$('#select-tipo-despesa').selectpicker('val', data.idTipoDespesa);
			$('#select-centro-custo').selectpicker('val', data.idProjeto);
			$("#projeto").val(data.descricaoProjeto);
			$("#justificativaDespesa").val(data.justificativa);
			$('#add-despesa-modal').modal();
		},
		error: function(erro) {
			if (erro.status === 403) {                        
				location.reload();                            
			} else {                                          
				$("#span-msg").html("Erro!" + erro.status);   
			}                                                 
		}
	});
	
}

function abrirModalVisualizacaoSolicitante(idDespesa) {
	$.ajax({
		url: "despesa/"+idDespesa,
		type: "get", 
		success: function(data) {
			
			if(data.idStatus == 1) {
				$("#statusExib").attr("class","label-status label-status-analise");
			} else if (data.idStatus == 2) {
				$("#statusExib").attr("class","label-status label-status-aprovado");
			} else {
				$("#statusExib").attr("class","label-status label-status-reprovado");
			}
			
			$("#statusExib").html(data.nomeStatus);   
			$("#dataOcorrenciaExib").val(data.dataOcorrencia);
			$("#valorDespesaExib").val("R$ " + Number(data.valor).toFixed(2).replace(".",","));
			$("#TipoDespesaExib").val(data.nomeTipoDespesa);
			$("#projetoExib").val(data.descricaoProjeto);
			$("#justificativaDespesaExib").val(data.justificativa);
			if (data.caminhoComprovante != null) {
				$("#btnDownloadArquivo").show();
				$("#btnDownloadArquivo").attr("href", "http://" + window.location.host + "/pause/despesa/arquivo/" + data.id);
			} else {
				$("#btnDownloadArquivo").hide();
				$("#btnDownloadArquivo").hide();
			}

			$('#exibe-despesa-modal').modal();
		},
		error: function(erro) {
			if (erro.status === 403) {                        
				location.reload();                            
			} else {                                          
				$("#span-msg").html("Erro!" + erro.status);   
			}                                                 
		}
	});
}


