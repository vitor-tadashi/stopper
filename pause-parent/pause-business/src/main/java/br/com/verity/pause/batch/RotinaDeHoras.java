package br.com.verity.pause.batch;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.ControleDiarioBusiness;
import br.com.verity.pause.business.ControleMensalBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;

@Component
@EnableScheduling
public class RotinaDeHoras {
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness;
	
	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";
	
	/** Método .bat que é executado todo dia as 00:00 hrs, para inclusão de controle Mensal e controle Diário.
	 * @author guilherme.oliveira
	 */
	@Scheduled(cron = "00 00 00 * * *", zone = TIME_ZONE)
	public void verificaPorHora() {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodosComPis();
		ControleMensalBean controleMensalBean;
		ControleDiarioBean controleDiarioBean;
		Date dtHoje = new Date();
		for (FuncionarioBean funcionarioBean : funcionarios) {
			controleMensalBean = controleMensalBusiness.obterPorIdFuncionarioMesAnoDataDia(dtHoje, funcionarioBean.getId());
			if(controleMensalBean == null){
				controleMensalBean = controleMensalBusiness.obterPorMesAnoIdFuncionario(dtHoje, funcionarioBean.getId());
				controleDiarioBean = controleDiarioBusiness.obterPorDataIdFuncionario(dtHoje, funcionarioBean.getId());
			} else if (controleMensalBean.getControleDiario() == null || controleMensalBean.getControleDiario().size() == 0){
				controleDiarioBean = controleDiarioBusiness.obterPorDataIdFuncionario(dtHoje, funcionarioBean.getId());
			}
		}
	}
}