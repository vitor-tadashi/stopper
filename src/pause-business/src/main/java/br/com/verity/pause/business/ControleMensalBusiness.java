package br.com.verity.pause.business;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ControleMensalConverter;
import br.com.verity.pause.dao.ControleMensalDAO;
import br.com.verity.pause.entity.ConsultaCompletaEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.enumeration.MesEnum;

@Service
public class ControleMensalBusiness {
	
	@Autowired
	private ControleMensalDAO controleMensalDAO;
	
	@Autowired
	private ControleMensalConverter controleMensalConverter;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;

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
	public Boolean verificarMesFechado(Date data) {
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

	public ControleMensalBean obterPorIdFuncionarioMesAnoDataDia(Date dtHoje, Integer id) {
		return controleMensalConverter.convertEntityToBean(controleMensalDAO.findByDataAndIdFunc(dtHoje, id));
	}

	public List<Double> obterTrimestreAtualEAnterior() throws SQLException {
		List<Double>bancosTrimestre = new ArrayList<>();
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		Integer idFuncionario = usuarioLogado.getFuncionario().getId();
		int mesAtual = LocalDate.now().getMonthValue();
		
		int trimestre = Integer.parseInt(MesEnum.valueOf(mesAtual).getSemestre());
		int ultimoMesTrimestre = trimestre * 3;
		int primeiroMesTrimestre = ultimoMesTrimestre - 2;
		
		Double bancoTrimestre = controleMensalDAO.findSumBancoTrimestre(primeiroMesTrimestre, ultimoMesTrimestre
				, LocalDate.now().getYear(), idFuncionario);
		bancosTrimestre.add(Math.round(bancoTrimestre*100.0)/100.0);
		
		if(primeiroMesTrimestre == 1){
			primeiroMesTrimestre = 10;
			ultimoMesTrimestre = 12;
		}else{
			primeiroMesTrimestre -= 3;
			ultimoMesTrimestre -= 3;
		}
		Double bancoTrimestreAnterior = controleMensalDAO.findSumBancoTrimestre(primeiroMesTrimestre, ultimoMesTrimestre
				, LocalDate.now().getYear(), idFuncionario);
		bancosTrimestre.add(Math.round(bancoTrimestreAnterior*100.0)/100.0);
		
		return bancosTrimestre;
	}

	public List<ControleMensalBean> obterBancoESaldoPorIdFuncionario(Integer id, String de) throws SQLException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		LocalDate date = LocalDate.parse(de, dtf);
		int trimestre = Integer.parseInt(MesEnum.valueOf(date.getMonthValue()).getSemestre());
		int ultimoMesTrimestre = trimestre * 3;
		int primeiroMesTrimestre = ultimoMesTrimestre - 2;
		
		List<ControleMensalEntity> bancoEHoras = controleMensalDAO.findHoraAndBancoByIdFuncionario(id, primeiroMesTrimestre,
				date.getMonthValue(), date.getYear());
		if(bancoEHoras.size()<1)bancoEHoras = null;
		
		return controleMensalConverter.convertEntityToBean(bancoEHoras);
	}
}
