package com.mindtrack.services.helpers;

import com.mindtrack.entity.Usuario;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String remetente;

    @Value("${spring.mail.username}")
    private String destinatario;

    public String enviaEmailCadastro(Usuario u, String url) {
        try {
            SimpleMailMessage simpleMessage = new SimpleMailMessage();
            simpleMessage.setFrom(remetente);
            simpleMessage.setTo(destinatario);
            simpleMessage.setSubject("Bem vindo(a) ao MindTrack");
            simpleMessage.setText("Caro(a)" + u.getNome() + " bem vindo à plataforma MindTrack. "
                    + "Cadastre sua senha através do link: " + url);
            javaMailSender.send(simpleMessage);
            return "Email enviado";
        }catch(Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }

    public String enviaEmailRecuperaSenha(String subject, String templateName, Map<String, Object> templateVariables) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            templateVariables.put("subject", subject);
            Context context = new Context();
            context.setVariables(templateVariables); // Define as variáveis para o template
            String htmlContent = templateEngine.process(templateName, context);
            helper.setTo(destinatario);
            helper.setFrom(remetente); // Configure seu e-mail de remetente
            helper.setSubject(subject);
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            //SimpleMailMessage simpleMessage = new SimpleMailMessage();
            //simpleMessage.setFrom(remetente);
            //simpleMessage.setTo(destinatario);
            //simpleMessage.setSubject("Recuperação de Senha");
            //simpleMessage.setText("Caro(a)" + user.getNome() + "Você requisitou a recuperação da senha para o email: "
            //        + user.getEmail()
            //        + "Cadastre sua senha através do link: " + resetLink);
            //javaMailSender.send(simpleMessage);

            return "Email enviado";
        }catch(Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
    }
    }
}
