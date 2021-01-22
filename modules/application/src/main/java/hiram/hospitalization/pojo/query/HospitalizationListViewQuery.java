package hiram.hospitalization.pojo.query;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 14:43
 * @Description: ""
 */

@Data
public class HospitalizationListViewQuery {

    private Long hospitalizationId;
    private Long userId;
    private Long patientId;
    private Long departmentId;
    private Long wardId;
    private Long bedId;

    @ApiParam(value = "2021-01-01 10:01:01")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkinTime;
}
