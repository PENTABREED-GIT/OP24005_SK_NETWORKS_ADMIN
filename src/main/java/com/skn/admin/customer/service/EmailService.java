package com.skn.admin.customer.service;

import com.skn.admin.customer.dto.Inquiry;
import com.skn.admin.customer.dto.request.InquirySearchParam;
import com.skn.admin.customer.mapper.InquiryMapper;
import com.skn.admin.file.dto.FileInfo;
import com.skn.admin.file.service.FileInfoService;
import com.skn.admin.util.NTResult;
import io.swagger.v3.oas.models.examples.Example;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    private final InquiryMapper mapper;
    private final FileInfoService fileInfoService;
    private final Environment env;
    private final JavaMailSender javaMailSender;
    private static final Logger logger = LoggerFactory.getLogger(Example.class);

    @Value("${skn.mail.send.address}")
    private String replyAddress;


    /**
     * Utility method to send simple HTML email
     * @param toEmail
     * @param subject
     * @param body
     */
    public NTResult sendEmail(String toEmail, String subject, String body) {
        logger.debug("START sendEmail SERVICE  :::::::::::::::::::::::::::");
        NTResult ntResult = new NTResult();
        try {
            if ("local".equals(env.getProperty("spring.profiles.active"))) {
                System.out.println("Email not sent: Environment URL is localhost");
                ntResult.setSuccess();
                return ntResult;
            }

            logger.debug("SimpleEmail Start");

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setFrom(replyAddress);
            helper.setText(body, false);

            javaMailSender.send(message);

            logger.debug("EMail Sent Successfully!!");
            System.out.println("EMail Sent Successfully!!");
            ntResult.setSuccess();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ntResult;
    }
}
