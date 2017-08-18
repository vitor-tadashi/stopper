package br.com.verity.regponto.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.verity.regponto.bean.EmpresaBean;
import br.com.verity.regponto.converter.EmpresaConverter;
import br.com.verity.regponto.dao.EmpresaDAO;
import br.com.verity.regponto.entity.EmpresaEntity;

@Service
public class EmpresaBusiness {
	
	@Autowired
	private EmpresaDAO empresaDAO;
	
	@Autowired
	private EmpresaConverter empresaConverter;
	
	@Transactional
	public List<EmpresaBean> obterTodos(){
		List<EmpresaEntity> entities = empresaDAO.findAll();
		return empresaConverter.convertEntityToBean(entities);
	}

}
