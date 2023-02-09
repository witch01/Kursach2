package com.example.fitnessclub.controller;
import com.example.fitnessclub.models.City_services;
import com.example.fitnessclub.repo.City_servicesRepository;
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
@RequestMapping("/cityservices")
public class City_servicesController {
    @Autowired
    private City_servicesRepository city_servicesRepository;

    @GetMapping("/add")
    public String CityServicesAdds(City_services city_services, Model model)
    {
        return "city_services_add";
    }

    @PostMapping("/add")
    public String CityServicesAdd(@ModelAttribute("city_services") @Valid City_services city_services,
                          BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "city_services_add";
        }
        city_servicesRepository.save(city_services);
        return "redirect:/cityservices/view";
    }


    @GetMapping("/view")
    public String city_serviceMain(Model model) throws IOException {
        Iterable<City_services> city_services = city_servicesRepository.findAll();
        model.addAttribute("city_services", city_services);
        exportDataToExcel();
        return "city_services_view";
    }

    @PostMapping("/{id}/del")
    public String employeeDelete(@PathVariable("id") long id, Model model){
        City_services city_services = city_servicesRepository.findById(id).orElseThrow();
        city_servicesRepository.delete(city_services);
        return "redirect:/cityservices/view";
    }


    @GetMapping("/{id}/edit")
    public String cityservicesEdit(@PathVariable("id")long id,
                              Model model)
    {
        City_services city_services = city_servicesRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid posts Id" + id));
        model.addAttribute("city_services",city_services);

        return "city_service_edit";
    }

    @PostMapping("/{id}/edit")
    public String cityServicesUpdate(@ModelAttribute("city_services") @Valid City_services city_services, BindingResult bindingResult,
                                @PathVariable("id") long id) {

        city_services.setId(id);
        if (bindingResult.hasErrors()) {
            return "city_service_edit";
        }
        city_servicesRepository.save(city_services);
        return "redirect:/cityservices/view";
    }
    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM city_services");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("city_services");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("city_services.xlsx")) {
            workbook.write(outputStream);
        }
    }

}
