package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.History;
import com.example.cachenow.dto.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface IHistoryService extends IService<History> {
   Page<History> getUserHistory(Long userId, Long pageNo, Long pageSize);

    void read(int resourceId);


//   List<History> getUserHistory(Long userId);
//   List<History> getUserHistoryClean(Long userId);

}
