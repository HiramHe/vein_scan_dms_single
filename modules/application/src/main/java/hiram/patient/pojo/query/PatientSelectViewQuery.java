package hiram.patient.pojo.query;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/10/5 16:44
 * @Description: ""
 */

@Data
public class PatientSelectViewQuery {

    private String patientName;
    private String email;
    private String phoneNumber;
}
