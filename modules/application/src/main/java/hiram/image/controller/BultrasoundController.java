package hiram.image.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.*;
import hiram.image.service.BultrasoundService;
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

@Api(tags = "图像模块-B超图像管理接口")
@RestController
@RequestMapping("/bultrasound")
public class BultrasoundController extends BaseController {

    @Autowired
    private BultrasoundService bultrasoundService;

    @ApiOperation(value = "添加B超图像信息")
    @PostMapping("/add")
    public ResultObject<?> add(BultrasoundAddViewQuery bUltrasoundAddViewQuery){

        BultrasoundAddServiceQuery bultrasoundAddServiceQuery = new BultrasoundAddServiceQuery();
        BeanUtils.copyProperties(bUltrasoundAddViewQuery,bultrasoundAddServiceQuery);

        Bultrasound bultrasound = bultrasoundService.add(bultrasoundAddServiceQuery);

        Assert.notNull(bultrasound,"插入失败");

        Map<String,Object> data = new HashMap<>();
        data.put("bultrasound",bultrasound);

        return ResultObject.success(data);
    }

    @ApiOperation("根据B超图像id删除记录")
    @DeleteMapping("/delete/{bultrasoundId}")
    public ResultObject<?> deleteById(@PathVariable("bultrasoundId") Long bultrasoundId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = bultrasoundService.deleteByIdPermanently(bultrasoundId);
        }else {
            affectRows = bultrasoundService.deleteByIdLogically(bultrasoundId);
        }

        Assert.state(affectRows>0,"删除失败");

        return ResultObject.success();

    }

    @ApiOperation("根据B超图像id修改记录")
    @PutMapping("/update/{bultrasoundId}")
    public ResultObject<?> updateById(@PathVariable("bultrasoundId") Long bultrasoundId,
                                      @Valid BultrasoundUpdateViewQuery bultrasoundUpdateViewQuery){

        int affectRows = bultrasoundService.updateById(bultrasoundId,bultrasoundUpdateViewQuery);

        Assert.state(affectRows>0,"更新失败");

        return ResultObject.success();
    }

    @ApiOperation(value = "根据B超图像id查询记录")
    @GetMapping("/query/{bultrasoundId}")
    public ResultObject<?> queryById(@PathVariable("bultrasoundId") Long bultrasoundId){

        Bultrasound bultrasound = bultrasoundService.queryById(bultrasoundId);

        Assert.notNull(bultrasound,"数据不存在");

        Map<String,Object> data = new HashMap<>();
        data.put("bultrasound",bultrasound);

        return ResultObject.success(data);

    }

    @ApiOperation(value = "查询B超图像列表")
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "5") Long pageSize,
            BultrasoundListViewQuery bultrasoundListViewQuery){

        Page<Bultrasound> page = bultrasoundService.list(pageNum,pageSize,bultrasoundListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);
    }

}
