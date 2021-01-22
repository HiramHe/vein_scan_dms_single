package hiram.ucenter.service.impl;

import hiram.acl.pojo.po.SysRole;
import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.acl.pojo.po.SysUser;
import hiram.properties.TokenProperties;
import hiram.redis.RedisService;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.acl.service.UserService;
import hiram.springSecurity.pojo.dto.SecurityUser;
import hiram.ucenter.service.LoginService;
import hiram.token.TokenService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HiramHe
 * @Date: 2020/6/26 14:21
 * @Description: ""
 */

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private TokenProperties tokenProperties;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public String login(String username, String password) {

        SysUser dbUser = userService.queryByUsername(username);

        if(dbUser == null){
            throw new CustomException(ResultCodeEnum.USER_NOT_EXIST);
        }

        boolean matches = encoder.matches(password, dbUser.getPassword());
        Assert.state(matches,"登录失败");


        /*
        设置UserDetails
         */
        CustomUserDetails customUserDetails = new CustomUserDetails();

        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(dbUser,securityUser);
        customUserDetails.setSecurityUser(securityUser);

        LocalDateTime now = LocalDateTime.now();
        ZoneOffset systemZoneOffset = OffsetDateTime.now().getOffset();

        long currentTimeMillis = now.toInstant(systemZoneOffset).toEpochMilli();
        long currentTimeSec = currentTimeMillis/1000;
        LocalDateTime loginDateTime = LocalDateTime.ofEpochSecond(currentTimeSec,0, systemZoneOffset);
        customUserDetails.setLoginDateTime(loginDateTime);

        long minuteExpireTime = tokenProperties.getMinuteExpireTime();
        long expireTimeMillis = minuteExpireTime * Constants.MINUTE_MILLIS;
        long expireTimeSec = (currentTimeMillis + expireTimeMillis)/1000;
        LocalDateTime expireDateTime = LocalDateTime.ofEpochSecond(expireTimeSec, 0, systemZoneOffset);
        customUserDetails.setExpireDateTime(expireDateTime);

        List<SysRole> roles = userService.getRolesByUserId(dbUser.getUserId());
        List<String> roleNameEns = new ArrayList<>();
        for (SysRole role:roles){
            roleNameEns.add(role.getRoleNameEn());
        }
        customUserDetails.setRoleNameEns(roleNameEns);

        //生成token
        Map<String, String> rt = tokenService.createToken(expireTimeMillis);
        String token = rt.get("token");
        String uuid = rt.get("uuid");

        customUserDetails.setUuid(uuid);

        /*
        存入redis
         */
        if(minuteExpireTime != 0){
            redisService.setCacheObject(uuid, customUserDetails, expireTimeMillis, TimeUnit.MILLISECONDS);
        }else{
            // ==0:缓存不失效
            redisService.setCacheObject(uuid, customUserDetails);
        }

        return token;
    }
}
