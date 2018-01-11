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
import br.com.verity.pause.entity.TipoDespesaEntity;

@Repository
public class TipoDespesaDAO {

	@Autowired
	private ConnectionFactory connectionFactory;
	
	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public List<TipoDespesaEntity> findAll() {
		List<TipoDespesaEntity> entities = new ArrayList<>();

		String sql = "SELECT * FROM tipo_despesa";

		try {
			conn = connectionFactory.createConnection();

			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TipoDespesaEntity entity = new TipoDespesaEntity();

				entity.setId(rs.getLong(1));
				entity.setNome(rs.getString(2));
				entity.setDescricao(rs.getString(3));

				entities.add(entity);
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fecharConexoes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return entities;
	}

	public TipoDespesaEntity findById(Long id) {
		String sql = "SELECT id, nome, descricao FROM tipo_despesa where id = ?";

		TipoDespesaEntity tipoDespesa = new TipoDespesaEntity();
		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);
			ps.setLong(1, id);
			rs = ps.executeQuery();

			if (rs.next()) {
				tipoDespesa.setId(rs.getLong("id"));
				tipoDespesa.setNome(rs.getString("nome"));
				tipoDespesa.setDescricao(rs.getString("descricao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fecharConexoes();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return tipoDespesa;
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
