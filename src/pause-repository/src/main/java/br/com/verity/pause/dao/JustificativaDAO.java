package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.TipoJustificativaEntity;

@Repository
public class JustificativaDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	private Connection conn;

	public List<TipoJustificativaEntity> findAll() {
		List<TipoJustificativaEntity> entities = new ArrayList<>();
		
		String sql = "SELECT * FROM PAUSETipoJustificativa";
		
		try {
			conn = connectionFactory.createConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TipoJustificativaEntity entity = new TipoJustificativaEntity();
				
				entity.setId(rs.getInt(1));
				entity.setDescricao(rs.getString(2));
				
				entities.add(entity);
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entities;
	}
}
