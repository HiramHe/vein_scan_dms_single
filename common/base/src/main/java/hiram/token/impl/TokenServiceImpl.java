package hiram.token.impl;

import hiram.constant.Constants;
import hiram.redis.RedisService;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.token.TokenService;
import hiram.properties.TokenProperties;
import hiram.utils.IdUtils;
import hiram.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/5/14 21:50
 * @Description: ""
 */

/*
使用redis实现token的时效性，不使用jwt本身的时效性
 */

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisService redisService;

    @Override
    public Map<String,String> createToken(long expireTimeMillis) {

        String uuid = IdUtils.randomUUID();
        String token = JwtUtils.generateToken(expireTimeMillis, Constants.LOGIN_USER_KEY, uuid, Constants.TOKEN_SECRET);

        Map<String,String> data = new HashMap<>();
        data.put("uuid", uuid);
        data.put("token",token);

        return data;
    }

    @Override
    public String getTokenFromRequest(HttpServletRequest request, String key) {

        String token = request.getHeader(key);
        if ( !StringUtils.isEmpty(token) && token.startsWith(Constants.TOKEN_PREFIX)) {
            /*
            trim()：去掉空格
            */
            token = token.replace(Constants.TOKEN_PREFIX, "").trim();
        }

        return token;
    }

    @Override
    public CustomUserDetails getCustomUserDetailsByTokenInRequest(HttpServletRequest request) throws Exception {
        String token = getTokenFromRequest(request, Constants.TOKEN_HEADER);
        String value = getValueFromToken(token, Constants.LOGIN_USER_KEY);

        return redisService.getCacheObject(value);
    }

    public String getValueFromToken(String token, String key) throws Exception {

        Claims claims = JwtUtils.parseToken(token, Constants.TOKEN_SECRET);

        return (String)claims.get(key);
    }

    @Override
    public void refreshToken(long expireTimeMillis, CustomUserDetails customUserDetails) {

        LocalDateTime now = LocalDateTime.now();
        ZoneOffset systemZoneOffset = OffsetDateTime.now().getOffset();

        long currentTimeMillis = now.toInstant(systemZoneOffset).toEpochMilli();
        long currentTimeSec = currentTimeMillis/1000;
        LocalDateTime loginDateTime = LocalDateTime.ofEpochSecond(currentTimeSec,0, systemZoneOffset);
        customUserDetails.setLoginDateTime(loginDateTime);

        long expireTimeSec = (currentTimeMillis + expireTimeMillis)/1000;
        LocalDateTime expireDateTime = LocalDateTime.ofEpochSecond(expireTimeSec, 0, systemZoneOffset);
        customUserDetails.setExpireDateTime(expireDateTime);

    }

}
