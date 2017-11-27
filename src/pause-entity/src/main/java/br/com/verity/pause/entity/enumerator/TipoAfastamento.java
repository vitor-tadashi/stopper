package br.com.verity.pause.entity.enumerator;

public enum TipoAfastamento {
	Ferias(1,"Férias"), LicencaNaoRemunerada(2, "Licença não remunerada"), AbonoDoCliente(3, "Abono do cliente")
	, NovoFuncionario(4, "Novo funcionário"), Desligamento(5, "Desligamento"), Estagiario(6, "Estagiário")
	, Greve(7, "Greve"), QA360(8, "QA360"), AfastamentoINSS(9, "Afastamento/INSS"), AbonoCasamentoEOutro(10, "Abono (Casamento e outro)");

	public Integer idTipoAfastamento;
	public String descricao;

	TipoAfastamento(Integer idTipoAfastamento , String descricao) {
		this.idTipoAfastamento = idTipoAfastamento;
		this.descricao = descricao;
	}
	
	public int getIdTipoAfastamento(){
        return idTipoAfastamento;
    }
	public String getDescricao(){
        return descricao;
    }
	public static TipoAfastamento getById(Integer id) {
	    for(TipoAfastamento e : values()) {
	        if(e.idTipoAfastamento.equals(id)) return e;
	    }
	    return null;
	 }
}
