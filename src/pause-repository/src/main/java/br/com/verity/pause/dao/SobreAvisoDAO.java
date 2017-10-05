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
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.entity.SobreAvisoEntity;

@Repository
public class SobreAvisoDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	private Connection conn;

	public SobreAvisoEntity save(SobreAvisoEntity entity) {
		String sql = "INSERT INTO PAUSESobreAviso VALUES (?,?,?,?,?,?)";

		try {
			conn = connectionFactory.createConnection();

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
		
		return findByControleDiarioMaxIdSobreAviso(entity.getControleDiario().getId());
	}

	private SobreAvisoEntity findByControleDiarioMaxIdSobreAviso(Integer id) {
		SobreAvisoEntity entity = new SobreAvisoEntity();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT MAX(idSobreAviso) FROM PAUSESobreAviso WHERE idControleDiario = ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 
	        rs = ps.executeQuery();

			if (rs.next()) {
                entity.setId(rs.getInt(1));
            }

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entity;
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
		conn = connectionFactory.createConnection();

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

	public List<SobreAvisoEntity> findByPeriodoAndIdFuncionario(Integer id, Date de, Date ate) {
		String sql = "SELECT sa.idSobreAviso, sa.data, sa.horaInicio, sa.horaFim, sa.idControleDiario FROM PAUSESobreAviso sa" + 
				"  RIGHT JOIN PAUSEControleDiario cd ON cd.idControleDiario = sa.idControleDiario" + 
				"  RIGHT JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal" + 
				"  WHERE cm.idFuncionario = ? AND sa.data BETWEEN ? AND ?";
		
		List<SobreAvisoEntity> entities = new ArrayList<>();

		try {
			Connection conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setDate(2, de);
			ps.setDate(3, ate);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				SobreAvisoEntity entity = new SobreAvisoEntity();
				ControleDiarioEntity controleDiario = new ControleDiarioEntity();

				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(2));
				entity.setHoraInicio(rs.getTime(3));
				entity.setHoraFim(rs.getTime(4));

				controleDiario.setId(rs.getInt(5));
				entity.setControleDiario(controleDiario);

				entities.add(entity);
			}

			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}

	public SobreAvisoEntity findById(Integer id) {
		SobreAvisoEntity entity = null;
		String sql = " SELECT sa.idSobreAviso, sa.data, sa.idControleDiario, cd.idControleMensal, cm.idFuncionario FROM PAUSESobreAviso sa" + 
				"  INNER JOIN PAUSEControleDiario cd ON cd.idControleDiario = sa.idControleDiario" + 
				"  INNER JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal" + 
				"  WHERE sa.idSobreAviso = ?";
		
		try {
			Connection conn = connectionFactory.createConnection();
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				entity = new SobreAvisoEntity();
				ControleDiarioEntity diarioEntity = new ControleDiarioEntity();
				ControleMensalEntity mensalEntity = new ControleMensalEntity();
				
				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(2));
				diarioEntity.setId(rs.getInt(3));
				mensalEntity.setId(rs.getInt(4));
				mensalEntity.setIdFuncionario(rs.getInt(5));
				
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

			String sql = "DELETE FROM PAUSESobreAviso WHERE idSobreAviso = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
