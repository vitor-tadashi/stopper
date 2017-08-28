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

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ApontamentosBean;
import br.com.verity.pause.exception.BusinessException;

@Component
public class ImportarTxt {

	private BufferedReader lerArquivo;

	public List<ApontamentosBean> importar(String caminho, String empresa) throws BusinessException, ParseException {
		List<ApontamentosBean> apontamentos = new ArrayList<ApontamentosBean>();
		ApontamentosBean apontamento = new ApontamentosBean();
		String linha;
		Date data;
		Time hrs;
		String codReg;
		String pis;
		Date dataImportacao;
		SimpleDateFormat formataData = new SimpleDateFormat("ddMMyyyy");
		SimpleDateFormat formataHora = new SimpleDateFormat("HHmmSS");
		
		try {
			FileReader arquivo = new FileReader(caminho);
			lerArquivo = new BufferedReader(arquivo);

			linha = lerArquivo.readLine();
			linha = lerArquivo.readLine(); // lê a próxima linha linha
			
			dataImportacao = formataData.parse(linha.substring(10, 18));
			while (linha != null) {
				codReg = linha.substring(0, 10);
				
				data = formataData.parse(linha.substring(10, 18));
				if (!codReg.contains("9999999") && dataImportacao.equals(data)) {
					pis = linha.substring(23, 34);

					Date dtTime = formataHora.parse(linha.substring(18, 22)+00);
					hrs = new Time(dtTime.getTime());
					
					
					apontamento = new ApontamentosBean();
					
					apontamento.setPis(pis);
					apontamento.setData(data);
					apontamento.setHora(hrs);
					apontamento.setEmpresa(empresa);

					apontamentos.add(apontamento);
				}else if(!dataImportacao.equals(data) && !codReg.contains("9999999")){
					throw new BusinessException("Arquivo contém mais de uma data");
				}
				linha = lerArquivo.readLine();
			}
			arquivo.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//horas.sort(Comparator.comparing(ApontamentosBean::getPis));
		
		return apontamentos;
	}
}