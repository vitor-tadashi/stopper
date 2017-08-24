package br.com.verity.pause.integration;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.FuncionarioIntegrationBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.FuncionarioIntegrationConverter;

@Component
public class SavIntegration {
	
	@Autowired
	private FuncionarioIntegrationConverter funcionarioConverter;

	public List<FuncionarioBean> getFuncionarios(String empresa){
		List<FuncionarioIntegrationBean> funcionarios = new ArrayList<FuncionarioIntegrationBean>();
		ObjectMapper mapper = new ObjectMapper();
		// Properties props = this.getProp();
		String endereco = "http://localhost:9090/sav/listFuncionariosDaEmpresa/"+empresa;
		try {
			URL url = new URL(endereco);
			funcionarios = mapper.readValue(url,  new TypeReference<List<FuncionarioIntegrationBean>>(){});
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return funcionarioConverter.convertEntityToBean(funcionarios);
	}
	
	public UsuarioBean getUsuario(String user){
		UsuarioBean usuario = new UsuarioBean();
		ObjectMapper mapper = new ObjectMapper();
		// Properties props = this.getProp();
		String endereco = "http://localhost:9090/sav/getUser/";
		try {
			URL url = new URL(endereco + user + "/");
			usuario = mapper.readValue(url, UsuarioBean.class);
		} catch (IOException e ) {
			e.printStackTrace();
		}

		return usuario;
	}

	public static String genericGet(URL url) {
		InputStream inputstream = null;
		String response = null;
		try {
			// Criando objeto para efetuar conexão com o serviço
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// Atribuindo método GET para conexão
			conn.setRequestMethod("GET");

			// Atribuindo JSON como parâmetro de retorno
			conn.setRequestProperty("Accept", "application/json");

			// Verificando se a conexão com o serviço foi bem sucedida
			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			// pegando resultado do serviço
			inputstream = conn.getInputStream();

			// Transformando em Stting
			response = new String(FileCopyUtils.copyToByteArray(inputstream), StandardCharsets.UTF_8);

			conn.disconnect();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

	public Properties getProp() {
		try {
			Properties props = new Properties();
			InputStream file = getClass().getResourceAsStream(".regponto-business/src/main/resources/dados.properties");
			props.load(file);
			return props;
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return null;
	}
}