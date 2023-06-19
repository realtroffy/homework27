package by.epam.training.homework27.service.impl;

import by.epam.training.homework27.dto.OrderDto;
import by.epam.training.homework27.model.User;
import by.epam.training.homework27.service.EmailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    public EmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
        this.templateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(User user, OrderDto orderDto) throws MessagingException {
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("order", orderDto);

        String process = templateEngine.process("order", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setSubject("Welcome to online-shop " + user.getUsername());
        helper.setText(process, true);
        helper.setTo(user.getUsername());
        javaMailSender.send(mimeMessage);
    }
}
