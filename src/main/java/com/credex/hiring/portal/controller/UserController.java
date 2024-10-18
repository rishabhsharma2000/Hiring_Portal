package com.credex.hiring.portal.controller;

import com.credex.hiring.portal.config.Endpoints;
import com.credex.hiring.portal.dto.EmailData;
import com.credex.hiring.portal.dto.JwtRequest;
import com.credex.hiring.portal.dto.JwtResponse;
import com.credex.hiring.portal.dto.PasswordChangedData;
import com.credex.hiring.portal.entities.User;
import com.credex.hiring.portal.exception.UserNotFoundException;
import com.credex.hiring.portal.repository.UserRepository;
import com.credex.hiring.portal.security.JwtTokenUtil;
import com.credex.hiring.portal.security.JwtUserDetailsService;
import com.credex.hiring.portal.serviceImpl.EmailService;
import com.credex.hiring.portal.utility.Constants;
import com.credex.hiring.portal.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/users")
@CrossOrigin(origins = Endpoints.CORS_ORIGIN)
public class UserController {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository repo;
  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private PasswordEncoder bcryptEncoder;

  @Autowired
  private EmailService emailService;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @PostMapping("/process_register")
  public ResponseEntity<?> processRegistration(@RequestBody User user) {
    String originalPass = user.getPassword();
    user.setPassword(bcryptEncoder.encode(user.getPassword()));
    // template if user then Template:email-template1.ftl
    repo.save(user);
    EmailData email = new EmailData();

    email.setTo(user.getEmail());
    email.setSubject("Login Details");

    // Populate the template data
    Map<String, Object> templateData = new HashMap<>();
    templateData.put("Username", user.getEmail());
    templateData.put("Password", originalPass);
    email.setEmailData(templateData);

    // Calling email service
    //emailService.sendUserNamePassEmail(email);
    return Utility.responseCreate(HttpStatus.CREATED);
  }

  @GetMapping("/{id}")
  public User getUserById(@PathVariable("id") Long id) {
    Optional<User> user = repo.findById(id);
    if (user.isPresent()) {
      return user.get();
    }
    throw new RuntimeException("User Doesn't Exist");
  }

  @GetMapping("/list_users")
  public List<User> viewUsersList() {
    List<User> listUsers = repo.findAll();
    return listUsers;
  }

  @GetMapping(path = "/current-user")
  public Integer getCurrentUsersId(@RequestParam("email") String email) {
    return repo.getUserId(email);
  }

  @RequestMapping(value = "/login", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

    authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());

    final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

    Optional<User> user = repo.findByEmail(authenticationRequest.getEmail());
    if (user.isEmpty()) {
      throw new UsernameNotFoundException("User not found with username: " + authenticationRequest.getEmail());
    }
    final String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(JwtResponse.builder()
        .jwttoken("Bearer " + token)
        .userId(Math.toIntExact(user.get().getId()))
        .role(userDetails.getAuthorities().toString())
        .build());
  }

  private void authenticate(String email, String password) throws Exception {
    try {
      authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
    } catch (DisabledException e) {
      throw new DisabledException("USER_DISABLED", e);
    } catch (BadCredentialsException e) {
      throw new BadCredentialsException("INVALID_CREDENTIALS", e);
    }
  }

  @PostMapping("/changedPassword")
  public ResponseEntity<?> changedPassword(@RequestBody PasswordChangedData passwordChangedData) {
    String response = "";
    User userdb = null;
    Optional<User> user = repo.findByEmail(passwordChangedData.getEmail());
    if (user.isPresent()) {
      userdb = user.get();
      try {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(passwordChangedData.getEmail(),
            passwordChangedData.getOldPassword()));
      } catch (DisabledException e) {

        response = "old password is not correct";

        // throw new DisabledException("USER_DISABLED", e);
      } catch (BadCredentialsException e) {
        response = "old password is not correct";
        // throw new BadCredentialsException("INVALID_CREDENTIALS", e);
      }
      String newencodepass = bcryptEncoder.encode(passwordChangedData.getNewPassword());
      userdb.setPassword(newencodepass);
      repo.save(userdb);
      try {

        EmailData email = new EmailData();

        email.setTo(passwordChangedData.getEmail());
        email.setSubject("password change succesfully");

        // Populate the template data
        Map<String, Object> templateData = new HashMap<>();
        templateData.put("Username", passwordChangedData.getEmail());

        templateData.put("Password", passwordChangedData.getNewPassword());
        email.setEmailData(templateData);

        // Calling email service
        emailService.sendUserNamePassEmail(email);
      } catch (Exception e) {

      }
      response = "password changed successfully ";

    } else {
      response = "user not found=>" + passwordChangedData.getEmail();
    }
    return ResponseEntity.ok(response);
  }

  @PostMapping(path = "/forget-password")
  public ResponseEntity<?> forgetPassword(@RequestParam("newPassword") String password,
      @RequestParam("email") String email) {
    try {
      Optional<User> user = repo.findByEmail(email);
      if (!user.isPresent())
        throw new UserNotFoundException("User Doesn't Exist");
      user.get().setPassword(bcryptEncoder.encode(password));
      repo.save(user.get());
      return new ResponseEntity<Object>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }
  }

  @PostMapping(path = "/send-otp", produces = Constants.APPLICATION_JSON)
  public ResponseEntity<?> sendOtp(@RequestParam("email") String email) {
    try {
      Optional<User> user = repo.findByEmail(email);
      if (!user.isPresent())
        throw new UserNotFoundException("User doesn't exist");

      return emailService.sendOtp(user.get());
    } catch (Exception e) {
      throw new RuntimeException(e.getLocalizedMessage());
    }

  }

}
