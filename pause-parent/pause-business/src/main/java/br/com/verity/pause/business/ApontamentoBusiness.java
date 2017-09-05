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
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.converter.ConsultaCompletaConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.dao.ConsultaCompletaDAO;
import br.com.verity.pause.entity.ApontamentoEntity;

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
	
	public List<ConsultaCompletaBean> obterApontamentosPeriodoPorIdFuncionario(Integer id, String de,
			String ate) {
		List<ConsultaCompletaBean> consultaCompleta = new ArrayList<ConsultaCompletaBean>();
		SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
		Date dtDe = null;
		Date dtAte = null;
		try {
			dtDe = formataData.parse(de);
			dtAte = formataData.parse(ate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		consultaCompleta = consultaCompletaConverter.convertEntityToBean(consultaCompletaDAO.findByIdAndPeriodo(id,dtDe,dtAte));
		
		return consultaCompleta;
	}

}
