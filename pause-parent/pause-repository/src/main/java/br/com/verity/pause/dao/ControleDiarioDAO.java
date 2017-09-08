package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;

@Repository
public class ControleDiarioDAO {

	public ControleDiarioEntity findByDataIdFuncionario(Date data, int idFuncionario) {
		String sql = "SELECT * FROM PAUSEControleDiario WHERE data = ? AND idFuncionario = ?";

		Connection conn;
		ControleDiarioEntity entity = null;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, data);
			ps.setInt(2, idFuncionario);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleDiarioEntity();
				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(7));
				
				ControleMensalEntity controleMensal = new ControleMensalEntity();
				controleMensal.setId(rs.getInt(8));
				
				entity.setControleMensal(controleMensal);
				entity.setIdFuncionario(rs.getInt(9));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void save(ControleDiarioEntity entity) {
		String sql = "INSERT INTO PAUSEControleDiario (data, idControleMensal ,idFuncionario) VALUES (?, ?, ?)";

		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, entity.getData());
			ps.setInt(2, entity.getControleMensal().getId());
			ps.setInt(3, entity.getIdFuncionario());
			
			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
