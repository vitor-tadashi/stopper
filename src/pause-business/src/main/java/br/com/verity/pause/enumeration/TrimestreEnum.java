package br.com.verity.pause.enumeration;

import java.util.HashMap;

/**
 * formato de data mm-dd
 * @author pedro.olejnik
 *
 */
public enum TrimestreEnum {

	PRIMEIRO(1, "01-01", "03-31"), SEGUNDO(2, "04-01", "06-30"), TERCEIRO(3, "07-01", "09-30"), QUARTO(4, "10-01", "12-31");

	private final Integer value;
	private final String diaInicioTrimestre;
	private final String diaFimTrimestre;
	private static HashMap<Integer, TrimestreEnum> map = new HashMap<Integer, TrimestreEnum>();

	TrimestreEnum(Integer value, String diaInicioTrimestre, String diaFimTrimestre) {
		this.value = value;
		this.diaInicioTrimestre = diaInicioTrimestre;
		this.diaFimTrimestre = diaFimTrimestre;
	}

	static {
		for (TrimestreEnum pageType : TrimestreEnum.values()) {
			map.put(pageType.value, pageType);
		}
	}

	public static TrimestreEnum valueOf(Integer TrimestreEnum) {
		return map.get(TrimestreEnum);
	}

	public Integer getValue() {
		return value;
	}

	public String getDiaInicioTrimestre() {
		return diaInicioTrimestre;
	}

	public String getDiaFimTrimestre() {
		return diaFimTrimestre;
	}

}