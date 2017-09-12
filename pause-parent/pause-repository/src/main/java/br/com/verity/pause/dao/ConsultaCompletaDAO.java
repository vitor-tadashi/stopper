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
			String sql = "CREATE TABLE ##Apontamento (idFuncionario int, ano int, mes int, cmHora float, cmBanco float, cmAdcNot float, "+
								"cmSA float, cmST float, cdData date, cdHora float, cdBanco float, cdAdcNot float, "+
								"cdSA float, cdST float, aData date, aHorario TIME, aTpImport bit, aObs varchar(100), aIdTpJustificativa int, aIdApontamento int, "+
								"atQtdHora int, sbId int) "+
		
							"INSERT INTO ##Apontamento (idFuncionario, ano, mes, cmHora, cmBanco, cmAdcNot, "+
													   "cmSA, cmST, cdData, cdHora, cdBanco, cdAdcNot, "+
												       "cdSA, cdST, aData, aHorario, aTpImport, aObs, aIdTpJustificativa, aIdApontamento, "+
													   "atQtdHora, sbId) "+
							"SELECT cm.idFuncionario, cm.ano, cm.mes, cm.horaTotal as cmHora, cm.bancoHora as cmBanco, cm.adcNoturno as cmAdcNot, "+
								   "cm.sobreAviso as cmSA, cm.sobreAvisoTrabalhado as cmST, "+
								   "cd.data as cdData, cd.horaTotal as cdHora, cd.bancoHora as cdBanco, cd.adcNoturno as cdAdcNot, "+
								   "cd.sobreAviso as cdSA, cd.sobreAvisoTrabalhado as cdST, "+
								   "a.data as aData, a.horario as aHorario, a.tipoImportacao as aTpImport, a.observacao as aObs, a.idTipoJustificativa as aIdTpJustificativa, "+
								   "a.idApontamento as aIdApontamento, at.quantidadeHora as atQtdHora, sb.idSobreAviso as sbId "+
							  "FROM PAUSEControleMensal cm "+
								   "LEFT JOIN PAUSEControleDiario cd ON cm.idControleMensal = cd.idControleMensal and cm.idFuncionario = ? AND cd.data >= ? AND cd.data <= ? "+
								   "LEFT JOIN PAUSEApontamento a ON cd.idControleDiario = a.idControleDiario "+
								   "LEFT JOIN PAUSEAtestado at ON at.idControleDiario = cd.idControleDiario "+
								   "LEFT JOIN PAUSESobreAviso sb ON sb.idControleDiario = cd.idControleDiario ";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);
			ps.setDate(2, de);
			ps.setDate(3, ate);
			
			ps.execute();
			
			sql ="declare @start datetime = ? "+
					   "declare @end   datetime = ? "+
					   ";with amonth(day) as "+
					   "( "+
					       "select @start as day "+
					           "union all "+
					       "select day + 1 "+
					           "from amonth "+
					           "where day < @end "+
					   ")"+
					   "SELECT * "+
					     "FROM amonth am "+
					   	"LEFT JOIN ##Apontamento ap ON am.day = ap.cdData option (maxrecursion 0)";
			
			ps = conn.prepareStatement(sql);
			
			ps.setDate(1, de);
			ps.setDate(2, ate);
			
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
					entity.setApData(rs.getDate("aData"));
					entity.setApHorario(rs.getTime("aHorario"));
					entity.setApTpImportacao(rs.getBoolean("aTpImport"));
					entity.setApIdApontamento(rs.getInt("aIdApontamento"));
					entity.setApObs(rs.getString("aObs"));
					entity.setApIdTpJustificativa(rs.getInt("aIdTpJustificativa"));
					entity.setAtQtdHora(rs.getDouble("atQtdHora"));
					entity.setSbId(rs.getInt("sbId"));
				}
				entities.add(entity);
			}
			ps.close();
			rs.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entities;
	}
}
