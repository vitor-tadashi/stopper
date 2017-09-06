package br.com.verity.pause.business;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.dao.ControleDiarioDAO;
import br.com.verity.pause.entity.ControleDiarioEntity;

@Service
public class ControleDiarioBusiness {

	@Autowired
	private ControleDiarioDAO controleDiarioDAO;

	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	@Autowired
	private ApontamentoBusiness apontamentoBusiness;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	public ControleDiarioBean obterPorData(Date data) {
		ControleDiarioBean bean = new ControleDiarioBean();

		ControleDiarioEntity entity = controleDiarioDAO.findByData(new java.sql.Date(data.getTime()));
		if (entity != null) {
			bean = controleDiarioConverter.convertEntityToBean(entity);
			return bean;
		} else {
			bean.setData(data);
			inserir(bean);
		}
		return obterPorData(data);
	}

	public void inserir(ControleDiarioBean bean) {
		ControleDiarioEntity entity = controleDiarioConverter.convertBeanToEntity(bean);

		controleDiarioDAO.save(entity);
	}

	public List<ControleDiarioBean> listarApontamentos(String pis, String[] periodo) {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorPIS(pis);

		List<ConsultaCompletaBean> dadosGerais = apontamentoBusiness
				.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), "01-08-2017", "07-08-2017");

		List<ControleDiarioBean> controleDiarios = separarDia(dadosGerais);
		
		return controleDiarios;
	}

	private List<ControleDiarioBean> separarDia(List<ConsultaCompletaBean> dadosGerais) {
		List<ControleDiarioBean> controleDiarios = new ArrayList<>();
		List<ApontamentoBean> apontamentos = null;
		Date dia = null;

		for (ConsultaCompletaBean cc : dadosGerais) {
			if (dia == null || dia.compareTo(cc.getData()) != 0) {
				ControleDiarioBean cd = obterControleDiarioDeConsultaCompleta(cc);
				apontamentos = cd.getApontamentos();
				controleDiarios.add(cd);
				
				dia = cc.getData();
			}
			apontamentos.add(apontamentoBusiness.obterApontamentoDeConsultaCompleta(cc));
		}
		return controleDiarios;
	}

	private ControleDiarioBean obterControleDiarioDeConsultaCompleta(ConsultaCompletaBean cc) {
		ControleDiarioBean cd = new ControleDiarioBean();
		List<ApontamentoBean> apontamentos = new ArrayList<>();

		cd.setData(cc.getData());
		cd.setHoraTotal(cc.getControleDiarioHoraTotal());
		cd.setBancoHora(cc.getControleDiarioBancoHora());
		cd.setAdicNoturno(cc.getControleDiarioAdcNoturno());
		cd.setSobreAviso(cc.getControleDiarioSA());
		cd.setApontamentos(apontamentos);

		return cd;
	}

}
