package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.SobreAvisoConverter;
import br.com.verity.pause.dao.SobreAvisoDAO;
import br.com.verity.pause.entity.ApontamentoPivotEntity;
import br.com.verity.pause.entity.SobreAvisoEntity;
import br.com.verity.pause.exception.BusinessException;

@Service
public class SobreAvisoBusiness {

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@Autowired
	private CustomUserDetailsBusiness userBusiness;

	@Autowired
	private SobreAvisoConverter sobreAvisoConverter;

	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	@Autowired
	private SobreAvisoDAO sobreAvisoDAO;

	@Autowired
	private ControleMensalBusiness controleMensalBusiness;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@Autowired
	private ControleApontamentoBusiness controleApontamentoBusiness;

	@Autowired
	private CalculoBusiness calculoBusiness;

	public SobreAvisoBean salvar(SobreAvisoBean sobreAviso) throws BusinessException {
		List<SobreAvisoEntity> sobreAvisos = new ArrayList<SobreAvisoEntity>();
		
		if (controleMensalBusiness.verificarMesFechado(sobreAviso.getData()))
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
		
		sobreAvisos = sobreAvisoDAO.findByPeriodoAndIdFuncionario(sobreAviso.getIdFuncionario(), new java.sql.Date(sobreAviso.getData().getTime()), new java.sql.Date(sobreAviso.getData().getTime()));

		if (sobreAvisos.isEmpty()) {
			
			UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
			Integer idFuncionario = sobreAviso.getIdFuncionario();
			if (idFuncionario == null)
				idFuncionario = usuarioLogado.getFuncionario().getId();

			sobreAviso.setDataInclusao(new Date());
			sobreAviso.setIdUsuarioInclusao(usuarioLogado.getId());

			SobreAvisoEntity entity = sobreAvisoConverter.convertBeanToEntity(sobreAviso);

			ControleDiarioBean controleDiario = controleDiarioBusiness.obterPorDataIdFuncionario(sobreAviso.getData(),
					idFuncionario);
			entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));

			sobreAviso.setId(sobreAvisoDAO.save(entity).getId());

			ApontamentoPivotEntity apontamento = calculoBusiness.calcularApontamento(idFuncionario, sobreAviso.getData());
			
			controleDiario.setSobreAviso(apontamento.getTotalSobreAviso());
			
			sobreAviso.setControleDiario(controleDiarioBusiness.tratarArredondamentos(controleDiario));
			
		} else{
			
			throw new BusinessException("Não é possível lançar dois sobre avisos para o mesmo dia");
		}


		return sobreAviso;
	}

	public void remover(Integer id) throws BusinessException {
		if (id == null || id.equals(0)) {
			throw new BusinessException("Sobre aviso não encontrado em nossa base.");
		}
		SobreAvisoEntity sobreAviso = sobreAvisoDAO.findById(id);

		if (controleMensalBusiness.verificarMesFechado(sobreAviso.getData()))
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");

		sobreAvisoDAO.deleteById(id);

		int idFuncionario = sobreAviso.getControleDiario().getControleMensal().getIdFuncionario();
		calculoBusiness.calcularApontamento(idFuncionario, sobreAviso.getData());
	}

	public List<SobreAvisoBean> listarPorIdFuncionarioEPeriodo(Integer idFuncionario, String[] periodo) {
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
		
		List<SobreAvisoEntity> entities = sobreAvisoDAO.findByPeriodoAndIdFuncionario(funcionario.getId(), periodos[0], periodos[1]);
		List<SobreAvisoBean> avisoBeans = sobreAvisoConverter.convertEntityToBean(entities);
		
		for (SobreAvisoBean sobreAvisoBean : avisoBeans) {
			
			Boolean mesFechado = controleMensalBusiness.verificarMesFechado(sobreAvisoBean.getData());
			
			sobreAvisoBean.setMesFechado(mesFechado);
		}
		
		return avisoBeans;
	}
}