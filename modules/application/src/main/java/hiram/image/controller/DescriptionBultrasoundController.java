package hiram.image.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.image.pojo.po.DescriptionBultrasound;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.*;
import hiram.image.service.DescriptionBultrasoundService;
import hiram.pojo.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/5/20 16:05
 * @Description: ""
 */

@Api(tags = "图像模块-描述之B超部分管理接口")
@RestController
@RequestMapping("/description/bultrasound")
public class DescriptionBultrasoundController extends BaseController {

    @Autowired
    private DescriptionBultrasoundService descriptionBultrasoundService;

    @ApiOperation(value = "添加信息")
    @PostMapping("/add")
    public ResultObject<?> add(DescriptionBultrasoundAddViewQuery descriptionBUltrasoundAddViewQuery){

        DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery = new DescriptionBultrasoundAddServiceQuery();
        BeanUtils.copyProperties(descriptionBUltrasoundAddViewQuery,descriptionBUltrasoundAddServiceQuery);

        DescriptionBultrasound descriptionBultrasound = descriptionBultrasoundService.add(descriptionBUltrasoundAddServiceQuery);

        Assert.notNull(descriptionBultrasound,"插入失败");

        Map<String,Object> data = new HashMap<>();
        data.put("descriptionBultrasound",descriptionBultrasound);

        return ResultObject.success(data);
    }

    @ApiOperation("根据id删除记录")
    @DeleteMapping("/delete/{id}")
    public ResultObject<?> deleteById(@PathVariable("id") Long id,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = descriptionBultrasoundService.deleteByIdPermanently(id);
        }else {
            affectRows = descriptionBultrasoundService.deleteByIdLogically(id);
        }

        Assert.state(affectRows>0,"删除失败");

        return ResultObject.success();

    }

    @ApiOperation("根据id修改记录")
    @PutMapping("/update/{id}")
    public ResultObject<?> updateById(@PathVariable("id") Long id,
                                      DescriptionBultrasoundUpdateViewQuery descriptionBultrasoundUpdateViewQuery){

        int affectRows = descriptionBultrasoundService.updateById(id,descriptionBultrasoundUpdateViewQuery);

        Assert.state(affectRows>0,"更新失败");

        return ResultObject.success();
    }

    @ApiOperation(value = "根据id查询记录")
    @GetMapping("/query/{id}")
    public ResultObject<?> queryById(@PathVariable("id") Long id){

        DescriptionBultrasound descriptionBultrasound = descriptionBultrasoundService.queryById(id);

        Assert.notNull(descriptionBultrasound,"数据不存在");

        Map<String,Object> data = new HashMap<>();
        data.put("descriptionBultrasound",descriptionBultrasound);

        return ResultObject.success(data);

    }

    @ApiOperation(value = "查询记录列表")
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "5") Long pageSize,
            DescriptionBultrasoundListViewQuery descriptionBultrasoundListViewQuery){

        Page<DescriptionBultrasound> page = descriptionBultrasoundService.list(pageNum,pageSize,descriptionBultrasoundListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);
    }

}
