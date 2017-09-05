package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ControleDiarioEntity;

@Repository
public class ControleDiarioDAO {

	public ControleDiarioEntity findByData(Date data) {
		String sql = "SELECT * FROM PAUSEControleDiario WHERE data = ?";

		Connection conn;
		ControleDiarioEntity entity = null;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, data);

			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleDiarioEntity();
				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(7));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void save(ControleDiarioEntity entity) {
		String sql = "INSERT INTO PAUSEControleDiario (data) VALUES (?)";

		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, entity.getData());
			
			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
