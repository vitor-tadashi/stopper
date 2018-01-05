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

	public List<TipoDespesaEntity> findAll() {
		Connection conn;
		List<TipoDespesaEntity> entities = new ArrayList<>();

		String sql = "SELECT * FROM tipo_despesa";

		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				TipoDespesaEntity entity = new TipoDespesaEntity();

				entity.setId(rs.getLong(1));
				entity.setNome(rs.getString(2));
				entity.setDescricao(rs.getString(3));

				entities.add(entity);
			}

			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entities;
	}

}
