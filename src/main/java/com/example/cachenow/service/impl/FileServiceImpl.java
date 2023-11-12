package com.example.cachenow.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.File;
import com.example.cachenow.dto.FileDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.mapper.FileDao;
import com.example.cachenow.service.IFileService;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.cachenow.utils.Constants.UserConstants.PAGE_SIZE;

@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements IFileService {

    @Autowired
    private FileDao fileMapper;

    @Value("${file.upload.directory}")
    private String uploadDirectory;

    public Result handleFileUpload(MultipartFile file) {
        if (file.isEmpty()) {
            return Result.fail("文件为空");
        }
        try {
            String fileName = file.getOriginalFilename();
            String filePath = uploadDirectory + fileName;
            Path destPath = Paths.get(filePath);
            Files.createDirectories(destPath.getParent());
            Files.copy(file.getInputStream(), destPath, StandardCopyOption.REPLACE_EXISTING);

            // 文件上传成功后保存文件信息到数据库
            File fileInfo = new File();
            fileInfo.setFilename(fileName);
            fileInfo.setFilepath(filePath);
            fileInfo.setUploader_id(Math.toIntExact(UserHolder.getUser().getId()));
            fileInfo.setUploaded_at(LocalDateTime.now());
            fileInfo.setIs_pass(0);
            fileMapper.insert(fileInfo);

            return Result.ok("文件上传成功");
        } catch (IOException e) {
            e.printStackTrace();
            return Result.fail("文件上传失败");
        }
    }

    @Override
    public MultipartFile getFileById(int id) {
        final File file = fileMapper.selectById(id);
        if (file != null) {
            // 文件存储在本地文件系统中
            Path filePath = Paths.get(file.getFilepath()); // 获取文件路径
            String originalFilename = file.getFilename(); // 获取原始文件名
            String contentType = file.getContent_type(); // 获取文件类型
            try {
                byte[] fileContent = Files.readAllBytes(filePath); // 读取文件内容
                // 使用CommonsMultipartFile构造MultipartFile对象
                return new MockMultipartFile("file", originalFilename, contentType, fileContent);
            } catch (IOException e) {
                // 处理文件读取异常
                e.printStackTrace();
                // 返回null或者抛出异常，根据实际情况决定
            }
        }
        // 如果文件不存在或者读取失败，可以返回null或者抛出异常，根据实际情况决定
        return null;
    }

    @Override
    public List<FileDTO> getAllFilesById(int page) {
        final Long id = UserHolder.getUser().getId();

        Page<File> filePage = new Page<>(page, PAGE_SIZE); // 假设有一个 PAGE_SIZE 变量来表示每页的文件数量
        final QueryWrapper<File> objectQueryWrapper = new QueryWrapper<File>().eq("uploader_id", id);
        return getFileDTOS(filePage, objectQueryWrapper);
    }
    @Override
    public List<FileDTO> getAllFiles(int page) {
        Page<File> filePage = new Page<>(page, PAGE_SIZE); // 假设有一个 PAGE_SIZE 变量来表示每页的文件数量
        final QueryWrapper<File> objectQueryWrapper = new QueryWrapper<>();
        return getFileDTOS(filePage, objectQueryWrapper);
    }

    @Override
    public Boolean deleteFileById(int id) {
        final File byId = fileMapper.selectById(id);
        final String filepath = byId.getFilepath();
        fileMapper.deleteById(id);
        // 删除文件系统中的文件
        //File file = new File(filepath);
        final java.io.File file = new java.io.File(filepath);
        if (file.exists()) {
            if (file.delete()) {
                return false;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public List<FileDTO> getAllFilesOfNoPass(int page) {
        final Page<File> objectPage = new Page<>(page, PAGE_SIZE);
        final QueryWrapper<File> noPass = new QueryWrapper<File>().eq("pass", 0);
        return getFileDTOS(objectPage,noPass);
    }

    @Override
    public Boolean check(int id) {
        final File file = fileMapper.selectById(id);
        if (file!=null) {
            file.setIs_pass(1);
            return true;
        }else {
            return false;
        }
    }

    private List<FileDTO> getFileDTOS(Page<File> filePage, QueryWrapper<File> objectQueryWrapper) {
        final Page<File> filePages = fileMapper.selectPage(filePage, objectQueryWrapper);
        List<FileDTO> fileDTOs = new ArrayList<>();
        for (File file : filePages.getRecords()) {
            FileDTO fileDTO = new FileDTO(file);
            fileDTOs.add(fileDTO);
        }
        return fileDTOs;
    }
}