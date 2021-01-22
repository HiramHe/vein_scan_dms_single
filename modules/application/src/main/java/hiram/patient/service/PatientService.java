package hiram.patient.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.patient.pojo.po.Patient;
import hiram.patient.pojo.query.PatientAddServiceQuery;
import hiram.patient.pojo.query.PatientSelectViewQuery;
import hiram.patient.pojo.query.PatientUpdateViewQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 10:59
 * @Description: ""
 */

public interface PatientService {

    /**
     * 新增患者
     * @param patientAddServiceQuery
     * @return
     */
    Patient insertOne(PatientAddServiceQuery patientAddServiceQuery);

    /**
     * 校验患者姓名唯一性
     * @param patientId
     * @param patientName
     * @return
     */
    Boolean checkPatientNameUnique(Long patientId, String patientName);

    /**
     * 校验电话号码唯一性
     * @param patientId
     * @param phoneNumber
     * @return
     */
    Boolean checkPhoneNumberUnique(Long patientId, String phoneNumber);

    /**
     * 校验电子邮件唯一性
     * @param patientId
     * @param email
     * @return
     */
    Boolean checkEmailUnique(Long patientId, String email);

    int deleteByIdPermanently(Long patientId);

    int deleteByIdLogically(Long patientId);

    int deleteByIdsPermanently(Long[] patientIds);

    int deleteByIdsLogically(Long[] patientIds);

    int recoverById(Long patientId);

    int recoverByIds(Long[] patientIds);

    int updateById(PatientUpdateViewQuery patientUpdateViewQuery);

    /**
     * 根据患者id获取患者信息
     * @param patientId
     * @return
     */
    Patient selectPatientByPatientId(Long patientId);


    Page list(Long pageNum, Long pageSize, PatientSelectViewQuery patientSelectViewQuery);
}
