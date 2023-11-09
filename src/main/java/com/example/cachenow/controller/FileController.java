package com.example.cachenow.controller;


import com.example.cachenow.dto.Result;
import com.example.cachenow.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Controller
@RequestMapping("/file")
public class FileController {



    @Autowired
    private FileServiceImpl fileService;

    /**
     * 上传文件操作
     * @param file 上传的文件
     * @return 返回上传的文件
     */
    @PostMapping("/file/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        return fileService.handleFileUpload(file);
    }
    @PutMapping("/file/review/{id}")
    public MultipartFile getFileById(@PathVariable int id) {
        return fileService.getFileById(id);
    }

}

