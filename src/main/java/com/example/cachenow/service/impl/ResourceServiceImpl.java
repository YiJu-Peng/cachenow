package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.Resource;
import com.example.cachenow.mapper.ResourceDao;
import com.example.cachenow.service.IResourceService;
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
public class ResourceServiceImpl extends ServiceImpl<ResourceDao, Resource> implements IResourceService {

}
