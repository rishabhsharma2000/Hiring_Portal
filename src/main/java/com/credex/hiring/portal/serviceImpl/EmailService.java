package com.credex.hiring.portal.serviceImpl;

import com.credex.hiring.portal.dto.EmailData;
import com.credex.hiring.portal.entities.User;
import com.credex.hiring.portal.utility.Constants;
import com.credex.hiring.portal.utility.ResourceProperties;
import com.credex.hiring.portal.utility.Utility;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Service
public class EmailService implements Constants {

  @Autowired
  private JavaMailSender mailSender;
  @Autowired
  // for making template
  private FreeMarkerConfigurer freemarkerConfig;

  // The email content is generated based on the provided EmailData object.
  public void sendUserNamePassEmail(EmailData emailDTO) {

    MimeMessage mimeMessage = mailSender.createMimeMessage();
    try {

      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());

      String templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(
          freemarkerConfig.getConfiguration().getTemplate("email-template1.ftl"), emailDTO.getEmailData());

      helper.setFrom(ResourceProperties.DEFAULT_SENDER_MAIL);
      helper.setTo(emailDTO.getTo());
      helper.setSubject(emailDTO.getSubject());
      helper.setText(templateContent, true);
      mailSender.send(mimeMessage);

    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  public void sendMailRecruitmentTeamEmail(EmailData emailDTO) {
    MimeMessage mimeMessage = mailSender.createMimeMessage();
    try {

      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());

      String templateContent = FreeMarkerTemplateUtils.processTemplateIntoString(
          freemarkerConfig.getConfiguration().getTemplate("recruiters.ftl"), emailDTO.getEmailData());

      helper.setTo(emailDTO.getTo());
      helper.setSubject(emailDTO.getSubject());
      helper.setText(templateContent, true);
      mailSender.send(mimeMessage);

    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  public ResponseEntity<String> sendOtp(User user) {
    try {
      String otp = Utility.generateOtp();
      HashMap<String, String> emailData = new HashMap<>();
      emailData.put("OTP", otp);

      String templateContent = FreeMarkerTemplateUtils
          .processTemplateIntoString(freemarkerConfig.getConfiguration().getTemplate("forgotpass.ftl"), emailData);

      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
          StandardCharsets.UTF_8.name());

      helper.setTo(user.getEmail());
      helper.setSubject("Forget password");
      helper.setText(templateContent, true);
      mailSender.send(mimeMessage);

      JSONObject json = new JSONObject();
      json.put("OTP", otp);
      return new ResponseEntity<String>(json.toString(), HttpStatus.OK);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }

  }

}
