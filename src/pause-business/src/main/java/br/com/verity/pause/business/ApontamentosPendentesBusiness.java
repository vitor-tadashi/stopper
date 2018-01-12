package br.com.verity.pause.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentosPendentesBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.dao.ApontamentosPendentesDAO;
import br.com.verity.pause.entity.ApontamentosPendentesEntity;

@Service
public class ApontamentosPendentesBusiness {

	@Autowired
	private CustomUserDetailsBusiness userBusiness;
	
	@Autowired
	private ApontamentosPendentesDAO pendentesDAO;	
	
	@Autowired
	private FuncionarioBusiness funcionarioBusiness;	

	public List<ApontamentosPendentesBean> obterApontamentosPendentes() throws SQLException {

		List<ApontamentosPendentesBean> pendentes = new ArrayList<ApontamentosPendentesBean>();
		UsuarioBean usuarioLogado = null;
		usuarioLogado = userBusiness.usuarioLogado();
		int idEmpresa = usuarioLogado.getIdEmpresaSessao();
		
		for (ApontamentosPendentesEntity a : pendentesDAO.findPendentes(idEmpresa)) {
			FuncionarioBean f = funcionarioBusiness.obterPorId(a.getIdFuncionario());
			if (a.getId() != null && f.getNome() != null)
			pendentes.add(new ApontamentosPendentesBean(a.getId(), a.getData(), a.getIdEmpresa(), a.getIdFuncionario(), a.getDiasSemApontar(), f.getNome(), f.getEmailCorporativo()));
		}
		return pendentes;
	}
}
