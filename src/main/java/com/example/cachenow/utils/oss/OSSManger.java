package com.example.cachenow.utils.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.example.cachenow.common.ErrorCode;
import com.example.cachenow.expetion.BizException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.*;

@Component
public class OSSManger {
    private static String endpoint;
    private static String accessKeyId;
    private static String accessKeySecret;
    private static String bucketName;
    public static String OSS_HOST;
    private static OSS ossClient;


    @PostConstruct
    void init() {
        // 创建OSS实例
        endpoint = "oss-cn-hangzhou.aliyuncs.com";
        accessKeyId = "LTAI5t7nbGvEKaecdg8mZZuh";
        accessKeySecret = "7OeWcShYptA6lHvpkXv99zGCoeOMJk";
        bucketName = "alef-edu";
        OSS_HOST = "https://" + bucketName + "." + endpoint;
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    }

    @PreDestroy
    void destroy() {
        // 关闭OSSClient
        ossClient.shutdown();
    }

    /**
     * 上传对象
     *
     * @param key           唯一键
     * @param localFilePath 本地文件路径
     * @return
     */
    public PutObjectResult putObject(String key, String localFilePath) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,
                new File(localFilePath));
        return ossClient.putObject(putObjectRequest);
    }

    /**
     * 上传对象
     *
     * @param key  唯一键
     * @param file 文件
     * @return
     */
    public PutObjectResult putObject(String key, File file) {
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,
                file);
        return ossClient.putObject(putObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key 唯一键
     * @return
     */
    public OSSObject getObject(String key) {
        // 判断 要下载的文件 是否存在
        if (ossClient.doesObjectExist(bucketName, key)) {
            throw new BizException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
        }
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        return ossClient.getObject(getObjectRequest);
    }

    /**
     * 下载对象
     *
     * @param key       唯一键
     * @param localPath 本地文件路径
     * @return
     */
    public ObjectMetadata getObject(String key, String localPath) {
        // 判断 要下载的文件 是否存在
        if (ossClient.doesObjectExist(bucketName, key)) {
            throw new BizException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
        }
        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
        return ossClient.getObject(getObjectRequest,new File(localPath));
    }

/*
    public static String upload(File file) {
        try {
            String fileName1 = file.getName();
            // 获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            // 获取文件名称
            String fileName = file.getOriginalFilename();

            // 1.在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // yuy76t5rew01.jpg
            fileName = uuid + fileName;

            // 2.把文件按照日期进行分类
            // 获取当前日期，如【2023/5/12】
            String datePath = new DateTime().toString("yyyy/MM/dd");
            // 拼接，2023/5/12/ewtqr313401.jpg
            fileName = datePath + "/" + fileName;

            ossClient.putObject(bucketName, , file);

            // 把上传之后文件路径返回
            // 需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public static void download(String objectName) throws IOException {
        BufferedReader reader = null;
        try {
            // 判断 要下载的文件 是否存在
            Boolean exist = ossClient.doesObjectExist(bucketName, objectName);
            if (exist) {
                throw new BizException(ErrorCode.NOT_FOUND_ERROR, "文件不存在");
            }
            // ossObject 包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流
            OSSObject ossObject = ossClient.getObject(bucketName, objectName);
            // 按行读取文件内容
            reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
            }
        } finally {
            reader.close();
        }
    } */
}
