package br.com.verity.pause.entity.enumerator;

public enum TipoAfastamento {
	Ferias(1,"Férias"), LicencaNaoRemunerada(2, "Licença não remunerada"), A(3, "Licença não remunerada")
	, B(4, "Licença não remunerada"), C(5, "Licença não remunerada"), D(6, "Licença não remunerada")
	, E(7, "Licença não remunerada"), F(8, "Licença não remunerada"), G(9, "Licença não remunerada");

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
