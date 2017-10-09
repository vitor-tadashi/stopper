package br.com.verity.pause.business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

	@Autowired
	private ControleMensalBusiness controleMensalBusiness;

	public String salvarArquivo(MultipartFile arquivosSelecionado, Integer idEmpresa, String dataImportacao)
			throws BusinessException {
		String diretorio = "";
		boolean indicadorMesFechado = false;
		try {
			indicadorMesFechado = controleMensalBusiness
					.verificarMesFechado(DataUtil.converterData(dataImportacao, "yyyy-MM-dd"));

			if (!indicadorMesFechado) {

				diretorio = "C:" + File.separator + "Pause" + File.separator + "importacao" + File.separator + idEmpresa
						+ File.separator;
				File arquivo = new File(diretorio);
				arquivo.mkdirs();

				arquivo = new File(diretorio + arquivosSelecionado.getOriginalFilename());
				IOUtils.copy(arquivosSelecionado.getInputStream(), new FileOutputStream(arquivo));
				diretorio = diretorio + arquivosSelecionado.getOriginalFilename();

			} else {

				throw new BusinessException("Não é possível importar o arquivo porque o mês escolhido está fechado");

			}

		} catch (Exception e) {
			throw new BusinessException("Houve um erro ao tentar salvar o arquivo, tente novamente mais tarde.");
		}

		return diretorio;
	}

	public List<FuncionarioBean> importarArquivoDeApontamento(String caminho, int idEmpresa, String data)
			throws BusinessException{
		List<FuncionarioBean> funcionarios = new ArrayList<FuncionarioBean>();
		List<FuncionarioBean> funcionariosComApontamentos = new ArrayList<FuncionarioBean>();
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();
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

			funcionarios = integration.getFuncionarios(idEmpresa);

			for (FuncionarioBean bean : funcionarios) {

				List<ApontamentoBean> a = apontamentos.stream().filter(hr -> hr.getPis().equals(bean.getPis()))
						.collect(Collectors.toList());
				
				bean.setApontamentos(a);

				if (bean.getApontamentos() != null && bean.getApontamentos().size() > 0) {
					funcionariosComApontamentos.add(bean);
				}
			}

		} catch (Exception e) {
			throw new BusinessException("Houve um erro interno, tente novamente mais tarde.");
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

				dataImportacao = DataUtil.converterData(diaSemFormatacao, "ddMMyyyy");

				while (linha != null) {

					combinacao = padraoLinha.matcher(linha);

					if (combinacao.find()) {

						codReg = linha.substring(0, 10);
						data = DataUtil.converterData(linha.substring(10, 18), "ddMMyyyy");

						if (!codReg.contains("9999999") && dataImportacao.equals(data)) {

							pis = linha.substring(23, 34);
							horario = new Time(
									DataUtil.converterData(linha.substring(18, 22) + 00, "HHmmSS").getTime());

							ApontamentoBean ap = new ApontamentoBean(pis, data, horario.toLocalTime(), empresa.getId(),
									new Date(), true, usuarioLogado.getId());
							
							if ( !existeApontamentoComMesmoPisDataHorario(ap, apontamentos)){
								
								apontamentos.add(ap);
								
							}
								
							
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

	// Este método retorna true caso o funcionário tenha dois apontamentos no mesmo horário: Exemplo: 9:12 e 9:12
	private boolean existeApontamentoComMesmoPisDataHorario(ApontamentoBean ap, List<ApontamentoBean> apontamentos) {

		for (ApontamentoBean a : apontamentos) {
			
			if (a.getPis().equals(ap.getPis())) {
				
				if ( a.getData().equals(ap.getData()) ) {
					
					if ( a.getHorario().equals(ap.getHorario()) ) {
						return true;
					}
					
				}
				
			}
			
		}
		
		
		return false;
	}

	public boolean verificarReenvioDeArquivo(String dataImportacao, Integer idEmpresa) throws BusinessException{
		ArquivoApontamentoEntity arquivoApontamento =  new ArquivoApontamentoEntity();
		Integer idArquivoApontamento = null;
		boolean indicadorReenvio = false;
		
		try {
			
			arquivoApontamento.setData(DataUtil.converterData(dataImportacao, "yyyy-MM-dd"));
			arquivoApontamento.setIdEmpresa(idEmpresa);
			
			idArquivoApontamento = arquivoApontamentoDao.findByDateAndEmpresa(arquivoApontamento);
			
			indicadorReenvio = ((idArquivoApontamento != null) && (idArquivoApontamento > 0));
			
		} catch (Exception e) {
			
			throw new BusinessException("Houve um erro ao importar arquivo, tente novamente mais tarde");
		}
		
		
		return indicadorReenvio;
	}
}