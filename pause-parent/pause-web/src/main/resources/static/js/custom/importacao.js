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

function importarArquivo() {
	var div = document.getElementById("textDiv");
	$("#textDiv").removeClass("alert alert-danger");
	$("#textDiv").text("");
	$(".rmvLinha").remove();
	if ($("#upload-arquivo").val()) {
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
				if(data != ''){
					$(data).each(function(index, value) {
						if(value.id != null){
							preencherImportacao(value, index);
							$("#upload-arquivo").attr("disabled", true);
							$("#empresa").attr("disabled", true);
							exibirImportacao(exibir);
						}else if(value.mensagem.includes("Arquivo") == true){
							textDiv.className = "alert alert-danger";
							textDiv.textContent = value.mensagem;
							var text = "[" + div.textContent + "]";
						
						}else{
							exibir = false;
							$('#modal-confirmacao').modal('show'); 
						}
					});
				}else{
					textDiv.className = "alert alert-danger";
					textDiv.textContent = "Nenhum funcionário coincide com o arquivo.";
					var text = "[" + div.textContent + "]";
				}
			},
		});
	}
};
	
function exibirImportacao(bool){
	if(bool == true){
		$('#modal-confirmacao').modal('hide'); 
		$("#tbHoras").removeClass("hide");
		$("#botoes").removeClass("hide");
	}
}
	
function preencherImportacao(value, index){
	var campos = "<tr role='row' class='odd text-center rmvLinha'>"+
	"<td id='pis"+index+"' class='pis'></td>" +
	"<td id='funcionario"+index+"'></td>" +
	"<td id='data"+index+"' class='data'></td>" +
	"<td id='hora0"+index+"' class='hora'></td>" +
	"<td id='hora1"+index+"' class='hora'></td>" +
	"<td id='hora2"+index+"' class='hora'></td>" +
	"<td id='hora3"+index+"' class='hora'></td>" +
	"<td id='hora4"+index+"' class='hora'></td>" +
	"<td id='hora5"+index+"' class='hora'></td>" +
	"<td id='hora6"+index+"' class='hora'></td>" +
	"<td id='hora7"+index+"' class='hora'></td>"+
	"</tr>";
	
	$("#list").append(campos);
	
	
	$('#pis'+index).text(value.pis);
	$('#funcionario'+index).text(value.nome);
	$(value.apontamentos).each(function(i, value) {
		$('#hora'+i+index).text(value.horario);
	})
	date = new Date(value.apontamentos[0].data);
	$('#data'+index).text(date.toLocaleDateString());
	$('.pis').mask('999.9999.999-9');
	$('.data').mask('99/99/9999');
}