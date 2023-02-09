package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.*;
import com.example.fitnessclub.repo.City_servicesRepository;
import com.example.fitnessclub.repo.ClientRepository;
import com.example.fitnessclub.repo.ServicesRepository;
import com.example.fitnessclub.repo.TicketRepository;
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
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/add")
    public String TicketAdd(@ModelAttribute("ticket") Ticket ticket, Model model)
    {

        Iterable<Client> client = clientRepository.findAll();
        model.addAttribute("client", client);
        return "ticket_add";
    }
    @PostMapping("/add")
    public String ticketPostAdd(@ModelAttribute("ticket") @Valid Ticket ticket, BindingResult bindingResult, @RequestParam String phone,
                                  Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Client> client = clientRepository.findAll();
            model.addAttribute("client", client);
            return "ticket_add";
        }
        ticket.setClient(clientRepository.findByPhone(phone));
        ticketRepository.save(ticket);
        return "redirect:/ticket/add";
    }
    @GetMapping("/view")
    public String ticketMain(Model model) throws IOException {
        Iterable<Ticket> ticket = ticketRepository.findAll();
        model.addAttribute("ticket", ticket);
        exportDataToExcel();
        return "ticket_view";
    }

    @Autowired
    private DataSource dataSource;
    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM ticket");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("ticket");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("ticket.xlsx")) {
            workbook.write(outputStream);
        }
    }
}
