package hiram.hospitalization.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 14:42
 * @Description: ""
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("hosp_hospitalization")
public class Hospitalization extends BasePO {

    @TableId(value = "hospitalization_id", type = IdType.AUTO)
    private Long hospitalizationId;

    private Long userId;
    private Long patientId;
    private Long departmentId;
    private Long wardId;
    private Long bedId;
    private LocalDateTime checkinTime;

    private String chiefComplaint;
    private String presentIllness;
    private String drinkSmoke;
    private String healthCondition;
    private String allergies;
    private String surgery;
    private String bloodTransfusion;
    private String surgicalSpecialty;
    private String diagnosis1;
    private String diagnosis2;
    private String diagnosis3;
}
