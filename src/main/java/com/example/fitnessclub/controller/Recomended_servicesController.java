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
@RequestMapping("/recomendedservices")
public class Recomended_servicesController {
    @Autowired
    private ServicesRepository servicesRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private Recomended_servicesRepository recomended_servicesRepository;

    @GetMapping("/add")
    public String RecomendedServicesAdd(@ModelAttribute("recomended_services") Recomended_services recomended_services, Model model)
    {

        Iterable<Client> client = clientRepository.findAll();
        Iterable<Services> services = servicesRepository.findAll();
        model.addAttribute("client", client);
        model.addAttribute("services", services);
        return "recomended_cervices_add";
    }
    @PostMapping("/add")
    public String recomendedservicesPostAdd(@ModelAttribute("recomended_services") @Valid Recomended_services recomended_services, BindingResult bindingResult, @RequestParam double cent,
                                     @RequestParam String phone,
                                     Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Services> services = servicesRepository.findAll();
            Iterable<Client> client = clientRepository.findAll();
            model.addAttribute("services", services);
            model.addAttribute("client", client);
            return "recomended_cervices_add";
        }
        recomended_services.setServices(servicesRepository.findByCent(cent));
        recomended_services.setClient(clientRepository.findByPhone(phone));
        recomended_servicesRepository.save(recomended_services);
        return "redirect:/recomendedservices/add";
    }

    @GetMapping("/view")
    public String recomendedservicesMain(Model model) throws IOException {
        Iterable<Recomended_services> recomended_services = recomended_servicesRepository.findAll();
        model.addAttribute("recomended_services", recomended_services);
        exportDataToExcel();
        return "recomended_services_view";
    }

    @Autowired
    private DataSource dataSource;
    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM recomended_services");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("recomended_services");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("recomended_services.xlsx")) {
            workbook.write(outputStream);
        }
    }

}

