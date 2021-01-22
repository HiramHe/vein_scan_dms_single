package hiram.patient.pojo.po;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.*;

import java.time.LocalDate;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 12:28
 * @Description: ""
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("patient")
public class Patient extends BasePO {

    @TableId("patient_id")
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
