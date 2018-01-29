package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.AfastamentoBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.TipoAfastamentoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.AfastamentoConverter;
import br.com.verity.pause.converter.TipoAfastamentoConverter;
import br.com.verity.pause.dao.AfastamentoDAO;
import br.com.verity.pause.entity.AfastamentoEntity;
import br.com.verity.pause.entity.enumerator.TipoAfastamento;
import br.com.verity.pause.exception.BusinessException;
import br.com.verity.pause.util.DataUtil;

@Service
@Component
public class AfastamentoBusiness {
	
	@Autowired
	private ControleMensalBusiness controleMensalBusiness;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	@Autowired
	private AfastamentoConverter afastamentoConverter;
	
	@Autowired
	private TipoAfastamentoConverter tipoAfastamentoConverter;
	
	@Autowired
	private AfastamentoDAO afastamentoDAO;
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;
	
	@Autowired
	private CalculoBusiness calculoBusiness;
	
	//@Autowired
	//private CalculoBusiness calculoBusiness;

	public List<TipoAfastamentoBean> listarTipoAfastamento() {
		List<TipoAfastamento> entities = afastamentoDAO.findAll();
		List<TipoAfastamentoBean> beans = tipoAfastamentoConverter.convertEntityToBean(entities);
		
		return beans;
	}

	public AfastamentoBean salvar(AfastamentoBean afastamento) throws BusinessException {
		if(controleMensalBusiness.verificarMesFechado(afastamento.getDataInicio()))
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
		
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		if(afastamento.getIdFuncionario() == null)
			afastamento.setIdFuncionario(usuarioLogado.getFuncionario().getId());
		
		afastamento.setDataInclusao(new Date());
		afastamento.setIdUsuarioInclusao(usuarioLogado.getId());

		AfastamentoEntity entity = afastamentoConverter.convertBeanToEntity(afastamento);
		entity.setTipoAfastamento(tipoAfastamentoConverter.convertBeanToEntity(afastamento.getTipoAfastamento()));

		afastamento.setId(afastamentoDAO.save(entity).getIdAfastamento());
		
		calculoAfastamento(afastamento);
		//calculoBusiness.calcularApontamento(idFuncionario, afastamento.getData());
		
		return afastamento;
	}

	private void calculoAfastamento(AfastamentoBean afastamento) {
		if(!afastamento.getDataInicio().after(new Date())){
			LocalDate dataCalcula = DataUtil.dateUtilToLocalDate(afastamento.getDataInicio());
			LocalDate dataStopCalculo = DataUtil.dateUtilToLocalDate(afastamento.getDataFim());
			if(dataCalcula != null){
				for(;!dataCalcula.isAfter(LocalDate.now())&& !dataCalcula.isAfter(dataStopCalculo); dataCalcula = dataCalcula.plusDays(1)){
					calculoBusiness.calcularApontamento(afastamento.getIdFuncionario(), DataUtil.localDateToDateUtil(dataCalcula));
				}
			}
		}
	}
	
	public Boolean existeAfastamentoPara (int idFuncionario, java.sql.Date hoje) {
		
		AfastamentoEntity entity = null;
		
		entity = this.afastamentoDAO.findAbsence(idFuncionario, hoje);
		
		return (entity != null);
	}

	public void remover(Integer id) throws BusinessException {
		if(id == null || id.equals(0)) {
			throw new BusinessException("Afastamento não encontrado em nossa base.");
		}
		AfastamentoEntity afastamento = afastamentoDAO.findById(id);
		if(afastamento != null){
			if(controleMensalBusiness.verificarMesFechado(afastamento.getDataInicio()))
				throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
			
			afastamentoDAO.deleteById(id);
			
			calculoAfastamento(afastamentoConverter.convertEntityToBean(afastamento));
		}
	}

	public List<AfastamentoBean> listarPorIdFuncionarioEPeriodo(Integer idFuncionario, String[] periodo) {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorId(idFuncionario);
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date periodos[] = new java.sql.Date[2];
		try {
			if (periodo == null || (periodo[0].isEmpty() && periodo[1].isEmpty())) {
				periodos[0] = java.sql.Date.valueOf((LocalDate.now().minusDays(6)));
				periodos[1] = java.sql.Date.valueOf((LocalDate.now()));
			} else if (periodo[0].isEmpty()) {
				periodos[0] =  java.sql.Date.valueOf("2010-03-10");
				periodos[1] =  new java.sql.Date(fmt2.parse(periodo[1]).getTime());
			} else if (periodo[1].isEmpty()) {
				periodos[1] = java.sql.Date.valueOf((LocalDate.now()));
				periodos[0] = new java.sql.Date(fmt2.parse(periodo[0]).getTime());
			} else {
				periodos[1] = new java.sql.Date(fmt2.parse(periodo[1]).getTime());
				periodos[0] = new java.sql.Date(fmt2.parse(periodo[0]).getTime());
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		List<AfastamentoEntity> entities = afastamentoDAO.findByPeriodoAndIdFuncionario(funcionario.getId(), periodos[0], periodos[1]);
		List<AfastamentoBean> afastamentos = afastamentoConverter.convertEntityToBean(entities);
		
		for(int i = 0; i < entities.size(); i++) {
			afastamentos.get(i).setTipoAfastamento(tipoAfastamentoConverter.convertEntityToBean(entities.get(i).getTipoAfastamento()));
		}
		
		for (AfastamentoBean afastamento : afastamentos) {
			
			Boolean mesFechado = controleMensalBusiness.verificarMesFechado(afastamento.getDataInicio());
			
			afastamento.setMesFechado(mesFechado);
			
		}
		
		return afastamentos;
	}

}
