package br.com.verity.regponto.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.verity.regponto.bean.HorasBean;

@Component
public class ImportarTxt {

	/*public List<HorasBean> importar(String caminho) {
		List<HorasBean> importacoes = new ArrayList<HorasBean>();
		SavIntegration sav = new SavIntegration();
		HorasBean importacao = new HorasBean();
		List<FuncionarioIntegrationBean> funcs = sav.getFuncionarios();
		FuncionarioIntegrationBean funcionario = new FuncionarioIntegrationBean();
		String linha;
		String data;
		String hora;
		String codReg;
		try {
			FileReader arquivo = new FileReader(caminho);
			BufferedReader lerArquivo = new BufferedReader(arquivo);

			linha = lerArquivo.readLine();
			linha = lerArquivo.readLine(); // lê a próxima linha linha
			while (linha != null) {
				codReg = linha.substring(0, 10);
				if (!codReg.contains("9999999")) {
					data = linha.substring(10, 12);
					data += "/";
					data += linha.substring(12, 14);
					data += "/";
					data += linha.substring(14, 18);
					hora = linha.substring(18, 20) + ":";
					hora += linha.substring(20, 22);
					final String PIS = linha.substring(23, 34);
					funcionario = new FuncionarioIntegrationBean();
					try {
						funcionario = funcs.stream().filter(st -> st.getPis().equals(PIS)).findFirst().get();
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
					System.out.println("cod: " + codReg + "\tdata: " + data + "\thora: " + hora + "\tPIS: " + PIS
							+ "\tNome Func: " + funcionario.getNome());

					importacao = new HorasBean();

					importacao.setPis(PIS);
					importacao.setDataImportacao(data);
					importacao.setHora(hora);
					importacao.setCaminhoArquivo(caminho);
					importacao.setFuncionario(funcionario);

					importacoes.add(importacao);
				}
				linha = lerArquivo.readLine();
			}
			arquivo.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		return importacoes;
	}*/
	
	public List<HorasBean> importar(String caminho) {
		List<HorasBean> horas = new ArrayList<HorasBean>();
		HorasBean hora = new HorasBean();
		String linha;
		String data;
		String hrs;
		String codReg;
		String pis;
		try {
			FileReader arquivo = new FileReader(caminho);
			BufferedReader lerArquivo = new BufferedReader(arquivo);

			linha = lerArquivo.readLine();
			linha = lerArquivo.readLine(); // lê a próxima linha linha
			while (linha != null) {
				codReg = linha.substring(0, 10);
				if (!codReg.contains("9999999")) {
					data = linha.substring(10, 12);
					data += "/";
					data += linha.substring(12, 14);
					data += "/";
					data += linha.substring(14, 18);
					hrs = linha.substring(18, 20) + ":";
					hrs += linha.substring(20, 22);
					pis = linha.substring(23, 34);
					System.out.println("cod: " + codReg + "\tdata: " + data + "\thora: " + hrs + "\tPIS: " + pis);

					hora = new HorasBean();

					hora.setPis(pis);
					hora.setDataImportacao(data);
					hora.setHora(hrs);

					horas.add(hora);
				}
				linha = lerArquivo.readLine();
			}
			arquivo.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		horas.sort(Comparator.comparing(HorasBean::getPis));
		
		return horas;
	}
}