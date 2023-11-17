package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.File;
import com.example.cachenow.dto.Result;
import com.example.cachenow.mapper.FileDao;
import com.example.cachenow.service.IFileService;
import com.example.cachenow.utils.oss.OSSManger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileDao, File> implements IFileService {

}
