package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.AfastamentoEntity;
import br.com.verity.pause.entity.enumerator.TipoAfastamento;

@Repository
public class AfastamentoDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
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
	public AfastamentoEntity findAbsence(int idFuncionario, Date data) {
		AfastamentoEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		
		try {
		conn = connectionFactory.createConnection();

		sql = new StringBuilder();

		sql.append("SELECT idAfastamento, dataInicio,");
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
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return response;
	}

	public AfastamentoEntity save(AfastamentoEntity entity) {
		String sql = "INSERT INTO PAUSEAfastamento VALUES (?,?,?,?,?,?)";
		Connection conn = null;
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDate(1, entity.getDataInicio());
			ps.setDate(2, entity.getDataFim());
			ps.setDate(3, entity.getDataInclusao());
			ps.setInt(4, entity.getIdFuncionario());
			ps.setInt(5, entity.getTipoAfastamento().getIdTipoAfastamento());
			ps.setInt(6, entity.getIdUsuarioInclusao());

			ps.execute();
			ps.close();
			conn.close();
			
			return findByIdFuncionarioMaxIdAfastamento(entity.getIdFuncionario());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private AfastamentoEntity findByIdFuncionarioMaxIdAfastamento(int id) {
		AfastamentoEntity entity = new AfastamentoEntity();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT MAX(idAfastamento) FROM PAUSEAfastamento WHERE idFuncionario = ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 
	        rs = ps.executeQuery();

			if (rs.next()) {
                entity.setIdAfastamento(rs.getInt(1));
            }

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	public AfastamentoEntity findById(Integer id) {
		AfastamentoEntity entity = null;
		String sql = "SELECT * FROM PAUSEAfastamento WHERE idAfastamento = ?";
		try {
			Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				entity = new AfastamentoEntity();
				
				entity.setIdAfastamento(rs.getInt(1));
				entity.setDataInicio(rs.getDate(2));
				entity.setDataFim(rs.getDate(3));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	public void deleteById(Integer id) {
		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			String sql = "DELETE FROM PAUSEAfastamento WHERE idAfastamento = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TipoAfastamento> findAll() {
		Connection conn;
		List<TipoAfastamento> entities = new ArrayList<>();
		
		String sql = "SELECT * FROM PAUSETipoAfastamento";
		
		try {
			conn = connectionFactory.createConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				entities.add(TipoAfastamento.getById(rs.getInt(1)));
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entities;
	}

	public List<AfastamentoEntity> findByPeriodoAndIdFuncionario(Integer id, Date de, Date ate) {
		String sql = "SELECT * FROM PAUSEAfastamento WHERE idFuncionario = ? AND dataInicio BETWEEN ? AND ?";
		
		List<AfastamentoEntity> entities = new ArrayList<>();

		try {
			Connection conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setDate(2, de);
			ps.setDate(3, ate);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				AfastamentoEntity entity = new AfastamentoEntity();

				entity.setIdAfastamento(rs.getInt(1));
				entity.setDataInicio(rs.getDate(2));
				entity.setDataFim(rs.getDate(3));
				entity.setDataInclusao(rs.getDate(4));
				entity.setIdFuncionario(rs.getInt(5));
				entity.setTipoAfastamento(TipoAfastamento.getById(rs.getInt(6)));
				entity.setIdUsuarioInclusao(rs.getInt(7));
					

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
