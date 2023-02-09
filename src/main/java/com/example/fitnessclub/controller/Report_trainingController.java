package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.*;
import com.example.fitnessclub.repo.ClientRepository;
import com.example.fitnessclub.repo.EmployeeRepository;
import com.example.fitnessclub.repo.Plain_trainingRepository;
import com.example.fitnessclub.repo.Report_trainingRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import javax.validation.Valid;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/reporttraining")
public class Report_trainingController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private Report_trainingRepository report_trainingRepository;

    @GetMapping("/add")
    public String ReportrainingAdd(@ModelAttribute("report_training") Report_training report_training, Model model)
    {

        Iterable<Employee> employee = employeeRepository.findAll();
        model.addAttribute("employee", employee);
        return "report_training_add";
    }
    @PostMapping("/add")
    public String reportTrainingPostAdd(@ModelAttribute("report_training") @Valid Report_training report_training, BindingResult bindingResult, @RequestParam String surname,
                                       Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Employee> employee = employeeRepository.findAll();
            model.addAttribute("employee", employee);
            return "report_training_add";
        }
        report_training.setEmployee(employeeRepository.findBySurname(surname));
        report_trainingRepository.save(report_training);
        return "redirect:/reporttraining/add";
    }

    @GetMapping("/view")
    public String reporttrainingMain(Model model) throws IOException {
        Iterable<Report_training> report_training = report_trainingRepository.findAll();
        model.addAttribute("report_training", report_training);
        exportDataToExcel();
        return "report_training_view";
    }

    @Autowired
    private DataSource dataSource;
    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM report_training");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("report_training");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("report_training.xlsx")) {
            workbook.write(outputStream);
        }
    }

}
