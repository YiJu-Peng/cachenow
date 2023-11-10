package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.File;
import com.example.cachenow.dto.FileDTO;
import com.example.cachenow.dto.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    List<FileDTO> getAllFilesById(int page);
    List<FileDTO> getAllFiles(int page);

    Boolean deleteFileById(int id);

    List<FileDTO> getAllFilesOfNoPass(int page);

    Boolean check(int id);
}
