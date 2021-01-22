package hiram.image.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import hiram.image.pojo.query.InfraredAddViewQuery;
import hiram.image.pojo.query.InfraredListViewQuery;
import hiram.image.pojo.query.InfraredUpdateViewQuery;
import hiram.image.service.InfraredService;
import hiram.pojo.vo.ResultObject;
import hiram.pojo.vo.TableData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/5/20 16:05
 * @Description: ""
 */

@Api(tags = "图像模块-红外图像管理接口")
@RestController
@RequestMapping("/infrared")
public class InfraredController extends BaseController {

    @Autowired
    private InfraredService infraredService;

    @ApiOperation(value = "添加红外图像信息",hidden = false)
    @PostMapping("/add")
    public ResultObject<?> add(InfraredAddViewQuery infraredAddViewQuery){

        InfraredAddServiceQuery infraredAddServiceQuery = new InfraredAddServiceQuery();
        BeanUtils.copyProperties(infraredAddViewQuery,infraredAddServiceQuery);

        Infrared infrared = infraredService.add(infraredAddServiceQuery);

        Assert.notNull(infrared,"插入失败");

        Map<String,Object> data = new HashMap<>();
        data.put("infrared",infrared);

        return ResultObject.success(data);
    }

    @ApiOperation("根据红外图像id删除记录")
    @DeleteMapping("/delete/{infraredId}")
    public ResultObject<?> deleteById(@PathVariable("infraredId") Long infraredId,
                                      @RequestParam(required = false, defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = infraredService.deleteByIdPermanently(infraredId);
        }else {
            affectRows = infraredService.deleteByIdLogically(infraredId);
        }

        Assert.state(affectRows>0,"删除失败");

        return ResultObject.success();

    }

    @ApiOperation("根据红外图像id修改记录")
    @PutMapping("/update/{infraredId}")
    public ResultObject<?> updateById(@PathVariable("infraredId") Long infraredId,
                                      @Valid InfraredUpdateViewQuery infraredUpdateViewQuery){

        int affectRows = infraredService.updateById(infraredId,infraredUpdateViewQuery);

        Assert.state(affectRows>0,"更新失败");

        return ResultObject.success();
    }

    @ApiOperation(value = "根据红外图像id查询红外图像")
    @GetMapping("/query/{infraredId}")
    public ResultObject<?> queryById(@PathVariable("infraredId") Long infraredId){
        Infrared infrared = infraredService.queryById(infraredId);

        try {
            Assert.notNull(infrared,"数据不存在");
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Map<String,Object> data = new HashMap<>();
        data.put("infrared",infrared);

        return ResultObject.success(data);

    }

    @ApiOperation(value = "查询红外图像列表")
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "5") Long pageSize,
            InfraredListViewQuery infraredListViewQuery){

        Page<Infrared> page = infraredService.list(pageNum,pageSize,infraredListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);
    }
}
