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
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.enumeration.DiaSemanaEnum;

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

	public ControleDiarioBean obterPorDataIdFuncionario(Date data, int idFuncionario) {
		ControleDiarioBean bean = new ControleDiarioBean();

		ControleDiarioEntity entity = controleDiarioDAO.findByDataIdFuncionario(new java.sql.Date(data.getTime()),
				idFuncionario);
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
		ControleMensalBean controleMensal = controleMensalBusiness.obterPorMesAnoIdFuncionario(bean.getData(),
				bean.getIdFuncionario());

		ControleDiarioEntity entity = controleDiarioConverter.convertBeanToEntity(bean);
		ControleMensalEntity controleMensalEntity = controleMensalConverter.convertBeanToEntity(controleMensal);

		entity.setControleMensal(controleMensalEntity);

		controleDiarioDAO.save(entity);
	}

	public List<ControleDiarioBean> listarControleDiario(Integer idFuncionario, String[] periodo) {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorId(idFuncionario);
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		String periodos[] = new String[2];
		try {
			if (periodo == null || (periodo[0].isEmpty() && periodo[1].isEmpty())) {
				periodos[0] = fmt.format(java.sql.Date.valueOf((LocalDate.now().minusDays(6))));
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

		controleDiarios = tratarArredondamentos(controleDiarios);
		
		return controleDiarios;
	}

	public List<ControleDiarioBean> tratarArredondamentos(List<ControleDiarioBean> controleDiarios) {
		
		
		for (ControleDiarioBean c : controleDiarios) {
			
			Double adicionalNoturno =  c.getAdicNoturno();
			Double horaTotal = c.getHoraTotal();
			Double banco = c.getBancoHora();
			Double sobreAviso = c.getSobreAviso();
			
			if (adicionalNoturno != null) {
				adicionalNoturno = Math.round(adicionalNoturno*100.0)/100.0;
				c.setAdicNoturno(adicionalNoturno);
			}
			
			if (horaTotal != null) {
				horaTotal = Math.round(horaTotal*100.0)/100.0;
				c.setHoraTotal(horaTotal);
			}
			
			if (banco != null) {
				banco = Math.round(banco*100.0)/100.0;
				c.setBancoHora(banco);
			}
			
			if (sobreAviso != null) {
				sobreAviso = Math.round(sobreAviso*100.0)/100.0;
				c.setSobreAviso(sobreAviso);
			}
		}
		
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
					int resp = 0;
					if (o1.getHorario() != null && o2.getHorario() != null) {
						resp = o1.getHorario().compareTo(o2.getHorario());
					} else if(o1.getHorario() != null && o2.getHorario() == null){
						resp = 1;
					}else if(o1.getHorario() == null && o2.getHorario() != null){
						resp = -1;
					}else if(o1.getHorario() == null && o2.getHorario() == null){
						resp = 0;
					}
					return resp;
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
		cd.setDiaDaSemana(DiaSemanaEnum.valueOf(cc.getData().getDay()).getDiaCompleto());
		cd.setHoraTotal(cc.getControleDiarioHoraTotal());
		cd.setBancoHora(cc.getControleDiarioBancoHora());
		cd.setAdicNoturno(cc.getControleDiarioAdcNoturno());
		cd.setSobreAviso(cc.getControleDiarioSA());
		cd.setSobreAvisoTrabalhado(cc.getControleDiarioST());
		cd.setQtdAtestadoHoras(cc.getAtestadoQuantidadeHora());
		cd.setMesFechado(controleMensalBusiness.verificarMesFechado(cd.getData()));
		cd.setApontamentos(apontamentos);

		return cd;
	}

	public List<ControleDiarioBean> listSomaControleDiario(List<FuncionarioBean> funcionarios) {
		java.util.Date data = new java.util.Date();
		java.sql.Date de = new java.sql.Date(data.getYear(), data.getMonth(), 01);
		java.sql.Date ate = new java.sql.Date(data.getTime());
		String idsFuncs = this.getIdFuncsString(funcionarios);
		List<ControleDiarioBean> beans = this
				.setControleMensalEntityToBean(controleDiarioDAO.findByDataSum(de, ate, idsFuncs));
		return beans;
	}

	public List<ControleDiarioBean> listSomaControleDiarioPorPeriodo(List<FuncionarioBean> funcionarios, Date de,
			Date ate) {
		java.sql.Date dtDe = new java.sql.Date(de.getTime());
		java.sql.Date dtAte = new java.sql.Date(ate.getTime());
		String idsFuncs = this.getIdFuncsString(funcionarios);
		List<ControleDiarioBean> beans = this
				.setControleMensalEntityToBean(controleDiarioDAO.findByDataSum(dtDe, dtAte, idsFuncs));
		return beans;
	}

	private List<ControleDiarioBean> setControleMensalEntityToBean(List<ControleDiarioEntity> controleDiarioEntity) {
		List<ControleDiarioBean> controlesDiario = new ArrayList<ControleDiarioBean>();
		ControleDiarioBean controleDiario = null;

		for (ControleDiarioEntity entity : controleDiarioEntity) {
			controleDiario = new ControleDiarioBean();

			controleDiario = controleDiarioConverter.convertEntityToBean(entity);
			controleDiario.setControleMensal(controleMensalConverter.convertEntityToBean(entity.getControleMensal()));

			controlesDiario.add(controleDiario);
		}
		return controlesDiario;
	}

	private String getIdFuncsString(List<FuncionarioBean> funcionarios) {
		String idFuncs = "";

		for (FuncionarioBean funcionarioBean : funcionarios) {
			idFuncs += funcionarioBean.getId().toString() + ",";
		}
		idFuncs = idFuncs.substring(0, idFuncs.length() - 1);

		return idFuncs;
	}

	public ControleDiarioBean tratarArredondamentos(ControleDiarioBean controleDiario) {
		Double adicionalNoturno =  controleDiario.getAdicNoturno();
		Double horaTotal = controleDiario.getHoraTotal();
		Double banco = controleDiario.getBancoHora();
		Double sobreAviso = controleDiario.getSobreAviso();
		
		if (adicionalNoturno != null) {
			adicionalNoturno = Math.round(adicionalNoturno*100.0)/100.0;
			controleDiario.setAdicNoturno(adicionalNoturno);
		}
		
		if (horaTotal != null) {
			horaTotal = Math.round(horaTotal*100.0)/100.0;
			controleDiario.setHoraTotal(horaTotal);
		}
		
		if (banco != null) {
			banco = Math.round(banco*100.0)/100.0;
			controleDiario.setBancoHora(banco);
		}
		
		if (sobreAviso != null) {
			sobreAviso = Math.round(sobreAviso*100.0)/100.0;
			controleDiario.setSobreAviso(sobreAviso);
		}
		
		return controleDiario;
	}
}