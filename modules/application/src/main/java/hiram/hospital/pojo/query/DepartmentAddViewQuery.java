package hiram.hospital.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author: HiramHe
 * @Date: 2021/1/3 10:35
 * @Description: ""
 */

@Data
public class DepartmentAddViewQuery {

    @NotNull(message = "必填")
    @NotBlank(message = "不得为空")
    @ApiParam(required = true)
    private String departmentName;

    private String remark;
}
