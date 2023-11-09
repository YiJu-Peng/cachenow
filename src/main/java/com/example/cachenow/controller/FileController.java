package com.example.cachenow.controller;


import cn.hutool.core.io.FileUtil;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.enums.FileUploadEnum;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.expetion.BizException;
import com.example.cachenow.service.IFileService;
import com.example.cachenow.utils.oss.OSSManger;
import com.example.cachenow.utils.other.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private OSSManger ossManger;

    @Autowired
    private IFileService fileService;

    @GetMapping("download/{fileId}")
    public Result download(@PathVariable("fileId") long fileId) {
        String key = fileService.getById(fileId).getFilepath();
        OSSObject object = ossManger.getObject(key);
        // todo 这里可以修改成别的文件类型
        return Result.ok(object);
    }

    @GetMapping("download/{path}/{fileId}")
    public Result download(@PathVariable("fileId") long fileId,
                           @PathVariable("path") String path) {
        String key = fileService.getById(fileId).getFilepath();
        ObjectMetadata object = ossManger.getObject(key,path);
        return Result.ok(object);
    }

    @GetMapping("upload")
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
        File file = null;
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

}

