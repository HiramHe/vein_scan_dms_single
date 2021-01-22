package hiram.patient.pojo.query;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/10/5 14:38
 * @Description: ""
 */

/*
@NotNull：用在所有类型上面
@NotEmpty：用在集合上面
@NotBlank：用在string上面
 */

@Data
public class PatientAddViewQuery {

    @NotNull
    @NotBlank
    @ApiParam(required = true)
    private String patientName;

    @Size(max = 1,message = "字符个数不得超过1")
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

    @ApiParam(defaultValue = "true")
    private Boolean enabled;

    private String remark;

    private String addId;
    private String addOrient;
}
