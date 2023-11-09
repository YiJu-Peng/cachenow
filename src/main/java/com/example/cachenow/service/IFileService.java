package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.File;
import com.example.cachenow.dto.Result;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IFileService extends IService<File> {

    Result handleFileUpload(MultipartFile file);

    MultipartFile getFileById(int id);
}
