package br.com.verity.pause.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.exception.BusinessException;

@Component
public class ImportarTxt {

	private BufferedReader lerArquivo;

	public List<ApontamentoBean> importar(String caminho, EmpresaBean empresa)
			throws BusinessException, IOException, ParseException {
		List<ApontamentoBean> apontamentos = new ArrayList<ApontamentoBean>();
		ApontamentoBean apontamento = new ApontamentoBean();
		String linha;
		Date data;
		Date dataInclusao = new Date();
		Time hrs;
		String codReg;
		String pis;
		String cnpjArquivo;
		Date dataImportacao;
		SimpleDateFormat formataData = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formataHora = new SimpleDateFormat("HHmmSS");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UsuarioBean usuarioLogado = (UsuarioBean) auth.getPrincipal();

		try {
			FileReader arquivo = new FileReader(caminho);
			lerArquivo = new BufferedReader(arquivo);

			linha = lerArquivo.readLine();
			cnpjArquivo = linha.substring(11, 25);
			if (empresa.getCnpj().equals(cnpjArquivo)) {
				linha = lerArquivo.readLine(); // lê a próxima linha linha

				dataImportacao = formataData.parse(linha.substring(10, 18));
				while (linha != null) {
					codReg = linha.substring(0, 10);

					data = formataData.parse(linha.substring(10, 18));
					if (!codReg.contains("9999999") && dataImportacao.equals(data)) {
						pis = linha.substring(23, 34);

						Date dtTime = formataHora.parse(linha.substring(18, 22) + 00);
						hrs = new Time(dtTime.getTime());

						apontamento = new ApontamentoBean();

						apontamento.setPis(pis);
						apontamento.setData(data);
						apontamento.setHorario(hrs.toLocalTime());
						apontamento.setIdEmpresa(empresa.getId());
						apontamento.setDataInclusao(dataInclusao);
						apontamento.setTipoImportacao(true);
						apontamento.setIdUsuarioInclusao(usuarioLogado.getId());

						apontamentos.add(apontamento);
					} else if (!dataImportacao.equals(data) && !codReg.contains("9999999")) {
						throw new BusinessException("Arquivo contém mais de uma data");
					}
					linha = lerArquivo.readLine();
				}
			} else {
				throw new BusinessException("Arquivo importado, não é da empresa: " + empresa.getNomeFantasia());
			}
			arquivo.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		// horas.sort(Comparator.comparing(ApontamentoBean::getPis));

		return apontamentos;
	}
}