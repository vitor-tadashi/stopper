package br.com.verity.pause.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.dao.EmpresaDAO;

@Service
public class EmpresaBusiness {
	
	@Autowired
	private EmpresaDAO empresaDAO;
	
	@Transactional
	public List<EmpresaBean> obterTodos(){
		List<EmpresaBean> empresas = new ArrayList<EmpresaBean>();
		try {
			empresas = empresaDAO.findAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return empresas;
	}

}
