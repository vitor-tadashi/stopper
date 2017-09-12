package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.AtestadoEntity;

@Repository
public class AtestadoDAO {

	/**
	 * Encontra os afastamentos do funcionário da respectiva data
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do apontamento do funcionário
	 * @return
	 * @throws SQLException
	 */
	public AtestadoEntity findSickNote(int idFuncionario, Date data) throws SQLException {
		AtestadoEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		conn = ConnectionFactory.createConnection();

		sql = new StringBuilder();

		sql.append("SELECT a.idAtestado, a.quantidadeHora");
		sql.append("  FROM PAUSEControleMensal cm");
		sql.append("       INNER JOIN PAUSEControleDiario cd on cm.idControleMensal = cd.idControleMensal");
		sql.append("	   INNER JOIN PAUSEAtestado a on cd.idControleDiario = a.idControleDiario");
		sql.append(" WHERE cd.data = (?) and cm.idFuncionario = (?)");

		ps = conn.prepareStatement(sql.toString());
		ps.setDate(1, data);
		ps.setInt(2, idFuncionario);

		rs = ps.executeQuery();

		if (rs.next()) {
			response = new AtestadoEntity();
			response.setIdAtestado(rs.getInt("idAtestado"));
			response.setQuantidadeHora(rs.getDouble("quantidadeHora"));
		}

		ps.close();

		return response;
	}
}
