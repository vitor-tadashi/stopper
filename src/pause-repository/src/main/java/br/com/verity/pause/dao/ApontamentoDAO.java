package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.entity.ApontamentoPivotEntity;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.ControleMensalEntity;
import br.com.verity.pause.entity.TipoJustificativaEntity;

@Repository
public class ApontamentoDAO {

	@Autowired
	private ConnectionFactory connectionFactory;

	public List<ApontamentoEntity> findAll() throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "SELECT * FROM PAUSEApontamento";
		// Prepara a instrução SQL
		PreparedStatement ps = conn.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			// String nome = rs.getString("nmEMpresa");

			// System.out.println(nome);
		}

		// Executa a instrução SQL
		ps.execute();
		ps.close();
		return null;
	}

	public Boolean findByData(ApontamentoEntity horas, int idEmpresa) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "SELECT * FROM PAUSEApontamento WHERE data = ? AND idEmpresa = ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		java.sql.Date sqlDate = new java.sql.Date(horas.getData().getTime());
		ps.setDate(1, sqlDate);
		ps.setInt(2, idEmpresa);

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			ps.execute();
			ps.close();
			return true;
		}
		ps.execute();
		ps.close();
		return false;
	}

	public ApontamentoEntity save(ApontamentoEntity horas) {
		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			String sql = "INSERT INTO PAUSEApontamento VALUES (?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			//ps.setString(1, horas.getPis()); // pis
			ps.setDate(1, horas.getData()); // data
			ps.setTime(2, horas.getHorario()); // horario
			ps.setBoolean(3, horas.getTipoImportacao()); // tipoImportacao
			ps.setDate(4, horas.getDataInclusao()); // dataInclusao
			ps.setString(5, horas.getObservacao()); // observacao
			ps.setInt(6, horas.getTipoJustificativa().getId()); // idTpJustificativa
			ps.setInt(7, horas.getControleDiario().getId()); // idControleDiario
			ps.setInt(8, horas.getIdEmpresa()); // idEmpresa
			ps.setInt(9, horas.getIdUsuarioInclusao()); // idUsuarioInclusao
			if (horas.getArquivoApontamento() != null)
				ps.setInt(10, horas.getArquivoApontamento().getId()); // idArquivoApontamento
			else
				ps.setNull(10, Types.INTEGER);

			ps.execute();
			ps.close();
			conn.close();

			return findByControleDiarioMaxIdApontamento(horas.getControleDiario().getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void saveAll(List<ApontamentoEntity> horas, Integer idArquivo) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "INSERT INTO PAUSEApontamento VALUES (?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		for (ApontamentoEntity horasEntity : horas) {
			if (horasEntity.getHorario() != null) {
				java.sql.Date data = new java.sql.Date(horasEntity.getData().getTime());
				ps.setDate(1, data); // data
				ps.setTime(2, horasEntity.getHorario()); // horario
				ps.setBoolean(3, horasEntity.getTipoImportacao()); // tipoImportacao
				java.sql.Date dataInclusao = new java.sql.Date(horasEntity.getDataInclusao().getTime());
				ps.setDate(4, dataInclusao); // dataInclusao
				if (horasEntity.getTipoImportacao()) {
					ps.setString(5, null); // observacao
					ps.setNull(6, java.sql.Types.INTEGER); // idTpJustificativa
					ps.setInt(10, idArquivo); // idArquivoApontamento
				} else {
					ps.setString(5, horasEntity.getObservacao()); // observacao
					ps.setInt(6, horasEntity.getTipoJustificativa().getId()); // idTpJustificativa
					ps.setNull(10, java.sql.Types.INTEGER); // idArquivoApontamento
				}
				ps.setInt(7, horasEntity.getControleDiario().getId()); // idControleDiario
				ps.setInt(8, horasEntity.getIdEmpresa()); // idEmpresa
				ps.setInt(9, horasEntity.getIdUsuarioInclusao()); // idUsuarioInclusao
				ps.execute();
			}
		}
		ps.close();
	}

	public void excludeAllDate(Date data) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "DELETE PAUSEApontamento WHERE data = ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setDate(1, data);

		ps.execute();
		ps.close();
	}

	public List<ApontamentoEntity> findByPisAndPeriodo(Integer idFunc, java.sql.Date data) {
		List<ApontamentoEntity> entities = new ArrayList<>();

		String sql = "SELECT PA.* FROM PAUSEApontamento PA JOIN PAUSEControleDiario PCD	ON PA.idControleDiario = PCD.idControleDiario JOIN PAUSEControleMensal PCM ON PCD.idControleMensal = PCM.idControleMensal	WHERE PA.data LIKE ? AND PCM.idFuncionario = ?";

		try {
			Connection conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setDate(1, data);
			ps.setInt(2, idFunc);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ApontamentoEntity entity = new ApontamentoEntity();
				TipoJustificativaEntity justificativa = new TipoJustificativaEntity();
				ControleDiarioEntity controleDiario = new ControleDiarioEntity();
				ArquivoApontamentoEntity arquivoApontamento = new ArquivoApontamentoEntity();

				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(2));
				entity.setHorario(rs.getTime(3));
				entity.setTipoImportacao(rs.getBoolean(4));
				entity.setDataInclusao(rs.getDate(5));
				entity.setObservacao(rs.getString(6));

				justificativa.setId(rs.getInt(7));
				entity.setTipoJustificativa(justificativa);

				controleDiario.setId(rs.getInt(8));
				entity.setControleDiario(controleDiario);

				entity.setIdEmpresa(rs.getInt(9));
				entity.setIdUsuarioInclusao(rs.getInt(10));

				arquivoApontamento.setId(rs.getInt(11));
				entity.setArquivoApontamento(arquivoApontamento);

				entities.add(entity);
			}

			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return entities;
	}

	public ApontamentoEntity findById(Integer id) {
		ApontamentoEntity entity = null;

		String sql = "SELECT ap.*,cd.idControleMensal, cm.idFuncionario FROM PAUSEApontamento ap"+
				" inner join PAUSEControleDiario cd on cd.idControleDiario = ap.idControleDiario" + 
				" inner join PAUSEControleMensal cm on cm.idControleMensal = cd.idControleMensal" + 
				" where ap.idApontamento = ?";

		try {
			Connection conn = connectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TipoJustificativaEntity justificativa = new TipoJustificativaEntity();
				ControleDiarioEntity controleDiario = new ControleDiarioEntity();
				ArquivoApontamentoEntity arquivoApontamento = new ArquivoApontamentoEntity();
				ControleMensalEntity controleMensalEntity = new ControleMensalEntity();
				entity = new ApontamentoEntity();
				
				entity.setId(rs.getInt(1));
				entity.setData(rs.getDate(2));
				entity.setHorario(rs.getTime(3));
				entity.setTipoImportacao(rs.getBoolean(4));
				entity.setDataInclusao(rs.getDate(5));
				entity.setObservacao(rs.getString(6));

				justificativa.setId(rs.getInt(7));
				entity.setTipoJustificativa(justificativa);

				controleDiario.setId(rs.getInt(8));

				entity.setIdEmpresa(rs.getInt(9));
				entity.setIdUsuarioInclusao(rs.getInt(10));

				arquivoApontamento.setId(rs.getInt(11));
				entity.setArquivoApontamento(arquivoApontamento);
				
				controleMensalEntity.setId(rs.getInt(12));
				controleMensalEntity.setIdFuncionario(rs.getInt(13));
				controleDiario.setControleMensal(controleMensalEntity);
				entity.setControleDiario(controleDiario);
			}

			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return entity;
	}

	public void update(ApontamentoEntity horas) {
		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			String sql = "UPDATE PAUSEApontamento SET horario = ?, dataInclusao = ?, observacao = ?, idTipoJustificativa = ?, idUsuarioInclusao = ? WHERE idApontamento = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setTime(1, horas.getHorario());
			ps.setDate(2, horas.getDataInclusao());
			ps.setString(3, horas.getObservacao());
			ps.setInt(4, horas.getTipoJustificativa().getId());
			ps.setInt(5, horas.getIdUsuarioInclusao());
			ps.setInt(6, horas.getId());

			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public ApontamentoEntity findByControleDiarioMaxIdApontamento(int id) {
		ApontamentoEntity entity = new ApontamentoEntity();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String sql = null;

		sql = "SELECT MAX(idApontamento) FROM PAUSEApontamento WHERE idControleDiario = ?";

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

	public void deleteById(Integer id) {
		Connection conn;
		try {
			conn = connectionFactory.createConnection();

			String sql = "DELETE FROM PAUSEApontamento WHERE idApontamento = ?";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id); 

			ps.execute();
			ps.close();
			conn.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Encontra os apontamentos do funcionário da respectiva data
	 * 
	 * @param idFuncionario
	 *            Id do funcionário
	 * @param data
	 *            Data do apontamento do funcionário
	 * @return
	 * @throws SQLException
	 */
	public ApontamentoPivotEntity findByEmployeeAndDate(int idFuncionario, Date data) throws SQLException {
		ApontamentoPivotEntity response = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		StringBuilder sql = null;
		conn = connectionFactory.createConnection();

		sql = new StringBuilder();

		sql.append("select idFuncionario, data,");
		sql.append("       [1] as entrada1,[2] as saida1,");
		sql.append("       [3] as entrada2, [4] as saida2,");
		sql.append("	   [5] as entrada3, [6] as saida3,");
		sql.append("	   [7] as entrada4, [8] as saida4 ");
		sql.append("from");
		sql.append("(");
		sql.append("  SELECT cm.idFuncionario, cd.data, ROW_NUMBER() OVER(ORDER BY a.horario ASC) as ordem, a.horario");
		sql.append("    FROM PAUSEControleMensal cm");
		sql.append("         INNER JOIN PAUSEControleDiario cd on cm.idControleMensal = cd.idControleMensal");
		sql.append("         LEFT JOIN PAUSEApontamento a on cd.idControleDiario = a.idControleDiario");
		sql.append("   WHERE cm.idFuncionario = (?) and");
		sql.append("         cd.data = (?) ");
		sql.append(") apontamento ");
		sql.append("PIVOT ");
		sql.append("(");
		sql.append("	MAX(horario)");
		sql.append("    FOR ordem IN ([1],[2],[3],[4],[5],[6],[7],[8])");
		sql.append(") PivotTable");

		ps = conn.prepareStatement(sql.toString());
		ps.setInt(1, idFuncionario);
		ps.setDate(2, data);

		rs = ps.executeQuery();

		if (rs.next()) {
			response = new ApontamentoPivotEntity();
			response.setIdFuncionario(rs.getInt("idFuncionario"));
			response.setDataApontamento(rs.getDate("data"));
			response.setEntrada1(rs.getTime("entrada1"));
			response.setSaida1(rs.getTime("saida1"));
			response.setEntrada2(rs.getTime("entrada2"));
			response.setSaida2(rs.getTime("saida2"));
			response.setEntrada3(rs.getTime("entrada3"));
			response.setSaida3(rs.getTime("saida3"));
			response.setEntrada4(rs.getTime("entrada4"));
			response.setSaida4(rs.getTime("saida4"));
		}
		
		ps.close();
		conn.close();

		return response;
	}
}