package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentoPivotEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;

@Repository
public class ControleDiarioDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	public ControleDiarioEntity findByDataIdFuncionario(Date data, int idFuncionario) {
		String sql = "SELECT cd.idControleMensal, cd.idControleDiario, cm.idFuncionario, cd.data, cd.sobreAviso "
				+ "FROM PAUSEControleDiario cd RIGHT JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal\r\n"
				+ "  WHERE cm.idFuncionario = ? And cd.data = ?";

		Connection conn;
		ControleDiarioEntity entity = null;
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, idFuncionario);
			ps.setDate(2, data);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				entity = new ControleDiarioEntity();
				ControleMensalEntity controleMensal = new ControleMensalEntity();

				controleMensal.setId(rs.getInt(1));
				entity.setId(rs.getInt(2));
				controleMensal.setIdFuncionario(3);
				entity.setData(rs.getDate(4));
				entity.setSobreAviso(rs.getDouble(5));
				
				entity.setControleMensal(controleMensal);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void save(ControleDiarioEntity entity) {
		String sql = "INSERT INTO PAUSEControleDiario (data, idControleMensal) VALUES (?, ?)";

		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setDate(1, entity.getData());
			ps.setInt(2, entity.getControleMensal().getId());

			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Salva todos os calculos dos controle diário e atualiza o controle mensal
	 * 
	 * @param apontamento
	 *            Apontamento com os cálculos realizados
	 */
	public void saveCalculation(ApontamentoPivotEntity apontamento) {
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			StringBuilder sql = null;
			conn = connectionFactory.createConnection();

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

	/**
	 * Busca todos os os funcionarios com a soma do banco de horas de controle diario, por empresa;
	 * @author guilherme.oliveira
	 */
	public List<ControleDiarioEntity> findByDataSum(Date de, Date ate, String idFuncs) {
		List<ControleDiarioEntity> entities = new ArrayList<ControleDiarioEntity>();
		ControleDiarioEntity entity = new ControleDiarioEntity();
		ControleMensalEntity cmEntity = new ControleMensalEntity();
		DecimalFormat twoDForm = new DecimalFormat("####.##");
		
		try {
			Connection conn = null;
			PreparedStatement ps = null;
			StringBuilder sql = null;
			conn = connectionFactory.createConnection();

			sql = new StringBuilder();
			
			sql.append("SELECT cd.idControleMensal AS cdIdControleMensal,");
			sql.append("		SUM(cd.horaTotal) AS horaTotal, SUM(cd.bancoHora) AS bancoHora,");
			sql.append("		SUM(cd.adcNoturno) AS adcNoturno, SUM(cd.sobreAviso) AS sobreAviso,");
			sql.append("	    SUM(cd.sobreAvisoTrabalhado) AS sobreAvisoTrabalhado, cm.idFuncionario as idFuncionario");
			sql.append("	FROM PAUSEControleDiario AS cd");
			sql.append("		INNER JOIN PAUSEControleMensal AS cm ON cm.idControleMensal = cd.idControleMensal");
			sql.append("											AND cm.idFuncionario IN ("+idFuncs+")");
			sql.append("  WHERE data BETWEEN ? AND ?");
			sql.append("	GROUP BY cd.idControleMensal, cm.idFuncionario");
			
			ps = conn.prepareStatement(sql.toString());
			ps.setDate(1, de);
			ps.setDate(2, ate);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				entity = new ControleDiarioEntity();
				cmEntity = new ControleMensalEntity();
				
				cmEntity.setIdFuncionario(rs.getInt("idFuncionario"));
				cmEntity.setId(rs.getInt("cdIdControleMensal"));
				
				entity.setHrTotal(Double.parseDouble(twoDForm.format(rs.getDouble("horaTotal")).replace(",", ".")));
				entity.setBancoHora(Double.parseDouble(twoDForm.format(rs.getDouble("bancoHora")).replace(",", ".")));
				entity.setAdcNoturno(Double.parseDouble(twoDForm.format(rs.getDouble("adcNoturno")).replace(",", ".")));
				entity.setSobreAviso(Double.parseDouble(twoDForm.format(rs.getDouble("sobreAviso")).replace(",", ".")));
				entity.setSobreAvisoTrabalhado(Double.parseDouble(twoDForm.format(rs.getDouble("sobreAvisoTrabalhado")).replace(",", ".")));
				entity.setControleMensal(cmEntity);
				
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
