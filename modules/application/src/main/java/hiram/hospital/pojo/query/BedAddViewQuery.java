package hiram.hospital.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Author: HiramHe
 * @Date: 2021/1/2 16:13
 * @Description: ""
 */

@Data
public class BedAddViewQuery {

    @NotNull(message = "不能为空")
    @ApiParam(required = true, example = "1")
    private Long bedNumber;

    @NotNull(message = "不能为空")
    @ApiParam(required = true, defaultValue = "true")
    private Boolean vacant;
}
