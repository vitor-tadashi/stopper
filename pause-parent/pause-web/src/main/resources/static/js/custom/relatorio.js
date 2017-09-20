var erro = false;

function permitirData(){
	var deAno = $("#dtDe").val().substring(0, 4);
	var deMes = $("#dtDe").val().substring(5, 7);
	var deDia = $("#dtDe").val().substring(8, 10);
	
	var ateAno = $("#dtAte").val().substring(0, 4);
	var ateMes = $("#dtAte").val().substring(5, 7);
	var ateDia = $("#dtAte").val().substring(8, 10);
	if($("#dtAte").val() != ""){
		if(deAno != ateAno || (deAno == ateAno && deMes != ateMes)){
			$("#dtDe").val("");
			$("#dtAte").val("");
			textDiv.className = "alert alert-danger";
			textDiv.textContent = "Por favor, selecione um período dentro de um único mês.";
			var text = "[" + textDiv.textContent + "]";
			$("#textDiv").removeClass("hide");
			erro = true;
		}else{
			$("#dtAte").attr("min", $("#dtDe").val());
			$("#dtDe").attr("max", $("#dtAte").val());
			$("#textDiv").addClass("hide");
			erro = false;
		}
	}else{
		$("#dtAte").attr("min", $("#dtDe").val());
	}
}

function gerarRelatorio(){
	if($("#dtDe").val() != "" && $("#dtAte").val() != "" && $("#idFunc").val() != "" && erro == false){
		$("#textDiv").addClass("hide");
		var deAno = $("#dtDe").val().substring(0, 4);
		var deMes = $("#dtDe").val().substring(5, 7);
		var deDia = $("#dtDe").val().substring(8, 10);
		
		var ateAno = $("#dtAte").val().substring(0, 4);
		var ateMes = $("#dtAte").val().substring(5, 7);
		var ateDia = $("#dtAte").val().substring(8, 10);
		
		var de = deDia+"-"+deMes+"-"+deAno;
		var ate = ateDia+"-"+ateMes+"-"+ateAno;
		$.ajax({
			url: "relatorio/gerar-relatorio",
			type : 'POST',
			data : {'idFuncionario': $("#idFunc").val(),
					'ate' : ate,
					'de' : de},
			DataType: "text",
			success: function(data){
				$("#download").attr("href", "/pause/relatorio/download?caminho="+data);
				window.open($("#download").attr("href"),'_blank');
			}
		})
	}else{
		textDiv.className = "alert alert-danger";
		textDiv.textContent = "Por favor, selecione um funcionário e um período.";
		var text = "[" + textDiv.textContent + "]";
	}
}

$(document).ready(function(){
	var date = new Date();
	date = date.toLocaleDateString();
	var dia = date.substring(0, 2);
	var mes = date.substring(3, 5);
	var ano = date.substring(6, 10);
	
	$("#dtDe").attr("max", ano+"-"+mes+"-"+dia);
	$("#dtAte").attr("max", ano+"-"+mes+"-"+dia);
	$("#dtAte").attr("min", ano+"-"+mes+"-"+dia);

})