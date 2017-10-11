package br.com.verity.pause.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class ConnectionFactory {

	@Autowired
	private Environment ambiente;

	private Connection conexao = null;

	public Connection createConnection() throws SQLException {
		
		try {
			
			if (conexao == null) {
				
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				
				String url = ambiente.getProperty("datasource.url");
				String user = ambiente.getProperty("datasource.username");
				String password = ambiente.getProperty("datasource.password");
				
				conexao = DriverManager.getConnection(url, user, password);
				
			}


		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return conexao;
	}

}
