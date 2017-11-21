package br.com.verity.pause.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.business.FuncionarioBusiness;
import br.com.verity.pause.business.RelatorioBusiness;

@Controller
@RequestMapping(value = "/relatorio")
public class RelatorioController {

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	@Autowired
	private RelatorioBusiness relatorioBusiness;

	@PreAuthorize("hasRole('ROLE_GERAR_RELATORIOS')")
	@RequestMapping(method = RequestMethod.GET)
	public String loginPage(Model model) {
		List<FuncionarioBean> funcionarios = funcionarioBusiness.obterTodos();
		model.addAttribute("funcionarios", funcionarios);
		return "relatorio/relatorio";
	}

	@PreAuthorize("hasRole('ROLE_GERAR_RELATORIOS')")
	@RequestMapping(value = "gerar-relatorio", method = RequestMethod.GET)
	public ResponseEntity<?> gerarRelatorio(Integer idFuncionario, String de, String ate,
			HttpServletResponse response) throws SQLException {
		FuncionarioBean funcionario = funcionarioBusiness.obterPorId(idFuncionario);
		byte[] outArray;
		outArray = relatorioBusiness.gerarRelatorio(idFuncionario, de, ate);
		if (outArray != null) {
			response.setContentType("application/ms-excel");
			response.setContentLength(outArray.length);
			response.setHeader("Expires:", "0"); // eliminates browser caching
			response.setHeader("Content-Disposition", "attachment; filename=" + funcionario.getNome() + ".xlsx");
			OutputStream outStream;
			try {
				outStream = response.getOutputStream();
				outStream.write(outArray);
				outStream.flush();

				return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@PreAuthorize("hasRole('ROLE_GERAR_RELATORIOS')")
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(String caminho, HttpServletResponse response) {
		String[] nome = caminho.split("\\\\");
		int tamanho = nome.length - 1;
		try {
			File file = new File(caminho);
			response.addHeader("Content-Disposition", "attachment; filename=" + nome[tamanho]);
			InputStream is = new FileInputStream(file);
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			is.close();
			file.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}