package br.com.verity.pause.business;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.entity.ApontamentoEntity;

@Service
public class ApontamentoBusiness {

	@Autowired
	private ApontamentoDAO apontamentoDAO;
	
	@Autowired
	private ApontamentoConverter apontamentoConverter;
	
	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	public void apontar(ApontamentoBean apontamento) {
		UsuarioBean usuarioLogado = userBusiness.usuarioLogado();
		
		apontamento.setDataInclusao(new Date());
		apontamento.setIdUsuarioInclusao(usuarioLogado.getId());
		
		if(apontamento.getPis() == null){
			apontamento.setPis(usuarioLogado.getFuncionario().getPis());
			apontamento.setIdEmpresa(usuarioLogado.getFuncionario().getEmpresa().getId());
		}else{
			//TODO buscar funcionario pelo pis ou id
		}
		ApontamentoEntity entity = apontamentoConverter.convertBeanToEntity(apontamento);
		apontamentoDAO.save(entity);
	}

}
