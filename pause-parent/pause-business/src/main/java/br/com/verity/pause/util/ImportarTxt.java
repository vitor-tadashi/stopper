package br.com.verity.pause.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.HorasBean;
import br.com.verity.pause.exception.BusinessException;

@Component
public class ImportarTxt {

	public List<HorasBean> importar(String caminho) throws BusinessException {
		List<HorasBean> horas = new ArrayList<HorasBean>();
		HorasBean hora = new HorasBean();
		String linha;
		String data;
		String hrs;
		String codReg;
		String pis;
		String dataImportacao;
		try {
			FileReader arquivo = new FileReader(caminho);
			BufferedReader lerArquivo = new BufferedReader(arquivo);

			linha = lerArquivo.readLine();
			linha = lerArquivo.readLine(); // lê a próxima linha linha
			dataImportacao = linha.substring(10, 18);
			while (linha != null) {
				codReg = linha.substring(0, 10);
				data = linha.substring(10, 18);
				if (!codReg.contains("9999999") && dataImportacao.equals(data)) {
					hrs = linha.substring(18, 22);
					pis = linha.substring(23, 34);

					hora = new HorasBean();
					
					hora.setPis(pis);
					hora.setDataImportacao(data);
					hora.setHora(hrs);

					horas.add(hora);
				}else if(!dataImportacao.equals(data) && !codReg.contains("9999999")){
					throw new BusinessException("Arquivo contém mais de uma data");
				}
				linha = lerArquivo.readLine();
			}
			arquivo.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//horas.sort(Comparator.comparing(HorasBean::getPis));
		
		return horas;
	}
}