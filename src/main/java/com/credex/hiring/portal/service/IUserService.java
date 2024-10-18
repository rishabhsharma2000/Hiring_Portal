package com.credex.hiring.portal.service;

import com.credex.hiring.portal.entities.Candidates;
import com.credex.hiring.portal.exception.UsersException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IUserService {
    Candidates upload(Candidates candidates, MultipartFile filePhoto, MultipartFile fileResume)
            throws UsersException, IOException;

    default boolean isCandidateEligibleForReopeningExam(String rollNumber) {
        return false;
    }
    public boolean isExamStarted(String rollNumber);
}
