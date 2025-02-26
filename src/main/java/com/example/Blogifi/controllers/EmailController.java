package com.example.Blogifi.controllers;

import com.example.Blogifi.services.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    @Autowired
    EmailService emailService;

    @GetMapping("/email/sent")
    public ResponseEntity<String> sentEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body
    ){
        String discount = "50% Off";
        String product = "Premium Subscription";
        String offerLink = "https://github.com/RishiSetpal";
        String supportEmail = "rishisetpal123@gmail.com";
        String companyName = "Developer's Hub";

        body = getBody(discount, subject, product, offerLink, supportEmail, companyName);
        emailService.sendEmail(to, subject, body);
        return ResponseEntity.ok("Email sent Successfully to"+to);
    }

    public String getBody(String discount, String subject, String product, String offerLink, String supportEmail, String companyName) {
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
