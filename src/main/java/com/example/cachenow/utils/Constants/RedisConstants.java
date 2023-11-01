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

    public static final String LOGIN_CODE_KEY = "login:code:";
    public static final Long LOGIN_CODE_TTL = 2L;
    public static final String LOGIN_USER_KEY = "login:token:";
    public static final Long LOGIN_USER_TTL = 36000L;

    public static final Long CACHE_NULL_TTL = 2L;

    public static final Long CACHE_SHOP_TTL = 30L;
    public static final String CACHE_SHOP_KEY = "cache:shop:";

    public static final String LOCK_SHOP_KEY = "lock:shop:";
    public static final Long LOCK_SHOP_TTL = 10L;

    public static final String SECKILL_STOCK_KEY = "seckill:stock:";
    public static final String BLOG_LIKED_KEY = "blog:liked:";
    public static final String FEED_KEY = "feed:";
    public static final String SHOP_GEO_KEY = "shop:geo:";
    public static final String USER_SIGN_KEY = "sign:";






}
