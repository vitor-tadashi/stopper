package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.HorasEntity;

@Repository
public class HorasDAO {
	
	ConnectionFactory connection;
	
	public List<HorasEntity> findAll() throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();		
		String sql = "SELECT * FROM PAUSEHoras";
		//Prepara a instrução SQL
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		while (rs.next()) {
		 // String nome = rs.getString("nmEMpresa");

		//System.out.println(nome);
		}

		//Executa a instrução SQL
		ps.execute();
		ps.close();
		return null;
	}
	
	public void save(HorasEntity horas) throws SQLException{
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEHoras VALUES (?,?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, horas.getPis());
		java.sql.Date sqlDate = new java.sql.Date(horas.getDtImportacao().getTime());
		ps.setDate(2, sqlDate);
		ps.setTime(3, horas.getHora());
		ps.setBoolean(4, horas.getTpApontamento());
		
		ps.execute();
		ps.close();
	}
	
	public void saveAll(List<HorasEntity> horas) throws SQLException{
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEHoras VALUES (?,?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		for (HorasEntity horasEntity : horas) {
			if (horasEntity.getHora() != null) {
				ps.setString(1, horasEntity.getPis());
				java.sql.Date sqlDate = new java.sql.Date(horasEntity.getDtImportacao().getTime());
				ps.setDate(2, sqlDate);
				ps.setTime(3, horasEntity.getHora());
				ps.setBoolean(4, horasEntity.getTpApontamento());
				ps.execute();
			}
		}
		ps.close();
	}
}