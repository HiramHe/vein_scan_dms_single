package hiram.hospitalization.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 15:04
 * @Description: ""
 */

@Data
public class HospitalizationAddViewQuery {

    @ApiParam(required = true, example = "-1")
    @NotNull(message = "不得为空")
    @Positive(message = "必须为正数")
    private Long userId;

    @ApiParam(required = true, example = "1")
    @NotNull(message = "不得为空")
    private Long patientId;

    @ApiParam(required = true, example = "1")
    @NotNull(message = "不得为空")
    private Long departmentId;

    @ApiParam(required = true, example = "1")
    @NotNull(message = "不得为空")
    private Long wardId;

    @ApiParam(required = true, example = "1")
    @NotNull(message = "不得为空")
    private Long bedId;

    @ApiParam(required = false, value = "2021-01-01 15:05:45")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkinTime;

}
