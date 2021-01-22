package hiram.hospital.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/3 16:15
 * @Description: ""
 */

@Data
public class DepartmentListViewQuery {

    private Long departmentId;
    private String departmentName;

    @ApiParam(value = "2021-01-01")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate gmtCreate;
}
