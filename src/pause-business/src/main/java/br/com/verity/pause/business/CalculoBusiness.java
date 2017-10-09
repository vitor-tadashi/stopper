package br.com.verity.pause.business;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.FeriadoBean;
import br.com.verity.pause.dao.AfastamentoDAO;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.dao.AtestadoDAO;
import br.com.verity.pause.dao.ControleDiarioDAO;
import br.com.verity.pause.dao.ControleMensalDAO;
import br.com.verity.pause.dao.SobreAvisoDAO;
import br.com.verity.pause.entity.AfastamentoEntity;
import br.com.verity.pause.entity.ApontamentoPivotEntity;
import br.com.verity.pause.entity.AtestadoEntity;
import br.com.verity.pause.entity.SobreAvisoEntity;
import br.com.verity.pause.integration.SavIntegration;

/**
 * Classe que possui todos os cálculos relacionados ao controle de horas
 * @author vitor.tadashi
 *
 */
@Service
public class CalculoBusiness {

	@Autowired
	private ApontamentoDAO apontamentoDAO;
	
	@Autowired
	private AfastamentoDAO afastamentoDAO;
	
	@Autowired
	private SobreAvisoDAO sobreAvisoDAO;
	
	@Autowired
	private AtestadoDAO atestadoDAO;
	
	@Autowired
	private ControleDiarioDAO controleDiarioDAO;
	
	@Autowired
	private SavIntegration savIntegration;
	
	@Autowired
	private ControleMensalDAO controleMensalDAO;
	
	private static final int SECOND = 1000;
	private static final int MINUTE = 60 * SECOND;
	private static final int HOUR = 60 * MINUTE;
	private Time adNotInicio1 = new Time(0,0,0);
	private Time adNotFim1 = new Time(6,0,0);
	private Time adNotInicio2 = new Time(22,0,0);
	private Time adNotFim2 = new Time(23,59,59);

	/**
	 * Realiza os calculos de horas totais, extras, sobre aviso e adicional noturno.
	 * Salva os dados calculados.
	 * @param idFuncionario Id do funcionário
	 * @param data
	 */
	public ApontamentoPivotEntity calcularApontamento(int idFuncionario, java.util.Date data) {
		ApontamentoPivotEntity apontamento = null;
		
		apontamento = calcularApontamentoDiario(idFuncionario, new Date(data.getTime()));
		salvarApontamento(apontamento);
		
		return apontamento;
	}
	
	/**
	 * Atualiza as horas calculadas do apontamento do respectivo funcionario
	 * @param apontamento 
	 */
	@SuppressWarnings("deprecation")
	private void salvarApontamento(ApontamentoPivotEntity apontamento) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(apontamento.getDataApontamento());
		
