package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.Resource;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IResourceService extends IService<Resource> {

    Object creatResoure();
}
