var erro = false;

$(document).ready(function(){
	var date = new Date();
	date = date.toLocaleDateString();
	var dia = date.substring(0, 2);
	var mes = date.substring(3, 5);
	var ano = date.substring(6, 10);
	
	$("#EdH_dtDe").attr("max", ano+"-"+mes+"-"+dia);
	$("#EdH_dtAte").attr("max", ano+"-"+mes+"-"+dia);
	$("#EdH_dtAte").attr("min", ano+"-"+mes+"-"+dia);
	
	$("#AD_dtDe").attr("max", ano+"-"+mes+"-"+dia);
	$("#AD_dtAte").attr("max", ano+"-"+mes+"-"+dia);

})

function permitirDataExtratoDeHoras(){
	var deAno = $("#EdH_dtDe").val().substring(0, 4);
	var deMes = $("#EdH_dtDe").val().substring(5, 7);
	var deDia = $("#EdH_dtDe").val().substring(8, 10);
	
	var ateAno = $("#EdH_dtAte").val().substring(0, 4);
	var ateMes = $("#EdH_dtAte").val().substring(5, 7);
	var ateDia = $("#EdH_dtAte").val().substring(8, 10);
	if($("#EdH_dtAte").val() != ""){
		if(deAno != ateAno || (deAno == ateAno && deMes != ateMes)){
			$("#EdH_dtDe").val("");
			$("#EdH_dtAte").val("");
			EdH_textDiv.className = "alert alert-danger";
			EdH_textDiv.textContent = "Por favor, selecione um período dentro de um único mês.";
			var text = "[" + EdH_textDiv.textContent + "]";
			$("#EdH_textDiv").removeClass("hide");
			erro = true;
		}else{
			$("#EdH_dtAte").attr("min", $("#dtDe").val());
			$("#EdH_dtDe").attr("max", $("#dtAte").val());
			$("#EdH_textDiv").addClass("hide");
			erro = false;
		}
	}else{
		$("#EdH_dtAte").attr("min", $("#EdH_dtDe").val());
	}
}

function permitirDataApontamentoDiario(){
	var deAno = $("#AD_dtDe").val().substring(0, 4);
	var deMes = $("#AD_dtDe").val().substring(5, 7);
	var deDia = $("#AD_dtDe").val().substring(8, 10);
	
	var ateAno = $("#AD_dtAte").val().substring(0, 4);
	var ateMes = $("#AD_dtAte").val().substring(5, 7);
	var ateDia = $("#AD_dtAte").val().substring(8, 10);
	if($("#AD_dtAte").val() != ""){
			$("#AD_dtAte").attr("min", $("#AD_dtDe").val());
			$("#AD_dtDe").attr("max", $("#AD_dtAte").val());
			$("#AD_textDiv").addClass("hide");
	}else{
		$("#AD_dtAte").attr("min", $("#AD_dtDe").val());
	}
}

function gerarRelatorioExtratoDeHoras(){
	if($("#EdH_dtDe").val() != "" && $("#EdH_dtAte").val() != "" && $("#EdH_idFunc").val() != "" && erro == false){
		$("#EdH_textDiv").addClass("hide");
		var deAno = $("#EdH_dtDe").val().substring(0, 4);
		var deMes = $("#EdH_dtDe").val().substring(5, 7);
		var deDia = $("#EdH_dtDe").val().substring(8, 10);
		
		var ateAno = $("#EdH_dtAte").val().substring(0, 4);
		var ateMes = $("#EdH_dtAte").val().substring(5, 7);
		var ateDia = $("#EdH_dtAte").val().substring(8, 10);
		
		var de = deDia+"-"+deMes+"-"+deAno;
		var ate = ateDia+"-"+ateMes+"-"+ateAno;
		var id = $("#EdH_idFunc").val();
		
		$("#EdH_download").attr("href", "/pause/relatorio/gerar-relatorio?idFuncionario="+id+"&de="+de+"&ate="+ate);
		window.open($("#EdH_download").attr("href"),'_blank');
	} else{
		EdH_textDiv.className = "alert alert-danger";
		EdH_textDiv.textContent = "Por favor selecione um funcionário e um período válido.";
		var text = "[" + textDiv.textContent + "]";
	}
}

function gerarRelatorioApontamentoDiario(){
	if($("#AD_dtDe").val() != "" && $("#AD_dtAte").val() != "" && erro == false){
		$("#AD_textDiv").addClass("hide");
		var deAno = $("#AD_dtDe").val().substring(0, 4);
		var deMes = $("#AD_dtDe").val().substring(5, 7);
		var deDia = $("#AD_dtDe").val().substring(8, 10);
		
		var ateAno = $("#AD_dtAte").val().substring(0, 4);
		var ateMes = $("#AD_dtAte").val().substring(5, 7);
		var ateDia = $("#AD_dtAte").val().substring(8, 10);
		
		var de = deDia+"-"+deMes+"-"+deAno;
		var ate = ateDia+"-"+ateMes+"-"+ateAno;
		var id = $("#AD_idFunc").val();
		
		$("#AD_download").attr("href", "/pause/relatorio/gerar-relatorio-ad?de="+de+"&ate="+ate);
		window.open($("#AD_download").attr("href"),'_blank');
	} else{
		AD_textDiv.className = "alert alert-danger";
		AD_textDiv.textContent = "Por favor selecione um período válido.";
		var text = "[" + textDiv.textContent + "]";
	}
}
