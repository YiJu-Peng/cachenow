package com.example.cachenow.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.SensitiveWord;
import com.example.cachenow.mapper.SensitiveWordDao;
import com.example.cachenow.service.ISensitiveWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordDao, SensitiveWord> implements ISensitiveWordService {
    @Autowired
    private SensitiveWordDao sensitiveWordMapper;

    public void addSensitiveWord(String word) {
        SensitiveWord sensitiveWord = new SensitiveWord();
        sensitiveWord.setWord(word);
        sensitiveWordMapper.insert(sensitiveWord);
    }

    public List<SensitiveWord> getAllSensitiveWords() {
        return sensitiveWordMapper.selectList(null);
    }
}
