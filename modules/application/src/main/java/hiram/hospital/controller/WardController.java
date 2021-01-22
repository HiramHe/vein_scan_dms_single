package hiram.hospital.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.WardListViewQuery;
import hiram.hospital.service.WardService;
import hiram.pojo.vo.ResultObject;
import hiram.pojo.vo.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 19:25
 * @Description: ""
 */

@Api(tags = "医院信息模块-病房信息接口")
@RestController
@RequestMapping("/ward")
public class WardController extends BaseController {

    @Autowired
    private WardService wardService;

    @ApiOperation("根据病房id获取病房信息")
    @GetMapping("/query/{wardId}")
    public ResultObject<?> getWardById(@PathVariable("wardId") Long wardId){

        Ward ward = wardService.getWardById(wardId);

        try {
            Assert.notNull(ward,"ward为null");
        }catch (IllegalArgumentException e){
            throw new CustomException(ResultCodeEnum.FAILED_AUTHENTICATE);
        }

        return ResultObject.success(ward);
    }

    @ApiOperation("根据病房id获取病房的床位信息")
    @GetMapping("/getBeds/{wardId}")
    public ResultObject<?> getBedsOfWardById(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "5") Integer pageSize,
            @PathVariable("wardId") Long wardId){

        Ward ward = wardService.getWardById(wardId);

        try {
            Assert.notNull(ward,"record is null");
        } catch (Exception e){
            throw new CustomException(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        startPage();
        List<Bed> beds = wardService.getBedsOfWardById(wardId);
        TableData tableData = getTableData(beds);

        Map<String,Object> data = new HashMap<>();
        data.put("ward",ward);
        data.put("beds",tableData);

        return ResultObject.success(data);
    }

    @ApiOperation("条件查询列表")
    @GetMapping("/list")
    public ResultObject<?> list(@RequestParam(defaultValue = "1") Long pageNum,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                WardListViewQuery wardListViewQuery){

        Page<Ward> page = wardService.list(pageNum,pageSize,wardListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);

    }
}
