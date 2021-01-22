package hiram.springSecurity.exception;

import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: HiramHe
 * @Date: 2020/4/29 20:57
 * @Description: ""
 */

/**
 * 统一处理 认证异常。
 * 用来处理匿名用户访问无权限资源时的异常（即未登录，或者登录状态过期失效）
 * authenticationEntryPoint其实是在没有认证时，
 * 配置自动跳转到登录页面的url，这里将其改造成前后端分离接口。
 * 如果用户在未登录状态下直接请求需要登录的接口，应该报401，
 * 然而spring security默认会报403。
 * 因为spring security的未登录用户会拥有一个已认证的匿名权限，
 * 此时会认为其没有权限所以报403。
 * 可重新配置成401.
 *
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) {

        if (logger.isDebugEnabled()){
            authException.printStackTrace();
        }

        ResultCodeEnum resultCodeEnum;
        if( authException instanceof CustomAuthenticationException){

            resultCodeEnum = ((CustomAuthenticationException) authException).getResultCodeEnum();

        } else if (authException instanceof InsufficientAuthenticationException){

            resultCodeEnum = ResultCodeEnum.LOGIN_FIRST;

        } else {

            resultCodeEnum = ResultCodeEnum.FAILED;
        }

        ResultObject<String> resultObject = ResultObject.failed(HttpServletResponse.SC_UNAUTHORIZED, resultCodeEnum);

        ResponseUtils.writeObject(response,resultObject);
    }
}
