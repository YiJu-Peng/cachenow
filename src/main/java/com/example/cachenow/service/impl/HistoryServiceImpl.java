package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.History;
import com.example.cachenow.mapper.HistoryDao;
import com.example.cachenow.service.IHistoryService;
import com.example.cachenow.utils.other.UserHolder;
import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @Autowired
    HistoryDao historyDao;

    @Override
    public Page<History> getUserHistory(Long userId, Long pageNo, Long pageSize) {
        QueryWrapper<History> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        Page<History> page = new Page<>(pageNo, pageSize);
        return historyDao.selectPage(page, queryWrapper);
    }

    @Override
    public void read(int resourceId) {
        final History history = new History();
        history.setAccess_time(LocalDateTime.now());
        history.setUser_id(Math.toIntExact(UserHolder.getUser().getId()));
        history.setResource_id(resourceId);
        historyDao.insert(history);
    }


//    public List<History> getUserHistory(Long userId) {
//        Long lastId;
//        final Object o = RedisUtil.get(USER_HISTORY_KEY + userId);
//        if (o ==null) {
//            lastId = 0L;
//        }else{
//            lastId = Long.valueOf(o.toString());
//        }
//
//        // 调用DAO接口中的方法查询历史记录
//        List<History> historyList = historyDao.
//                getHistorysByUserId(userId, lastId, UserHistoryPageSize);
//
//        // 如果有查询到数据，则更新lastId为最后一条数据的id
//        if (!historyList.isEmpty()) {
//            lastId = Long.valueOf(historyList.get(historyList.size() - 1).getResource_id());
//            RedisUtil.set(USER_HISTORY_KEY+userId, lastId,USER_HISTROY_TTL);
//        }
//
//        // 返回查询结果和更新后的lastId
//        return historyList;
//    }
//
//    @Override
//    public List<History> getUserHistoryClean(Long userId) {
//
//        // 调用DAO接口中的方法查询历史记录
//        List<History> historyList = historyDao.
//                getHistorysByUserId(userId, 0L, UserHistoryPageSize);
//
//        // 如果有查询到数据，则更新lastId为最后一条数据的id
//        if (!historyList.isEmpty()) {
//            Long lastId = Long.valueOf(historyList.get(historyList.size() - 1).getResource_id());
//            RedisUtil.set(USER_HISTORY_KEY+userId, lastId,USER_HISTROY_TTL);
//        }
//
//        // 返回查询结果和更新后的lastId
//        return historyList;
//    }
}
