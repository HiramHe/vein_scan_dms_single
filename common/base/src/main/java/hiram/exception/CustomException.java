package hiram.exception;

import hiram.enums.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: HiramHe
 * @Date: 2020/12/16 20:31
 * @Description: "自定义异常"
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CustomException extends RuntimeException {

    private Long code;
    private String msg;
    private ResultCodeEnum resultCodeEnum;

    public CustomException(ResultCodeEnum resultCodeEnum){
        this.resultCodeEnum = resultCodeEnum;
    }

    public CustomException(Long code,String msg){
        this.code = code;
        this.msg = msg;
    }
}
