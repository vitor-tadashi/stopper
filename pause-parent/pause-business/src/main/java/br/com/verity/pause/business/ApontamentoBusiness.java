package br.com.verity.pause.business;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.JustificativaConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.integration.SavIntegration;

@Service
public class ApontamentoBusiness {

	@Autowired
	private ApontamentoDAO apontamentoDAO;
	
	@Autowired
	private ApontamentoConverter apontamentoConverter;
	
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
		
		apontamento.setDataInclusao(new Date());
		apontamento.setIdUsuarioInclusao(usuarioLogado.getId());
		apontamento.setTipoImportacao(false);
		
		if(apontamento.getPis() == null){
			apontamento.setPis(usuarioLogado.getFuncionario().getPis());
			apontamento.setIdEmpresa(usuarioLogado.getFuncionario().getEmpresa().getId());
		}else{
			FuncionarioBean funcionario = sav.getFuncionarioPorPis(apontamento.getPis());
			
			apontamento.setIdEmpresa(funcionario.getEmpresa().getId());
		}
		
		ApontamentoEntity entity = apontamentoConverter.convertBeanToEntity(apontamento);
		entity.setTipoJustificativa(justificativaConverter.convertBeanToEntity(apontamento.getTpJustificativa()));
		
		ControleDiarioBean controleDiario = controleDiarioBusiness.obterPorData(apontamento.getData());
		entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));
		
		apontamentoDAO.save(entity);
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
