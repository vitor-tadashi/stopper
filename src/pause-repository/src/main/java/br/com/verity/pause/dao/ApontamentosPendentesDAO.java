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
import br.com.verity.pause.entity.ApontamentosPendentesEntity;

@Repository
public class ApontamentosPendentesDAO {

	@Autowired
	private ConnectionFactory connectionFactory;

	public List<ApontamentosPendentesEntity> findPendentes(int idEmpresa) throws SQLException {
		Connection conn = connectionFactory.createConnection();
		StringBuilder sql = new StringBuilder();
		sql.append(" SELECT A.idApontamento, A.data, A.idEmpresa, CM.idFuncionario ");
		sql.append("	FROM PAUSEApontamento A ");
		sql.append("	INNER JOIN PAUSEControleDiario CD ON A.idControleDiario = CD.idControleDiario ");
		sql.append("	INNER JOIN PAUSEControleMensal CM ON CD.idControleMensal = CM.idControleMensal ");
		sql.append(" WHERE DATEDIFF(day, A.data, CURRENT_TIMESTAMP) >= 3 ");
		sql.append("	AND A.idApontamento = (SELECT MAX(AP.idApontamento) FROM PAUSEApontamento AP ");
		sql.append("		INNER JOIN PAUSEControleDiario CDS ON AP.idControleDiario = CDS.idControleDiario ");
		sql.append("		INNER JOIN PAUSEControleMensal CMS ON CDS.idControleMensal = CMS.idControleMensal ");
		sql.append("		WHERE CMS.idFuncionario = CM.idFuncionario) AND A.idEmpresa = ? ");
		sql.append(" ORDER BY A.data ASC");

		List<ApontamentosPendentesEntity> pendentes = new ArrayList<ApontamentosPendentesEntity>();

		PreparedStatement ps = conn.prepareStatement(sql.toString());

		java.sql.Date agora = new java.sql.Date(new java.util.Date().getTime());
		ps.setInt(1, idEmpresa);

		// AQUI
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			int diasSemApontar;
			long dif = Math.abs(agora.getTime() - rs.getDate("data").getTime());
			dif /= (24 * 60 * 60 * 1000);

			diasSemApontar = (int) dif;

			pendentes.add(new ApontamentosPendentesEntity(rs.getInt("idApontamento"), rs.getDate("data"),
					rs.getInt("idEmpresa"), rs.getInt("idFuncionario"), diasSemApontar));
		}
		ps.execute();
		ps.close();
		return pendentes;
	}
}