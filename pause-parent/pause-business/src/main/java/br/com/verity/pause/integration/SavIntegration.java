
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

import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.FuncionarioIntegrationBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.converter.FuncionarioIntegrationConverter;

@Component
public class SavIntegration {
	
	@Autowired
	private FuncionarioIntegrationConverter funcionarioConverter;

	public List<FuncionarioBean> getFuncionarios(int idEmpresa){
		List<FuncionarioIntegrationBean> funcionarios = new ArrayList<FuncionarioIntegrationBean>();
		ObjectMapper mapper = new ObjectMapper();
		// Properties props = this.getProp();
		String endereco = "http://localhost:9090/sav/listFuncionariosDaEmpresa/"+idEmpresa;
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
		String endereco = "http://localhost:9090/sav/getUsuarioSistema/";
		try {
			URL url = new URL(endereco + user + "/SAV");
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

	public FuncionarioBean getFuncionario(Integer idFuncionario) {
		FuncionarioIntegrationBean funcionario = new FuncionarioIntegrationBean();
		ObjectMapper mapper = new ObjectMapper();
		// Properties props = this.getProp();
		String endereco = "http://192.168.3.17:9090/sav/getFuncionario/"+idFuncionario;
		try {
			URL url = new URL(endereco);
			funcionario = mapper.readValue(url,  new TypeReference<FuncionarioIntegrationBean>(){});
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return funcionarioConverter.convertEntityToBean(funcionario);
	}
	
	public EmpresaBean getEmpresa(Integer idEmpresa) {
		EmpresaBean empresa = new EmpresaBean();
		ObjectMapper mapper = new ObjectMapper();
		// Properties props = this.getProp();
		String endereco = "http://192.168.3.17:9090/sav/getEmpresa/"+idEmpresa;
		try {
			URL url = new URL(endereco);
			empresa = mapper.readValue(url,  new TypeReference<EmpresaBean>(){});
		} catch (IOException e ) {
			e.printStackTrace();
		}
		
		return empresa;
	}
}