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
public class BultrasoundUpdateViewQuery {

    private String path;
    private String filename;
    private String url;

}
