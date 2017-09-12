package br.com.verity.pause.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ConsultaCompletaBean;
import br.com.verity.pause.bean.FuncionarioBean;

@Component
public class GerarRelatorioXlsx {

	@Autowired
	private VerificarData verificarData;

	/**
	 * @author guilherme.oliveira XSSF gera um arquivo .xlsx e HSSF gera um
	 *         arquivo .xls WorkBook é um arquivo Excel, Sheet é para gerar um
	 *         planilha, row gera/seleciona uma linha, Cell gera/seleciona uma
	 *         celula da linha. WoorkBook.write(FileOutputStram) salva os dados
	 *         na planilha
	 */
	@SuppressWarnings("deprecation")
	public String relatorioFuncionarioPeriodo(List<ConsultaCompletaBean> consultaCompleta, FuncionarioBean funcionario,
			String de, String ate) {
		String arquivo = "C:" + File.separator + "Pause" + File.separator + "Relatorios" + File.separator
				+ funcionario.getNome() + ".xlsx";
		DateTimeFormatter formatter = DateTimeFormatter.ISO_TIME;
		DateFormat formatDt = new SimpleDateFormat("dd/MM/yyyy");
		int linha = 0;
		int dia = 1;
		int mesmoDia = 0;
		int aux = 0;
		try {
			// Cria uma planilha .xlsx no diretorio escolhido
			FileOutputStream out = new FileOutputStream(new File(arquivo));
			XSSFWorkbook workbook = new XSSFWorkbook();
			// Cria uma planilha no arquivo;
			@SuppressWarnings("unused")
			XSSFSheet criaPlanilha = workbook.createSheet("Relatório");
			// Seleciona a planilha de trabalho
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow row = sheet.createRow(linha);
			sheet.setColumnWidth(0, 11 * 256);
			sheet.setColumnWidth(1, 7 * 256);
			sheet.setColumnWidth(2, 4 * 256);
			sheet.setColumnWidth(3, 9 * 256);
			sheet.setColumnWidth(4, 3 * 256);
			sheet.setColumnWidth(5, 9 * 256);
			sheet.setColumnWidth(6, 3 * 256);
			sheet.setColumnWidth(7, 9 * 256);
			sheet.setColumnWidth(8, 3 * 256);
			sheet.setColumnWidth(9, 9 * 256);
			sheet.setColumnWidth(10, 3 * 256);
			sheet.setColumnWidth(11, 9 * 256);
			sheet.setColumnWidth(12, 3 * 256);
			sheet.setColumnWidth(13, 9 * 256);
			sheet.setColumnWidth(14, 3 * 256);
			sheet.setColumnWidth(15, 9 * 256);
			sheet.setColumnWidth(16, 3 * 256);
			sheet.setColumnWidth(17, 9 * 256);
			sheet.setColumnWidth(18, 3 * 256);
			row.createCell(0).setCellValue("Verity");
			row.createCell(2).setCellValue("Extrato Horas - Banco de Horas");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("PIS Funcionário: " + funcionario.getPis());
			row.createCell(6).setCellValue("Matrícula: " + funcionario.getMatricula());
			row.createCell(17).setCellValue("Período: " + de + " a " + ate);
			linha++;
			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Nome: " + funcionario.getNome());
			linha++;

			row = sheet.createRow(linha);
			row.createCell(3).setCellValue("Ponto eletrônico");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Data Reg");
			row.createCell(1).setCellValue("Dia");
			row.createCell(2).setCellValue("SA");
			row.createCell(3).setCellValue("R1");
			row.createCell(4).setCellValue("TP");
			row.createCell(5).setCellValue("R2");
			row.createCell(6).setCellValue("TP");
			row.createCell(7).setCellValue("R3");
			row.createCell(8).setCellValue("TP");
			row.createCell(9).setCellValue("R4");
			row.createCell(10).setCellValue("TP");
			row.createCell(11).setCellValue("R5");
			row.createCell(12).setCellValue("TP");
			row.createCell(13).setCellValue("R6");
			row.createCell(14).setCellValue("TP");
			row.createCell(15).setCellValue("R7");
			row.createCell(16).setCellValue("TP");
			row.createCell(17).setCellValue("R8");
			row.createCell(18).setCellValue("TP");
			row.createCell(19).setCellValue("Atestado");
			row.createCell(20).setCellValue("Horas");
			row.createCell(21).setCellValue("Positivas");
			row.createCell(22).setCellValue("Negativas");
			row.createCell(23).setCellValue("Ad Not");
			row.createCell(24).setCellValue("Horas SA");
			row.createCell(25).setCellValue("Observação");
			linha++;

			row = sheet.createRow(linha);
			for (ConsultaCompletaBean consulta : consultaCompleta) {
				if (consulta.getData().getDate() == dia) {
					mesmoDia += 2;
				} else {
					mesmoDia = (aux == 0) ? 2 : mesmoDia + 2;
					while (mesmoDia <= 16) {
						row.createCell(1 + mesmoDia).setCellValue("00:00:00");
						mesmoDia += 2;
					}
					linha++;
					dia++;
					mesmoDia = 2;
					row = sheet.createRow(linha);
					aux = 0;
				}
				String dtApontamento = formatDt.format(consulta.getData());
				row.createCell(0).setCellValue(dtApontamento);
				row.createCell(1).setCellValue(verificarData.qualDiaSimples(consulta.getData().getDay()));
				row.createCell(2).setCellValue(
						(consulta.getSobreAvisoId() != null && consulta.getSobreAvisoId() != 0) ? "S" : "");
				if (consulta.getApontamentoHorario() != null) {
					String hora = formatter.format(consulta.getApontamentoHorario());
					row.createCell(1 + mesmoDia).setCellValue(hora);
					row.createCell(2 + mesmoDia)
							.setCellValue((consulta.getApontamentoTpImportacao() == true) ? "E" : "M");
					aux++;
				}
				row.createCell(19).setCellValue(
						(consulta.getAtestadoQuantidadeHora() != null) ? consulta.getAtestadoQuantidadeHora() : 0);
				row.createCell(20).setCellValue(
						(consulta.getControleDiarioHoraTotal() != null) ? consulta.getControleDiarioHoraTotal() : 0);
				row.createCell(21).setCellFormula("IF(U" + (linha + 1) + "+T" + (linha + 1) + ">8,U" + (linha + 1)
						+ "+T" + (linha + 1) + "-8,0)");
				row.createCell(22).setCellFormula(
						"IF(OR(B" + (linha + 1) + "=\"Dom\", B" + (linha + 1) + "=\"Sáb\"),0,IF(U" + (linha + 1) + "+T"
								+ (linha + 1) + "<8,U" + (linha + 1) + "+T" + (linha + 1) + "-8,0))");
				row.createCell(23).setCellValue(
						(consulta.getControleDiarioAdcNoturno() != null) ? consulta.getControleDiarioAdcNoturno() : 0);
				row.createCell(24)
						.setCellValue((consulta.getControleDiarioSA() != null) ? consulta.getControleDiarioSA() : 0);
				row.createCell(25).setCellValue(consulta.getApontamentoObs());
			}

			row = sheet.createRow(linha);
			row.createCell(20).setCellFormula("SUM(U6:U" + (linha) + ")");
			row.createCell(21).setCellFormula("SUM(V6:V" + (linha) + ")");
			row.createCell(22).setCellFormula("SUM(W6:W" + (linha) + ")");
			row.createCell(23).setCellFormula("SUM(X6:X" + (linha) + ")");
			row.createCell(24).setCellFormula("SUM(Y6:Y" + (linha) + ")");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(18).setCellValue("Saldo Final: ");
			row.createCell(20).setCellFormula("V" + (linha) + "+W" + (linha));
			row.createCell(21).setCellFormula("IF(U" + (linha + 1) + ">0,TEXT(U" + (linha + 1)
					+ "/24,\"d hh:mm\"),TEXT(-(U" + (linha + 1) + ")/24,\"- d hh:mm\"))");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Controle de Saldo de Horas");
			linha++;
			
			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Trimestre");
			row.createCell(1).setCellValue("Ano");
			row.createCell(2).setCellValue("Mês");
			row.createCell(3).setCellValue("Banco");
			row.createCell(4).setCellValue("Saldo");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(0)
					.setCellValue(verificarData.qualSemestre((consultaCompleta.get(0).getControleMensalMes() != null)
							? consultaCompleta.get(0).getControleMensalMes() : Integer.parseInt(de.substring(3, 5))));
			row.createCell(1).setCellValue((consultaCompleta.get(0).getControleMensalAno() != null)
					? consultaCompleta.get(0).getControleMensalAno() : Integer.parseInt(de.substring(6, 10)));
			row.createCell(2).setCellValue((consultaCompleta.get(0).getControleMensalMes() != null)
					? consultaCompleta.get(0).getControleMensalMes() : Integer.parseInt(de.substring(3, 5)));
			if (consultaCompleta.get(0).getControleMensalHoraTotal() != null) {
				row.createCell(3).setCellValue(consultaCompleta.get(0).getControleMensalBancoHora());
				row.createCell(4).setCellValue(consultaCompleta.get(0).getControleMensalHoraTotal()
						+ consultaCompleta.get(0).getControleMensalBancoHora());
			}else{
				row.createCell(3).setCellValue(0);
				row.createCell(4).setCellValue(0);
			}
			linha++;

			row = sheet.createRow(linha);
			row.createCell(18).setCellValue("________________________________________________________________________");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(18).setCellValue("Declaro que analisei as informações deste relatório e reconheço a");
			linha++;

			row = sheet.createRow(linha);
			row.createCell(18).setCellValue("veracidade dos registros.");
			linha++;
			linha++;

			row = sheet.createRow(linha);
			row.createCell(0).setCellValue(
					"Legenda : Origem Marcação: M- Manual / E- Eletrônico / SA = Sobre Aviso (Plantão); SA = Sobre Aviso sem Acionamento / ST = Sobre Aviso Trabalhado / TP = Tipo do Ponto");
			linha++;

			row = sheet.createRow(linha);
			Date hoje = new Date();
			String dtHoje = formatDt.format(hoje);
			row.createCell(0).setCellValue(dtHoje);

			// Escreve no arquivo
			workbook.write(out);
			// Fecha e salva o arquivo
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return arquivo;
	}
}