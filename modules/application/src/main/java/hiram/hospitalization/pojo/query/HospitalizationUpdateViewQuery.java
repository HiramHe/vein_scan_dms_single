package hiram.hospitalization.pojo.query;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 10:37
 * @Description: ""
 */

@Data
public class HospitalizationUpdateViewQuery {

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
