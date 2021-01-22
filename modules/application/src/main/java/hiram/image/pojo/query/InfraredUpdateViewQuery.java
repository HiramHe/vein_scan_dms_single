package hiram.image.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/7 10:13
 * @Description: ""
 */

@Data
public class InfraredUpdateViewQuery {

    private String perspective;
    private String path;
    private String filename;
    private String url;

    @ApiParam(value = "2021-01-07 10:14:04")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scanTime;

    @Positive(message = "必须为正数")
    private Long patientId;

    @Positive(message = "必须为正数")
    private Long userId;
}
