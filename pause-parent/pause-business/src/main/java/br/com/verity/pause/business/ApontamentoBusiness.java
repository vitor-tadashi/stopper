package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.converter.ConsultaCompletaConverter;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.JustificativaConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.dao.ConsultaCompletaDAO;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.exception.BusinessException;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class ApontamentoBusiness {

	@Autowired
	private ApontamentoDAO apontamentoDAO;

	@Autowired
	private ApontamentoConverter apontamentoConverter;

	@Autowired
	private ConsultaCompletaConverter consultaCompletaConverter;

	@Autowired
	private ConsultaCompletaDAO consultaCompletaDAO;

	@Autowired
	private CustomUserDetailsBusiness userBusiness;

	@Autowired
	private SavIntegration sav;

	@Autowired
	private JustificativaConverter justificativaConverter;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	@Autowired
	private ControleMensalBusiness controleMensalBusiness;
	
	@Autowired
	private CalculoBusiness calculoBusiness;

	public ApontamentoBean apontar(ApontamentoBean apontamento) throws BusinessException {

		if (controleMensalBusiness.verificarMesFechado(apontamento.getData())) {
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
		}
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		Integer idFuncionario;

		if (apontamento.getIdFuncionario() == null || apontamento.getIdFuncionario().equals(0)) {
			apontamento.setIdEmpresa(usuarioLogado.getFuncionario().getEmpresa().getId());
			idFuncionario = usuarioLogado.getFuncionario().getId();
		} else {
			FuncionarioBean funcionario = sav.getFuncionario(apontamento.getIdFuncionario());

			apontamento.setIdEmpresa(funcionario.getEmpresa().getId());
			idFuncionario = funcionario.getId();
		}
		apontamento.setDataInclusao(new Date());
		apontamento.setIdUsuarioInclusao(usuarioLogado.getId());
		apontamento.setTipoImportacao(false);

		ApontamentoEntity entity = apontamentoConverter.convertBeanToEntity(apontamento);
		entity.setTipoJustificativa(justificativaConverter.convertBeanToEntity(apontamento.getTpJustificativa()));

		ControleDiarioBean controleDiario = controleDiarioBusiness.obterPorDataIdFuncionario(apontamento.getData(),
				idFuncionario);
		entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));

		if (apontamento.getId() == null || apontamento.getId().equals(0)) {
			apontamento.setId(apontamentoDAO.save(entity).getId());
		} else {
			ApontamentoEntity apontamentoAtual = apontamentoDAO.findById(apontamento.getId());
			if(apontamentoAtual.getTipoImportacao()) {
				throw new BusinessException("Não foi possível realizar a ação, pois o apontamento é eletrônico.");
			}
			apontamentoDAO.update(entity);
		}
		calculoBusiness.calcularApontamento(idFuncionario, apontamento.getData());
	
		return apontamento;
	}

	public List<ConsultaCompletaBean> obterApontamentosPeriodoPorIdFuncionario(Integer id, String de, String ate) {
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
		java.sql.Date dtDe = null;
		java.sql.Date dtAte = null;
		try {
			dtDe = new java.sql.Date(formataData.parse(de).getTime());
			dtAte = new java.sql.Date(formataData.parse(ate).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		consultaCompleta = consultaCompletaConverter
				.convertEntityToBean(consultaCompletaDAO.findByIdAndPeriodo(id, dtDe, dtAte));

		return consultaCompleta;
	}

	public ApontamentoBean obterApontamentoDeConsultaCompleta(ConsultaCompletaBean cc) {
		ApontamentoBean apontamento = new ApontamentoBean();

		apontamento.setId(cc.getApontamentoId());
		apontamento.setData(cc.getData());
		apontamento.setHorario(cc.getApontamentoHorario());
		apontamento.setTipoImportacao(cc.getApontamentoTpImportacao());
		apontamento.setObservacao(cc.getApontamentoObs());

		return apontamento;
	}

	public ApontamentoBean obterPorId(Integer id) {
		ApontamentoEntity entity = apontamentoDAO.findById(id);

		ApontamentoBean bean = apontamentoConverter.convertEntityToBean(entity);
		bean.setTpJustificativa(justificativaConverter.convertEntityToBean(entity.getTipoJustificativa()));

		return bean;
	}
	public void remover(Integer idApontamento) throws BusinessException {
		if(idApontamento == null || idApontamento.equals(0)) {
			throw new BusinessException("Apontamento não encontrado em nossa base.");
		}
		ApontamentoEntity apontamento = apontamentoDAO.findById(idApontamento);
		
		if(controleMensalBusiness.verificarMesFechado(apontamento.getData()))
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
		else if(apontamento.getTipoImportacao())
			throw new BusinessException("Não foi possível realizar a ação, pois o apontamento é eletrônico.");
		
		apontamentoDAO.deleteById(idApontamento);
		
		int idFuncionario = apontamento.getControleDiario().getControleMensal().getIdFuncionario();
		calculoBusiness.calcularApontamento(idFuncionario, apontamento.getData());
	}
}
