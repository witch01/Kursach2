package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.*;
import com.example.fitnessclub.repo.EmployeeRepository;
import com.example.fitnessclub.repo.PostRepository;
import com.example.fitnessclub.repo.ServicesRepository;
import com.example.fitnessclub.repo.Work_sheduleRepository;
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
@RequestMapping("/workshedule")
public class Work_sheduleController {
    @Autowired
    private Work_sheduleRepository work_sheduleRepository;
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/add")
    public String WorkSheduleAdd(@ModelAttribute("work_shedule") Work_shedule work_shedule, Model model)
    {

        Iterable<Services> services = servicesRepository.findAll();
        Iterable<Employee> employee = employeeRepository.findAll();
        model.addAttribute("services", services);
        model.addAttribute("employee", employee);
        return "work_shedule_add";
    }
    @PostMapping("/add")
    public String workshedulePostAdd(@ModelAttribute("work_shedule") @Valid Work_shedule work_shedule, BindingResult bindingResult, @RequestParam double cent,
                                     @RequestParam String surname,
                                  Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Services> services = servicesRepository.findAll();
            Iterable<Employee> employee = employeeRepository.findAll();
            model.addAttribute("services", services);
            model.addAttribute("employee", employee);
            return "work_shedule_add";
        }
        work_shedule.setServices(servicesRepository.findByCent(cent));
        work_shedule.setEmployee(employeeRepository.findBySurname(surname));
        work_sheduleRepository.save(work_shedule);
        return "redirect:/workshedule/add";
    }

    @GetMapping("/view")
    public String worksheduleMain(Model model) throws IOException {
        Iterable<Work_shedule> work_shedule = work_sheduleRepository.findAll();
        model.addAttribute("work_shedule", work_shedule);
        exportDataToExcel();
        return "work_shedule_view";
    }

    @Autowired
    private DataSource dataSource;
    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM work_shedule");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("work_shedule");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("work_shedule.xlsx")) {
            workbook.write(outputStream);
        }
    }

}
