package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ControleMensalEntity;

@Repository
public class ControleMensalDAO {
	public ControleMensalEntity findByMesAnoIdFuncionario(int mes, int ano, int idFuncionario) {
		String sql = "SELECT * FROM PAUSEControleMensal WHERE mes = ? AND ano = ? AND idFuncionario = ?";

		Connection conn;
		ControleMensalEntity entity = null;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mes);
			ps.setInt(2, ano);
			ps.setInt(3, idFuncionario);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleMensalEntity();
				entity.setId(rs.getInt(1));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void save(ControleMensalEntity entity) {
		String sql = "INSERT INTO PAUSEControleMensal (mes, ano, idFuncionario) VALUES (?,?,?)";

		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, entity.getMes());
			ps.setInt(2, entity.getAno());
			ps.setInt(3, entity.getIdFuncionario());
			
			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ControleMensalEntity findByDataAndIdFunc(java.util.Date dtHoje, Integer id) {
		ControleMensalEntity entity = null;
		int mes = dtHoje.getMonth() + 1;
		int ano = dtHoje.getYear() + 1900;
		
		String sql = "SELECT * FROM PAUSEControleMensal as cm"+
						  "INNER JOIN PAUSEControleDiario as cd ON cd.idControleMensal = cm.idControleMensal "+
					   "where cm.mes = ? AND cm.ano = ? AND cm.idFuncionario = ? AND cd.data = ?";

		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, mes);
			ps.setInt(2, ano);
			ps.setInt(3, id);
			ps.setDate(4, new java.sql.Date(dtHoje.getTime()));
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleMensalEntity();
				entity.setId(rs.getInt("idControleMensal"));
				entity.setMes(rs.getInt("mes"));
				entity.setAno(rs.getInt("ano"));
				entity.setIdFuncionario(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}
}
