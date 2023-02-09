package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.Client;
import com.example.fitnessclub.models.Customer_preferences;
import com.example.fitnessclub.models.Employee;
import com.example.fitnessclub.models.Plain_training;
import com.example.fitnessclub.repo.ClientRepository;
import com.example.fitnessclub.repo.Customer_preferencesRepository;
import com.example.fitnessclub.repo.EmployeeRepository;
import com.example.fitnessclub.repo.Plain_trainingRepository;
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
@RequestMapping("/customerpreferences")
public class Customer_preferencesController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private Customer_preferencesRepository customer_preferencesRepository;

    @GetMapping("/add")
    public String CustomerPreferencesAdd(@ModelAttribute("customer_preferences") Customer_preferences customer_preferences, Model model)
    {

        Iterable<Client> client = clientRepository.findAll();
        model.addAttribute("client", client);
        return "customer_preferences_add";
    }
    @PostMapping("/add")
    public String customerpreferencesPostAdd(@ModelAttribute("customer_preferences") @Valid Customer_preferences customer_preferences, BindingResult bindingResult, @RequestParam String phone,
                                       Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Client> client = clientRepository.findAll();
            model.addAttribute("client", client);
            return "customer_preferences_add";
        }
        customer_preferences.setClient(clientRepository.findByPhone(phone));
        customer_preferencesRepository.save(customer_preferences);
        return "redirect:/customerpreferences/view";
    }

    @GetMapping("/view")
    public String customerpreferencesMain(Model model) throws IOException {
        Iterable<Customer_preferences> customer_preferences = customer_preferencesRepository.findAll();
        model.addAttribute("customer_preferences", customer_preferences);
        exportDataToExcel();
        return "customer_preferences_view";
    }

    @PostMapping("/{id}/del")
    public String preferencesDelete(@PathVariable("id") long id, Model model){
        Customer_preferences customer_preferences = customer_preferencesRepository.findById(id).orElseThrow();
        customer_preferencesRepository.delete(customer_preferences);
        return "redirect:/customerpreferences/view";
    }

    @GetMapping("/{id}/edit")
    public String preferencesEdit(@PathVariable("id")long id,
                               Model model)
    {
        Iterable<Client> client = clientRepository.findAll();
        Customer_preferences customer_preferences = customer_preferencesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid students Id" + id));
        model.addAttribute("client", client);
        model.addAttribute("customer_preferences",customer_preferences);

        return "customer_preferences_view";
    }

    @PostMapping("/{id}/edit")
    public String preferencesUpdate(@ModelAttribute("customer_preferences") @Valid Customer_preferences customer_preferences,
                                    BindingResult bindingResult, @RequestParam String phone,
                                 @PathVariable("id") long id) {

        customer_preferences.setId(id);
        if (bindingResult.hasErrors()) {
            Iterable<Client> posts = clientRepository.findAll();
            return "customer_preferences_edit";
        }
        customer_preferences.setClient(clientRepository.findByPhone(phone));
        customer_preferencesRepository.save(customer_preferences);

        return "redirect:/customerpreferences/view";
    }

    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM customer_preferences");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("customer_preferences");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("customer_preferences.xlsx")) {
            workbook.write(outputStream);
        }
    }
}

