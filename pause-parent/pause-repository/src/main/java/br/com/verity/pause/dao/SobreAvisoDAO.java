package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

			ps.setTime(1, entity.getHoraInicio());
			ps.setTime(2, entity.getHoraFim());
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

	/**
	 * Encontra o sobre aviso da data informada
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do apontamento do funcionário
	 * @return
	 * @throws SQLException
	 */
	public SobreAvisoEntity findNoticePeriod(int idFuncionario, Date data) throws SQLException {
		SobreAvisoEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		conn = ConnectionFactory.createConnection();

		sql = new StringBuilder();

		sql.append("SELECT sa.idSobreAviso,");
		sql.append("	   sa.horaInicio,");
		sql.append("	   sa.horaFim,");
		sql.append("	   sa.dataInclusao,");
		sql.append("	   sa.idUsuarioInclusao,");
		sql.append("	   sa.data");
		sql.append("  FROM PAUSESobreAviso sa");
		sql.append("  	   INNER JOIN PAUSEControleDiario cd on sa.IdControleDiario = cd.IdControleDiario");
		sql.append("       INNER JOIN PAUSEControleMensal cm on cd.idControleMensal = cm.idControleMensal");
		sql.append(" WHERE sa.data = (?) and cm.idFuncionario = (?)");

		ps = conn.prepareStatement(sql.toString());
		ps.setDate(1, data);
		ps.setInt(2, idFuncionario);

		rs = ps.executeQuery();

		if (rs.next()) {
			response = new SobreAvisoEntity();
			response.setId(rs.getInt("idSobreAviso"));
			response.setHoraInicio(rs.getTime("horaInicio"));
			response.setHoraFim(rs.getTime("horaFim"));
			response.setDataInclusao(rs.getDate("dataInclusao"));
			response.setIdUsuarioInclusao(rs.getInt("idUsuarioInclusao"));
			response.setData(rs.getDate("data"));
		}

		ps.close();

		return response;
	}
}
