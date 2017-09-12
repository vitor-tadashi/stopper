package br.com.verity.pause.entity.enumerator;

public enum TipoAfastamento {
	Ferias(1), LicencaNaoRemunerada(2);

	public Integer idTipoAfastamento;

	TipoAfastamento(Integer idTipoAfastamento) {
		this.idTipoAfastamento = idTipoAfastamento;
	}
	
	public int getIdTipoAfastamento(){
        return idTipoAfastamento;
    }
	
	public static TipoAfastamento getById(Integer id) {
	    for(TipoAfastamento e : values()) {
	        if(e.idTipoAfastamento.equals(id)) return e;
	    }
	    return null;
	 }
}
