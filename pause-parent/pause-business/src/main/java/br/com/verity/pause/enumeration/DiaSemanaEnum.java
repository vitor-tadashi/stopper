package br.com.verity.pause.enumeration;

import java.util.HashMap;

public enum DiaSemanaEnum {

	DOMINGO(0, "Domingo", "Dom"), SEGUNDA(1, "Segunda-Feira", "Seg"), TERCA(2, "Terça-Feira", "Ter"), QUARTA(3,"Quarta-Feira",
			"Qua"), QUINTA(4, "Quinta-Feira", "Qui"), SEXTA(5, "Sexta-Feira", "Sex"), SABADO(6, "Sábado", "Sáb");

	private final Integer valor;
	private final String diaCompleto;
	private final String diaSimples;
	private static HashMap<Integer, DiaSemanaEnum> map = new HashMap<Integer, DiaSemanaEnum>();
	
	DiaSemanaEnum(Integer valor, String diaCompleto, String diaSimples) {
		this.valor = valor;
		this.diaCompleto = diaCompleto;
		this.diaSimples = diaSimples;
	}
	
	static {
        for (DiaSemanaEnum pageType : DiaSemanaEnum.values()) {
            map.put(pageType.valor, pageType);
        }
    }

    public static DiaSemanaEnum valueOf(Integer statusVagaEnum) {
        return map.get(statusVagaEnum);
    }

	public Integer getValue() {
		return valor;
	}

	public String getDiaCompleto() {
		return diaCompleto;
	}
	
	public String getDiaSimples() {
		return diaSimples;
	}
}