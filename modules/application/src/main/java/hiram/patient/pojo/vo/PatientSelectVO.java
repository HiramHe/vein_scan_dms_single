package hiram.patient.pojo.vo;

import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/10/5 16:24
 * @Description: ""
 */

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PatientSelectVO extends BasePO {

    private Long patientId;
    private String patientName;
    private String sex;
    private LocalDate birthday;
    private String email;
    private String phoneNumber;
    private String idcardNumber;
    private String birthProvince;
    private String birthCity;
    private String birthCounty;
    private Boolean enabled;
    private String remark;
    private String addId;
    private String addOrient;
}
