package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.*;
import com.example.fitnessclub.repo.*;
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
@RequestMapping("/plaintraining")
public class Plain_trainingController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private Plain_trainingRepository plain_trainingRepository;

    @GetMapping("/add")
    public String PlainTrainingAdd(@ModelAttribute("plain_training") Plain_training plain_training, Model model)
    {

        Iterable<Employee> employee = employeeRepository.findAll();
        Iterable<Client> client = clientRepository.findAll();
        model.addAttribute("employee", employee);
        model.addAttribute("client", client);
        return "plain_training_add";
    }
    @PostMapping("/add")
    public String plainTrainingPostAdd(@ModelAttribute("plain_training") @Valid Plain_training plain_training, BindingResult bindingResult, @RequestParam String surname,
                                     @RequestParam String phone,
                                     Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Employee> employee = employeeRepository.findAll();
            Iterable<Client> client = clientRepository.findAll();
            model.addAttribute("employee", employee);
            model.addAttribute("client", client);
            return "plain_training_add";
        }
        plain_training.setEmployee(employeeRepository.findBySurname(surname));
        plain_training.setClient(clientRepository.findByPhone(phone));
        plain_trainingRepository.save(plain_training);
        return "redirect:/plaintraining/view";
    }

    @GetMapping("/view")
    public String plainrainingMain(Model model) throws IOException {
        Iterable<Plain_training> plain_training = plain_trainingRepository.findAll();
        model.addAttribute("plain_training", plain_training);
        exportDataToExcel();
        return "plain_training_view";
    }

    @PostMapping("/{id}/del")
    public String plainDelete(@PathVariable("id") long id, Model model){
        Plain_training plain_training = plain_trainingRepository.findById(id).orElseThrow();
        plain_trainingRepository.delete(plain_training);
        return "redirect:/plaintraining/view";
    }

    @GetMapping("/{id}/edit")
    public String plainEdit(@PathVariable("id")long id,
                               Model model)
    {
        Iterable<Employee> employee = employeeRepository.findAll();
        Iterable<Client> client = clientRepository.findAll();
        Plain_training plain_training = plain_trainingRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid students Id" + id));
        model.addAttribute("client", client);
        model.addAttribute("employee",employee);
        model.addAttribute("plain_training", plain_training);
        return "plain_training_edit";
    }

    @PostMapping("/{id}/edit")
    public String plainUpdate(@ModelAttribute("plain_training") @Valid Plain_training plain_training,
                                 BindingResult bindingResult, @RequestParam String phone, @RequestParam String surname,
                                 @PathVariable("id") long id) {

        plain_training.setId(id);
        if (bindingResult.hasErrors()) {
            Iterable<Client> client = clientRepository.findAll();
            Iterable<Employee> employee = employeeRepository.findAll();
            return "plain_training_edit";
        }
        plain_training.setClient(clientRepository.findByPhone(phone));
        plain_training.setEmployee(employeeRepository.findBySurname(surname));
        plain_trainingRepository.save(plain_training);

        return "redirect:/plaintraining/view";
    }

    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM plain_training");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("plain_training");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("plain_training.xlsx")) {
            workbook.write(outputStream);
        }
    }
}
