package br.com.verity.pause.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.dao.ApontamentoDAO;

@Service
public class ApontamentoBusiness {
	
	@Autowired
	private ApontamentoDAO apontamentoDao;

	@Autowired
	private ApontamentoConverter apontamentoConverter;
	
	public List<ApontamentoBean> obterApontamentosPeriodoPorPisFuncionario(String pis, String de,
			String ate) {
		List<ApontamentoBean> apontamentosBean = new ArrayList<ApontamentoBean>();
		SimpleDateFormat formataData = new SimpleDateFormat("dd-MM-yyyy");
		Date dtDe = null;
		Date dtAte = null;
		try {
			dtDe = formataData.parse(de);
			dtAte = formataData.parse(ate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		apontamentosBean = apontamentoConverter.convertEntityToBean(apontamentoDao.findByPisAndPeriodo(pis,dtDe,dtAte));
		
		return apontamentosBean;
	}

}
