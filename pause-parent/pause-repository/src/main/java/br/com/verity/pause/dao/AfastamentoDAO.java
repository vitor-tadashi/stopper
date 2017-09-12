package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.AfastamentoEntity;
import br.com.verity.pause.entity.enumerator.TipoAfastamento;

@Repository
public class AfastamentoDAO {
	
	private ConnectionFactory connection;
	
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
	public AfastamentoEntity findAbsence(int idFuncionario, Date data) throws SQLException {
		AfastamentoEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		conn = ConnectionFactory.createConnection();

		sql = new StringBuilder();

		sql.append("SELECT idAfastamento, dataInicio");
		sql.append("	   dataFim, dataInclusao, ");
		sql.append("	   idFuncionario, idTipoAfastamento, idUsuarioInclusao");
		sql.append("  FROM PAUSEAfastamento");
		sql.append(" WHERE (?) >= dataInicio and (?) <= dataFim and idFuncionario = (?)");
		
		ps = conn.prepareStatement(sql.toString());
		ps.setDate(1, data);
		ps.setDate(2, data);
		ps.setInt(3, idFuncionario);
		
		rs = ps.executeQuery();

		if (rs.next()) {
			response = new AfastamentoEntity();
			response.setIdAfastamento(rs.getInt("idAfastamento"));
			response.setDataInicio(rs.getDate("dataInicio"));
			response.setDataFim(rs.getDate("dataFim"));
			response.setDataInclusao(rs.getDate("dataInclusao"));
			response.setIdFuncionario(rs.getInt("idFuncionario"));
			response.setIdUsuarioInclusao(rs.getInt("idUsuarioInclusao"));
			response.setTipoAfastamento(TipoAfastamento.getById((rs.getInt("idTipoAfastamento"))));
		}

		ps.close();

		return response;
	}
}
