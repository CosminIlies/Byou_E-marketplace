package org.example.handmademarketplace.Email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class EmailService {

    private SendGrid sendGrid;

    @Autowired
    public EmailService(SendGrid sendGrid){
        this.sendGrid = sendGrid;
    }


    public void sendEmail(String to, String subject, String body){
        Email fromEmail = new Email("cosminilies1234@gmail.com");
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);


        Mail mail = new Mail(fromEmail, subject, toEmail, content);

        sendRequestMail(mail);
//        Request req = new Request();
//
//        try{
//            req.setMethod(Method.POST);
//            req.setEndpoint("mail/send");
//            req.setBody(mail.build());
//
//            Response resp = sendGrid.api(req);
//
//            System.out.println(resp.getStatusCode());
//            System.out.println(resp.getBody());
//            System.out.println(resp.getHeaders());
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }


    public void sendDynamicEmail(String to, String subject, String templateId, Personalization personalization){
        Email fromEmail = new Email("cosminilies1234@gmail.com");
        Email toEmail = new Email(to);

        personalization.addTo(toEmail);
        personalization.addDynamicTemplateData("subject", subject);

        Mail mail = new Mail();
        mail.setFrom(fromEmail);
        mail.setTemplateId(templateId);
        mail.addPersonalization(personalization);

        sendRequestMail(mail);

    }

    private void sendRequestMail(Mail mail){
        Request req = new Request();

        try{
            req.setMethod(Method.POST);
            req.setEndpoint("mail/send");
            req.setBody(mail.build());

            Response resp = sendGrid.api(req);

            System.out.println(resp.getStatusCode());
            System.out.println(resp.getBody());
            System.out.println(resp.getHeaders());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
