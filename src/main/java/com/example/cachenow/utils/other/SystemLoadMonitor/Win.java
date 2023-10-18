package com.example.cachenow.utils.other.SystemLoadMonitor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 时间  18/10/2023 下午 4:55
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public class Win implements SystemLoadInterface{
    @Override
    public boolean isOverLoad() {
        String command = "wmic cpu get loadpercentage";
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("LoadPercentage")) {
                    double cpuUsage = Double.parseDouble(line.trim());
                    return cpuUsage > THRESHOLD;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;  // 获取 CPU 使用率失败或不支持的操作系统返回 false
    }
}
