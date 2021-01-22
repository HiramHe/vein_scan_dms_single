package hiram.acl.pojo.query;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/7/17 10:51
 * @Description: ""
 */

@Data
@ApiModel
public class UserAddViewQuery {

    @NotNull
    @NotEmpty
    @ApiParam(required = true)
    private String username;

    @NotNull
    @NotEmpty
    @ApiParam(required = true)
    private String password;

    private String nickname;

    private String realName;

    @Size(min = 0,max = 1,message = "长度不能大于1")
    private String sex;

    @ApiModelProperty(value = "2021-01-08")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd",timezone = "GTM+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Email
    private String email;

    @Size(min = 0,max = 11,message = "长度不能大于11")
    private String phoneNumber;

    private String remark;

    @ApiParam(defaultValue = "true")
    private Boolean enabled;

}
