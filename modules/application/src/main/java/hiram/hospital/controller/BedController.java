package hiram.hospital.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.query.BedAddViewQuery;
import hiram.hospital.pojo.query.BedListViewQuery;
import hiram.hospital.service.BedService;
import hiram.pojo.vo.ResultObject;
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
 * @Date: 2020/12/30 16:38
 * @Description: ""
 */

@Api(tags = "医院信息模块-床位信息接口")
@RestController
@RequestMapping("/bed")
public class BedController {

    @Autowired
    private BedService bedService;

    @ApiOperation("添加床位信息")
    @PostMapping("/add")
    public ResultObject<?> add(@Valid BedAddViewQuery bedAddViewQuery){

        Boolean isBedNumberExist = bedService.checkBedNumberExist(bedAddViewQuery.getBedNumber());

        try {
            Assert.state(!isBedNumberExist,"床位号已存在");
        } catch (IllegalStateException e){
            throw new CustomException(ResultCodeEnum.BEDNUMBER_EXIST);
        }

        Bed bed = bedService.add(bedAddViewQuery);

        return ResultObject.success(bed);
    }

    @ApiOperation("根据床位id删除床位信息")
    @DeleteMapping("delete/{bedId}")
    public ResultObject<?> deleteById(@PathVariable(value = "bedId") Long bedId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = bedService.deleteByIdPermanently(bedId);
        }else {
            affectRows = bedService.deleteByIdLogically(bedId);
        }

        Assert.state(affectRows>0,"影响行数为0");

        Map<String,Integer> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("反转床位状态")
    @PutMapping("/invertVacantValue")
    public ResultObject<?> invertVacantValue(@RequestParam Long bedId){

        try {
            Assert.notNull(bedId,"bedId不得为空");
        } catch (IllegalArgumentException e){
            throw new CustomException(ResultCodeEnum.ID_EMPTY);
        }

        int affectRows = bedService.invertVacantValue(bedId);

        Assert.state(affectRows>0,"影响行数为0");

        Map<String, Integer> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation("根据床位id获取床位信息")
    @GetMapping("/query/{bedId}")
    public ResultObject<?> getBedById(@PathVariable(value = "bedId") Long bedId){

        Bed bed = bedService.getBedById(bedId);
        Map<String,Bed> data;
        if (null == bed){
            return ResultObject.failed(ResultCodeEnum.RECORD_NOT_EXIST);
        } else {
            data = new HashMap<>();
            data.put("bed",bed);
            return ResultObject.success(data);
        }

    }

    @ApiOperation("床位信息列表")
    @GetMapping("/list")
    public ResultObject<?> list(@RequestParam(defaultValue = "1") Long pageNum,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                @Valid BedListViewQuery bedListViewQuery){

        Page page = bedService.list(pageNum,pageSize,bedListViewQuery);

        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        List beds = page.getRecords();

        Map<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("pages",pages);
        data.put("current",current);
        data.put("beds",beds);

        return ResultObject.success(data);
    }
}

