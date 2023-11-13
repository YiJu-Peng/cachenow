package com.example.cachenow.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.cachenow.domain.History;
import com.example.cachenow.domain.User;
import com.example.cachenow.domain.UserInfo;
import com.example.cachenow.dto.LoginFormDTO;
import com.example.cachenow.dto.ResourceDTO;
import com.example.cachenow.dto.Result;
import com.example.cachenow.dto.UserDTO;
import com.example.cachenow.mapper.UserDao;
import com.example.cachenow.service.IUserService;
import com.example.cachenow.utils.other.RedisUtil;
import com.example.cachenow.utils.other.RegexUtils;
import com.example.cachenow.utils.other.UserHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.example.cachenow.utils.Constants.RedisConstants.*;
import static com.example.cachenow.utils.Constants.SystemConstants.USER_NICK_NAME_PREFIX;
import static com.example.cachenow.utils.Constants.UserConstants.PAGE_SIZE;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 * 用户登录等事项,将用户进行redis的缓存
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements IUserService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserInfoServiceImpl userInfoService;
    @Autowired
    private HistoryServiceImpl historyService;
    @Autowired
    private ResourceServiceImpl resourceService;

    @Override
    public Result sendCode(String phone, HttpSession session) {
        // 1.校验手机号
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }
        // 3.符合，生成验证码
        //todo: 这个地方要使用外部的sdk获取验证码,还未完成
        String code = RandomUtil.randomNumbers(6);

        // 4.保存验证码到 session
        stringRedisTemplate.opsForValue().set(LOGIN_CODE_KEY + phone, code, LOGIN_CODE_TTL, TimeUnit.MINUTES);

        // 5.发送验证码
        //todo: 这个地方我们还需要发送到手机上,现在只是简单的打印下
        log.debug("发送短信验证码成功，验证码：{}", code);
        // 返回ok
        return Result.ok();
    }

    @Override
    public Result login(LoginFormDTO loginForm, HttpSession session) {
        // 1.校验手机号
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }

        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();

        if(user.getPassword().equals(loginForm.getPassword())
                &&user.getPhone().equals(loginForm.getPhone())){
            // 7.保存用户信息到 redis中
            // 7.1.随机生成token，作为登录令牌
            String token = UUID.randomUUID().toString(true);
            // 7.2.将User对象转为HashMap存储
            UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
            Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                    CopyOptions.create()
                            .setIgnoreNullValue(true)
                            .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
            // 7.3.存储
            String tokenKey = LOGIN_USER_KEY + token;
            stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
            // 7.4.设置token有效期
            stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

            // 8.返回token
            return Result.ok(token);

        }
        else {
            return Result.fail("对不起您的用户不存在");
        }

    }

    public  Result register(LoginFormDTO loginForm, HttpSession session){

        // 1.校验手机号
        String phone = loginForm.getPhone();
        if (RegexUtils.isPhoneInvalid(phone)) {
            // 2.如果不符合，返回错误信息
            return Result.fail("手机号格式错误！");
        }



        // 3.从redis获取验证码并校验
        String cacheCode = stringRedisTemplate.opsForValue().get(LOGIN_CODE_KEY + phone);
        String code = loginForm.getCode();
        if (cacheCode == null || !cacheCode.equals(code)) {
            // 不一致，报错
            return Result.fail("验证码错误");
        }

        // 4.一致，根据手机号查询用户 select * from tb_user where phone = ?
        User user = query().eq("phone", phone).one();

        // 5.判断用户是否存在
        if (user == null) {
            // 6.不存在，创建新用户并保存
            user = createUserWithPhone(phone);
        }

        // 7.保存用户信息到 redis中
        // 7.1.随机生成token，作为登录令牌
        String token = UUID.randomUUID().toString(true);
        // 7.2.将User对象转为HashMap存储
        UserDTO userDTO = BeanUtil.copyProperties(user, UserDTO.class);
        Map<String, Object> userMap = BeanUtil.beanToMap(userDTO, new HashMap<>(),
                CopyOptions.create()
                        .setIgnoreNullValue(true)
                        .setFieldValueEditor((fieldName, fieldValue) -> fieldValue.toString()));
        // 7.3.存储
        String tokenKey = LOGIN_USER_KEY + token;
        stringRedisTemplate.opsForHash().putAll(tokenKey, userMap);
        // 7.4.设置token有效期
        stringRedisTemplate.expire(tokenKey, LOGIN_USER_TTL, TimeUnit.MINUTES);

        // 8.返回token
        return Result.ok(token);

    }

    @Override
    public Result userProfile() {
        Long id = UserHolder.getUser().getId();
        final UserInfo byId = userInfoService.getById(id);
        return Result.ok(byId);
    }

    /**
     * 获取历史数据
     * 使用不断下拉的方式获取
     * @return list of resources
     */
    @Override
    public List<com.example.cachenow.domain.Resource> userHistory() {
        final Long id = UserHolder.getUser().getId();
        final Object o = RedisUtil.get(USER_HISTORY_KEY + id);
        long pageNo =0L;
        if (o.toString()!=null){
            pageNo = Long.parseLong(o.toString());
            RedisUtil.set(USER_HISTORY_KEY + id,pageNo+1,USER_HISTROY_TTL);
        }
        final Page<History> userHistory = historyService.
                getUserHistory(id, pageNo, PAGE_SIZE);

        List<com.example.cachenow.domain.Resource> resources = new ArrayList<> ();
        for (History history : userHistory.getRecords()) {
            // 处理每条历史记录的逻辑
            resources.add(resourceService.getById(history.getResource_id()));
        }
        return resources;
    }

    /**
     * 获取指定的页数
     *
     * @param pageNumber 页数
     * @return list of resourcesDto 这个返回的只是简单的信息
     */
    @Override
    public List<ResourceDTO> userHistory(Long pageNumber) {
        final Long id = UserHolder.getUser().getId();
        final Page<History> userHistory =
                historyService.getUserHistory(id, pageNumber, PAGE_SIZE);
        List<com.example.cachenow.domain.Resource> resources = new ArrayList<> ();
        for (History history : userHistory.getRecords()) {
            // 处理每条历史记录的逻辑
            resources.add(resourceService.getById(history.getResource_id()));
        }
        return resources.stream().map(ResourceDTO::new).collect(Collectors.toList());
    }

    /**
     * 重置下拉的页数并获取第一页
     *
     * @return 历史记录
     */
    @Override
    public List<ResourceDTO> userHistoryClean() {
        final Long id = UserHolder.getUser().getId();
        RedisUtil.set(USER_HISTORY_KEY + id,0,USER_HISTROY_TTL);
        final Page<History> userHistory = historyService.
                getUserHistory(id, 0L, PAGE_SIZE);
        List<com.example.cachenow.domain.Resource> resources = new ArrayList<> ();
        for (History history : userHistory.getRecords()) {
            // 处理每条历史记录的逻辑
            resources.add(resourceService.getById(history.getResource_id()));
        }
        return resources.stream().map(ResourceDTO::new).collect(Collectors.toList());
    }

    /**
     * 用户签到的接口
     * @return 无返回
     */
    @Override
    public Result sign() {
        // 1.获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.写入Redis SETBIT key offset 1
        stringRedisTemplate.opsForValue().setBit(key, dayOfMonth - 1, true);
        return Result.ok();
    }

    /**
     * 用户统计签到的功能
     * @return 签到的天数
     */
    @Override
    public Result signCount() {
        // 1.获取当前登录用户
        Long userId = UserHolder.getUser().getId();
        // 2.获取日期
        LocalDateTime now = LocalDateTime.now();
        // 3.拼接key
        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String key = USER_SIGN_KEY + userId + keySuffix;
        // 4.获取今天是本月的第几天
        int dayOfMonth = now.getDayOfMonth();
        // 5.获取本月截止今天为止的所有的签到记录，返回的是一个十进制的数字 BITFIELD sign:5:202203 GET u14 0
        List<Long> result = stringRedisTemplate.opsForValue().bitField(
                key,
                BitFieldSubCommands.create()
                        .get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)
        );
        if (result == null || result.isEmpty()) {
            // 没有任何签到结果
            return Result.ok(0);
        }
        Long num = result.get(0);
        if (num == null || num == 0) {
            return Result.ok(0);
        }
        // 6.循环遍历
        int count = 0;
        while (true) {
            // 6.1.让这个数字与1做与运算，得到数字的最后一个bit位  // 判断这个bit位是否为0
            if ((num & 1) == 0) {
                // 如果为0，说明未签到，结束
                break;
            }else {
                // 如果不为0，说明已签到，计数器+1
                count++;
            }
            // 把数字右移一位，抛弃最后一个bit位，继续下一个bit位
            num >>>= 1;
        }
        return Result.ok(count);
    }



    private User createUserWithPhone(String phone) {
        // 1.创建用户
        User user = new User();
        user.setPhone(phone);
        user.setUsername(USER_NICK_NAME_PREFIX + RandomUtil.randomString(10));
        // 2.保存用户
        save(user);
        return user;
    }
}
