package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.City_services;
import com.example.fitnessclub.models.Employee;
import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.models.Services;
import com.example.fitnessclub.repo.City_servicesRepository;
import com.example.fitnessclub.repo.EmployeeRepository;
import com.example.fitnessclub.repo.PostRepository;
import com.example.fitnessclub.repo.ServicesRepository;
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
@RequestMapping("/services")
public class ServicesController {
    @Autowired
    private City_servicesRepository city_servicesRepository;
    @Autowired
    private ServicesRepository servicesRepository;

    @GetMapping("/add")
    public String ServicesAdd(@ModelAttribute("services") Services services, Model model)
    {

        Iterable<City_services> city_services = city_servicesRepository.findAll();
        model.addAttribute("city_services", city_services);
        return "services_add";
    }
    @PostMapping("/add")
    public String servicesPostAdd(@ModelAttribute("services") @Valid Services services, BindingResult bindingResult, @RequestParam String name,
                                  Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<City_services> city_services = city_servicesRepository.findAll();
            model.addAttribute("city_services", city_services);
            return "services_add";
        }
        services.setCity_services(city_servicesRepository.findByName(name));
        servicesRepository.save(services);
        return "redirect:/services/view";
    }

    @GetMapping("/view")
    public String servicesMain(Model model) throws IOException {
        Iterable<Services> services = servicesRepository.findAll();
        model.addAttribute("services", services);
        exportDataToExcel();
        return "services_view";
    }

    @Autowired
    private DataSource dataSource;
    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM services");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("services");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("services.xlsx")) {
            workbook.write(outputStream);
        }
    }
}
