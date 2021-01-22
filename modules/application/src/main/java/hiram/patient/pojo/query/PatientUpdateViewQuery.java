package hiram.patient.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/12/29 20:21
 * @Description: ""
 */

@Data
public class PatientUpdateViewQuery {

    @NotNull
    @ApiParam(required = true,example = "1")
    private Long patientId;

    private String patientName;
    private String sex;

    @ApiParam(value = "2021-01-21")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthday;

    @Email
    private String email;

    private String phoneNumber;
    private String idcardNumber;
    private String birthProvince;
    private String birthCity;
    private String birthCounty;
    private Boolean enabled;
    private String remark;
}
