package br.com.verity.pause.business;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.verity.pause.bean.ApontamentosPendentesBean;
import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.UsuarioBean;
import br.com.verity.pause.dao.ApontamentosPendentesDAO;
import br.com.verity.pause.entity.ApontamentosPendentesEntity;
import br.com.verity.pause.util.EnviarEmailUtil;

@Service
public class ApontamentosPendentesBusiness {

	@Autowired
	private CustomUserDetailsBusiness userBusiness;

	@Autowired
	private ApontamentosPendentesDAO pendentesDAO;

	@Autowired
	private FuncionarioBusiness funcionarioBusiness;

	public List<ApontamentosPendentesBean> obterApontamentosPendentes() throws SQLException {

		List<ApontamentosPendentesBean> pendentes = new ArrayList<ApontamentosPendentesBean>();
		UsuarioBean usuarioLogado = null;
		usuarioLogado = userBusiness.usuarioLogado();
		int idEmpresa = usuarioLogado.getIdEmpresaSessao();

		for (ApontamentosPendentesEntity a : pendentesDAO.findPendentes(idEmpresa)) {
			FuncionarioBean f = funcionarioBusiness.obterPorId(a.getIdFuncionario());
			if (a.getId() != null && f.getNome() != null)
				pendentes.add(new ApontamentosPendentesBean(a.getId(), a.getData(), a.getIdEmpresa(),
						a.getIdFuncionario(), a.getDiasSemApontar(), f.getNome(), f.getEmailCorporativo()));
		}
		return pendentes;
	}

	public void enviarEmailFuncionarioPendente(int idFuncionarioDestinatario) {
		String remetente, assunto, mensagem, nomeDestinatario;

		FuncionarioBean fDestinatario = funcionarioBusiness.obterPorId(idFuncionarioDestinatario);

		remetente = "pause@verity.com.br";
		nomeDestinatario = fDestinatario.getNome();
		assunto = "Apontamentos pendentes no sistema Pause";
		mensagem = "<table><tr><td colspan='2'>Ol&aacute;, " + nomeDestinatario + "!<br/><br/>"
				+ "Voc&ecirc; tem apontamentos pendentes no sistema Pause.<br/> "
				+ "Por favor, regularize-os assim que poss&iacute;vel.<br/><br/>Atenciosamente,<br/><br/><td></tr>"
				+ "<tr><td><a href=\"http://www.verity.com.br/pause\" target=\"_blank\">"
				+ "<img src=\"https://verity.com.br/pause/img/logo/LOGO.png\" width=112 height=118></a>"
				+ "</td><td><table></div><tr><div><b><font color='#01B2CD'>Equipe Pause</font></b></div></tr><tr>"
				+ "<div><font color='#01B2CD'>Email: </font><a href=\"mailto:pause@verity.com.br\" style='color:#AAAAAA;' target=\"_blank\">pause@verity.com.br</a></div></tr>"
				+ "<tr><div><font color='#01B2CD'>Url: </font><font color='#AAAAAA'><a href=\"http://www.verity.com.br/pause\" style='color:#AAAAAA;' target=\"_blank\">www.verity.com.br/pause</a></font></div></tr></div></table></tr></table>"
				+ "<br/><table border='0' cellspacing='0' width='100%'><tr><td width='410'><div style='font-size: 13; text-align: justify;'><font color='#01B2CD'>Aviso legal:</font><font color='#AAAAAA'> A informa&ccedil;&atilde;o contida nesta mensagem de e-mail, incluindo seus anexos, tem car&aacute;ter confidencial"
				+ " e est&aacute; reservada apenas ao destinat&aacute;rio. Se voc&ecirc; n&atilde;o &eacute; o destinat&aacute;rio ou a pessoa respons&aacute;vel por encaminhar esta mensagem ao destinat&aacute;rio,"
				+ " voc&ecirc; est&aacute;, por meio desta, notificado que n&atilde;o dever&aacute; replicar ou disseminar o conte&uacute;do desta mensagem ou parte dela, sob qualquer meio, sendo"
				+ " expressamente proibido. Caso tenha recebido esta mensagem por engano, por favor, contate o remetente imediatamente e apague-a de seus arquivos. Muito obrigado.</font></td><td></td></tr></table>";

		EnviarEmailUtil.enviarEmailHtml(fDestinatario.getEmailCorporativo(), remetente, assunto, mensagem, nomeDestinatario);
	}
}
