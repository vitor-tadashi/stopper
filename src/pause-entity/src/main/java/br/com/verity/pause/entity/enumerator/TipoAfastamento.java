package br.com.verity.pause.entity.enumerator;

public enum TipoAfastamento {
	Ferias(1,"Férias"), LicencaNaoRemunerada(2, "Licença não remunerada");

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
