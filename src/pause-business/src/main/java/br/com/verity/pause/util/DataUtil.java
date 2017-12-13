package br.com.verity.pause.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.FeriadoBean;
import br.com.verity.pause.integration.SavIntegration;

@Component
public final class DataUtil {
	
	private static SimpleDateFormat formatador;
	
	@Autowired
	private SavIntegration savIntegration;
		
	private final static int DiaPrimeiro = 1;
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
	public String inverterOrdem(String data){
		String[] s = data.split("-");
		String novaData = s[2]+"-"+s[1]+"-"+s[0];
		
		return novaData;
	}
	/**
     * Retorna segundo dia util do mes.
     *
     * @param   data
     * @return  segundo dia util do mes
     */
	public LocalDate segundoDiaUtil(LocalDate data) {
		LocalDate diaUtil = null;
		LocalDate segundoDiaUtil = null;
		LocalDate primeiroDiaMes = data.withDayOfMonth(DiaPrimeiro);
		
		for(LocalDate dt = primeiroDiaMes; segundoDiaUtil == null; dt = dt.plusDays(1)){
			Boolean isFeriado = false;
			Boolean isFimDeSemana = verificarFinalSemana(dt);
			if(!isFimDeSemana){
				isFeriado = verificarFeriado(dt);
			}
			if(!(isFeriado || isFimDeSemana) && diaUtil != null){
				segundoDiaUtil = dt;
			}
			else if(!(isFeriado || isFimDeSemana)){
				diaUtil = dt;
			}
		}
		return segundoDiaUtil;
	}
	 /**
     * Verifica se a data é feriado.
     *
     * @param   data
     * @return  boolean
     */
	public Boolean verificarFeriado(LocalDate localDate)
    {
    	Boolean response = false;
    	Date data = java.sql.Date.valueOf(localDate);
    	
    	try {
    		Optional<FeriadoBean> optional = Arrays.stream(savIntegration.listFeriados().toArray(new FeriadoBean[0]))
    		         .filter(x -> x.getDataFeriado().compareTo(data) == 0).findFirst();
    		        
    		response = optional.isPresent();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return response;
    }
	/**
     * Verifica se data á sábado ou domingo.
     *
     * @param   data Data a ser verificada
     * @return  Retorna se é final de semana
     */
	private Boolean verificarFinalSemana(LocalDate localDate)
    {
		Date data = java.sql.Date.valueOf(localDate);
    	Boolean response = null;
    	Calendar cal = Calendar.getInstance();
    	
    	cal.setTime(data);
    	
    	response = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
    			cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;

        return response;
    }
}
