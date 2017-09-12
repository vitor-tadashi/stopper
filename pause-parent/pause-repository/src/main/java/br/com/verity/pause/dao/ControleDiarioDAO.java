package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentoPivotEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;

@Repository
public class ControleDiarioDAO {

	public ControleDiarioEntity findByDataIdFuncionario(Date data, int idFuncionario) {
		String sql = "SELECT * FROM PAUSEControleDiario WHERE data = ? AND idFuncionario = ?";

		Connection conn;
		ControleDiarioEntity entity = null;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, data);
			ps.setInt(2, idFuncionario);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				entity = new ControleDiarioEntity();
				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(7));
				
				ControleMensalEntity controleMensal = new ControleMensalEntity();
				controleMensal.setId(rs.getInt(8));
				
				entity.setControleMensal(controleMensal);
				entity.setIdFuncionario(rs.getInt(9));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void save(ControleDiarioEntity entity) {
		String sql = "INSERT INTO PAUSEControleDiario (data, idControleMensal ,idFuncionario) VALUES (?, ?, ?)";

		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, entity.getData());
			ps.setInt(2, entity.getControleMensal().getId());
			ps.setInt(3, entity.getIdFuncionario());
			
			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Salva todos os calculos dos controle diário e atualiza o controle mensal
	 * @param apontamento Apontamento com os cálculos realizados
	 */
	public void saveCalculation(ApontamentoPivotEntity apontamento) {
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			StringBuilder sql = null;
			conn = ConnectionFactory.createConnection();

			sql = new StringBuilder();
			
			sql.append(" UPDATE CD");
			sql.append("	SET CD.HORATOTAL = (?),");
			sql.append("	    CD.BANCOHORA = (?),");
			sql.append("		CD.ADCNOTURNO = (?),");
			sql.append("		CD.SOBREAVISOTRABALHADO = (?),");
			sql.append("		CD.SOBREAVISO = (?)");
			sql.append("   FROM PAUSECONTROLEMENSAL CM");
			sql.append("        INNER JOIN PAUSECONTROLEDIARIO CD ON CM.IDCONTROLEMENSAL = CD.IDCONTROLEMENSAL");
			sql.append("  WHERE CM.IDFUNCIONARIO = (?) and CD.DATA = (?)");
			
			ps = conn.prepareStatement(sql.toString());
			ps.setDouble(1, apontamento.getTotalHoras());
			ps.setDouble(2, apontamento.getHorasExtras());
			ps.setDouble(3, apontamento.getTotalAdicionalNoturno());
			ps.setDouble(4, apontamento.getTotalSobreAvisoTrabalhado());
			ps.setDouble(5, apontamento.getTotalSobreAviso());
			ps.setInt(6, apontamento.getIdFuncionario());
			ps.setDate(7, apontamento.getDataApontamento());
			
			ps.execute();
			ps.close();
			conn.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
