package hiram.image.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/8/5 16:23
 * @Description: ""
 */

@Data
public class InfraredListViewQuery {

    private Long patientId;

    private Long userId;

    @ApiParam(value = "2021-01-05 16:47:08")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scanTimeBegin;

    @ApiParam(value = "2021-01-05 16:48:30")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scanTimeEnd;
}
