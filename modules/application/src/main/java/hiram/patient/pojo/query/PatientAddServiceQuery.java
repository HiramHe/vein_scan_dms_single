package hiram.patient.pojo.query;

import lombok.Data;

import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/10/5 14:51
 * @Description: ""
 */

@Data
public class PatientAddServiceQuery {

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
