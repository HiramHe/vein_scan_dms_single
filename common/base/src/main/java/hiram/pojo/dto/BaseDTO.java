package hiram.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiParam;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Null;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/4/23 20:25
 * @Description: "公共的DTO"
 */

@NoArgsConstructor
@AllArgsConstructor
@Null
@Getter
@Setter
@ToString
public class BaseDTO implements Serializable {

    private Boolean deleted;

    private LocalDateTime gmtCreate;

    private LocalDateTime gmtModify;

    private Long version;
}
