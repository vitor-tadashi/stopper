package br.com.verity.pause.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	
	public static Connection createConnection() throws SQLException{
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/* Configura os parâmetros da conexão */
		String url = "jdbc:sqlserver://192.168.2.11:1433;databaseName=Pause";
		String user = "Pausedb";
		String password = "Verity@123";
		
		Connection conexao = null;
		conexao = DriverManager.getConnection(url, user, password);
		
		return conexao;
	}
	
}
