package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ApontamentoEntity;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;
import br.com.verity.pause.entity.ControleDiarioEntity;
import br.com.verity.pause.entity.TipoJustificativaEntity;

@Repository
public class ApontamentoDAO {

	private ConnectionFactory connection;

	public List<ApontamentoEntity> findAll() throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
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
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
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

	public void save(ApontamentoEntity horas) {
		Connection conn;
		try {
			conn = ConnectionFactory.createConnection();

			String sql = "INSERT INTO PAUSEApontamento VALUES (?,?,?,?,?,?,?,?,?,?,?)";

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, horas.getPis()); // pis
			ps.setDate(2, horas.getData()); // data
			ps.setTime(3, horas.getHorario()); // horario
			ps.setBoolean(4, horas.getTipoImportacao()); // tipoImportacao
			ps.setDate(5, horas.getDataInclusao()); // dataInclusao
			ps.setString(6, horas.getObservacao()); // observacao
			ps.setInt(7, horas.getTipoJustificativa().getId()); // idTpJustificativa
			ps.setInt(8, horas.getControleDiario().getId()); // idControleDiario
			ps.setInt(9, horas.getIdEmpresa()); // idEmpresa
			ps.setInt(10, horas.getIdUsuarioInclusao()); // idUsuarioInclusao
			if (horas.getArquivoApontamento() != null)
				ps.setInt(11, horas.getArquivoApontamento().getId()); // idArquivoApontamento
			else
				ps.setNull(11, Types.INTEGER);

			ps.execute();
			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void saveAll(List<ApontamentoEntity> horas, Integer idArquivo) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "INSERT INTO PAUSEApontamento VALUES (?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = conn.prepareStatement(sql);

		for (ApontamentoEntity horasEntity : horas) {
			if (horasEntity.getHorario() != null) {
				ps.setString(1, horasEntity.getPis()); // pis
				java.sql.Date data = new java.sql.Date(horasEntity.getData().getTime());
				ps.setDate(2, data); // data
				ps.setTime(3, horasEntity.getHorario()); // horario
				ps.setBoolean(4, horasEntity.getTipoImportacao()); // tipoImportacao
				java.sql.Date dataInclusao = new java.sql.Date(horasEntity.getDataInclusao().getTime());
				ps.setDate(5, dataInclusao); // dataInclusao
				if (horasEntity.getTipoImportacao()) {
					ps.setString(6, null); // observacao
					ps.setNull(7, java.sql.Types.INTEGER); // idTpJustificativa
					ps.setNull(8, java.sql.Types.INTEGER); // idControleDiario
					ps.setInt(11, idArquivo); // idArquivoApontamento
				} else {
					ps.setString(6, horasEntity.getObservacao()); // observacao
					ps.setInt(7, horasEntity.getTipoJustificativa().getId()); // idTpJustificativa
					ps.setInt(8, horasEntity.getControleDiario().getId()); // idControleDiario
					ps.setNull(11, java.sql.Types.INTEGER); // idArquivoApontamento
				}
				ps.setInt(9, horasEntity.getIdEmpresa()); // idEmpresa
				ps.setInt(10, horasEntity.getIdUsuarioInclusao()); // idUsuarioInclusao
				ps.execute();
			}
		}
		ps.close();
	}

	public void excludeAllDate(Date data) throws SQLException {
		connection = new ConnectionFactory();
		Connection conn = connection.createConnection();
		String sql = "DELETE PAUSEApontamento WHERE data = ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		java.sql.Date sqlDate = new java.sql.Date(data.getTime());
		ps.setDate(1, sqlDate);

		ps.execute();
		ps.close();
	}

