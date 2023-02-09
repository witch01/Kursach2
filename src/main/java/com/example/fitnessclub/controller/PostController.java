package com.example.fitnessclub.controller;

import com.example.fitnessclub.models.City_services;
import com.example.fitnessclub.models.Plain_training;
import com.example.fitnessclub.models.Post;
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
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @GetMapping("/add")
    public String PostsAdds(Post post, Model model)
    {
        return "post_add";
    }

    @PostMapping("/add")
    public String PostAdd(@Valid Post post,
                          BindingResult bindingResult, Model model)
    {
        if(bindingResult.hasErrors())
        {
            return "post_add";
        }
        postRepository.save(post);
        return "redirect:/post/view";
    }

    @GetMapping("/view")
    public String postMain(Model model) throws IOException {
        Iterable<Post> post = postRepository.findAll();
        model.addAttribute("post", post);
        exportDataToExcel();
        return "post_view";
    }

    @PostMapping("/{id}/del")
    public String postDelete(@PathVariable("id") long id, Model model){
        Post post = postRepository.findById(id).orElseThrow();
        postRepository.delete(post);
        return "redirect:/post/view";
    }

    @GetMapping("/{id}/edit")
    public String postEdit(@PathVariable("id")long id,
                                   Model model)
    {
        Post post = postRepository.findById(id).orElseThrow(()
                ->new IllegalArgumentException("Invalid posts Id" + id));
        model.addAttribute("post",post);

        return "post_edit";
    }

    @PostMapping("/{id}/edit")
    public String postUpdate(@ModelAttribute("post") @Valid Post post, BindingResult bindingResult,
                                     @PathVariable("id") long id) {

        post.setId(id);
        if (bindingResult.hasErrors()) {
            return "post_edit";
        }
        postRepository.save(post);
        return "redirect:/post/view";
    }

    @Autowired
    private DataSource dataSource;


    public void exportDataToExcel() throws IOException {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        List<Map<String, Object>> rows = jdbcTemplate.queryForList("SELECT * FROM post");

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("post");

        int rowNum = 0;
        for (Map<String, Object> row : rows) {
            Row currentRow = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                Cell cell = currentRow.createCell(colNum++);
                cell.setCellValue(entry.getValue().toString());
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream("post.xlsx")) {
            workbook.write(outputStream);
        }
    }

}
