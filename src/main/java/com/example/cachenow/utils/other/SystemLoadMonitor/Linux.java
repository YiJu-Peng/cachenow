package com.example.cachenow.utils.other.SystemLoadMonitor;

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
public class Linux implements SystemLoadInterface{

    @Override
    public boolean isOverLoad() {
        String []command = new String[] { "sh", "-c", "top -b -n1 | grep \"Cpu(s)\" | awk '{print $2}'" };
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(inputStreamReader);

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }

            String output = sb.toString();
            Pattern pattern = Pattern.compile("(\\d+)");
            Matcher matcher = pattern.matcher(output);
            if (matcher.find()) {
                double cpuUsage = Double.parseDouble(matcher.group(1));
                return cpuUsage > THRESHOLD;
            } else {
                log.info("未成功读取到cpu负载,无法动态更新rate");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;  // 获取 CPU 使用率失败或不支持的操作系统返回 false
    }
}
