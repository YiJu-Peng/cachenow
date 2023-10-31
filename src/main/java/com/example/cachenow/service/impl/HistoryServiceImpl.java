package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.History;
import com.example.cachenow.mapper.HistoryDao;
import com.example.cachenow.service.IHistoryService;
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
public class HistoryServiceImpl extends ServiceImpl<HistoryDao, History> implements IHistoryService {

}
