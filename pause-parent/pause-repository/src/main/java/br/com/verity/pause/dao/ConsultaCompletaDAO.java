package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ConsultaCompletaEntity;

@Repository
public class ConsultaCompletaDAO {
	
	public List<ConsultaCompletaEntity> findByIdAndPeriodo(Integer id, Date de, Date ate) {
		List<ConsultaCompletaEntity> entities = new ArrayList<ConsultaCompletaEntity>();
		ConsultaCompletaEntity entity = new ConsultaCompletaEntity();
		try {
			Connection conn = ConnectionFactory.createConnection();
			String sql = "declare @start datetime = ? "+
					 	"declare @end   datetime = ? "+
					 	";with amonth(day) as "+
						"( "+
						    "select  @start as day "+
						        "union all "+
						    "select day + 1 "+
						        "from amonth "+
						        "where day < @end "+
						") "+
						"SELECT am.day ,cm.idFuncionario, cm.ano, cm.mes, cm.horaTotal as cmHora, cm.bancoHora as cmBanco, cm.adcNoturno as cmAdcNot, "+
						"cm.sobreAviso as cmSA, cm.sobreAvisoTrabalhado as cmST, "+
						"cd.data as cdData, cd.horaTotal as cdHora, cd.bancoHora as cdBanco, cd.adcNoturno as cdAdcNot, "+
						"cd.sobreAviso as cdSA, cd.sobreAvisoTrabalhado as cdST, "+
						"a.data as apData, a.horario as apHorario, a.tipoImportacao as apTpImport, a.observacao as apObs FROM amonth am "+
						"LEFT JOIN PAUSEControleDiario cd ON am.day = cd.data "+
						"LEFT JOIN PAUSEControleMensal cm ON cm.idControleMensal = cd.idControleMensal "+
						"LEFT JOIN PAUSEApontamento a ON cd.idControleDiario = a.idControleDiario "+
						"WHERE cm.idFuncionario = ? OR cm.idFuncionario is null ";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDate(1, de);
			ps.setDate(2, ate);
			ps.setInt(3, id);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				entity = new ConsultaCompletaEntity();
				entity.setData(rs.getDate("day"));
				Integer idFuncionario = rs.getInt("idFuncionario");
				if(idFuncionario != null && idFuncionario != 0){
					entity.setIdFuncionario(rs.getInt("idFuncionario"));
					entity.setCmAno(rs.getInt("ano"));
					entity.setCmMes(rs.getInt("mes"));
					entity.setCmHoraTotal(rs.getDouble("cmHora"));
					entity.setCmBancoHora(rs.getDouble("cmBanco"));
					entity.setCmAdcNoturno(rs.getDouble("cmAdcNot"));
					entity.setCmSA(rs.getDouble("cmSA"));
					entity.setCmST(rs.getDouble("cmST"));
					entity.setCdData(rs.getDate("cdData"));
					entity.setCdHoraTotal(rs.getDouble("cdHora"));
					entity.setCdBancoHora(rs.getDouble("cdBanco"));
					entity.setCdAdcNoturno(rs.getDouble("cdAdcNot"));
					entity.setCdSA(rs.getDouble("cdSA"));
					entity.setCdST(rs.getDouble("cdST"));
					entity.setApData(rs.getDate("apData"));
					entity.setApHorario(rs.getTime("apHorario"));
					entity.setApTpImportacao(rs.getBoolean("apTpImport"));
					entity.setApObs(rs.getString("apObs"));
				}
				entities.add(entity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
