package com.mindtrack.services.helpers;

import com.mindtrack.entity.Usuario;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.HashMap;
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

    public String enviaEmailCadastro(String token, String templateName, Usuario user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String link = "http://localhost:4200/login/nova-senha?token=" + token;
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("emailTitle", "Bem vindo!");
        templateVariables.put("subject", "Bem vindo ao Mindtrack!");
        templateVariables.put("userName", user.getNome());
        templateVariables.put("bodyContent", "Seu cadastro esta quase completo, " +
                "acesse o linK abaixo para cadastrar sua senha. " +
                "Este link é válido por 24 horas após o recebimento desta mensagem!");
        templateVariables.put("linkUrl", link);
        templateVariables.put("linkText", "Cadastrar Senha");

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Context context = new Context();
            context.setVariables(templateVariables); // Define as variáveis para o template
            String htmlContent = templateEngine.process(templateName, context);
            helper.setTo(destinatario);
            helper.setFrom(remetente); // Configure seu e-mail de remetente
            helper.setSubject((String)templateVariables.get("subject"));
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            return "Email de cadastro enviado";
        }catch(Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }

    public String enviaEmailRecuperaSenha(String token, String templateName, Usuario user) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String link = "http://localhost:4200/login/nova-senha?token=" + token;
        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("emailTitle", "Recuperação de Senha!");
        templateVariables.put("subject", "Recuperação de Senha!");
        templateVariables.put("userName", user.getNome());
        templateVariables.put("bodyContent", "Recebemos uma solicitação para redefinir a senha da sua conta. " +
                "Se foi você, clique no botão abaixo para criar uma nova senha." +
                "Este link é válido por 3 horas após o recebimento desta mensagem!");
        templateVariables.put("linkUrl", link);
        templateVariables.put("linkText", "Redefinir minha senha");

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            Context context = new Context();
            context.setVariables(templateVariables); // Define as variáveis para o template
            String htmlContent = templateEngine.process(templateName, context);
            helper.setTo(destinatario);
            helper.setFrom(remetente); // Configure seu e-mail de remetente
            helper.setSubject((String)templateVariables.get("subject"));
            helper.setText(htmlContent, true);
            javaMailSender.send(mimeMessage);

            return "Email enviado";
        }catch(Exception e) {
            return "Erro ao enviar email" + e.getLocalizedMessage();
        }
    }
}
