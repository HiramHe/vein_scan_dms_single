package hiram.exception;

import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: HiramHe
 * @Date: 2020/6/26 15:08
 * @Description: ""
 */

/**
 * 先找特定异常的处理方法，没有的话则交给全局异常处理的方法进行处理
 */

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    //全局异常

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    //为了返回数据
    @ResponseBody
    public ResultObject<?> error(Exception e){

        //这样可以将异常信息记录进logback配置的日志文件中去
        logger.warn(ExceptionUtils.getStackTrace(e));

        return ResultObject.failed();
    }

    //特定异常

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResultObject<?> error(ArithmeticException e){
        logger.warn(ExceptionUtils.getStackTrace(e));

        return ResultObject.failed(ResultCodeEnum.ARITHMETIC_EXCEPTION);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResultObject<?> error(AccessDeniedException e){

        return ResultObject.failed(HttpStatus.FORBIDDEN.value(), ResultCodeEnum.FORBIDDEN);
    }

    //处理参数校验异常
    @ExceptionHandler(BindException.class)
    public ResultObject<?> error(BindException e){
        logger.warn(ExceptionUtils.getStackTrace(e));

        Map<String,String> data = new HashMap<>();
        List<FieldError> errors = e.getFieldErrors();
        for (FieldError error:errors){
            data.put(error.getField(),error.getDefaultMessage());
        }

        return ResultObject.failed(ResultCodeEnum.FAILED,data);
    }

    //自定义异常

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultObject<?> error(CustomException e){

        logger.warn(ExceptionUtils.getStackTrace(e));

        return ResultObject.failed(e.getResultCodeEnum());
    }

}
