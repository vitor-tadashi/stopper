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
import br.com.verity.pause.entity.StatusEntity;
import br.com.verity.pause.entity.TipoDespesaEntity;

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
					+ "id_projeto, id_solicitante, caminho_comprovante, data_ocorrencia) values (?,?,?,?,?,?,?,?,?)";

			ps = conn.prepareStatement(sql);

			ps.setInt(1, despesa.getStatus().getId());
			ps.setLong(2, despesa.getTipoDespesa().getId());
			ps.setString(3, despesa.getJustificativa());
			ps.setDouble(4, despesa.getValor());
			ps.setDate(5, new java.sql.Date(despesa.getDataSolicitacao().getTime()));
			ps.setLong(6, despesa.getIdProjeto());
			ps.setLong(7, despesa.getIdSolicitante());
			ps.setString(8, despesa.getCaminhoComprovante());
			ps.setDate(9,  new java.sql.Date(despesa.getDataOcorrencia().getTime()));

			ps.execute();

			return findDespesaMaxId(despesa.getIdSolicitante());
		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}
	
	public synchronized DespesaEntity updateDespesa(DespesaEntity despesa) throws SQLException {
		try {
			conn = connectionFactory.createConnection();

			
			String sql = "UPDATE despesa set id_tipo_despesa = ?, justificativa = ?, valor = ?,"
					+ " id_projeto = ?, caminho_comprovante = ?, data_ocorrencia = ? where id = ?";

			ps = conn.prepareStatement(sql);

			ps.setLong(1, despesa.getTipoDespesa().getId());
			ps.setString(2, despesa.getJustificativa());
			ps.setDouble(3, despesa.getValor());
			ps.setLong(4, despesa.getIdProjeto());
			ps.setString(5, despesa.getCaminhoComprovante());
			ps.setDate(6,  new java.sql.Date(despesa.getDataOcorrencia().getTime()));
			ps.setLong(7, despesa.getId());


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

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id = (SELECT MAX(id) FROM despesa) and id_solicitante = ? ";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, id); 
			rs = ps.executeQuery();

			if (rs.next()) {
				createDespesa(entity);
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
		return entity;
	}

	public List<DespesaEntity> listarDespesaPorFuncionario(Integer idFuncionario) {
		List<DespesaEntity> despesas = new ArrayList<DespesaEntity>();
		String sql = null;

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id_solicitante = ? order by d.data_solicitacao desc";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, idFuncionario); 
			rs = ps.executeQuery();

			while (rs.next()) {
				DespesaEntity despesa = new DespesaEntity();
				createDespesa(despesa);
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

	private void createDespesa(DespesaEntity despesa) throws SQLException {
		despesa.setId(rs.getLong(1));
		despesa.setStatus(new StatusEntity());
		despesa.getStatus().setId(rs.getInt(2));
		despesa.setTipoDespesa(new TipoDespesaEntity());
		despesa.getTipoDespesa().setId(rs.getLong(3));
		despesa.setJustificativa(rs.getString(4));
		despesa.setValor(rs.getDouble(5));
		despesa.setDataSolicitacao(rs.getDate(6));
		despesa.setIdProjeto(rs.getLong(7));
		despesa.setIdSolicitante(rs.getLong(8));
		despesa.setDataAcaoGp(rs.getDate(9));
		despesa.setDataAcaoFinanceiro(rs.getDate(10));
		despesa.setIdGpAprovador(rs.getLong(11));
		despesa.setIdFinanceiroAprovador(rs.getLong(12));
		despesa.setCaminhoComprovante(rs.getString(13));
		despesa.setDataOcorrencia(rs.getDate(14));
		despesa.getStatus().setNome(rs.getString(15));
		despesa.getTipoDespesa().setNome(rs.getString(16));
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

	public void salvarAnaliseDespesaGP(Long idDespesa, Long idAprovador, Integer integer) throws Exception {
		try {

			String sql = "UPDATE despesa set data_acao_gp = ?, id_gp = ?, id_status = ? where id = ?";
			salvarAnaliseDespesa(idDespesa, idAprovador, integer, sql);

		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}
	
	public void salvarAnaliseDespesaFinanceiro(Long idDespesa, Long idAprovador, Integer idStatus) throws Exception {
		try {

			String sql = "UPDATE despesa set data_acao_financeiro = ?, id_financeiro = ?, id_status = ? where id = ?";
			salvarAnaliseDespesa(idDespesa, idAprovador, idStatus, sql);

		} catch (SQLException e) {
			throw e;
		} finally {
			fecharConexoes();
		}
	}

	private void salvarAnaliseDespesa(Long idDespesa, Long idAprovador, Integer integer, String sql) throws SQLException {
		conn = connectionFactory.createConnection();
		ps = conn.prepareStatement(sql);

		ps.setDate(1, new java.sql.Date((new java.util.Date()).getTime()));
		ps.setLong(2, idAprovador);
		ps.setLong(3, integer);
		ps.setLong(4, idDespesa);

		ps.execute();
	}
	
	public DespesaEntity findById(Long id) {

		DespesaEntity entity = new DespesaEntity();
		String sql = null;

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id = ? ";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, id); 
			rs = ps.executeQuery();

			if (rs.next()) {
				createDespesa(entity);
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
		return entity;
	}
	
	public List<DespesaEntity> listarDespesaPorStatus(StatusEntity status) {
		List<DespesaEntity> despesas = new ArrayList<DespesaEntity>();
		String sql = null;

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id_status = ? order by d.data_solicitacao desc";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, status.getId()); 
			rs = ps.executeQuery();

			while (rs.next()) {
				DespesaEntity despesa = new DespesaEntity();
				createDespesa(despesa);
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
	
	public List<DespesaEntity> listarDespesaPorStatusPendenteAnaliseFinaceiro(StatusEntity status) {
		List<DespesaEntity> despesas = new ArrayList<>();
		String sql = null;

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id_status = ? and data_acao_financeiro is null and data_acao_gp is not null order by d.data_solicitacao desc";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, status.getId()); 
			rs = ps.executeQuery();

			while (rs.next()) {
				DespesaEntity despesa = new DespesaEntity();
				createDespesa(despesa);
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
	
	public List<DespesaEntity> listarDespesaPorStatusPorProjeto(StatusEntity status, Integer idProjeto) {
		List<DespesaEntity> despesas = new ArrayList<>();
		String sql = null;

		sql = "select d.*, s.nome, t.nome from despesa d "
			   + " inner join status s " 
			   + " on d.id_status = s.id "
			   + " inner join tipo_despesa t "
			   + " on d.id_tipo_despesa = t.id where d.id_status = ? and id_Projeto = ? and data_acao_gp is null order by d.data_solicitacao desc";

		try {
			conn = connectionFactory.createConnection();
			ps = conn.prepareStatement(sql);

			ps.setLong(1, status.getId()); 
			ps.setInt(2, idProjeto);
			rs = ps.executeQuery();

			while (rs.next()) {
				DespesaEntity despesa = new DespesaEntity();
				createDespesa(despesa);
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
}
