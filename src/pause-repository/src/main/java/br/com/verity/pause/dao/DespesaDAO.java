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
import br.com.verity.pause.entity.DespesaEntity;

@Repository
public class DespesaDAO {

	@Autowired
	private ConnectionFactory connectionFactory;

	Connection conn;
	PreparedStatement ps;
	ResultSet rs;

	public synchronized DespesaEntity salvaDespesa(DespesaEntity despesa) throws SQLException {
		try {
			conn = connectionFactory.createConnection();

			String sql = "INSERT INTO despesa (id_status, id_tipo_despesa, justificativa, valor, data_solicitacao, "
					+ "id_projeto, id_solicitante, caminho_comprovante) values (?,?,?,?,?,?,?,?)";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, despesa.getStatus());
			ps.setLong(2, despesa.getTipoDespesa());
			ps.setString(3, despesa.getJustificativa());
			ps.setDouble(4, despesa.getValor());
			ps.setDate(5, new java.sql.Date(despesa.getDataSolicitacao().getTime()));
			ps.setLong(6, despesa.getIdProjeto());
			ps.setLong(7, despesa.getIdSolicitante());
			ps.setString(8, despesa.getCaminhoJustificativa());

			ps.execute();

			return findDespesaMaxId(despesa.getIdSolicitante());
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	public DespesaEntity findDespesaMaxId(Long id) {

		DespesaEntity entity = new DespesaEntity();
		String sql = null;

		sql = "SELECT * FROM despesa where id = (SELECT MAX(id) FROM despesa) and id_solicitante = ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, id); 
			rs = ps.executeQuery();

			if (rs.next()) {
				entity.setId(rs.getLong(1));
				entity.setStatus(rs.getLong(2));
				entity.setTipoDespesa(rs.getLong(3));
				entity.setJustificativa(rs.getString(4));
				entity.setValor(rs.getDouble(5));
				entity.setDataSolicitacao(rs.getDate(6));
				entity.setIdProjeto(rs.getLong(7));
				entity.setIdSolicitante(rs.getLong(8));
				entity.setDataAcaoGp(rs.getDate(9));
				entity.setDataAcaoFinanceiro(rs.getDate(10));
				entity.setIdGpAprovador(rs.getLong(11));
				entity.setIdFinanceiroAprovador(rs.getLong(12));
				entity.setCaminhoJustificativa(rs.getString(13));
			}

			ps.execute();
			fecharConexoes();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fecharConexoes();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return entity;
	}

	public List<DespesaEntity> listarDespesaPorFuncionario(Integer idFuncionario) {
		List<DespesaEntity> despesas = new ArrayList<DespesaEntity>();
		String sql = null;

		sql = "SELECT * FROM despesa where id_solicitante = ?";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, idFuncionario); 
			rs = ps.executeQuery();

			if (rs.next()) {
				DespesaEntity despesa = new DespesaEntity();
				despesa.setId(rs.getLong(1));
				despesa.setStatus(rs.getLong(2));
				despesa.setTipoDespesa(rs.getLong(3));
				despesa.setJustificativa(rs.getString(4));
				despesa.setValor(rs.getDouble(5));
				despesa.setDataSolicitacao(rs.getDate(6));
				despesa.setIdProjeto(rs.getLong(7));
				despesa.setIdSolicitante(rs.getLong(8));
				despesa.setDataAcaoGp(rs.getDate(9));
				despesa.setDataAcaoFinanceiro(rs.getDate(10));
				despesa.setIdGpAprovador(rs.getLong(11));
				despesa.setIdFinanceiroAprovador(rs.getLong(12));
				despesa.setCaminhoJustificativa(rs.getString(13));
				despesas.add(despesa);
			}
			fecharConexoes();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				fecharConexoes();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return despesas;
	}

	private void fecharConexoes() throws SQLException {
		if (rs != null && !rs.isClosed()) {
			rs.close();
		}
		if (ps != null && !ps.isClosed()) {
			ps.close();
		}

		if (conn != null && conn.isClosed()) {
			conn.close();
		}
	}


}
