package br.com.verity.pause.enumeration;

import java.util.HashMap;

public enum MesEnum {

	JANEIRO(1, "Janeiro", "1"), FEVEREIRO(2, "Fevereiro", "1"), MARÇO(3, "Março", "1"), ABRIL(4, "Abril", "2"), 
	MAIO(5, "Maio", "2"), JUNHO(6, "Junho", "2"), JULHO(7, "Julho", "3"), AGOSTO(8, "Agosto", "3"), SETEMBRO(9, "Setembro", "3"),
	OUTUBRO(10, "Outubro", "4"), NOVEMBRO(11, "Novembro", "4"), DEZEMBRO(12, "Dezembro", "4");

	private final Integer value;
	private final String mes;
	private final String semestre;
	private static HashMap<Integer, MesEnum> map = new HashMap<Integer, MesEnum>();

	MesEnum(Integer value, String mes, String semestre) {
		this.value = value;
		this.mes = mes;
		this.semestre = semestre;
	}

	static {
		for (MesEnum pageType : MesEnum.values()) {
			map.put(pageType.value, pageType);
		}
	}

	public static MesEnum valueOf(Integer SemestreEnum) {
		return map.get(SemestreEnum);
	}

	public Integer getValue() {
		return value;
	}

	public String getMes() {
		return mes;
	}

	public String getSemestre() {
		return semestre;
	}

}