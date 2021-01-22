package hiram.hospitalization.pojo.dto;

import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.po.Department;
import hiram.hospital.pojo.po.Ward;
import hiram.patient.pojo.po.Patient;
import hiram.pojo.dto.BaseDTO;
import hiram.acl.pojo.po.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 15:02
 * @Description: ""
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class HospitalizationDto extends BaseDTO {

    private Long hospitalizationId;

    private SysUser sysUser;
    private Patient patient;
    private Department department;
    private Ward ward;
    private Bed bed;

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
