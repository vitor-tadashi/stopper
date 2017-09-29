package br.com.verity.pause.business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Time;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.ArquivoApontamentoBean;
import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.ApontamentoConverter;
import br.com.verity.pause.converter.ArquivoApontamentoConverter;
import br.com.verity.pause.converter.ControleDiarioConverter;
import br.com.verity.pause.dao.ApontamentoDAO;
import br.com.verity.pause.dao.ArquivoApontamentoDAO;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.exception.BusinessException;
import br.com.verity.pause.integration.SavIntegration;
import br.com.verity.pause.util.DataUtil;

@Service
public class ImportacaoBusiness {

	@Autowired
	private SavIntegration integration;

	@Autowired
	private ApontamentoDAO apontamentoDao;

	@Autowired
	private ArquivoApontamentoDAO arquivoApontamentoDao;

	@Autowired
	private ApontamentoConverter apontamentoConverter;

	@Autowired
	private ArquivoApontamentoConverter arquivoApontamentoConverter;

	@Autowired
	private CalculoBusiness calculoBusiness;

	@Autowired
	private ControleDiarioBusiness controleDiarioBusiness;

	@Autowired
	private ControleDiarioConverter controleDiarioConverter;

	public List<FuncionarioBean> importarArquivoDeApontamento(String caminho, int idEmpresa, String data)
			throws BusinessException, IOException, ParseException {
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComApontamentos = new ArrayList<FuncionarioBean>();
		FuncionarioBean funcMensagem = new FuncionarioBean();
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();
		Boolean verificarImportacao;
		EmpresaBean empresaBean = new EmpresaBean();
		Date dataImportacao = new Date();
		List<Date> diasImportacao = new ArrayList<Date>();

		dataImportacao = DataUtil.converterData(data, "yyyy-MM-dd");

		diasImportacao = DataUtil.verificarSegundaFeira(dataImportacao);

		try {

			empresaBean = integration.getEmpresa(idEmpresa);

			for (Date diaImportacao : diasImportacao) {
				apontamentos.addAll(this.selecionarApontamentos(caminho, empresaBean, diaImportacao));
			}

		} catch (BusinessException e) {
			throw new BusinessException(e.getMessage());
		}

		try {
			verificarImportacao = apontamentoDao
					.findByData(apontamentoConverter.convertBeanToEntity(apontamentos.get(0)), idEmpresa);

			if (verificarImportacao) {
				funcMensagem.setMensagem("Este arquivo já foi importado. Deseja substituí-lo?");
				funcionariosComApontamentos.add(funcMensagem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		funcionarios = integration.getFuncionarios(idEmpresa);

		for (FuncionarioBean bean : funcionarios) {

			bean.setApontamentos(
					apontamentos.stream().filter(hr -> hr.getPis().equals(bean.getPis())).collect(Collectors.toList()));

			if (bean.getApontamentos() != null && bean.getApontamentos().size() > 0) {
				funcionariosComApontamentos.add(bean);
			}
		}

		return funcionariosComApontamentos;
	}

	public void salvarApontamentos(List<ApontamentoBean> apontamentos, ArquivoApontamentoBean arquivoApontamento) {
		ArquivoApontamentoEntity arquivoApontamentoEntity = arquivoApontamentoConverter
				.convertBeanToEntity(arquivoApontamento);
		List<ApontamentoEntity> apontamentoEntity = this.setControleDiarioBeanToEntity(apontamentos);
		Integer idArquivo = null;
		try {
			apontamentoDao.excludeAllDate(new java.sql.Date(arquivoApontamentoEntity.getData().getTime()));
			arquivoApontamentoDao.excludeDate(arquivoApontamentoEntity);
			arquivoApontamentoDao.save(arquivoApontamentoEntity);
			idArquivo = arquivoApontamentoDao.findByDateAndEmpresa(arquivoApontamentoEntity);
			apontamentoDao.saveAll(apontamentoEntity, idArquivo);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private List<ApontamentoEntity> setControleDiarioBeanToEntity(List<ApontamentoBean> apontamentosBean) {
		List<ApontamentoEntity> apontamentos = new ArrayList<ApontamentoEntity>();
		ApontamentoEntity apontamento = null;
		ControleDiarioEntity controleDiario = null;

		for (ApontamentoBean bean : apontamentosBean) {
			apontamento = new ApontamentoEntity();
			apontamento = apontamentoConverter.convertBeanToEntity(bean);
			if (bean.getCntrDiario() != null && bean.getCntrDiario().getId() != null) {
				controleDiario = new ControleDiarioEntity();
				controleDiario = controleDiarioConverter.convertBeanToEntity(bean.getCntrDiario());
			}
			apontamento.setControleDiario(controleDiario);
			apontamentos.add(apontamento);
		}

		return apontamentos;
	}

	public void acionarCalculos(List<FuncionarioBean> funcionariosImportacao) {
		Date data = null;
		for (FuncionarioBean funcionarioBean : funcionariosImportacao) {
			data = funcionarioBean.getApontamentos().get(0).getData();
			controleDiarioBusiness.obterPorDataIdFuncionario(data, funcionarioBean.getId());
			calculoBusiness.calcularApontamento(funcionarioBean.getId(), data);
		}
	}

	@SuppressWarnings("resource")
	private List<ApontamentoBean> selecionarApontamentos(String caminho, EmpresaBean empresa, Date diaImportacao)
			throws ParseException, BusinessException, IOException {

		BufferedReader arquivo = null;
		FileReader leitor = null;
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();
		Date dataImportacao;
		Date data;
		Time horario;
		String linha;
		String codReg;
		String pis;
		String cnpjArquivo;
		String diaSemFormatacao;
		String expressaoRegular;
		Matcher combinacao;
		Pattern padraoLinha;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();

		try {
			leitor = new FileReader(caminho);
			arquivo = new BufferedReader(leitor);

			linha = arquivo.readLine();
			cnpjArquivo = linha.substring(11, 25);

			if (empresa.getCnpj().equals(cnpjArquivo)) {
				linha = arquivo.readLine();

				diaSemFormatacao = DataUtil.formatarData(diaImportacao, "ddMMyyyy");

				expressaoRegular = "(\\d{10})(" + diaSemFormatacao + ")((\\d{16}))";
				padraoLinha = Pattern.compile(expressaoRegular);
				combinacao = padraoLinha.matcher(linha);

				dataImportacao = DataUtil.converterData(linha.substring(10, 18), "ddMMyyyy");

				while (linha != null) {
					
					combinacao = padraoLinha.matcher(linha);

					if(combinacao.find()) {

						codReg = linha.substring(0, 10);
						data = DataUtil.converterData(linha.substring(10, 18), "ddMMyyyy");

						if (!codReg.contains("9999999") && dataImportacao.equals(data)) {

							pis = linha.substring(23, 34);
							horario = new Time(
									DataUtil.converterData(linha.substring(18, 22) + 00, "HHmmSS").getTime());

							apontamentos.add(new ApontamentoBean(pis, data, horario.toLocalTime(), empresa.getId(),
									new Date(), true, usuarioLogado.getId()));

						} else if (!dataImportacao.equals(data) && !codReg.contains("9999999")) {

							throw new BusinessException("Arquivo importado inválido.");

						}

					}

					linha = arquivo.readLine();

				}

			} else {

				throw new BusinessException("Arquivo importado inválido.");

			}

		} catch (IOException e) {

			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
			throw new BusinessException("Arquivo importado inválido.");

		} finally {
			leitor.close();
		}

		return apontamentos;
	}
}