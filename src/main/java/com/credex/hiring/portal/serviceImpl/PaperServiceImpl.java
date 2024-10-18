package com.credex.hiring.portal.serviceImpl;

import com.credex.hiring.portal.entities.Examination;
import com.credex.hiring.portal.entities.QuestionPaper;
import com.credex.hiring.portal.repository.ExamRepo;
import com.credex.hiring.portal.repository.PaperRepo;
import com.credex.hiring.portal.service.PaperService;
import com.credex.hiring.portal.utility.Utility;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperRepo paperRepo;

    @Autowired
    private ExamRepo examRepo;

    @Override
    public List<QuestionPaper> fetchAllPaper() {
        try {
            return paperRepo.findAll();
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity deletePaper(Long id) {
        try {
        	paperRepo.deleteQuestionByExamId(id);
        	examRepo.deleteById(id);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity updatePaper(Long id, QuestionPaper paper) {
        try {
            Optional<QuestionPaper> opt = paperRepo.findById(id);
            if (opt.isPresent()) {
                QuestionPaper paperToUpdate = opt.get();
                Utility.updateFields(paperToUpdate, paper, QuestionPaper.class);
                paperRepo.save(paperToUpdate);
                return new ResponseEntity("Data Update !!", HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity("paperId doesn't exist", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<String> insert(List<QuestionPaper> paper) {
        try {
            System.out.println(paper.toString());
            paperRepo.saveAll(paper);
            return Utility.responseCreate(HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity uploadFile(MultipartFile file, Long examId) {
        try {
            XSSFWorkbook workbook = new XSSFWorkbookFactory().create(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            List<QuestionPaper> listPaper = new ArrayList<>();
            // Iterate over rows
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                QuestionPaper paper = new QuestionPaper();
                // Iterate over columns
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    Cell cell = row.getCell(j);
                    String columnName = sheet.getRow(0).getCell(j).getStringCellValue();
                    switch(columnName) {
                        case "question":
                            paper.setQuestion(cell.getRichStringCellValue().getString());
                            break;
                        case "answer":
                            paper.setAnswer(cell.getRichStringCellValue().getString());
                            break;
                        case "choice1":
                            paper.setChoice1(cell.getRichStringCellValue().getString());
                            break;
                        case "choice2":
                            paper.setChoice2(cell.getRichStringCellValue().getString());
                            break;
                        case "choice3":
                            paper.setChoice3(cell.getRichStringCellValue().getString());
                            break;
                        case "choice4":
                            paper.setChoice4(cell.getRichStringCellValue().getString());
                            break;
                        default:
                            throw new RuntimeException("Unknown Column detected");
                    }
                }

                Examination exam = new Examination();
                exam.setExamId(examId);
                paper.setExamId(exam);
                listPaper.add(paper);
            }
            paperRepo.saveAll(listPaper);
            return new ResponseEntity("File Uploaded successfully !!!", HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<QuestionPaper> fetchPaperById(Long id) {
        try {
            return paperRepo.getRandomExam(id);
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }

    @Override
    public List<QuestionPaper> startExam() {
        try {
            return paperRepo.getRandomExam(examRepo.getRandomExamId());
        } catch (Exception e) {
            throw new RuntimeException(e.getLocalizedMessage());
        }
    }
}