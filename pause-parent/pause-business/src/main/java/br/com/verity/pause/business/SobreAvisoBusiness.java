package br.com.verity.pause.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ControleDiarioBean;
import br.com.verity.pause.bean.SobreAvisoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.converter.SobreAvisoConverter;
import br.com.verity.pause.dao.SobreAvisoDAO;
import br.com.verity.pause.entity.SobreAvisoEntity;

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

	public void salvar(SobreAvisoBean sobreAviso) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		
		sobreAviso.setDataInclusao(new Date());
		sobreAviso.setIdUsuarioInclusao(usuarioLogado.getId());
		
		SobreAvisoEntity entity = sobreAvisoConverter.convertBeanToEntity(sobreAviso);
		
		ControleDiarioBean controleDiario = controleDiarioBusiness.obterPorData(sobreAviso.getData());
		entity.setControleDiario(controleDiarioConverter.convertBeanToEntity(controleDiario));
		
		sobreAvisoDAO.save(entity);
	}

	public void remover(Integer id) {
		// TODO Auto-generated method stub
		
	}

}
