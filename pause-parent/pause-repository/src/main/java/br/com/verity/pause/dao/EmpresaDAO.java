package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.bean.EmpresaBean;
import br.com.verity.pause.connection.ConnectionFactory;

@Repository
public class EmpresaDAO {

	public List<EmpresaBean> findAll() throws SQLException {
		ConnectionFactory connection = new ConnectionFactory();
		Connection conn = connection.createConnection();		
		String sql = "SELECT * FROM REGPONTOEmpresa";
		//Prepara a instrução SQL
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
		  String nome = rs.getString("nmEMpresa");

		  System.out.println(nome);
		}

		//Executa a instrução SQL
		ps.execute();
		ps.close();
		return null;
	}

}
