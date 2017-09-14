package br.com.verity.pause.batch;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.ControleMensalBusiness;
import br.com.verity.pause.business.FuncionarioBusiness;

@Component
//@EnableScheduling
public class RotinaDeHoras {
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness;
	
	private static final String TIME_ZONE = "America/Sao_Paulo";

	//@Scheduled(cron = "* * * * * *", zone = TIME_ZONE)
	public void verificaPorHora() {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodosComPis();
		ControleMensalBean controleMensalBean;
		Date dtHoje = new Date();
		for (FuncionarioBean funcionarioBean : funcionarios) {
			//controleMensalBean = controleMensalBusiness.obterPorIdFuncionarioMesAnoDataDia(dtHoje, funcionarioBean.getId());
		}
		System.out.println(LocalDateTime.now());
	}

}
