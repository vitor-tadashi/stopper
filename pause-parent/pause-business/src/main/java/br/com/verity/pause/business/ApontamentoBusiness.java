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
	
	public void apontar(ApontamentoBean apontamento) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		Integer idFuncionario;
		
		apontamento.setDataInclusao(new Date());
		apontamento.setIdUsuarioInclusao(usuarioLogado.getId());
		apontamento.setTipoImportacao(false);
		
		if(apontamento.getPis() == null || apontamento.getPis().isEmpty()){
			apontamento.setPis(usuarioLogado.getFuncionario().getPis());
			apontamento.setIdEmpresa(usuarioLogado.getFuncionario().getEmpresa().getId());
			idFuncionario = usuarioLogado.getFuncionario().getId();
		}else{
			FuncionarioBean funcionario = sav.getFuncionarioPorPis(apontamento.getPis());
			
			apontamento.setIdEmpresa(funcionario.getEmpresa().getId());
			idFuncionario = funcionario.getId();
		}
		
		ApontamentoEntity entity = apontamentoConverter.convertBeanToEntity(apontamento);
		entity.setTipoJustificativa(justificativaConverter.convertBeanToEntity(apontamento.getTpJustificativa()));
		
		ControleDiarioBean controleDiario = controleDiarioBusiness.obterPorDataIdFuncionario(apontamento.getData(),idFuncionario);
		entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));
		
		if(apontamento.getId() == null || apontamento.getId().equals(0)) {
			apontamentoDAO.save(entity);
		}else {
			apontamentoDAO.update(entity);
		}
		
	}
	
	public List<ConsultaCompletaBean> obterApontamentosPeriodoPorIdFuncionario(Integer id, String de,
			String ate) {
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
		consultaCompleta = consultaCompletaConverter.convertEntityToBean(consultaCompletaDAO.findByIdAndPeriodo(id,dtDe,dtAte));
		
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

	/*public List<ApontamentoBean> listarApontamentos(String pis, String[] periodo) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		java.sql.Date[] periodoSQL = new java.sql.Date[2];
		
		if(pis == null) {
			pis = usuarioLogado.getFuncionario().getPis();
		}
		
		if(periodo.length <= 0) {
			LocalDate hoje = LocalDate.now();
			LocalDate domingo = hoje.with(previousOrSame(SUNDAY));
			LocalDate sabado = hoje.with(nextOrSame(SATURDAY));
			
			periodoSQL[0] = java.sql.Date.valueOf(domingo);
			periodoSQL[1] = java.sql.Date.valueOf(sabado);
		}
		List<ApontamentoEntity>entities = apontamentoDAO.findByPisAndPeriodo(pis,periodoSQL);
		List<ApontamentoBean> beans = apontamentoConverter.convertEntityToBean(entities);
		for(int i = 0; beans.size() > i; i++) {
			beans.get(i).setTpJustificativa(justificativaConverter.convertEntityToBean(entities.get(i).getTipoJustificativa()));
		}
		
		return beans;
	}*/

}
