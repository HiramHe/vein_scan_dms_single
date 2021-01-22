package hiram.springSecurity.handler;

import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: HiramHe
 * @Date: 2020/4/29 21:00
 * @Description: ""
 */

/**
 * 统一处理 权限异常。
 * 只对WebSecurityConfigurerAdapter中配置的权限出现异常时有效。
 * 对在接口或方法上配置的权限出现异常时不能捕获，对此只能由controller的全局异常处理器去处理。
 * 解决认证过的用户访问无权限资源时的异常.
 * 已登录成功，但是请求的url超出其权限.
 * spring security官方提供的，只能处理403异常,
 * 不推荐，太单一了。
 * AccessDeniedHandler的默认实现AccessDeniedHandler中只有对页面请求的处理，
 */

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        if (logger.isDebugEnabled()){
            accessDeniedException.printStackTrace();
        }

        ResultObject<String> resultObject = ResultObject.failed(HttpStatus.FORBIDDEN.value(), ResultCodeEnum.FORBIDDEN);

        ResponseUtils.writeObject(response,resultObject);
    }
}
