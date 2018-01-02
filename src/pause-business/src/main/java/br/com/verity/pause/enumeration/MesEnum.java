package br.com.verity.pause.enumeration;

import java.util.HashMap;

public enum MesEnum {

	JANEIRO(1, "Janeiro", "1"), FEVEREIRO(2, "Fevereiro", "1"), MARÇO(3, "Março", "1"), ABRIL(4, "Abril", "2"), 
	MAIO(5, "Maio", "2"), JUNHO(6, "Junho", "2"), JULHO(7, "Julho", "3"), AGOSTO(8, "Agosto", "3"), SETEMBRO(9, "Setembro", "3"),
	OUTUBRO(10, "Outubro", "4"), NOVEMBRO(11, "Novembro", "4"), DEZEMBRO(12, "Dezembro", "4");

	private final Integer value;
	private final String mes;
	private final String trimestre;
	private static HashMap<Integer, MesEnum> map = new HashMap<Integer, MesEnum>();

	MesEnum(Integer value, String mes, String trimestre) {
		this.value = value;
		this.mes = mes;
		this.trimestre = trimestre;
	}

	static {
		for (MesEnum pageType : MesEnum.values()) {
			map.put(pageType.value, pageType);
		}
	}

	public static MesEnum valueOf(Integer trimestreEnum) {
		return map.get(trimestreEnum);
	}

	public Integer getValue() {
		return value;
	}

	public String getMes() {
		return mes;
	}

	public String getTrimestre() {
		return trimestre;
	}

}