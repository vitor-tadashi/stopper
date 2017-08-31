package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentoEntity;

@Repository
public class ApontamentosDAO {

	private ConnectionFactory connection;

	public List<ApontamentoEntity> findAll() throws SQLException {
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

	public Boolean findByData(ApontamentoEntity horas, int idEmpresa) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "SELECT * FROM PAUSEApontamentos WHERE data = ? AND idEmpresa = ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		java.sql.Date sqlDate = new java.sql.Date(horas.getData().getTime());
		ps.setDate(1, sqlDate);
		ps.setInt(2, idEmpresa);

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

	public void save(ApontamentoEntity horas) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEApontamentos VALUES (?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setString(1, horas.getPis());
		java.sql.Date sqlDate = new java.sql.Date(horas.getData().getTime());
		ps.setDate(2, sqlDate);
		ps.setTime(3, horas.getHorario());
		ps.setBoolean(4, horas.getTipoImportacao());

		ps.execute();
		ps.close();
	}

	public void saveAll(List<ApontamentoEntity> horas) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEApontamentos VALUES (?,?,?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		for (ApontamentoEntity horasEntity : horas) {
			if (horasEntity.getHorario() != null) {
				ps.setString(1, horasEntity.getPis());
				java.sql.Date sqlDate = new java.sql.Date(horasEntity.getData().getTime());
				ps.setDate(2, sqlDate);
				ps.setTime(3, horasEntity.getHorario());
				ps.setBoolean(4, horasEntity.getTipoImportacao());
				ps.setString(5, null);
				ps.setString(6, null);
				ps.setInt(7, horasEntity.getIdEmpresa());
				ps.execute();
			}
		}
		ps.close();
	}

	public void excludeAllDate(Date data) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "DELETE PAUSEApontamentos WHERE data = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		
		java.sql.Date sqlDate = new java.sql.Date(data.getTime());
		ps.setDate(1, sqlDate);
		
		ps.execute();
		ps.close();
	}
}