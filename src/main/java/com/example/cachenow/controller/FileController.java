package com.example.cachenow.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cachenow.domain.File;
import com.example.cachenow.dto.FileDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.service.impl.FileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping("/file")
public class FileController {



    @Autowired
    private FileServiceImpl fileService;

    /**
     * 上传文件操作
     * @param file 上传的文件
     * @return 返回上传的文件
     */
    @PostMapping("/upload")
    public Result handleFileUpload(@RequestParam("file") MultipartFile file) {
        return fileService.handleFileUpload(file);
    }

    /**
     * 获取特定的文件id的文件
     * 注意,这个地方是直接进行下载的
     * @param id 传入的文件的id
     * @return 下载的文件
     */
    @GetMapping("/review/{id}")
    public MultipartFile getFileById(@PathVariable int id) {
        return fileService.getFileById(id);
    }

    /**
     * 获得与某个人所有有关的文件
     * @param page 查询到第几页
     * @return 返回文件的信息,(注意,这个地方不是返回文件,这个地方放回的是文件的信息)
     */
    @GetMapping("/getuserall/{page}")
    public Result getFileByUserId(@PathVariable int page) {
        final List<FileDTO> allFilesById = fileService.getAllFilesById(page);
        final int size = allFilesById.size();
        return Result.ok(allFilesById, (long) size);
    }

    /**
     * 获取所有的文件信息,注意这个地方是信息,不是文件
     * @param page 页数
     * @return 返回文件信息
     */
    @GetMapping("/getall/{page}")
    public Result getAllFiles(@PathVariable int page) {
        final List<FileDTO> allFilesById = fileService.getAllFiles(page);
        final int size = allFilesById.size();
        return Result.ok(allFilesById, (long) size);
    }

    /**
     * 删除文件
     * @param id 文件id
     * @return 是否成功
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteFile(@PathVariable int id) {
        final Boolean aBoolean = fileService.deleteFileById(id);
        return aBoolean ? Result.ok("删除成功") : Result.fail("删除文件失败");
    }

    /**
     * 返回所有还没有审核的文件
     * @param page 翻到哪页了
     * @return 返回待审核列表
     */
    @GetMapping("/getall_nopass/{page}")
    public Result getAllFilesOfNoPass(@PathVariable int page) {
        final List<FileDTO> allFilesById = fileService.getAllFilesOfNoPass(page);
        final int size = allFilesById.size();
        return Result.ok(allFilesById, (long) size);
    }

    /**
     * 将文件审核通过
     * @param id 文件的id
     * @return 返回是否审核通过
     */
    @PostMapping("/check/{id}")
    public Result check(@PathVariable int id) {
        Boolean ok = fileService.check(id);
        return ok? Result.ok("审核确认通过成功") : Result.fail("check fail");
    }


}

