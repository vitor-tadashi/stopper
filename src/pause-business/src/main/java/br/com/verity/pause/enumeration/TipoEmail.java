package br.com.verity.pause.enumeration;

public enum TipoEmail {
	
	GESTOR(1, "Nova despesa para análise!",
				"A despesa abaixo foi cadastrada, acesse o <a href=\"http://localhost:9001/pause/\"><strong>PAUSE</strong></a> para análise: "
				+ "</br></br><style>table {border-collapse: collapse;}table, td, th {border: 1px solid black;}</style>"
				+ "<table cellpadding=\"5\"><tr><td>Data ocorrência:</td><td>#data</td></tr>"
				+ "<tr><td>Nome:</td><td>#nome</td></tr>"
				+ "<tr><td>Valor:</td><td>#valor</td></tr>"
				+ "<tr><td>Tipo:</td><td>#tipo</td></tr>"
				+ "<tr><td>Projeto:</td><td>#projeto</td></tr>"
				+ "<tr><td>Descrição:</td><td>#descricao</td></tr></table>"),
	
	FINANCEIRO(2, "Nova despesa para análise!",
					"A despesa abaixo está disponível para análise financeira, "
					+ "acesse o <a href=\"http://localhost:9001/pause/\"><strong>PAUSE</strong></a> para análise: "
					+ "</br></br><style>table {border-collapse: collapse;}table, td, th {border: 1px solid black;}</style>"
					+ "<table cellpadding=\"5\"><tr><td>Data ocorrência:</td><td>#data</td></tr>"
					+ "<tr><td>Nome:</td><td>#nome</td></tr>"
					+ "<tr><td>Valor:</td><td>#valor</td></tr>"
					+ "<tr><td>Tipo:</td><td>#tipo</td></tr>"
					+ "<tr><td>Projeto:</td><td>#projeto</td></tr>"
					+ "<tr><td>Descrição:</td><td>#descricao</td></tr></table>"),

	APROVADO(3, "Despesa aprovada!", 		
				"A despesa abaixo foi aprovada, em alguns dias o dinheiro estará disponível em sua conta: "
				+ "</br></br><style>table {border-collapse: collapse;}table, td, th {border: 1px solid black;}</style>"
				+ "<table cellpadding=\"5\"><tr><td>Data ocorrência:</td><td>#data</td></tr>"
				+ "<tr><td>Nome:</td><td>#nome</td></tr>"
				+ "<tr><td>Valor:</td><td>#valor</td></tr>"
				+ "<tr><td>Tipo:</td><td>#tipo</td></tr>"
				+ "<tr><td>Projeto:</td><td>#projeto</td></tr>"
				+ "<tr><td>Descrição:</td><td>#descricao</td></tr></table>"),
	
	REPROVADO(4, "Despesa reprovada!", 
				"A despesa abaixo foi reprovada pelo motivo: <strong>#justificativaReject</strong>"
				+ "</br></br><style>table {border-collapse: collapse;}table, td, th {border: 1px solid black;}</style>"
				+ "<table cellpadding=\"5\"><tr><td>Data ocorrência:</td><td>#data</td></tr>"
				+ "<tr><td>Nome:</td><td>#nome</td></tr>"
				+ "<tr><td>Valor:</td><td>#valor</td></tr>"
				+ "<tr><td>Tipo:</td><td>#tipo</td></tr>"
				+ "<tr><td>Projeto:</td><td>#projeto</td></tr>"
				+ "<tr><td>Descrição:</td><td>#descricao</td></tr></table>");
	
	private final Integer value;
	private final String titulo;
	private final String corpo;
	
	TipoEmail(Integer value, String titulo, String corpo) {
		this.value = value;
		this.titulo = titulo;
		this.corpo = corpo;
	}
	
	public Integer getValue() {
		return value;
	}

	public String getTitulo() {
		return titulo;
	}
	public String getCorpo() {
		return corpo;
	}
}
