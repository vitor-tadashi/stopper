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
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;

@Repository
public class ControleMensalDAO {
	
	
	@Autowired
	private ConnectionFactory connectionFactory;
	
	public ControleMensalEntity findByMesAnoIdFuncionario(int mes, int ano, int idFuncionario) {
		String sql = "SELECT * FROM PAUSEControleMensal WHERE mes = ? AND ano = ? AND idFuncionario = ?";

		Connection conn;
		ControleMensalEntity entity = null;
		try {
			conn = connectionFactory.createConnection();

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
			conn = connectionFactory.createConnection();

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
			conn = connectionFactory.createConnection();

			sql = new StringBuilder();
			
			sql.append("   UPDATE CM ");
			sql.append("	SET CM.HORATOTAL = ROUND(DIARIO.HORATOTAL, 2), ");
			sql.append("	    CM.BANCOHORA = ROUND(DIARIO.BANCOHORA, 2), ");
			sql.append("		CM.ADCNOTURNO = ROUND(DIARIO.ADCNOTURNO, 2), ");
			sql.append("		CM.SOBREAVISOTRABALHADO = ROUND(DIARIO.SOBREAVISOTRABALHADO, 2), ");
			sql.append("		CM.SOBREAVISO = ROUND(DIARIO.SOBREAVISO, 2) ");
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
		ControleDiarioEntity cdEntity = null;
		List<ControleDiarioEntity> cdEntities = new ArrayList<ControleDiarioEntity>();
		int mes = dtHoje.getMonth() + 1;
		int ano = dtHoje.getYear() + 1900;
		
		String sql = "SELECT * FROM PAUSEControleMensal as cm "+
						  "LEFT JOIN PAUSEControleDiario as cd ON cd.idControleMensal = cm.idControleMensal AND cd.data = ? "+
					   "where cm.mes = ? AND cm.ano = ? AND cm.idFuncionario = ?";

		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, new java.sql.Date(dtHoje.getTime()));
			ps.setInt(2, mes);
			ps.setInt(3, ano);
			ps.setInt(4, id);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleMensalEntity();
				cdEntity = new ControleDiarioEntity();
				entity.setId(rs.getInt(1));
				entity.setAdcNoturno(rs.getDouble(4));
				entity.setBancoHora(rs.getDouble(3));
				entity.setHrTotal(rs.getDouble(2));
				entity.setSobreAviso(rs.getDouble(5));
				entity.setSobreAvisoTrabalhado(rs.getDouble(6));
				entity.setMes(rs.getInt(7));
				entity.setAno(rs.getInt(8));
				entity.setIdFuncionario(rs.getInt(9));
				cdEntity.setHrTotal(rs.getDouble(11));
				cdEntity.setBancoHora(rs.getDouble(12));
				cdEntity.setAdcNoturno(rs.getDouble(13));
				cdEntity.setSobreAviso(rs.getDouble(14));
				cdEntity.setSobreAvisoTrabalhado(rs.getDouble(15));
				cdEntity.setData(rs.getDate(16));				
				cdEntities.add(cdEntity);
				entity.setControleDiario(cdEntities);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public Double findSumBancoTrimestre(int primeiroMesTrimestre, int ultimoMesTrimestre, int year,
			Integer idFuncionario) throws SQLException {
		Double banco = null;
		Connection conn = null;
		
		String sql = "SELECT SUM(bancoHora) FROM PAUSEControleMensal WHERE ano = ? AND idFuncionario = ? AND mes BETWEEN ? AND ?";
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, year);
			ps.setInt(2, idFuncionario);
			ps.setInt(3, primeiroMesTrimestre);
			ps.setInt(4, ultimoMesTrimestre);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				banco = rs.getDouble(1);
			}
			ps.execute();
			ps.close();
			conn.close();
			}catch(SQLException e){
				throw new SQLException();
			}
		return banco;
	}

	public List<ControleMensalEntity> findHoraAndBancoByIdFuncionario(Integer id, int primeiroMesTrimestre,
			int mesSolicitado, int ano) throws SQLException {
		Connection conn = null;
		List<ControleMensalEntity> response = new ArrayList<>();
		ControleMensalEntity controleMensal = null;
		
		String sql = "SELECT cm.idControleMensal, cm.horaTotal, cm.bancoHora, cm.idFuncionario, cm.mes, cm.ano FROM PAUSEControleMensal cm"+
				" WHERE cm.mes >= ? AND cm.mes <= ? AND cm.idFuncionario = ? AND cm.ano = ? ORDER BY cm.mes ASC";
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, primeiroMesTrimestre);
			ps.setInt(2, mesSolicitado);
			ps.setInt(3, id);
			ps.setInt(4, ano);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				controleMensal = new ControleMensalEntity();
				
				controleMensal.setId(rs.getInt(1));
				controleMensal.setHrTotal(rs.getDouble(2));
				controleMensal.setBancoHora(rs.getDouble(3));
				controleMensal.setIdFuncionario(rs.getInt(4));
				controleMensal.setMes(rs.getInt(5));
				controleMensal.setAno(rs.getInt(6));
				
				response.add(controleMensal);
			}
			ps.execute();
			ps.close();
			conn.close();
			}catch(SQLException e){
				throw new SQLException();
			}
		return response;
	}
}
