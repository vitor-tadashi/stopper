package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.StatusEntity;
import br.com.verity.pause.entity.enumerator.StatusEnum;

@Repository
public class StatusDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	Connection conn;
	PreparedStatement ps;
	ResultSet rs;
	
	public StatusEntity findByName(StatusEnum statusEnum) {
		StatusEntity status = new StatusEntity();
		String sql = null;

		sql = "SELECT id, nome, descricao FROM status where upper(nome) like ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setString(1, statusEnum.nome.toUpperCase()); 
			rs = ps.executeQuery();

			if (rs.next()) {
				status.setId(rs.getInt("id"));
				status.setNome(rs.getString("nome"));
				status.setDescricao(rs.getString("descricao"));
			}

			ps.execute();
			fecharConexoes();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fecharConexoes();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return status;
	}
	
	private void fecharConexoes() throws SQLException {
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (ps != null && !ps.isClosed()) {
			ps.close();
		}

		if (conn != null && conn.isClosed()) {
			conn.close();
		}
	}

}
