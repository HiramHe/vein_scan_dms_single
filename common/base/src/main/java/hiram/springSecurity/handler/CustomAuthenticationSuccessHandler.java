package hiram.springSecurity.handler;

import hiram.constant.Constants;
import hiram.pojo.vo.ResultObject;
import hiram.redis.RedisService;
import hiram.properties.TokenProperties;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.token.TokenService;
import hiram.utils.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HiramHe
 * @Date: 2020/4/29 16:21
 * @Description: ""
 */

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private TokenProperties tokenProperties;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

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

        //生成token
        Map<String, String> rt = tokenService.createToken(expireTimeMillis);
        String token = rt.get("token");
        String uuid = rt.get("uuid");

        customUserDetails.setUuid(uuid);

        if(minuteExpireTime != 0){
            redisService.setCacheObject(uuid, customUserDetails, expireTimeMillis, TimeUnit.MILLISECONDS);
        }else{
            // ==0:缓存不失效
            redisService.setCacheObject(uuid, customUserDetails);
        }

        /*
         * 封装返回前端对象
         * R：mybatis-plus提供的结果封装工具
         */
        Map<String,Object> data = new HashMap<>();
        data.put("token",token);

        ResultObject< Map<String,Object> > resultObject = ResultObject.success(data);

        //写回浏览器
        ResponseUtils.writeObject(response, resultObject);

    }


}
