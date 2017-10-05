function cancelar(){
	var arquivo = $("#caminho").attr("data-title");
	$.ajax({
		url : 'importacao/cancelar/'+arquivo+'/' ,
		type : 'POST',
		data: arquivo,
		success : function(){
			location.reload();
		}
	})
}

$( document ).ready(function(){
	window.setTimeout(function() {
		$("#msg-sucesso").hide();
		$(".mensagem").hide();
	}, 5000);
})

function validarCamposImportacao(){
	var indicadorSucesso = true;
	
	if($("#dataImportacao").val() == ""){
		indicadorSucesso = false;
		
		if($("#data-importacao-erro").length == 0){
			$("#dataImportacao").css("border-color", "#a94442");
			$("#dataImportacao").after('<small class="help-block" id="data-importacao-erro">Campo obrigatório.</small>');
		}
		
	}
	
	if($("#upload-arquivo").val() == ""){
		indicadorSucesso = false;
		
		if($("#upload-file-erro").length == 0){
			$("#upload-file").css("border-color", "#a94442");
			$(".upload-file").after('<small class="help-block" id="upload-file-erro" style="margin-top: 8px;">Campo obrigatório.</small>');
		}

	}
	
	return indicadorSucesso;
}

$("#dataImportacao").change(function (){
	$("#dataImportacao").css("border-color", "");
	$("#dataImportacao").next('small').remove();
});

$("#upload-arquivo").change(function (){
	$("#upload-file").css("border-color", "");
	$(".upload-file").next('small').remove();
});

function importarArquivo() {
	
	var indicadorSucesso = validarCamposImportacao()
	
	if (indicadorSucesso) {
		var div = document.getElementById("textDiv");
		$("#textDiv").removeClass("alert alert-danger");
		$("#textDiv").text("");
		$(".rmvLinha").remove();
		
		$("#carregar-mensagem").removeClass("hide");
		$("#modal-loader").modal("show");
		
		var date;
		var form = document.getElementById("formValidar");
		var formData = new FormData(form);
		var exibir = true;
		$.ajax({
			url : 'importacao/importar-arquivo',
			enctype : 'multipart/form-data',
			type : 'POST',
			data : formData,
			processData : false,
			contentType : false,
			cache : false,
			success : function(data) {
				var arquivo = JSON.parse(data);
				
				arquivo.funcionariosImportacao = JSON.parse(arquivo.funcionariosImportacao);
				
				debugger;
				if(arquivo.funcionariosImportacao.length > 0){
					
					var indice = 0;
					for(var linha in arquivo.funcionariosImportacao){
						preencherImportacao(arquivo.funcionariosImportacao[linha], indice);
						indice++;
					}
					
					$("#upload-arquivo").attr("disabled", true);
					$("#empresa").attr("disabled", true);
					
					if(true){
						
                        $('#modal-confirmacao').modal('show'); 

					}else{
						
						exibirImportacao(true);
						
					}
					
					
				}else{
					
					textDiv.className = "alert alert-danger";
					textDiv.textContent = "Nenhum apontamento encontrado para o dia solicitado.";
					var text = "[" + div.textContent + "]";
					
				}
			},
			error: function(erro){
				$('#erro-label').text(erro.responseText);
				$('#erro-sm-modal').modal();
			}
		});
		
		$("#modal-loader").modal("hide");
		$("#carregar-mensagem").addClass("hide");
		
	}
	
};
	
function exibirImportacao(bool){
	if(bool == true){
		$('#modal-confirmacao').modal('hide');
		$("#tbHoras").removeClass("hide");
		$("#botoes").removeClass("hide");
	}
}
	
function preencherImportacao(funcionario, indice){
    var campos = "<tr role='row' class='odd text-center rmvLinha'>"+
    "<td id='pis" + indice + "' class='pis'></td>" +
    "<td id='funcionario" + indice + "'></td>" +
    "<td id='data" + indice + "' class='data'></td>" +
    "<td id='hora0" + indice + "' class='hora'></td>" +
    "<td id='hora1" + indice + "' class='hora'></td>" +
    "<td id='hora2" + indice + "' class='hora'></td>" +
    "<td id='hora3" + indice + "' class='hora'></td>" +
    "<td id='hora4" + indice + "' class='hora'></td>" +
    "<td id='hora5" + indice + "' class='hora'></td>" +
    "<td id='hora6" + indice + "' class='hora'></td>" +
    "<td id='hora7" + indice + "' class='hora'></td>"+
	"</tr>";
	
	$("#list").append(campos);
	
	
	$('#pis'+indice).text(funcionario.pis);
	$('#funcionario'+indice).text(funcionario.nome);
	
	var horario = "";
	$(funcionario.apontamentos).each(function(i, value) {
		if(value.horario.hour < 10){
			value.horario.hour = "0" + value.horario.hour;
		}
		if(value.horario.minute < 10){
			value.horario.minute = "0" + value.horario.minute;
		}
		horario = value.horario.hour + ":" + value.horario.minute; 
		$('#hora'+i+indice).text(horario);
	});
	
	date = new Date(funcionario.apontamentos[0].data);
	$('#data'+indice).text(date.toLocaleDateString());
	$('.data').mask('99/99/9999');
}

$("#submitbutton").click(function() {
	$("#salvar-mensagem").removeClass("hide");
	$("#modal-loader").modal("show");
	$("#formValidar").submit();
});