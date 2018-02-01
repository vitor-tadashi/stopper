package br.com.verity.pause.util;

import java.text.SimpleDateFormat;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.verity.pause.bean.FuncionarioBean;
import br.com.verity.pause.bean.ProjetoBean;
import br.com.verity.pause.entity.DespesaEntity;
import br.com.verity.pause.enumeration.TipoEmail;

@Component
public class SendEmail implements Runnable {

	@Autowired
	private Environment ambiente;

	private String destinatario;
	private TipoEmail tipoEmail;
	private ProjetoBean projeto;
	private DespesaEntity entity;
	private FuncionarioBean funcionario;
	
	public SendEmail(){
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public TipoEmail getTipoEmail() {
		return tipoEmail;
	}

	public void setTipoEmail(TipoEmail tipoEmail) {
		this.tipoEmail = tipoEmail;
	}

	public DespesaEntity getEntity() {
		return entity;
	}

	public void setEntity(DespesaEntity entity) {
		this.entity = entity;
	}
	
	public ProjetoBean getProjeto() {
		return projeto;
	}

	public void setProjeto(ProjetoBean projeto) {
		this.projeto = projeto;
	}

	public FuncionarioBean getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(FuncionarioBean funcionario) {
		this.funcionario = funcionario;
	}

	@Override
	public void run() {
		try {
			SimpleDateFormat spf = new SimpleDateFormat("dd/MM/yyyy");
			
			String emailCorpo = tipoEmail.getCorpo().replace("#valor", "R$ " + entity.getValor().toString().replace(".", ","))
					.replace("#projeto", projeto.getNome())
					.replace("#data", spf.format(entity.getDataOcorrencia()))
					.replace("#tipo", entity.getTipoDespesa().getNome())
					.replace("#descricao", entity.getJustificativa())
					.replace("#nome", funcionario.getNome());
					
					if(tipoEmail.equals(TipoEmail.REPROVADO)){
						emailCorpo = emailCorpo.replace("#justificativaReject", entity.getJustRejeicao());
					}
			
			HtmlEmail email = new HtmlEmail();
			email.setHostName(ambiente.getProperty("email.authentication.host"));
			email.setSmtpPort(Integer.valueOf(ambiente.getProperty("email.authentication.port")));
			email.setStartTLSEnabled(true);
			email.setAuthentication(ambiente.getProperty("email.authentication.login"),
					ambiente.getProperty("email.authentication.password"));
			email.setCharset("UTF-8");
			email.addTo(destinatario);
			email.setFrom(ambiente.getProperty("email.authentication.login"));
			email.setSubject(tipoEmail.getTitulo());
			email.setHtmlMsg(emailCorpo);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
