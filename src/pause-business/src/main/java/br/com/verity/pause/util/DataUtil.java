package br.com.verity.pause.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public final class DataUtil {
	
	private static SimpleDateFormat formatador;
		
	/**
	 * Converte um String em Date de acordo com o padrão fornecido
	 * @param data String que será convertida em Date
	 * @param padrao formato que será utilizado na conversão da data
	 * @return data convertida
	 */
	public static Date converterData(String data, String padrao){
		
		formatador = new SimpleDateFormat(padrao);

		Date dataFormatada = null;
		
		try {
			
			dataFormatada = formatador.parse(data);
			
		} catch (ParseException e) {
			//implementar log aqui
			e.printStackTrace();
		}
		
		return dataFormatada;
	}
	
	/**
	 * Muda a formatação da data de acordo com o padrão fornecido
	 * @param data
	 * @param padrao
	 * @return
	 */
	public static String formatarData(Date data, String padrao){
		
		formatador = new SimpleDateFormat(padrao);
		String dataFormatada = "";
		
		dataFormatada = formatador.format(data);
		
		return dataFormatada;
	}

	public static List<Date> verificarSegundaFeira(Date data){
		List<Date> datas = new ArrayList<Date>();
		boolean indicadorSegundaFeira = false;
		Calendar calendario = Calendar.getInstance();
		
		calendario.setTime(data);
		
		indicadorSegundaFeira = calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
		
		if(indicadorSegundaFeira){
			
			calendario.add(Calendar.DAY_OF_MONTH, -2);
			datas.add(calendario.getTime());
			
			
			calendario.add(Calendar.DAY_OF_MONTH, +1);
			datas.add(calendario.getTime());
			
			datas.add(data);
			
		}else{
			
			datas.add(data);
		}
		
		
		return datas;
	}

}
