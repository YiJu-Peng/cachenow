package com.example.cachenow.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.example.cachenow.domain.SensitiveWord;
import com.example.cachenow.utils.annotation.BatchQuery;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
public interface ISensitiveWordService extends IService<SensitiveWord> {
    void addSensitiveWord(String word);
    @BatchQuery(primaryKey="word_id")
    List<SensitiveWord> getAllSensitiveWords();
}
