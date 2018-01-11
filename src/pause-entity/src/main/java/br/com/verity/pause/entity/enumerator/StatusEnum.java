package br.com.verity.pause.entity.enumerator;

public enum StatusEnum {
	
	EM_ANALISE("Em Análise"), 
	APROVADO("Aprovado"),
	REPROVADO("Reprovado");
	
	public String nome;

	StatusEnum(String nome) {
		this.nome = nome;
	}
	
	public String getNome(){
        return nome;
    }
	public static StatusEnum getByNome(String nome) {
	    for(StatusEnum e : values()) {
	        if(e.nome.toUpperCase().equals(nome.toUpperCase())) { 
	        	return e;
	        }
	    }
	    return null;
	 }

}
