package com.example.fitnessclub.controller;


import com.example.fitnessclub.models.Client;
import com.example.fitnessclub.models.Customer_preferences;
import com.example.fitnessclub.models.Employee;
import com.example.fitnessclub.models.Post;
import com.example.fitnessclub.repo.EmployeeRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/add")
    public String EmployeeAdd(@ModelAttribute("employee") Employee employee, Model model)
    {

        Iterable<Post> post = postRepository.findAll();
        model.addAttribute("post", post);
        return "employee_add";
    }
    @PostMapping("/add")
    public String employeePostAdd(@ModelAttribute("employee") @Valid Employee employee, BindingResult bindingResult, @RequestParam String names,
                                  Model model)
    {
        if(bindingResult.hasErrors())
        {
            Iterable<Post> post = postRepository.findAll();
            model.addAttribute("post", post);
            return "employee_add";
        }
        employee.setPosts(postRepository.findByNames(names));
        employeeRepository.save(employee);
        return "redirect:/employee/view";
    }

    @GetMapping("/view")
    public String employeeMain(Model model) throws IOException {
        Iterable<Employee> employee = employeeRepository.findAll();
        model.addAttribute("employee", employee);
        exportDataToExcel();
        return "employee_view";
    }

    @PostMapping("/{id}/del")
    public String employeeDelete(@PathVariable("id") long id, Model model){
        Employee employee = employeeRepository.findById(id).orElseThrow();
        employeeRepository.delete(employee);
        return "redirect:/employee/view";
    }

    @GetMapping("/{id}/edit")
    public String employeeEdit(@PathVariable("id")long id,
                                  Model model)
    {
        Iterable<Post> post = postRepository.findAll();
        Employee employee = employeeRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid students Id" + id));
        model.addAttribute("post", post);
        model.addAttribute("employee",employee);

        return "employee_edit";
    }

    @PostMapping("/{id}/edit")
    public String employeeUpdate(@ModelAttribute("employee") @Valid Employee employee,
                                    BindingResult bindingResult, @RequestParam String names,
                                    @PathVariable("id") long id) {

        employee.setId(id);
        if (bindingResult.hasErrors()) {
            Iterable<Post> post = postRepository.findAll();
            return "employee_edit";
        }
        employee.setPosts(postRepository.findByNames(names));
        employeeRepository.save(employee);

        return "redirect:/employee/view";
    }

    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM employee");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("employee");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("employee.xlsx")) {
            workbook.write(outputStream);
        }
    }
}
