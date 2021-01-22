package hiram.springSecurity.handler;

import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.redis.RedisService;
import hiram.springSecurity.exception.CustomAuthenticationException;
import hiram.token.TokenService;
import hiram.utils.ResponseUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: HiramHe
 * @Date: 2021/1/19 23:23
 * @Description: ""
 */

/**
 * logout filter作用在自定义的filter之前，所以在自定义过滤器里面对异常的捕获和处理，对此处无效。
 */

public class CustomLogoutHandler implements LogoutHandler {

    private TokenService tokenService;

    private RedisService redisService;

    public CustomLogoutHandler(TokenService tokenService, RedisService redisService) {
        this.tokenService = tokenService;
        this.redisService = redisService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {

        ResultObject<Object> resultObject;

        String token = tokenService.getTokenFromRequest(request, Constants.TOKEN_HEADER);

        if (!StringUtils.hasLength(token)){

            resultObject = ResultObject.failed(ResultCodeEnum.TOKEN_NULL);
            ResponseUtils.writeObject(response,resultObject);

            return;
        }

        String uuid;
        try {

            uuid = tokenService. getValueFromToken(token, Constants.LOGIN_USER_KEY);

        } catch (Exception e) {

            resultObject = ResultObject.failed(ResultCodeEnum.TOKEN_UNTRUSTED);
            ResponseUtils.writeObject(response,resultObject);

            return;
        }

        if(StringUtils.hasLength(uuid)){
            redisService.deleteObject(uuid);
        }

        resultObject = ResultObject.success(ResultCodeEnum.SUCCESS_LOGOUT);
        ResponseUtils.writeObject(response,resultObject);
    }
}
