package hiram.hospitalization.controller;

import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospitalization.pojo.dto.HospitalizationDto;
import hiram.hospitalization.pojo.po.Hospitalization;
import hiram.hospitalization.pojo.query.HospitalizationAddViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationListViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationUpdateViewQuery;
import hiram.hospitalization.service.HospitalizationService;
import hiram.pojo.vo.ResultObject;
import hiram.pojo.vo.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 14:35
 * @Description: ""
 */

@Api(tags = "住院模块-入院资料接口")
@RestController
@RequestMapping("/hospitalization")
public class HospitalizationController extends BaseController {

    @Autowired
    HospitalizationService hospitalizationService;

    @ApiOperation("添加入院信息")
    @PostMapping("/add")
    public ResultObject<?> add(@Valid HospitalizationAddViewQuery hospitalizationAddViewQuery){

        Hospitalization hospitalization = hospitalizationService.add(hospitalizationAddViewQuery);

        Map<String,Object> data = new HashMap<>();
        data.put("hospitalization",hospitalization);

        return ResultObject.success(data);
    }

    @ApiOperation("根据id删除入院信息")
    @DeleteMapping("/delete/{hospitalizationId}")
    public ResultObject<?> deleteById(@PathVariable("hospitalizationId") Long hospitalizationId,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isPermanent){
        int affectRows;
        if (isPermanent){
            affectRows = hospitalizationService.deleteByIdPermanently(hospitalizationId);
        } else {
            affectRows = hospitalizationService.deleteByIdLogically(hospitalizationId);
        }

        Assert.state(affectRows>0,"影响行数为0，删除失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("恢复所有逻辑删除的数据")
    @PutMapping("/recoverAll")
    public ResultObject<?> recoverAll(){
        int affectRows;

        affectRows = hospitalizationService.recoverAll();

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("根据id更新入院信息")
    @PutMapping("/update/{hospitalizationId}")
    public ResultObject<?> updateById(@PathVariable("hospitalizationId") Long hospitalizationId,
                                      HospitalizationUpdateViewQuery hospitalizationUpdateViewQuery){

        int affectRows;
        affectRows = hospitalizationService.updateById(hospitalizationId,hospitalizationUpdateViewQuery);

        Assert.state(affectRows>0,"影响行数为0，更新失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("条件查询列表")
    @GetMapping("/list")
    public ResultObject<?> list(@RequestParam(defaultValue = "1") Long pageNum,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                HospitalizationListViewQuery hospitalizationListViewQuery){

        startPage();
        List<HospitalizationDto> list= hospitalizationService.list(hospitalizationListViewQuery);
        TableData tableData = getTableData(list);

        return ResultObject.success(tableData);
    }

    @ApiOperation("根据入院id查询入院信息")
    @GetMapping("/query/{hospitalizationId}")
    public ResultObject<?> queryByHospitalizationId(@PathVariable("hospitalizationId") Long hospitalizationId){

        HospitalizationDto hospitalizationDto = hospitalizationService.queryByHospitalizationId(hospitalizationId);

        try {
            Assert.notNull(hospitalizationDto,"数据不存在");
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Map<String,Object> data = new HashMap<>();
        data.put("hospitalizationDto",hospitalizationDto);

        return ResultObject.success(data);

    }

}
