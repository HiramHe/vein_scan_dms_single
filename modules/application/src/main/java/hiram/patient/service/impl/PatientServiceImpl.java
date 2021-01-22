package hiram.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.constant.Constants;
import hiram.patient.mapper.PatientMapper;
import hiram.patient.pojo.po.Patient;
import hiram.patient.pojo.query.PatientAddServiceQuery;
import hiram.patient.pojo.query.PatientSelectViewQuery;
import hiram.patient.pojo.query.PatientUpdateViewQuery;
import hiram.patient.service.PatientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 11:19
 * @Description: ""
 */

@Service
public class PatientServiceImpl implements PatientService {

    @Autowired
    private PatientMapper patientMapper;

    /**
     * 新增患者
     *
     * @param patientAddServiceQuery
     * @return
     */
    @Override
    public Patient insertOne(PatientAddServiceQuery patientAddServiceQuery) {

        Patient patient;
        if (patientAddServiceQuery!=null){
            patient = new Patient();
            BeanUtils.copyProperties(patientAddServiceQuery,patient);
        }else {
            return null;
        }

        Integer rt = patientMapper.insertOne(patient);

        if (rt>0){
            return patient;
        }else {
            return null;
        }
    }

    /**
     *
     * 插入和更新都需要用到
     *
     * 校验患者姓名唯一性
     *
     * @param patientId
     * @param patientName
     * @return
     */
    @Override
    public Boolean checkPatientNameUnique(Long patientId, String patientName) {

        patientId = patientId == null ? -1 : patientId;

        Patient patient = patientMapper.checkPatientNameUnique(patientName);

        if (patient!=null && !patient.getPatientId().equals(patientId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    /**
     * 校验电话号码唯一性
     *
     * @param patientId
     * @param phoneNumber
     * @return
     */
    @Override
    public Boolean checkPhoneNumberUnique(Long patientId, String phoneNumber) {

        patientId = patientId == null ? -1 : patientId;

        Patient patient = patientMapper.checkPhoneNumberUnique(phoneNumber);

        if (patient!=null && !patient.getPatientId().equals(patientId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    /**
     * 校验电子邮件唯一性
     *
     * @param patientId
     * @param email
     * @return
     */
    @Override
    public Boolean checkEmailUnique(Long patientId, String email) {

        patientId = patientId == null ? -1 : patientId;

        Patient patient = patientMapper.checkEmailUnique(email);

        if (patient!=null && !patient.getPatientId().equals(patientId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    @Override
    public int deleteByIdPermanently(Long patientId) {

        return patientMapper.deleteByIdPermanently(patientId);

    }

    @Override
    public int deleteByIdLogically(Long patientId) {

        return patientMapper.deleteById(patientId);
    }

    @Override
    public int deleteByIdsPermanently(Long[] patientIds) {
        return patientMapper.deleteByIdsPermanently(patientIds);
    }

    @Override
    public int deleteByIdsLogically(Long[] patientIds) {
        return patientMapper.deleteBatchIds(Arrays.asList(patientIds));
    }

    @Override
    public int recoverById(Long patientId) {

        return patientMapper.recoverById(patientId);
    }

    @Override
    public int recoverByIds(Long[] patientIds) {
        return patientMapper.recoverByIds(patientIds);
    }

    @Override
    public int updateById(PatientUpdateViewQuery patientUpdateViewQuery) {

        Patient dbPatient = patientMapper.selectById(patientUpdateViewQuery.getPatientId());

        Patient patient = new Patient();
        BeanUtils.copyProperties(patientUpdateViewQuery,patient);
        patient.setVersion(dbPatient.getVersion());

        return patientMapper.updateById(patient);
    }

    /**
     * 根据患者id获取患者信息
     *
     * @param patientId
     * @return
     */
    @Override
    public Patient selectPatientByPatientId(Long patientId) {

        Patient patient = patientMapper.selectPatientByPatientId(patientId);

        return patient;
    }

    @Override
    public Page<Patient> list(Long pageNum, Long pageSize, PatientSelectViewQuery patientSelectViewQuery) {

        Page<Patient> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        if (patientSelectViewQuery.getPatientName() != null){
            queryWrapper.like("patient_name", patientSelectViewQuery.getPatientName());
        }
        if (patientSelectViewQuery.getEmail() != null){
            queryWrapper.like("email", patientSelectViewQuery.getEmail());
        }
        if (patientSelectViewQuery.getPhoneNumber() != null){
            queryWrapper.like("phone_number", patientSelectViewQuery.getPhoneNumber());
        }

        return patientMapper.selectPage(page, queryWrapper);

    }

}
