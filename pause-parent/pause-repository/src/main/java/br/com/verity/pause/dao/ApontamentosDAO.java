package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentosEntity;

@Repository
public class ApontamentosDAO {

	ConnectionFactory connection;

	public List<ApontamentosEntity> findAll() throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "SELECT * FROM PAUSEApontamentos";
		// Prepara a instrução SQL
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			// String nome = rs.getString("nmEMpresa");

			// System.out.println(nome);
		}

		// Executa a instrução SQL
		ps.execute();
		ps.close();
		return null;
	}

	public Boolean findByData(ApontamentosEntity horas, String empresa) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "SELECT * FROM PAUSEApontamentos WHERE data = ? AND empresa LIKE (?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		java.sql.Date sqlDate = new java.sql.Date(horas.getData().getTime());
		ps.setDate(1, sqlDate);
		ps.setString(2, empresa);

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			ps.execute();
			ps.close();
			return true;
		}
		ps.execute();
		ps.close();
		return false;
	}

	public void save(ApontamentosEntity horas) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEApontamentos VALUES (?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setString(1, horas.getPis());
		java.sql.Date sqlDate = new java.sql.Date(horas.getData().getTime());
		ps.setDate(2, sqlDate);
		ps.setTime(3, horas.getHora());
		ps.setBoolean(4, horas.getTpApontamento());

		ps.execute();
		ps.close();
	}

	public void saveAll(List<ApontamentosEntity> horas) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEApontamentos VALUES (?,?,?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		for (ApontamentosEntity horasEntity : horas) {
			if (horasEntity.getHora() != null) {
				ps.setString(1, horasEntity.getPis());
				java.sql.Date sqlDate = new java.sql.Date(horasEntity.getData().getTime());
				ps.setDate(2, sqlDate);
				ps.setTime(3, horasEntity.getHora());
				ps.setString(4, horasEntity.getEmpresa());
				ps.setBoolean(5, horasEntity.getTpApontamento());
				ps.setString(6, null);
				ps.setString(7, null);
				ps.execute();
			}
		}
		ps.close();
	}
}