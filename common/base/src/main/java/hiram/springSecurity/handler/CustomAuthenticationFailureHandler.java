package hiram.springSecurity.handler;

import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败处理
 */

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {

        if (logger.isDebugEnabled()){
            e.printStackTrace();
        }

        ResultObject<String> resultObject = ResultObject.failed(ResultCodeEnum.FAILED_AUTHENTICATE);

        //输出
        ResponseUtils.writeObject(response, resultObject);
    }
}