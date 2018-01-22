package br.com.verity.pause.util;

import java.util.Properties;
import java.io.UnsupportedEncodingException;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public final class EnviarEmailUtil {

	public static void enviarEmailHtml(String destinatario, String remetente, String assunto, String corpoHtml,
			String nomeRemetente) {

		try {
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "outlook.office365.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props, new EmailAuth());
			Message msg = new MimeMessage(session);

			InternetAddress from = new InternetAddress(remetente, "Pause - Verity");
			msg.setFrom(from);

			InternetAddress toAddress = new InternetAddress(destinatario);

			msg.setRecipient(Message.RecipientType.TO, toAddress);
			msg.setSubject(assunto);
			msg.setContent(corpoHtml, "text/html");

			Transport.send(msg);
		} catch (UnsupportedEncodingException ex) {
			ex.printStackTrace();

		} catch (MessagingException ex) {
			ex.printStackTrace();
		}
	}

	static class EmailAuth extends Authenticator {

		@Override
		protected PasswordAuthentication getPasswordAuthentication() {

			return new PasswordAuthentication("pause@verity.com.br", "apontamento@123");

		}
	}
}