		controleDiarioDAO.saveCalculation(apontamento);
		controleMensalDAO.saveCalculation(apontamento.getIdFuncionario(), calendar.get(Calendar.MONTH) + 1);
	}

	/**
	 * Método que calcula o apontamento diário do funcionário. 
	 * Aciona o método de calcular horas totais.
	 * Aciona o método de calcular horas extras.
	 * Aciona o método de calcular horas de sobre aviso.
	 * Aciona o método de calcular horas de adicional noturno.
	 * Salva os cálculos.
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do qual deseja realizar o cálculo
	 */
	private ApontamentoPivotEntity calcularApontamentoDiario(int idFuncionario, Date data) {
		ApontamentoPivotEntity apontamento = null;
		Double totalHoras = 0d;
		Double totalHorasDiarias = 0d;
		Double horasExtras = 0d;
		Double totalSobreAvisoTrabalhado = 0d;
		Double totalSobreAviso = 0d;
		Double totalAdicionalNoturno = 0d;
		
		SobreAvisoEntity sobreAviso = null;
		
		apontamento = obterApontamentoFuncionario(idFuncionario, data);
		sobreAviso = obterSobreAvisoFuncionario(idFuncionario, data);
		
		if (apontamento != null && apontamento.getEntrada1() != null && apontamento.getSaida1() != null) {
			totalHoras = calcularHoraTotal(apontamento);
			totalHoras = calcularHoraTotalAtestado(totalHoras, idFuncionario, data);
			totalHorasDiarias = obterQuantidadeHorasDiarias(idFuncionario, data);
			horasExtras = calcularHorasExtras(totalHoras, totalHorasDiarias, data);
			totalAdicionalNoturno = calcularAdicionalNoturno(apontamento);
			
			if (sobreAviso != null) {
				totalSobreAvisoTrabalhado = calcularSobreAvisoTrabalhadas(apontamento, sobreAviso);
			}
		}
		else if (apontamento == null){
			apontamento = new ApontamentoPivotEntity();
			apontamento.setDataApontamento(data);
			apontamento.setIdFuncionario(idFuncionario);
		}
		
		if (sobreAviso != null){
			totalSobreAviso = calcularSobreAviso(sobreAviso, totalSobreAvisoTrabalhado);
		}
	
		tratarHorasCalculadas(apontamento, totalHoras, totalHorasDiarias, horasExtras, totalSobreAvisoTrabalhado,
				totalSobreAviso, totalAdicionalNoturno);
		
		return apontamento;
	}


	/**
	 * Tratar todas as horas calculadas e coloca no objeto de Apontamento
	 * @param apontamento
	 * @param totalHoras
	 * @param totalHorasDiarias
	 * @param horasExtras
	 * @param totalSobreAvisoTrabalhado
	 * @param totalSobreAviso
	 * @param totalAdicionalNoturno
	 */
	private void tratarHorasCalculadas(ApontamentoPivotEntity apontamento, Double totalHoras, Double totalHorasDiarias,
			Double horasExtras, Double totalSobreAvisoTrabalhado, Double totalSobreAviso,
			Double totalAdicionalNoturno) {
		apontamento.setTotalHoras(timeRound(totalHoras));
		apontamento.setTotalHorasDiarias(timeRound(totalHorasDiarias));
		apontamento.setHorasExtras(timeRound(horasExtras));
		apontamento.setTotalSobreAvisoTrabalhado(timeRound(totalSobreAvisoTrabalhado));
		apontamento.setTotalSobreAviso(timeRound(totalSobreAviso));
		apontamento.setTotalAdicionalNoturno(timeRound(totalAdicionalNoturno));
	}

	/**
	 * Soma o total de horas com o atestado do funcionario da respectiva data
	 * @param totalHoras Total de horas da respectiva data
	 * @param idFuncionario Id do funcionario
	 * @param data Data do atestado
	 * @return
	 */
	private Double calcularHoraTotalAtestado(Double totalHoras, int idFuncionario, Date data) {
		Double quantidadeHorasAtestado = 0d;
		AtestadoEntity atestado = null;
		
		atestado = obterAtestadoFuncionario(idFuncionario, data);
		
		if (atestado != null) {
			quantidadeHorasAtestado = atestado.getQuantidadeHora();
		}
		
		return totalHoras + quantidadeHorasAtestado;
	}

	/**
	 * Calcula as horas totais do período de apontamento do funcionário
	 * @param apontamento Apontamento diário do funcionário
	 */
	private Double calcularHoraTotal(ApontamentoPivotEntity apontamento) {
		Double total1 = null;
		Double total2 = null;
		Double total3 = null;
		Double total4 = null;
		Double total = null;
		
		total1 = calcularPeriodo(apontamento.getEntrada1(), apontamento.getSaida1());
		total2 = calcularPeriodo(apontamento.getEntrada2(), apontamento.getSaida2());
		total3 = calcularPeriodo(apontamento.getEntrada3(), apontamento.getSaida3());
		total4 = calcularPeriodo(apontamento.getEntrada4(), apontamento.getSaida4());
		
		total = total1 + total2 + total3 + total4;
		
		return total;
	}
	
	/**
	 * Calcula as horas de adicional noturno
	 * @param apontamento Apontamento diário do funcionário
	 */
	private Double calcularAdicionalNoturno(ApontamentoPivotEntity apontamento) {
		Double adicionalNoturno1 = null;
		Double adicionalNoturno2 = null;
		Double adicionalNoturno3 = null;
		Double adicionalNoturno4 = null;
		Double adicionalNoturnoTotal = 0d;
		
		adicionalNoturno1 = calcularAdicionalNoturnoPeriodo(apontamento.getEntrada1(), apontamento.getSaida1());
		adicionalNoturno2 = calcularAdicionalNoturnoPeriodo(apontamento.getEntrada2(), apontamento.getSaida2());
		adicionalNoturno3 = calcularAdicionalNoturnoPeriodo(apontamento.getEntrada3(), apontamento.getSaida3());
		adicionalNoturno4 = calcularAdicionalNoturnoPeriodo(apontamento.getEntrada4(), apontamento.getSaida4());
		
		adicionalNoturnoTotal = adicionalNoturno1 + adicionalNoturno2 + adicionalNoturno3 + adicionalNoturno4;
		
		return adicionalNoturnoTotal;
	}
	
	/**
	 * Calcula as horas sobre aviso trabalhadas
	 * @param apontamento Apontamento diário do funcionário
	 * @param sobreAviso Período sobre aviso do funcionário
	 */
	private Double calcularSobreAvisoTrabalhadas(ApontamentoPivotEntity apontamento, SobreAvisoEntity sobreAviso) {
		Double sobreAvisoTrabalhada1 = null;
		Double sobreAvisoTrabalhada2 = null;
		Double sobreAvisoTrabalhada3 = null;
		Double sobreAvisoTrabalhada4 = null;
		Double sobreAvisoTrabalhadaTotal = 0d;
		
		sobreAvisoTrabalhada1 = calcularSobreAvisoTrabalhadaPeriodo(apontamento.getEntrada1(), apontamento.getSaida1(), sobreAviso.getHoraInicio(), sobreAviso.getHoraFim());
		sobreAvisoTrabalhada2 = calcularSobreAvisoTrabalhadaPeriodo(apontamento.getEntrada2(), apontamento.getSaida2(), sobreAviso.getHoraInicio(), sobreAviso.getHoraFim());
		sobreAvisoTrabalhada3 = calcularSobreAvisoTrabalhadaPeriodo(apontamento.getEntrada3(), apontamento.getSaida3(), sobreAviso.getHoraInicio(), sobreAviso.getHoraFim());
		sobreAvisoTrabalhada4 = calcularSobreAvisoTrabalhadaPeriodo(apontamento.getEntrada4(), apontamento.getSaida4(), sobreAviso.getHoraInicio(), sobreAviso.getHoraFim());
		
		sobreAvisoTrabalhadaTotal = sobreAvisoTrabalhada1 + sobreAvisoTrabalhada2 + sobreAvisoTrabalhada3 + sobreAvisoTrabalhada4;
		
		return sobreAvisoTrabalhadaTotal;
	}
	
	/**
	 * Calcula as horas sobre aviso
	 * @param sobreAviso Período sobre aviso do funcionário
	 * @param totalSobreAvisoTrabalhado Total de horas sobre aviso trabalhados
	 * @return Retorna o total de horas sobre aviso
	 */
	private Double calcularSobreAviso(SobreAvisoEntity sobreAviso, Double totalSobreAvisoTrabalhado) {
		Double totalSobreAviso = 0d;
		
		totalSobreAviso = ((double) sobreAviso.getHoraFim().getTime() - sobreAviso.getHoraInicio().getTime()) / HOUR;
		totalSobreAviso = totalSobreAviso - totalSobreAvisoTrabalhado;
		
		return totalSobreAviso;
	}
	
	/**
	 * Calcula o periodo adicional noturno trabalhado
	 * @param entrada Horário de entrada
	 * @param saida Horário de saída
	 * @return Retorna o adicional noturno no periodo informado
	 */
	private Double calcularAdicionalNoturnoPeriodo(Time entrada, Time saida) {
		Double diferenca = 0d;
		
		if (entrada != null && saida != null) {
			Long msEntrada = entrada.getTime();
			Long msSaida = saida.getTime();
			Long msAdNotInicio1 = adNotInicio1.getTime();
			Long msAdNotFim1 = adNotFim1.getTime();
			Long msAdNotInicio2 = adNotInicio2.getTime();
			Long msAdNotFim2 = adNotFim2.getTime();
			
			if (msEntrada < msAdNotFim1 && msEntrada >= msAdNotInicio1) {
				diferenca = (double) ((msAdNotFim1-msEntrada)-(msAdNotFim1-msSaida));
			}
			else if (msEntrada < msAdNotFim2 && msEntrada >= msAdNotInicio2) {
				diferenca = (double) ((msAdNotFim2-msEntrada)-(msAdNotFim2-msSaida));
			}
			else if (msSaida < msAdNotFim2 && msSaida >= msAdNotInicio2) {
				diferenca = (double) ((msSaida-msEntrada)-(msAdNotInicio2-msEntrada));
			}
			else if (msEntrada < msAdNotInicio2 && msSaida <= msAdNotFim2 && msSaida > msAdNotInicio2) {
				diferenca = (double) (msSaida-msAdNotInicio2);
			}
		}
		
		return diferenca / HOUR;
	}
	
	/**
	 * Calcula o periodo sobre aviso trabalhado
	 * @param entrada Horário de entrada
	 * @param saida Horário de saída
	 * @param entradaSobreAviso Horário de entrada sobre aviso
	 * @param saidaSobreAviso Horário de saída sobre aviso
	 * @return Retorna o sobre aviso trabalhado no periodo informado
	 */
	private Double calcularSobreAvisoTrabalhadaPeriodo(Time entrada, Time saida, Time entradaSobreAviso, Time saidaSobreAviso) {
		Double diferenca = 0d;
		
		if (entrada != null && saida != null) {
			Long msEntrada = entrada.getTime();
			Long msSaida = saida.getTime();
			Long msEntradaSobreAviso = entradaSobreAviso.getTime();
			Long msSaidaSobreAviso = saidaSobreAviso.getTime();
			
			if (msEntrada >= msEntradaSobreAviso && msEntrada < msSaidaSobreAviso && msSaida <= msSaidaSobreAviso) {
				diferenca = (double) msSaida - msEntrada;
			} 
			else if (msEntrada >= msEntradaSobreAviso && msEntrada < msSaidaSobreAviso && msSaida > msSaidaSobreAviso) {
				diferenca = (double) msSaidaSobreAviso - msEntrada;
			}
			else if (msEntrada <= msEntradaSobreAviso && msSaida <= msSaidaSobreAviso) {
				diferenca = (double) msSaida - msEntradaSobreAviso;
			}
			else if (msEntrada <= msEntradaSobreAviso && msSaida > msSaidaSobreAviso) {
				diferenca = (double) msSaidaSobreAviso - msEntradaSobreAviso;
			}
		}
		
		return diferenca / HOUR;
	}
	 
	/**
	 * Calcula a diferença de dois horários
	 * @param entrada Horário de entrada
	 * @param saida Horário de saída
	 * @return Retorna a diferença entre dois horários
	 */
	private Double calcularPeriodo(Time entrada, Time saida) {
		Double diferenca = 0d;
		
		if (entrada != null && saida != null) {	
			diferenca = (double) saida.getTime() - entrada.getTime(); 
		}
		
		return diferenca / HOUR;
	}
	
	/**
	 * Calcular horas extras
	 * @param entrada Horário de entrada
	 * @param saida Horário de saída
	 * @return Retorna a diferença entre dois horários
	 */
	private Double calcularHorasExtras(Double horasRealizadas, Double horasARealizar, Date data) {
		Double horasExtras = 0d;
		Boolean finalSemanaOuFeriado = null;
		
		finalSemanaOuFeriado = verificarDomingo(data) || verificarFeriado(data);
		
		horasExtras = horasRealizadas - horasARealizar;
		
		if (finalSemanaOuFeriado) {
			horasExtras = horasExtras + (horasExtras * 0.4);
		}
		
		return horasExtras;
	}
	
	/**
	 * Obter a quantidade de horas diárias do funcionário na respectiva data.
	 * Considera as férias, feriados e finais de semana.
	 * @param idFuncionario Id do funcionário
	 * @param data Data do qual deseja obter a quantidade de horas a se realizar
	 * @return Retorna a quantidade de horas diárias a serem realizadas pelo funcionário
	 */
	private Double obterQuantidadeHorasDiarias(int idFuncionario, Date data) {
		java.util.Date dataAtual = new java.util.Date();
		Boolean possuiAfastamento = false;
		Boolean finalSemana = false;
		Boolean feriado = false;
		
		if (dataAtual.after(data)) {
			finalSemana = verificarFinalSemana(data);
			
			if (!finalSemana) feriado = verificarFeriado(data);
			
			if (!feriado) possuiAfastamento = possuiAfastamento(idFuncionario, data);
		}
		
		if (finalSemana || feriado || possuiAfastamento)
			return 0d; //TODO obter parametrizado
		else
			return 8d; //TODO obter parametrizado
	}
	
	/**
     * Verifica se data á sábado ou domingo.
     *
     * @param   data Data a ser verificada
     * @return  Retorna se é final de semana
     */
	private Boolean verificarFinalSemana(Date data)
    {
    	Boolean response = null;
    	Calendar cal = Calendar.getInstance();
    	
    	cal.setTime(data);
    	
    	response = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
    			cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;

        return response;
    }
    
    /**
     * Verifica se data é um domingo.
     *
     * @param   data Data a ser verificada
     * @return  Retorna se é domingo
     */
	private Boolean verificarDomingo(Date data)
    {
    	Boolean response = null;
    	Calendar cal = Calendar.getInstance();
    	
    	cal.setTime(data);
    	
    	response = cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;

        return response;
    }
    
    /**
     * Verifica se data á sábado ou domingo.
     *
     * @param   data Um objeto Calendar
     * @return  Calendar
     */
	private Boolean verificarFeriado(Date data)
    {
    	Boolean response = false;
    	
    	try {
    		Optional<FeriadoBean> optional = Arrays.stream(savIntegration.listFeriados().toArray(new FeriadoBean[0]))
    		         .filter(x -> x.getDataFeriado().compareTo(data) == 0).findFirst();
    		        
    		response = optional.isPresent();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return response;
    }
    
    /**
	 * Obtêm o apontamento do funcionário na data especificada
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do qual deseja realizar o cálculo
	 * @return Retorna o apontamento do funcionário da respectiva data
	 */
	private ApontamentoPivotEntity obterApontamentoFuncionario(int idFuncionario, Date data) {
		ApontamentoPivotEntity apontamento = null;

		try {

			apontamento = apontamentoDAO.findByEmployeeAndDate(idFuncionario, data);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return apontamento;
	}
	
	/**
	 * Verifica se o funcionário está afastado na data especificada
	 * @param idFuncionario Id do funcionário
	 * @param data Data a ser verificada
	 * @return
	 */
	private Boolean possuiAfastamento(int idFuncionario, Date data) {
		Boolean possuiAfastamento = false;
		

			AfastamentoEntity afastamento = afastamentoDAO.findAbsence(idFuncionario, data);
			
			if (afastamento != null) {
				possuiAfastamento = true;
			}

		
		return possuiAfastamento;
	}
	
	/**
	 * Obtêm o sobre aviso da data informada
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do qual deseja realizar o cálculo
	 * @return Retorna o sobre aviso do funcionário da respectiva data
	 */
	private SobreAvisoEntity obterSobreAvisoFuncionario(int idFuncionario, Date data) {
		SobreAvisoEntity sobreAviso = null;

		try {

			sobreAviso = sobreAvisoDAO.findNoticePeriod(idFuncionario, data);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sobreAviso;
	}
	
	/**
	 * Obtêm o atestado da data informada
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do qual deseja realizar o cálculo
	 * @return Retorna o atestado do funcionário da respectiva data
	 */
	private AtestadoEntity obterAtestadoFuncionario(int idFuncionario, Date data) {
		AtestadoEntity atestado = null;

		try {

			atestado = atestadoDAO.findSickNote(idFuncionario, data);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return atestado;
	}
	
	/**
	 * Padronização de arredondamento dos resultados de horas
	 * Arredondando em 2 casas decimais
	 * 
	 * @param valor Horas a serem arredondadas
	 * @return Hora arredondada
	 */
	private Double timeRound(Double valor) {
		return Math.round(valor * 1000000.0) / 1000000.0;
	}
}
