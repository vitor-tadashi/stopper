package br.com.verity.pause.business;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.HorasBean;
import br.com.verity.pause.converter.HorasConverter;
import br.com.verity.pause.dao.HorasDAO;
import br.com.verity.pause.exception.BusinessException;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.ImportarTxt;

@Service
public class ImportacaoBusiness {

	@Autowired
	private ImportarTxt importar;

	@Autowired
	private SavIntegration integration;

	@Autowired
	private HorasDAO horasDao;

	@Autowired
	private HorasConverter horasConverter;

	public List<FuncionarioBean> importarTxt(String caminho, String empresa) throws BusinessException {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComHoras = new ArrayList<FuncionarioBean>();
		List<HorasBean> horas = new ArrayList<HorasBean>();

		try {
			horas = importar.importar(caminho);
		} catch (BusinessException | ParseException e) {
			throw new BusinessException(e.getMessage());
		}
		funcionarios = integration.getFuncionarios(empresa);

		for (FuncionarioBean bean : funcionarios) {

			bean.setHoras(horas.stream().filter(hr -> hr.getPis().equals(bean.getPis())).collect(Collectors.toList()));

			if (bean.getHoras() != null && bean.getHoras().size() > 0) {
				funcionariosComHoras.add(bean);
			}
		}

		return funcionariosComHoras;
	}

	public void salvarHoras(List<HorasBean> horas) {
		try {
			horasDao.saveAll(horasConverter.convertBeanToEntity(horas));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}