	public List<ApontamentoEntity> findByPisAndPeriodo(String pis, java.sql.Date de, java.sql.Date ate) {
		List<ApontamentoEntity> entities = new ArrayList<>();

		String sql = "SELECT * FROM PAUSEApontamento WHERE pis LIKE ? AND data BETWEEN ? AND ? ORDER BY data ASC";

		try {
			Connection conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, pis);
			ps.setDate(2, de);
			ps.setDate(3, ate);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ApontamentoEntity entity = new ApontamentoEntity();
				TipoJustificativaEntity justificativa = new TipoJustificativaEntity();
				ControleDiarioEntity controleDiario = new ControleDiarioEntity();
				ArquivoApontamentoEntity arquivoApontamento = new ArquivoApontamentoEntity();

				entity.setId(rs.getInt(1));
				entity.setPis(rs.getString(2));
				entity.setData(rs.getDate(3));
				entity.setHorario(rs.getTime(4));
				entity.setTipoImportacao(rs.getBoolean(5));
				entity.setDataInclusao(rs.getDate(6));
				entity.setObservacao(rs.getString(7));

				justificativa.setId(rs.getInt(8));
				entity.setTipoJustificativa(justificativa);

				controleDiario.setId(rs.getInt(9));
				entity.setControleDiario(controleDiario);

				entity.setIdEmpresa(rs.getInt(10));
				entity.setIdUsuarioInclusao(rs.getInt(11));

				arquivoApontamento.setId(rs.getInt(12));
				entity.setArquivoApontamento(arquivoApontamento);

				entities.add(entity);
			}

			ps.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		/*
		 * public List<ApontamentoEntity> findByPisAndPeriodo(String pis, Date de, Date
		 * ate) { List<ApontamentoEntity> entities = new ArrayList<ApontamentoEntity>();
		 * ApontamentoEntity entity = new ApontamentoEntity(); try { connection = new
		 * ConnectionFactory(); Connection conn = connection.createConnection(); String
		 * sql =
		 * "SELECT * FROM PAUSEApontamento WHERE pis like (?) AND data >= ? AND data <= ?"
		 * ;
		 * 
		 * PreparedStatement ps = conn.prepareStatement(sql);
		 * 
		 * ps.setString(1, pis); java.sql.Date dtDe = new java.sql.Date(de.getTime());
		 * ps.setDate(2, dtDe); java.sql.Date dtAte = new java.sql.Date(ate.getTime());
		 * ps.setDate(3, dtAte);
		 * 
		 * ResultSet rs = ps.executeQuery();
		 * 
		 * while (rs.next()) { entity = new ApontamentoEntity();
		 * entity.setId(rs.getInt("idApontamento")); entity.setPis(rs.getString("pis"));
		 * entity.setHorario(rs.getTime("horario")); entity.setData(rs.getDate("data"));
		 * entity.setTipoImportacao(rs.getBoolean("tipoImportacao"));
		 * entity.setIdEmpresa(rs.getInt("idEmpresa"));
		 * entity.setDataInclusao(rs.getDate("dataInclusao"));
		 * entity.setIdUsuarioInclusao(rs.getInt("idUsuarioInclusao"));
		 * entity.setObservacao(rs.getString("observacao"));
		 * 
		 * entities.add(entity); } } catch (SQLException e) { e.printStackTrace(); }
		 * 
		 */
		return entities;
	}

	public ApontamentoEntity findById(Integer id) {
		ApontamentoEntity entity = new ApontamentoEntity();

		String sql = "SELECT * FROM PAUSEApontamento WHERE idApontamento = ?";

		try {
			Connection conn = ConnectionFactory.createConnection();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				TipoJustificativaEntity justificativa = new TipoJustificativaEntity();
				ControleDiarioEntity controleDiario = new ControleDiarioEntity();
				ArquivoApontamentoEntity arquivoApontamento = new ArquivoApontamentoEntity();

				entity.setId(rs.getInt(1));
				entity.setPis(rs.getString(2));
				entity.setData(rs.getDate(3));
				entity.setHorario(rs.getTime(4));
				entity.setTipoImportacao(rs.getBoolean(5));
				entity.setDataInclusao(rs.getDate(6));
				entity.setObservacao(rs.getString(7));

				justificativa.setId(rs.getInt(8));
				entity.setTipoJustificativa(justificativa);

				controleDiario.setId(rs.getInt(9));
				entity.setControleDiario(controleDiario);

				entity.setIdEmpresa(rs.getInt(10));
				entity.setIdUsuarioInclusao(rs.getInt(11));

				arquivoApontamento.setId(rs.getInt(12));
				entity.setArquivoApontamento(arquivoApontamento);
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
			conn = ConnectionFactory.createConnection();

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
}