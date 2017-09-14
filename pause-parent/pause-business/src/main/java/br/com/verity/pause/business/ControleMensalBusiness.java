package br.com.verity.pause.business;

import java.time.LocalDate;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.converter.ControleMensalConverter;
import br.com.verity.pause.dao.ControleMensalDAO;
import br.com.verity.pause.entity.ControleMensalEntity;

@Service
public class ControleMensalBusiness {
	
	@Autowired
	private ControleMensalDAO controleMensalDAO;
	
	@Autowired
	private ControleMensalConverter controleMensalConverter;

	@SuppressWarnings("deprecation")
	public ControleMensalBean obterPorMesAnoIdFuncionario(Date data, int idFuncionario) {
		int mes = data.getMonth()+1;
		int ano = data.getYear() + 1900;
		ControleMensalBean bean = new ControleMensalBean();

		ControleMensalEntity entity = controleMensalDAO.findByMesAnoIdFuncionario(mes,ano,idFuncionario);
		if (entity != null) {
			bean = controleMensalConverter.convertEntityToBean(entity);
			return bean;
		} else {
			bean.setMes(mes);
			bean.setAno(ano);
			bean.setIdFuncionario(idFuncionario);
			inserir(bean);
		}
		return obterPorMesAnoIdFuncionario(data, idFuncionario);
	}

	private void inserir(ControleMensalBean bean) {
		ControleMensalEntity entity = controleMensalConverter.convertBeanToEntity(bean);

		controleMensalDAO.save(entity);
		
	}
	@SuppressWarnings("deprecation")
	Boolean verificarMesFechado(Date data) {
		LocalDate hoje = LocalDate.now();

		if (hoje.getDayOfMonth() > 9) {
			if (data.getMonth() + 1 < hoje.getMonthValue()) {
				return true;
			}
		} else if(data.getMonth() + 1 < hoje.getMonthValue() - 1){
			return true;
		}
		return false;
	}

//	public ControleMensalBean obterPorIdFuncionarioMesAnoDataDia(Date dtHoje, Integer id) {
//		return controleMensalDAO.haveMesAnoIdFuncionario(dtHoje, id);
//	}
}
