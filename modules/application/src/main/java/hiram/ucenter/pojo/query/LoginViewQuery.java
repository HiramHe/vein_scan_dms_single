package hiram.ucenter.pojo.query;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: HiramHe
 * @Date: 2020/6/26 14:18
 * @Description: ""
 */

@Data
public class LoginViewQuery {

    @NotNull
    @NotBlank
    @ApiParam(value = "hiram")
    private String username;

    @NotNull
    @NotBlank
    @ApiParam(value = "12345")
    private String password;

}
