package com.example.cachenow.controller;


import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.dto.FileDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.enums.FileUploadEnum;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.service.IFileService;
import com.example.cachenow.service.impl.FileServiceImpl;
import com.example.cachenow.utils.oss.OSSManger;
import com.example.cachenow.utils.other.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@Slf4j
@RequestMapping("/file")
public class FileController {

    @Autowired
    private FileServiceImpl fileService;

    @Autowired
    private OSSManger ossManger;

    @GetMapping("/download/{fileId}")
    public Result download(@PathVariable("fileId") long fileId) {
        String key = fileService.getById(fileId).getFilepath();
        OSSObject object = ossManger.getObject(key);
        // todo 这里可以修改成别的文件类型
        return Result.ok(object);
    }

    @GetMapping("/download/{path}/{fileId}")
    public Result download(@PathVariable("fileId") long fileId,
                           @PathVariable("path") String path) {
        String key = fileService.getById(fileId).getFilepath();
        ObjectMetadata object = ossManger.getObject(key,path);
        return Result.ok(object);
    }

    @GetMapping("/upload")
    public Result upload(@RequestPart("file") MultipartFile multipartFile,
                         String uploadValue) {
        if (FileUploadEnum.getEnumByValue(uploadValue) == null) {
            throw new BizException(ErrorCode.PARAMS_ERROR);
        }
        validFile(multipartFile, uploadValue);
        UserDTO loginUser = UserHolder.getUser();
        String uuid = RandomStringUtils.randomAlphanumeric(8);
        String filename = uuid + "-" + multipartFile.getOriginalFilename();
        String filepath = String.format("/%s/%s/%s", uploadValue, loginUser.getId(), filename);
        java.io.File file = null;
        try {
            // 上传文件
            file = File.createTempFile(filepath, null);
            multipartFile.transferTo(file);
            ossManger.putObject(filepath, file);
            // 返回可访问地址
            return Result.ok(OSSManger.OSS_HOST + filepath);
        } catch (Exception e) {
            log.error("file upload error, filepath = " + filepath, e);
            throw new BizException(ErrorCode.SYSTEM_ERROR, "上传失败");
        } finally {
            if (file != null) {
                // 删除临时文件
                boolean delete = file.delete();
                if (!delete) {
                    log.error("file delete error, filepath = {}", filepath);
                }
            }
        }
    }

    /**
     * 校验文件
     *
     * @param multipartFile
     * @param uploadValue 上传文件类型
     */
    private void validFile(MultipartFile multipartFile, String uploadValue) {
        // 文件大小
        long fileSize = multipartFile.getSize();
        // 文件后缀
        String fileSuffix = FileUtil.getSuffix(multipartFile.getOriginalFilename());
        final long ONE_M = 1024 * 1024L;
        if (FileUploadEnum.USER_AVATAR.getValue().equals(uploadValue)) {
            if (fileSize > ONE_M) {
                throw new BizException(ErrorCode.PARAMS_ERROR, "文件大小不能超过 1M");
            }
            if (!Arrays.asList("jpeg", "jpg", "svg", "png", "webp").contains(fileSuffix)) {
                throw new BizException(ErrorCode.PARAMS_ERROR, "文件类型错误");
            }
        }
    }

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
    @GetMapping("/download/{id}")
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

