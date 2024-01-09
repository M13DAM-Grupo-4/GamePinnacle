package m13dam.grupo4.gamepinnacle.Classes.Other;


import android.os.AsyncTask;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import m13dam.grupo4.gamepinnacle.BuildConfig;

public class MailManager extends AsyncTask<Void, Void, Boolean> {

    private String username; // Your email address
    private String password; // Your email password
    private String recipient; // Recipient's email address
    private String subject;   // Email subject
    private String message;   // Email content

    public MailManager(String username, String password, String recipient, String subject, String message) {
        this.username = username;
        this.password = password;
        this.recipient = recipient;
        this.subject = subject;
        this.message = message;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", BuildConfig.mailsmtphost);
            props.put("mail.smtp.port", "587");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(new InternetAddress(username));
            mimeMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            mimeMessage.setSubject(subject);
            mimeMessage.setText(message);
            InternetAddress recipientAddress = new InternetAddress(recipient);
            recipientAddress.setPersonal("Game Pinnacle"); // Set the recipient's name
            mimeMessage.setRecipient(Message.RecipientType.TO, recipientAddress);

            Transport.send(mimeMessage);

            return true;
        } catch (MessagingException e) {
            Log.e("SendMailTask", "Error sending email", e);
            return false;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void onPostExecute(Boolean success) {
        if (success) {
            Log.d("SendMailTask", "Email sent successfully!");
        } else {
            Log.e("SendMailTask", "Failed to send email");
        }
    }

}
