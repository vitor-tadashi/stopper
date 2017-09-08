package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.ControleMensalBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.ControleMensalConverter;
import br.com.verity.pause.dao.ControleDiarioDAO;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.util.VerificarData;

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
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness; 
	
	@Autowired
	private ControleMensalConverter controleMensalConverter;

	public ControleDiarioBean obterPorDataIdFuncionario(Date data,int idFuncionario) {
		ControleDiarioBean bean = new ControleDiarioBean();

		ControleDiarioEntity entity = controleDiarioDAO.findByDataIdFuncionario(new java.sql.Date(data.getTime()),idFuncionario);
		if (entity != null) {
			bean = controleDiarioConverter.convertEntityToBean(entity);
			return bean;
		} else {
			bean.setData(data);
			bean.setIdFuncionario(idFuncionario);
			inserir(bean);
		}
		return obterPorDataIdFuncionario(data, idFuncionario);
	}

	public void inserir(ControleDiarioBean bean) {
		ControleMensalBean controleMensal = controleMensalBusiness.obterPorMesAnoIdFuncionario(bean.getData(), bean.getIdFuncionario());
		
		ControleDiarioEntity entity = controleDiarioConverter.convertBeanToEntity(bean);
		ControleMensalEntity controleMensalEntity = controleMensalConverter.convertBeanToEntity(controleMensal);
		
		entity.setControleMensal(controleMensalEntity);

		controleDiarioDAO.save(entity);
	}

	public List<ControleDiarioBean> listarControleDiario(String pis, String[] periodo) {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorPIS(pis);
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		String periodos[] = new String[2];
		try {
			if (periodo == null || (periodo[0].isEmpty() && periodo[1].isEmpty())) {
				periodos[0] = fmt.format(java.sql.Date.valueOf((LocalDate.now().minusDays(7))));
				periodos[1] = fmt.format(java.sql.Date.valueOf((LocalDate.now())));
			} else if (periodo[0].isEmpty()) {
				periodos[0] = "01-03-2010";
				periodos[1] = fmt.format(fmt2.parse(periodo[1]));
			} else if (periodo[1].isEmpty()) {
				periodos[1] = fmt.format(java.sql.Date.valueOf((LocalDate.now())));
				periodos[0] = fmt.format(fmt2.parse(periodo[0]));
			} else {
				periodos[1] = fmt.format(fmt2.parse(periodo[1]));
				periodos[0] = fmt.format(fmt2.parse(periodo[0]));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		List<ConsultaCompletaBean> dadosGerais = apontamentoBusiness
				.obterApontamentosPeriodoPorIdFuncionario(funcionario.getId(), periodos[0], periodos[1]);

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
			apontamentos.sort(new Comparator<ApontamentoBean>() {
				@Override
				public int compare(ApontamentoBean o1, ApontamentoBean o2) {
					return o1.getHorario().compareTo(o2.getHorario());
				}
			});
		}
		return controleDiarios;
	}

	@SuppressWarnings("deprecation")
	private ControleDiarioBean obterControleDiarioDeConsultaCompleta(ConsultaCompletaBean cc) {
		ControleDiarioBean cd = new ControleDiarioBean();
		List<ApontamentoBean> apontamentos = new ArrayList<>();

		cd.setData(cc.getData());
		cd.setDiaDaSemana(VerificarData.qualDia(cc.getData().getDay()));
		cd.setHoraTotal(cc.getControleDiarioHoraTotal());
		cd.setBancoHora(cc.getControleDiarioBancoHora());
		cd.setAdicNoturno(cc.getControleDiarioAdcNoturno());
		cd.setSobreAviso(cc.getControleDiarioSA());
		cd.setQtdAtestadoHoras(cc.getAtestadoQuantidadeHora());
		cd.setApontamentos(apontamentos);

		return cd;
	}

}
