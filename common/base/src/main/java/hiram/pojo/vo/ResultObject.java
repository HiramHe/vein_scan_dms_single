package hiram.pojo.vo;

import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 7:07
 * @Description: ""
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "接口统一返回对象")
public class ResultObject<T> {

    @ApiModelProperty(value = "http响应码")
    private int httpCode;

    @ApiModelProperty("接口响应码")
    private long code;

    @ApiModelProperty("接口响应信息")
    private String msg;

    @ApiModelProperty("接口响应数据")
    private T data;

    /*
    静态方法无法使用类上定义的泛型，如果静态方法要使用泛型，必须将静态方法也定义为泛型方法
     */

    public static <E> ResultObject<E> getResultObjectWithProperty(int httpCode, long code, String msg, E data) {

        return new ResultObject<>(httpCode, code, msg, data);
    }

    public static <E> ResultObject<E> getResultObjectWithResultCode(int httpCode, ResultCodeEnum resultCodeEnum, E data) {
        return getResultObjectWithProperty(httpCode, resultCodeEnum.getCode(), resultCodeEnum.getMsg(),data);
    }

    public static <E> ResultObject<E> success(int httpCode, ResultCodeEnum resultCodeEnum, E data) {
        return getResultObjectWithResultCode(httpCode, resultCodeEnum,data);
    }

    public static <E> ResultObject<E> success(ResultCodeEnum resultCodeEnum, E data) {
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT, resultCodeEnum,data);
    }

    public static <E> ResultObject<E> success(ResultCodeEnum resultCodeEnum) {
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT, resultCodeEnum,null);
    }

    public static <E> ResultObject<E> success(E data) {
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT, ResultCodeEnum.SUCCESS, data);
    }

    public static <E> ResultObject<E> success(){
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT,ResultCodeEnum.SUCCESS,null);
    }

    public static <E> ResultObject<E> failed() {
        return failed(ResultCodeEnum.FAILED);
    }

    public static <E> ResultObject<E> failed(int httpCode, ResultCodeEnum resultCodeEnum) {
        return getResultObjectWithResultCode(httpCode, resultCodeEnum,null);
    }

    public static <E> ResultObject<E> failed(ResultCodeEnum resultCodeEnum) {
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT, resultCodeEnum,null);
    }

    public static <E> ResultObject<E> failed(ResultCodeEnum resultCodeEnum, E data) {
        return getResultObjectWithResultCode(Constants.HTTPCODE_DEFAULT, resultCodeEnum,data);
    }

    public static <E> ResultObject<E> failed(int httpCode, long code, String msg, E data) {
        return getResultObjectWithProperty(httpCode,code,msg,data);
    }

    public static <E> ResultObject<E> failed(long code, String msg, E data) {
        return getResultObjectWithProperty(Constants.HTTPCODE_DEFAULT,code,msg,data);
    }

    public static <E> ResultObject<E> failed(long code, String msg) {
        return getResultObjectWithProperty(Constants.HTTPCODE_DEFAULT,code,msg,null);
    }

}
