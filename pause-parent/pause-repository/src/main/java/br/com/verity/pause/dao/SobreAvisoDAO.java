package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.SobreAvisoEntity;

@Repository
public class SobreAvisoDAO {

	private Connection conn;
	
	public void save(SobreAvisoEntity entity) {
		String sql = "INSERT INTO PAUSESobreAviso VALUES (?,?,?,?,?,?)";
		
		try {
			conn = ConnectionFactory.createConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setTime(1, entity.getEntrada());
			ps.setTime(2, entity.getSaida());
			ps.setDate(3, entity.getDataInclusao());
			ps.setInt(4, entity.getControleDiario().getId());
			ps.setInt(5, entity.getIdUsuarioInclusao());
			ps.setDate(6, entity.getData());
			
			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
