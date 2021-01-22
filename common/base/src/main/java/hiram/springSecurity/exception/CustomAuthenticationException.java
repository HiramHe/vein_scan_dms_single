package hiram.springSecurity.exception;

import hiram.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.AuthenticationException;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 21:57
 * @Description: ""
 */

/**
 * 自定义异常类，继承抽象类AuthenticationException，
 * 调用 AuthenticationEntryPoint的commence方法时，new 一
 * 个AuthenticationException对象，传进commence。
 */

public class CustomAuthenticationException extends AuthenticationException {

    private ResultCodeEnum resultCodeEnum;

    public CustomAuthenticationException(ResultCodeEnum resultCodeEnum){
        super(resultCodeEnum.getMsg());
        this.resultCodeEnum = resultCodeEnum;
    }

    public CustomAuthenticationException(String msg) {
        super(msg);
    }

    public ResultCodeEnum getResultCodeEnum(){
        return resultCodeEnum;
    }
}
