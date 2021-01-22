package hiram.image.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/6 10:30
 * @Description: ""
 */

@Data
public class InfraredAddViewQuery {

    private String perspective;
    private String filename;
    private String path;
    private String url;

    @ApiParam(value = "2021-01-06 10:37:07")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scanTime;

    private Long patientId;
    private Long userId;
}
