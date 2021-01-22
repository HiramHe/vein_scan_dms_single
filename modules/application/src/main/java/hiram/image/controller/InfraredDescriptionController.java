package hiram.image.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.*;
import hiram.image.service.InfraredDescriptionService;
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

@Api(tags = "图像模块-描述管理接口")
@RestController
@RequestMapping("/infraredDescription")
public class InfraredDescriptionController extends BaseController {

    @Autowired
    private InfraredDescriptionService infraredDescriptionService;

    @ApiOperation(value = "添加信息")
    @PostMapping("/add")
    public ResultObject<?> add(InfraredDescriptionAddViewQuery infraredDescriptionAddViewQuery){

        InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery = new InfraredDescriptionAddServiceQuery();
        BeanUtils.copyProperties(infraredDescriptionAddViewQuery,infraredDescriptionAddServiceQuery);

        InfraredDescription infraredDescription = infraredDescriptionService.add(infraredDescriptionAddServiceQuery);

        Assert.notNull(infraredDescription,"插入失败");

        Map<String,Object> data = new HashMap<>();
        data.put("infraredDescription",infraredDescription);

        return ResultObject.success(data);
    }

    @ApiOperation("根据描述id删除记录")
    @DeleteMapping("/delete/{descriptionId}")
    public ResultObject<?> deleteById(@PathVariable("descriptionId") Long descriptionId,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = infraredDescriptionService.deleteByIdPermanently(descriptionId);
        }else {
            affectRows = infraredDescriptionService.deleteByIdLogically(descriptionId);
        }

        Assert.state(affectRows>0,"删除失败");

        return ResultObject.success();

    }

    @ApiOperation("根据描述id修改记录")
    @PutMapping("/update/{descriptionId}")
    public ResultObject<?> updateById(@PathVariable("descriptionId") Long descriptionId,
                                      @Valid InfraredDescriptionUpdateViewQuery infraredDescriptionUpdateViewQuery){

        int affectRows = infraredDescriptionService.updateById(descriptionId,infraredDescriptionUpdateViewQuery);

        Assert.state(affectRows>0,"更新失败");

        return ResultObject.success();
    }

    @ApiOperation(value = "根据描述id查询记录")
    @GetMapping("/query/{descriptionId}")
    public ResultObject<?> queryById(@PathVariable("descriptionId") Long descriptionId){

        InfraredDescription infraredDescription = infraredDescriptionService.queryById(descriptionId);

        Assert.notNull(infraredDescription,"数据不存在");

        Map<String,Object> data = new HashMap<>();
        data.put("infraredDescription",infraredDescription);

        return ResultObject.success(data);

    }

    @ApiOperation(value = "查询记录列表")
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "5") Long pageSize,
            InfraredDescriptionListViewQuery infraredDescriptionListViewQuery){

        Page<InfraredDescription> page = infraredDescriptionService.list(pageNum,pageSize,infraredDescriptionListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);
    }

}
