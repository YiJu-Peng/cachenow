package com.example.cachenow.utils.processors;

import com.example.cachenow.domain.SensitiveWord;
import com.example.cachenow.service.impl.SensitiveWordServiceImpl;
import com.example.cachenow.utils.annotation.SensitiveWordFilter;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static io.reactivex.annotations.SchedulerSupport.IO;

/**
 * 时间  2023/11/11 15:20
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Aspect
@Component
public class SensitiveWordFilterProcessor {
    @Autowired
    private SensitiveWordServiceImpl sensitiveWordService;

    @Around("@annotation(sensitiveWordFilter)")
    public Object filterSensitiveWords(ProceedingJoinPoint joinPoint,SensitiveWordFilter sensitiveWordFilter) throws Throwable {
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                String filtered = filter((String) args[i]);
                args[i] = filtered;
            }
        }
        return joinPoint.proceed(args);
    }

    private String filter(String text) {
        List<SensitiveWord> sensitiveWords = sensitiveWordService.getAllSensitiveWords();
        for (SensitiveWord sensitiveWord : sensitiveWords) {
            text = text.replace(sensitiveWord.getWord(), "***");
        }
        return text;
    }
}
