package com.example.cachenow.utils.TokenBucket.SystemLoadMonitor;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 时间  18/10/2023 下午 4:57
 * 作者 Ctrlcv工程师  在线面对百度编程
 * 这个类是进行cpu占用率的读取的
 * 因为是直接的读取系统给出的占用率,而没有经过自己的运算,所以速度还算快
 */
@Slf4j
public class Linux extends SystemLoadInterface {
    static String []command = new String[] { "sh", "-c", "top -b -n1 | grep \"Cpu(s)\" | awk '{print $2}'" };

    public boolean isOverLoad() {
        return process(command);
    }
}
