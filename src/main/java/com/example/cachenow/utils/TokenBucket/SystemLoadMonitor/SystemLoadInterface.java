package com.example.cachenow.utils.TokenBucket.SystemLoadMonitor;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.cachenow.utils.Constants.TokenBucketConstants.SYSTEM_THRESHOLD;

/**
 * 时间  18/10/2023 下午 4:56
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
@Slf4j
public abstract class SystemLoadInterface {
    double  THRESHOLD = SYSTEM_THRESHOLD;  // 定义CPU设定的过载阀值,这个地方是说的是百分数


    boolean process(String[] command) {
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
                double cpuUsage = Double.parseDouble(matcher.group());
                log.debug("CPU usage: " + cpuUsage);
                return cpuUsage > THRESHOLD;
            } else {
                log.debug("未成功读取到cpu负载,无法动态更新rate");
            }
            int exitCode = process.waitFor();  // 等待命令执行完成
            if (exitCode != 0) {
                log.error("负载查询命令关闭失败");
            }
            inputStream.close();               // 关闭输入流
            inputStreamReader.close(); //
            reader.close();

            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        return false;  // 获取 CPU 使用率失败或不支持的操作系统返回 false
    }
    public abstract boolean isOverLoad() ;
}
