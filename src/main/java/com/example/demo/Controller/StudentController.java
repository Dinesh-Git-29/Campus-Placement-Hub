package com.example.demo.Controller;

import com.example.demo.Model.Profile;
import com.example.demo.Model.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.ProfileService;
import com.example.demo.Service.UserService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class StudentController {

    @Autowired
    private ProfileService studentService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/download-eligible-students")
    public ResponseEntity<byte[]> downloadEligibleStudents(
            @RequestParam double minTenth,
            @RequestParam double minTwelfth,
            @RequestParam double minCGPA,
            @RequestParam int maxArrears,
            @RequestParam String arrearHistory) {

        List<Profile> students = studentService.getEligibleStudents(minTenth, minTwelfth, minCGPA, maxArrears, arrearHistory);

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Students");

            Row headerRow = sheet.createRow(0);
            String[] columns = {"Name", "Email","Phone" ,"Department","10th Percent", "12th Percent", "CGPA", "Arrears", "No History of Arrears"};
            for (int i = 0; i < columns.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(columns[i]);
            }

            int rowNum = 1;
            for (Profile student : students) {
                User email=userRepository.getByEmail(student.getEmail());
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(email.getName());
                row.createCell(1).setCellValue(student.getEmail());
                row.createCell(2).setCellValue(email.getPhone());
                row.createCell(3).setCellValue(student.getDepartment());
                row.createCell(4).setCellValue(student.getTenthPercent());
                row.createCell(5).setCellValue(student.getTwelvePercent());
                row.createCell(6).setCellValue(student.getCgpa());
                row.createCell(7).setCellValue(student.getArrears());
                row.createCell(8).setCellValue(student.getArrearHistory());
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);
            byte[] excelBytes = outputStream.toByteArray();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=students.xlsx");

            return new ResponseEntity<>(excelBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
