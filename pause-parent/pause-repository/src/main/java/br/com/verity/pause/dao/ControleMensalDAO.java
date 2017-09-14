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
	/**
	 * Atualiza o controle mensal do funcionário
	 * @param idFuncionario Id do funcionário
	 * @param mes Mês que deseja atualizar os dados
	 */
	public void saveCalculation(int idFuncionario, int mes) {
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			StringBuilder sql = null;
			conn = ConnectionFactory.createConnection();

			sql = new StringBuilder();
			
			sql.append("   UPDATE CM ");
			sql.append("	SET CM.HORATOTAL = DIARIO.HORATOTAL, ");
			sql.append("	    CM.BANCOHORA = DIARIO.BANCOHORA, ");
			sql.append("		CM.ADCNOTURNO = DIARIO.ADCNOTURNO, ");
			sql.append("		CM.SOBREAVISOTRABALHADO = DIARIO.SOBREAVISOTRABALHADO, ");
			sql.append("		CM.SOBREAVISO = DIARIO.SOBREAVISO ");
			sql.append("   FROM PAUSECONTROLEMENSAL CM ");
			sql.append("		INNER JOIN ");
			sql.append("			( ");
			sql.append("				 SELECT CD.IDCONTROLEMENSAL, SUM(CD.HORATOTAL) as HORATOTAL, SUM(CD.BANCOHORA) as BANCOHORA, ");
			sql.append("				        SUM(CD.ADCNOTURNO) as ADCNOTURNO, SUM(CD.SOBREAVISOTRABALHADO) as SOBREAVISOTRABALHADO, "); 
			sql.append("						SUM(CD.SOBREAVISO) as SOBREAVISO ");
			sql.append("				   FROM PAUSECONTROLEMENSAL CM ");
			sql.append("						INNER JOIN PAUSECONTROLEDIARIO CD ON CM.IDCONTROLEMENSAL = CD.IDCONTROLEMENSAL ");
			sql.append("				  GROUP BY CD.IDCONTROLEMENSAL ");
			sql.append("			) DIARIO ");
			sql.append("    ON CM.idControleMensal = DIARIO.idControleMensal ");
			sql.append(" WHERE CM.idFuncionario = (?) and CM.mes = (?) ");

			
			ps = conn.prepareStatement(sql.toString());
			ps.setInt(1, idFuncionario);
			ps.setInt(2, mes);
			
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
