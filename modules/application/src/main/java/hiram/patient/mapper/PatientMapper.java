package hiram.patient.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.patient.pojo.po.Patient;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 10:53
 * @Description: ""
 */

@Mapper
@Component
public interface PatientMapper extends BaseMapper<Patient> {

    Integer insertOne(Patient patient);

    Integer deleteByIdPermanently(Long patientId);

    Integer deleteByIdsPermanently(Long[] patientId);

    Integer recoverById(Long patientId);

    Integer recoverByIds(Long[] patientIds);

    Patient checkPatientNameUnique(String patientName);

    Patient checkPhoneNumberUnique(String phoneNumber);

    Patient checkEmailUnique(String email);

    Patient selectPatientByPatientId(Long patientId);

}
