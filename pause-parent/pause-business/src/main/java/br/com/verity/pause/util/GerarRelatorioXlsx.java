package br.com.verity.pause.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.ApontamentoBean;
import br.com.verity.pause.bean.FuncionarioBean;

@Component
public class GerarRelatorioXlsx {
	
	/**
	 * @author guilherme.oliveira
	 *	XSSF gera um arquivo .xlsx e HSSF gera um arquivo .xls
	 *	WorkBook é um arquivo Excel, Sheet é para gerar um planilha, row gera/seleciona uma linha,
	 *	Cell gera/seleciona uma celula da linha. WoorkBook.write(FileOutputStram) salva os dados na planilha 
	 */
	public void relatorioFuncionarioPeriodo(FuncionarioBean funcionario, String de, String ate) {
		String caminho = "C:" + File.separator + funcionario.getNome() + ".xlsx";
		String arquivo = "C:" + File.separator + "Pause" + File.separator + "Relatorios" + File.separator
				+ funcionario.getNome() + ".xlsx";
		int linha = 0;
		File pasta = new File(caminho);
		pasta.mkdirs();
		try {
			//Cria uma planilha .xlsx no diretorio escolhido
			FileOutputStream out = new FileOutputStream(new File(arquivo));
			XSSFWorkbook workbook = new XSSFWorkbook();
			//Cria uma planilha no arquivo;
			@SuppressWarnings("unused")
			XSSFSheet criaPlanilha = workbook.createSheet("Relatório");
			//Seleciona a planilha de trabalho
			XSSFSheet sheet = workbook.getSheetAt(0);
			XSSFRow row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Verity");
			row.createCell(2).setCellValue("Extrato Horas - Banco de Horas");
			linha ++;
			
			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("PIS Funcionário: "+funcionario.getPis());
			row.createCell(6).setCellValue("Matrícula: "+funcionario.getMatricula());
			row.createCell(17).setCellValue("Período: "+de+" a "+ate);
			linha++;
			
			row = sheet.createRow(linha);
			row.createCell(0).setCellValue("Nome: "+funcionario.getNome());
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
			
			int mesmoDia = 0;
			for(int i = 0; funcionario.getApontamentos().size() > i;){
				row = sheet.createRow(linha);
				
				DateFormat formatDt = new SimpleDateFormat("dd/MM/yyyy");
				String dtApontamento = formatDt.format(funcionario.getApontamentos().get(i).getData());
				row.createCell(0).setCellValue(dtApontamento);
				row.createCell(1).setCellValue("Data");
				for(int j = 0; funcionario.getApontamentos().size() > j; j++){
					if(funcionario.getApontamentos().get(j).getData().compareTo(funcionario.getApontamentos().get(i).getData()) == 0){
						row.createCell(2).setCellValue("SA");
						DateFormat formatter = new SimpleDateFormat("HH:mm");
						String hora = formatter.format(funcionario.getApontamentos().get(j).getHorario());
						row.createCell(3+mesmoDia).setCellValue(hora);
						row.createCell(4+mesmoDia).setCellValue((funcionario.getApontamentos().get(j).getTipoImportacao()==true)?"E":"M");
						mesmoDia += 2;
						i++;
					}else{
						mesmoDia = 0;
					}
				}
				row.createCell(19).setCellValue("Atestado");
				row.createCell(20).setCellValue("Horas");
				row.createCell(21).setCellValue("Positivas");
				row.createCell(22).setCellValue("Negativas");
				row.createCell(23).setCellValue("Ad Not");
				row.createCell(24).setCellValue("Horas SA");
				row.createCell(25).setCellValue(funcionario.getApontamentos().get(0).getObservacao());
				linha++;
			}
			
			row = sheet.createRow(linha);
			row.createCell(20).setCellValue("Soma hrs positivas");
			row.createCell(21).setCellValue("Soma hrs negativas");
			row.createCell(22).setCellValue("Soma hrs SA");
			row.createCell(23).setCellValue("Soma hrs Ad Not");
			linha++;
			
			row = sheet.createRow(linha);
			row.createCell(18).setCellValue("Saldo Final: ");
			row.createCell(20).setCellValue("Total Decimal");
			row.createCell(21).setCellValue("Total Horas");
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
			row.createCell(0).setCellValue("Legenda : Origem Marcação: M- Manual / E- Eletrônico / SA = Sobre Aviso (Plantão); SA = Sobre Aviso sem Acionamento / ST = Sobre Aviso Trabalhado");
			linha++;
			
			row = sheet.createRow(linha);
			DateFormat formatDt = new SimpleDateFormat("dd/MM/yyyy");
			Date hoje = new Date();        
			String dtHoje = formatDt.format(hoje);
			row.createCell(0).setCellValue(dtHoje);
			
			//Escreve no arquivo
			workbook.write(out);
			//Fecha e salva o arquivo
			out.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}