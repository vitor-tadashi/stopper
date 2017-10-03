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
import br.com.verity.pause.entity.AtestadoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.entity.TipoAtestadoEntity;

@Repository
public class AtestadoDAO {
	
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
	public AtestadoEntity findSickNote(int idFuncionario, Date data) throws SQLException {
		AtestadoEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		conn = connectionFactory.createConnection();

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

	public AtestadoEntity save(AtestadoEntity entity) {
		Connection conn = null;
		String sql = "INSERT INTO PAUSEAtestado VALUES (?,?,?,?,?)";

		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setDouble(1, entity.getQuantidadeHora());
			ps.setInt(2, entity.getControleDiario().getId());
			ps.setDate(3, entity.getDataInclusao());
			ps.setInt(4, entity.getIdUsuarioInclusao());
			ps.setInt(5, entity.getTipoAtestado().getIdTipoAtestado());

			ps.execute();
			ps.close();
			conn.close();
			
			return findByControleDiarioMaxIdAtestado(entity.getControleDiario().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	private AtestadoEntity findByControleDiarioMaxIdAtestado(Integer id) {
		AtestadoEntity entity = new AtestadoEntity();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT MAX(idAtestado) FROM PAUSEAtestado WHERE idControleDiario = ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 
	        rs = ps.executeQuery();

			if (rs.next()) {
                entity.setIdAtestado(rs.getInt(1));
            }

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entity;
	}

	public AtestadoEntity findById(Integer id) {
		AtestadoEntity entity = null;
		String sql = " SELECT at.idAtestado, at.quantidadeHora ,cd.data, at.idControleDiario, cd.idControleMensal, cm.idFuncionario FROM PAUSEAtestado at" + 
				"  INNER JOIN PAUSEControleDiario cd ON cd.idControleDiario = at.idControleDiario" + 
				"  INNER JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal" + 
				"  WHERE at.idAtestado = ?";
		
		try {
			Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				entity = new AtestadoEntity();
				ControleDiarioEntity diarioEntity = new ControleDiarioEntity();
				ControleMensalEntity mensalEntity = new ControleMensalEntity();
				
				entity.setIdAtestado(rs.getInt(1));
				entity.setQuantidadeHora(rs.getDouble(2));
				diarioEntity.setData(rs.getDate(3));
				diarioEntity.setId(rs.getInt(4));
				mensalEntity.setId(rs.getInt(5));
				mensalEntity.setIdFuncionario(rs.getInt(6));
				
				diarioEntity.setControleMensal(mensalEntity);
				entity.setControleDiario(diarioEntity);
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

			String sql = "DELETE FROM PAUSEAtestado WHERE idAtestado = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<TipoAtestadoEntity> findAll() {
		Connection conn;
		List<TipoAtestadoEntity> entities = new ArrayList<>();
		
		String sql = "SELECT * FROM PAUSETipoAtestado";
		
		try {
			conn = connectionFactory.createConnection();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				TipoAtestadoEntity entity = new TipoAtestadoEntity();
				
				entity.setIdTipoAtestado(rs.getInt(1));
				entity.setDescricao(rs.getString(2));
				
				entities.add(entity);
			}
			
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entities;
	}

	public List<AtestadoEntity> findByPeriodoAndIdFuncionario(Integer id, Date de, Date ate) {
		String sql = "SELECT at.idAtestado, at.quantidadeHora, at.idControleDiario, cd.data, at.idTipoAtestado, tp.descricao FROM PAUSEAtestado at" + 
				"  RIGHT JOIN PAUSEControleDiario cd ON cd.idControleDiario = at.idControleDiario" + 
				"  RIGHT JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal" +
				"  INNER JOIN PAUSETipoAtestado tp ON tp.idTipoAtestado = at.idTipoAtestado" +
				"  WHERE cm.idFuncionario = ? AND cd.data BETWEEN ? AND ?";
		
		List<AtestadoEntity> entities = new ArrayList<>();

		try {
			Connection conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setDate(2, de);
			ps.setDate(3, ate);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				AtestadoEntity entity = new AtestadoEntity();
				ControleDiarioEntity diarioEntity = new ControleDiarioEntity();
				TipoAtestadoEntity atestadoEntity = new TipoAtestadoEntity();

				entity.setIdAtestado(rs.getInt(1));
				entity.setQuantidadeHora(rs.getDouble(2));
				diarioEntity.setId(rs.getInt(3));
				diarioEntity.setData(rs.getDate(4));
				atestadoEntity.setIdTipoAtestado(rs.getInt(5));
				atestadoEntity.setDescricao(rs.getString(6));
				
				entity.setControleDiario(diarioEntity);
				entity.setTipoAtestado(atestadoEntity);

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
