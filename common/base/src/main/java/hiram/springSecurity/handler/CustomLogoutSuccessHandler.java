package hiram.springSecurity.handler;

import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.redis.RedisService;
import hiram.token.TokenService;
import hiram.utils.ResponseUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: HiramHe
 * @Date: 2020/5/19 12:07
 * @Description: ""
 */

@Component
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        String token = tokenService.getTokenFromRequest(request, Constants.TOKEN_HEADER);
        String uuid = null;

        try {

            uuid = tokenService.getValueFromToken(token, Constants.LOGIN_USER_KEY);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(StringUtils.hasLength(uuid)){
            redisService.deleteObject(uuid);
        }

        ResultObject<Object> resultObject = ResultObject.success(ResultCodeEnum.SUCCESS_LOGOUT);
        ResponseUtils.writeObject(response,resultObject);

    }
}
