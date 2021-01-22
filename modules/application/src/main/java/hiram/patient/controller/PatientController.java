package hiram.patient.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.patient.pojo.po.Patient;
import hiram.patient.pojo.query.PatientAddServiceQuery;
import hiram.patient.pojo.query.PatientAddViewQuery;
import hiram.patient.pojo.query.PatientSelectViewQuery;
import hiram.patient.pojo.query.PatientUpdateViewQuery;
import hiram.patient.service.PatientService;
import hiram.pojo.vo.ResultObject;
import hiram.utils.MyStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/10/5 14:20
 * @Description: ""
 */

/*
不管是什么请求，参数都可以是对象；如果是对象，不要使用@RequestParam注解。
如果使用了@RequestBody注解，那么接收的需要是一个json。
 */

@Api(tags = "患者模块-患者管理接口")
@RestController
@RequestMapping("/patient")
public class PatientController extends BaseController {

    @Autowired
    private PatientService patientService;

    @ApiOperation("新增患者")
    @PostMapping("/add")
    public ResultObject<?> add(@Valid PatientAddViewQuery patientAddViewQuery){

        PatientAddServiceQuery patientAddServiceQuery;
        if (patientAddViewQuery != null){
            patientAddServiceQuery = new PatientAddServiceQuery();
            BeanUtils.copyProperties(patientAddViewQuery,patientAddServiceQuery);
        }else {
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }

        //校验姓名唯一性
        if (!MyStringUtils.isEmpty(patientAddViewQuery.getPatientName())){

            Boolean isPatientNameUnique = patientService.checkPatientNameUnique(null, patientAddViewQuery.getPatientName());
            if (!isPatientNameUnique){
                return ResultObject.failed(ResultCodeEnum.NAME_EXIST);
            }
        }

        //校验电话号码唯一性
        if (!MyStringUtils.isEmpty(patientAddViewQuery.getPhoneNumber())){

            Boolean isPhoneNumberUnique = patientService.checkPhoneNumberUnique(null, patientAddViewQuery.getPhoneNumber());
            if (!isPhoneNumberUnique){
                return ResultObject.failed(ResultCodeEnum.PHONENUMBER_NOT_UNIQUE);
            }
        }

        //校验电子邮件唯一性
        if (!MyStringUtils.isEmpty(patientAddViewQuery.getEmail())){

            Boolean isEmailUnique = patientService.checkEmailUnique(null, patientAddViewQuery.getEmail());
            if (!isEmailUnique){
                return ResultObject.failed(ResultCodeEnum.EMAIL_NOT_UNIQUE);
            }
        }

        Patient patient = patientService.insertOne(patientAddServiceQuery);

        if (patient == null){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }

        Map<String,Patient> data = new HashMap<>();
        data.put("patient",patient);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation("根据患者id删除患者记录")
    @DeleteMapping("/delete")
    public ResultObject<?> deleteById(@RequestParam Long patientId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affect_rows;

        if (isPermanent){
            affect_rows = patientService.deleteByIdPermanently(patientId);
        }else {
            affect_rows = patientService.deleteByIdLogically(patientId);
        }

        if (affect_rows == 0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }else {
            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }

    }

    @ApiOperation("根据患者id批量删除患者记录")
    @DeleteMapping("/deletes")
    public ResultObject<?> deleteByIds(@RequestParam Long[] patientIds,
                                       @RequestParam(defaultValue = "false") Boolean isPermanent){
        int affectRows;
        Map<String, Integer> data;
        if (isPermanent){
            affectRows = patientService.deleteByIdsPermanently(patientIds);
        }else {
            affectRows = patientService.deleteByIdsLogically(patientIds);
        }

        if (affectRows == 0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }else {
            data = new HashMap<>();
            data.put("affectRows",affectRows);
            return ResultObject.success(ResultCodeEnum.SUCCESS,data);
        }
    }

    @ApiOperation("根据患者id恢复患者记录")
    @PutMapping("/recover")
    public ResultObject<?> recoverById(@RequestParam Long patientId){
        int affectRows;
        affectRows = patientService.recoverById(patientId);

        if (affectRows == 0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }else {
            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }
    }

    @ApiOperation("根据患者id批量恢复患者记录")
    @PutMapping("/recovers")
    public ResultObject<?> recoverByIds(@RequestParam Long[] patientIds){
        int affectRows;
        affectRows = patientService.recoverByIds(patientIds);

        if (affectRows == 0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }else {
            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }
    }

    @ApiOperation("根据患者id更新患者记录")
    @PutMapping("/update")
    public ResultObject<?> updateById(@Valid PatientUpdateViewQuery patientUpdateViewQuery){

        if (patientUpdateViewQuery.getPatientId() == null){
            return ResultObject.failed(ResultCodeEnum.ID_EMPTY);
        }

        int affectRows;
        affectRows = patientService.updateById(patientUpdateViewQuery);

        if (affectRows == 0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }else {
            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }
    }

    @ApiOperation("根据患者id获取患者信息")
    @GetMapping("/query/{patientId}")
    public ResultObject<?> selectPatientByPatientId(@PathVariable(name = "patientId") Long patientId){

        Patient patient = patientService.selectPatientByPatientId(patientId);

        if (patient == null){
            return ResultObject.failed(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Map<String,Patient> data = new HashMap<>();
        data.put("patient",patient);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation(value = "查询患者列表",hidden = false)
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "5") Long pageSize,
            PatientSelectViewQuery patientSelectViewQuery
    ){

        Page page = patientService.list(pageNum,pageSize,patientSelectViewQuery);

        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        List patients = page.getRecords();

        Map<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("pages",pages);
        data.put("current",current);
        data.put("patients",patients);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

}
