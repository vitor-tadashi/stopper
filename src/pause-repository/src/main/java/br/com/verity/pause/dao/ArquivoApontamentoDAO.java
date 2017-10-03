package br.com.verity.pause.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.com.verity.pause.connection.ConnectionFactory;
import br.com.verity.pause.entity.ArquivoApontamentoEntity;

@Repository
public class ArquivoApontamentoDAO {
	
	@Autowired
	private ConnectionFactory connectionFactory;

	public void excludeDate(ArquivoApontamentoEntity arquivoApontamento) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "DELETE PAUSEArquivoApontamento WHERE data = ? AND idEmpresa = ?";

		PreparedStatement ps = conn.prepareStatement(sql);
		
		java.sql.Date sqlDate = new java.sql.Date(arquivoApontamento.getData().getTime());
		ps.setDate(1, sqlDate);
		ps.setInt(2, arquivoApontamento.getIdEmpresa());
		
		ps.execute();
		ps.close();
	}

	public void save(ArquivoApontamentoEntity arquivoApontamento) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		String sql = "INSERT INTO PAUSEArquivoApontamento VALUES (?,?,?,?,?)";
		
		PreparedStatement ps = conn.prepareStatement(sql);
		
		java.sql.Date data = new java.sql.Date(arquivoApontamento.getData().getTime());
		ps.setDate(1, data);
		ps.setString(2, arquivoApontamento.getCaminho());
		java.sql.Date dataInclusao = new java.sql.Date(arquivoApontamento.getDataInclusao().getTime());
		ps.setDate(3, dataInclusao);
		ps.setInt(4, arquivoApontamento.getIdEmpresa());
		ps.setInt(5, arquivoApontamento.getIdUsuarioInclusao());
		
		ps.execute();
		ps.close();
	}

	public Integer findByDateAndEmpresa(ArquivoApontamentoEntity arquivoApontamento) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		Integer idArquivo = null;
		String sql = "SELECT idArquivoApontamento FROM PAUSEArquivoApontamento WHERE data = ? AND idEmpresa = ?";

		PreparedStatement ps = conn.prepareStatement(sql);

		java.sql.Date sqlDate = new java.sql.Date(arquivoApontamento.getData().getTime());
		ps.setDate(1, sqlDate);
		ps.setInt(2, arquivoApontamento.getIdEmpresa());

		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			idArquivo = rs.getInt("idArquivoApontamento");
		}
		ps.execute();
		ps.close();
		return idArquivo;
	}
}
