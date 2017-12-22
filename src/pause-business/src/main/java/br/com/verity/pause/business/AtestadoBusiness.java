package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.AfastamentoBean;
import br.com.verity.pause.bean.AtestadoBean;
import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.TipoAtestadoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.AtestadoConverter;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.TipoAtestadoConverter;
import br.com.verity.pause.dao.AtestadoDAO;
import br.com.verity.pause.entity.AtestadoEntity;
import br.com.verity.pause.entity.TipoAtestadoEntity;
import br.com.verity.pause.exception.BusinessException;

@Service
public class AtestadoBusiness {

	@Autowired
	private ControleMensalBusiness controleMensalBusiness;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@Autowired
	private CustomUserDetailsBusiness userBusiness;

	@Autowired
	private AtestadoConverter atestadoConverter;

	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	@Autowired
	private AtestadoDAO atestadoDAO;

	@Autowired
	private CalculoBusiness calculoBusiness;

	@Autowired
	private TipoAtestadoConverter tipoAtestadoConverter;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	public AtestadoBean salvar(AtestadoBean atestado) throws BusinessException {
		if (controleMensalBusiness.verificarMesFechado(atestado.getControleDiario().getData()))
			throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");

		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		Integer idFuncionario = atestado.getIdFuncionario();
		if (idFuncionario == null)
			idFuncionario = usuarioLogado.getFuncionario().getId();

		atestado.setDataInclusao(new Date());
		atestado.setIdUsuarioInclusao(usuarioLogado.getId());

		AtestadoEntity entity = atestadoConverter.convertBeanToEntity(atestado);
		entity.setTipoAtestado(tipoAtestadoConverter.convertBeanToEntity(atestado.getTipoAtestado()));

		ControleDiarioBean controleDiario = controleDiarioBusiness
				.obterPorDataIdFuncionario(atestado.getControleDiario().getData(), idFuncionario);
		entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));

		atestado.setId(atestadoDAO.save(entity).getIdAtestado());

		calculoBusiness.calcularApontamento(idFuncionario, atestado.getControleDiario().getData());

		return atestado;
	}

	public void remover(Integer id) throws BusinessException {
		if (id == null || id.equals(0)) {
			throw new BusinessException("Atestado não encontrado em nossa base.");
		}
		AtestadoEntity atestado = atestadoDAO.findById(id);
		if(atestado != null){
			if (controleMensalBusiness.verificarMesFechado(atestado.getControleDiario().getData()))
				throw new BusinessException("Não foi possível realizar a ação, pois o mês está fechado.");
	
			atestadoDAO.deleteById(id);
	
			int idFuncionario = atestado.getControleDiario().getControleMensal().getIdFuncionario();
			calculoBusiness.calcularApontamento(idFuncionario, atestado.getControleDiario().getData());
		}
	}

	public List<TipoAtestadoBean> listarTipoAtestado() {
		List<TipoAtestadoEntity> entities = atestadoDAO.findAll();
		List<TipoAtestadoBean> beans = tipoAtestadoConverter.convertEntityToBean(entities);

		return beans;
	}

	public List<AtestadoBean> listarPorIdFuncionarioEPeriodo(Integer idFuncionario, String[] periodo) {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorId(idFuncionario);
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date periodos[] = new java.sql.Date[2];
		try {
			if (periodo == null || (periodo[0].isEmpty() && periodo[1].isEmpty())) {
				periodos[0] = java.sql.Date.valueOf((LocalDate.now().minusDays(6)));
				periodos[1] = java.sql.Date.valueOf((LocalDate.now()));
			} else if (periodo[0].isEmpty()) {
				periodos[0] = java.sql.Date.valueOf("2010-03-10");
				periodos[1] = new java.sql.Date(fmt2.parse(periodo[1]).getTime());
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

		List<AtestadoEntity> entities = atestadoDAO.findByPeriodoAndIdFuncionario(funcionario.getId(), periodos[0],
				periodos[1]);
		
		List<AtestadoBean> atestados = atestadoConverter.convertEntityToBean(entities);
		
		for (int i = 0; i < entities.size(); i++) {
			atestados.get(i)
					.setTipoAtestado(tipoAtestadoConverter.convertEntityToBean(entities.get(i).getTipoAtestado()));
			atestados.get(i).setControleDiario(
					controleDiarioConverter.convertEntityToBean(entities.get(i).getControleDiario()));
		}

		for (AtestadoBean atestado : atestados) {

			Boolean mesFechado = controleMensalBusiness.verificarMesFechado(atestado.getDataInclusao());

			atestado.setMesFechado(mesFechado);

		}

		return atestados;
	}

}
