package com.example.Blogifi.services;

import com.example.Blogifi.enteties.User;
import com.example.Blogifi.repositories.UserRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {
    @Autowired
    @Lazy
    private JavaMailSender javaMailSender;

    @Autowired
    @Lazy
    private UserRepository userRepository;

    @Value("spring.mail.username")
    private String email;

    public void sendEmail(String to, String subject, String body) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(body);
//        message.setFrom(email);
//        javaMailSender.send(message);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            body = getBody(subject);
            helper.setText(body, true); // 'true' enables HTML content
            helper.setFrom(email);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

//    @Scheduled(fixedRate = 5000) // Runs Every 5 Seconds
    @Scheduled(cron = "30 32 20 * * ?") // Runs at 8:32:30 PM daily
    @Async
    public void sendPromotionalEmails() {
        List<User> users = userRepository.findAll();
        users.forEach(user -> sendEmail(user.getEmail(), "Promotional Email", getBody("Promotional Email")));
    }

    public String getBody(String subject) {
        String discount = "50% Off";
        String product = "Premium Subscription";
        String offerLink = "https://github.com/RishiSetpal";
        String supportEmail = "rishisetpal123@gmail.com";
        String companyName = "Developer's Hub";
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>" + subject + "</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            background-color: #f4f4f4;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "        .container {\n" +
                "            width: 100%;\n" +
                "            max-width: 600px;\n" +
                "            margin: 20px auto;\n" +
                "            background: #ffffff;\n" +
                "            padding: 20px;\n" +
                "            border-radius: 8px;\n" +
                "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\n" +
                "            text-align: center;\n" +
                "        }\n" +
                "        .header {\n" +
                "            background: #0073e6;\n" +
                "            color: white;\n" +
                "            padding: 15px;\n" +
                "            font-size: 24px;\n" +
                "            border-radius: 8px 8px 0 0;\n" +
                "        }\n" +
                "        .content {\n" +
                "            padding: 20px;\n" +
                "            color: #333;\n" +
                "        }\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background: #0073e6;\n" +
                "            color: white;\n" +
                "            padding: 12px 20px;\n" +
                "            text-decoration: none;\n" +
                "            font-size: 18px;\n" +
                "            border-radius: 5px;\n" +
                "            margin-top: 20px;\n" +
                "        }\n" +
                "        .footer {\n" +
                "            margin-top: 20px;\n" +
                "            font-size: 14px;\n" +
                "            color: #666;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <div class=\"header\">" + subject + "</div>\n" +
                "        <div class=\"content\">\n" +
                "            <p>We have something special just for you! 🎉</p>\n" +
                "            <p>For a limited time, enjoy <strong>" + discount + "</strong> on <strong>" + product + "</strong>. Don’t miss this exclusive deal!</p>\n" +
                "            <a href=\"" + offerLink + "\" class=\"button\">Claim Your Offer Now</a>\n" +
                "        </div>\n" +
                "        <div class=\"footer\">\n" +
                "            <p>Need help? Contact us at <a href=\"mailto:" + supportEmail + "\">" + supportEmail + "</a></p>\n" +
                "            <p>&copy; 2025 " + companyName + ". All Rights Reserved.</p>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</body>\n" +
                "</html>";
    }
}
