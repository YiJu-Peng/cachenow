package com.example.cachenow.controller;


import com.example.cachenow.domain.SensitiveWord;
import com.example.cachenow.service.impl.SensitiveWordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Controller
@RequestMapping("/sensitiveWord")
public class SensitiveWordController {

    @Autowired
    private SensitiveWordServiceImpl sensitiveWordService;

    @PostMapping("/add")
    public void addSensitiveWord(@RequestParam String word) {
        sensitiveWordService.addSensitiveWord(word);
    }

    @GetMapping("/getAll")
    public List<SensitiveWord> getAllSensitiveWords() {
        return sensitiveWordService.getAllSensitiveWords();
    }
}

