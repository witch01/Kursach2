package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.City_services;
import com.example.fitnessclub.models.Client;
import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.repo.ClientRepository;
import com.example.fitnessclub.repo.PostRepository;
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
@RequestMapping("/client")
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;

    @GetMapping("/add")
    public String ClientAdds(Client client, Model model)
    {
        return "client_add";
    }

    @PostMapping("/add")
    public String ClientAdd(@ModelAttribute("client") @Valid Client client,
                          BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "client_add";
        }
        clientRepository.save(client);
        return "redirect:/client/view";
    }

    @GetMapping("/view")
    public String clientMain(Model model) throws IOException {
        Iterable<Client> client = clientRepository.findAll();
        model.addAttribute("client", client);
        exportDataToExcel();
        return "client_view";
    }

    @GetMapping("/{id}/edit")
    public String clientEdit(@PathVariable("id")long id,
                                   Model model)
    {
        Client client = clientRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid posts Id" + id));
        model.addAttribute("client",client);

        return "client_edit";
    }

    @PostMapping("/{id}/edit")
    public String clientUpdate(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
                                     @PathVariable("id") long id) {

        client.setId(id);
        if (bindingResult.hasErrors()) {
            return "client_edit";
        }
        clientRepository.save(client);
        return "redirect:/client/view";
    }

    @PostMapping("/{id}/del")
    public String clientDelete(@PathVariable("id") long id, Model model){
        Client client = clientRepository.findById(id).orElseThrow();
        clientRepository.delete(client);
        return "redirect:/client/view";
    }

    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM client");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("client");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("client.xlsx")) {
            workbook.write(outputStream);
        }
    }
}
