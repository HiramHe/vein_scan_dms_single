package hiram.scanClient.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/5 11:24
 * @Description: ""
 */

@Data
public class UploadViewQuery {

    private String perspective;

    @ApiParam(value = "2021-01-05 11:26:08")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scanTime;

    @Positive(message = "必须为正数")
    @NotNull(message = "不得为空")
    @ApiParam(required = true,example = "-1")
    private Long patientId;

    @Positive(message = "必须为正数")
    private Long userId;

    private String description;

    private String severityLevel;

    private Long descriptionXCoordinate;

    private Long descriptionYCoordinate;

    private Long bultrasoundXCoordinate;

    private Long bultrasoundYCoordinate;

}
