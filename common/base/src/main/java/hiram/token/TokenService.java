package hiram.token;

import hiram.springSecurity.pojo.dto.CustomUserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/5/14 21:47
 * @Description: ""
 */

/*
由于token在多个模块中都需要被解析，所以抽取成公共服务。
 */

public interface TokenService {

    Map<String,String> createToken(long expireTimeMillis);

    String getTokenFromRequest(HttpServletRequest request, String key);

    CustomUserDetails getCustomUserDetailsByTokenInRequest(HttpServletRequest request) throws Exception;

    void refreshToken(long expireTimeMillis, CustomUserDetails customUserDetails);

    String getValueFromToken(String token, String key) throws Exception;


}
