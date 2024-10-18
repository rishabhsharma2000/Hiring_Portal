package com.credex.hiring.portal.serviceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.credex.hiring.portal.dto.EmailData;
import com.credex.hiring.portal.entities.CampusDrive;
import com.credex.hiring.portal.entities.Recruiters;
import com.credex.hiring.portal.entities.User;
import com.credex.hiring.portal.exception.UserNotFoundException;
import com.credex.hiring.portal.repository.CampusRepo;
import com.credex.hiring.portal.repository.RecruiterRepo;
import com.credex.hiring.portal.repository.UserRepository;
import com.credex.hiring.portal.service.CampusService;
import com.credex.hiring.portal.utility.Utility;

@Service
public class CampusServiceImpl implements CampusService {

  @Autowired
  private CampusRepo campusRepo;

  @Autowired
  private RecruiterRepo recruiterRepo;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private EmailService emailService;

  @Autowired
  private JavaMailSender emailSender;

  @Autowired
  private FreeMarkerConfigurer freemarkerConfig;

  @Override
  public List<CampusDrive> listAllCampus() {
    try {
      return campusRepo.findAll();
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public ResponseEntity<?> createCampus(CampusDrive drive) {
    try {
      campusRepo.save(drive);
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @Override
  public void beginExam(Long driveCode) {
    Optional<CampusDrive> drive = campusRepo.findById(driveCode);
    if (drive.isPresent()) {
      campusRepo.startExam(driveCode);
    } else {
      throw new RuntimeException("This Campus doesn't exist");
    }
  }

  @Override
  public void endExam(Long driveCode) {
    Optional<CampusDrive> drive = campusRepo.findById(driveCode);
    if (drive.isPresent()) {
      campusRepo.endExam(driveCode);
    } else {
      throw new RuntimeException("This Campus doesn't exist");
    }
  }

  @Override
  public Boolean getExamStatusFromCollegeCode(String code) {
    return campusRepo.getExamStatusByCollegeCode(code);
  }

  @Override
  public ResponseEntity<?> uploadJD(MultipartFile file, Long driveCode, String recipientName) {
    try {
      HashMap<String, String> map = new HashMap<>();
      Optional<CampusDrive> campusDrive = campusRepo.findById(driveCode);
      if (!campusDrive.isPresent()) {
        throw new RuntimeException("Campus Drive Code Not Found");
      }
      MimeMessage message = emailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
              StandardCharsets.UTF_8.name());
      map.put("EventDate", campusDrive.get().getDateOfCampus());
      map.put("EventTime", campusDrive.get().getExamStartTime());
      map.put("Venue", campusDrive.get().getCollegeId().getCollegeName());
      map.put("RecipientName", recipientName);
      String templateContent = FreeMarkerTemplateUtils
              .processTemplateIntoString(freemarkerConfig.getConfiguration().getTemplate("job.ftl"), map);
      message.setSubject("Campus Drive Details");
      helper.setTo(campusDrive.get().getCollegeId().getEmail());
      helper.setText(templateContent, true);
      InputStreamSource attachment = new ByteArrayResource(file.getBytes());
      helper.addAttachment(file.getOriginalFilename(), attachment);
      emailSender.send(message);
      return Utility.responseSuccess();
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }



  @Override
  public ResponseEntity<?> addRecruiterToCampusDrive(List<Recruiters> rec) {
    try {
      recruiterRepo.saveAll(rec); // Add commit or rollback
      for (Recruiters recruiters : rec) {

        EmailData email = new EmailData();
        Optional<User> user = userRepository.findById(recruiters.getRecruiterUserId().getId());
        Optional<CampusDrive> campus = campusRepo.findById(recruiters.getRecruiterCampusId().getDriveCode());
        if (!user.isPresent())
          throw new UserNotFoundException("User Not Found");
        if (!campus.isPresent())
          throw new RuntimeException("Campus Drive Doesn't Exist");
        email.setTo(user.get().getEmail());
        email.setSubject("Placement Drive Details");

        // Populate the template data
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("EventDate", campus.get().getDateOfCampus());
        templateData.put("EventTime", campus.get().getExamStartTime());
        email.setEmailData(templateData);

        emailService.sendMailRecruitmentTeamEmail(email);
      }
      return Utility.responseCreate(HttpStatus.CREATED);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }
}
