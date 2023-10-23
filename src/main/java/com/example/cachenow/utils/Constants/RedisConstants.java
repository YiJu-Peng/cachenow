package com.example.cachenow.utils.Constants;

/**
 * 时间  20/10/2023 下午 4:56
 * 作者 Ctrlcv工程师  在线面对百度编程
 */
public class RedisConstants {
    public static final Long EXPIRE_SECONDS = 60L;//超时时间
    public static final Long EXPIRE_SECONDS_BATCH = EXPIRE_SECONDS;//集体缓存的超时时间
    public static final Long EXPIRE_SECONDS_ISINMYSQL = EXPIRE_SECONDS;//单个缓存的超时时间
    public static final String KEY_PREFIX_BATCH = "id";//集体缓存默认使用的主键名,如果不存在需要进行更改
}
