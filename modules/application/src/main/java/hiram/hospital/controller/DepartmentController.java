package hiram.hospital.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.controller.BaseController;
import hiram.hospital.pojo.po.Department;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.DepartmentAddViewQuery;
import hiram.hospital.pojo.query.DepartmentListViewQuery;
import hiram.hospital.pojo.query.DepartmentUpdateViewQuery;
import hiram.hospital.service.DepartmentService;
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
 * @Date: 2020/12/30 16:37
 * @Description: ""
 */

@Api(tags = "医院信息模块-科室信息接口")
@RestController
@RequestMapping("/department")
public class DepartmentController extends BaseController {

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation("添加科室")
    @PostMapping("/add")
    public ResultObject<?> add(DepartmentAddViewQuery departmentAddViewQuery){

        Department department = departmentService.add(departmentAddViewQuery);

        Map<String,Object> data = new HashMap<>();
        data.put("department",department);

        return ResultObject.success(data);
    }

    @ApiOperation("根据科室id删除科室信息")
    @DeleteMapping("/delete/{departmentId}")
    public ResultObject<?> deleteById(@PathVariable("departmentId") Long departmentId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;

        if (isPermanent){
            affectRows = departmentService.deleteByIdPermanently(departmentId);
        } else {
            affectRows = departmentService.deleteByIdLogically(departmentId);
        }

        Assert.state(affectRows>0,"影响行数为0，删除失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("根据科室id更新科室信息")
    @PutMapping("/update/{departmentId}")
    public ResultObject<?> updateById(@PathVariable("departmentId") Long departmentId,
                                  DepartmentUpdateViewQuery departmentUpdateViewQuery){

        int affectRows = departmentService.updateById(departmentId,departmentUpdateViewQuery);

        Assert.state(affectRows>0,"影响行数为0，更新失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation("根据科室id查询科室信息")
    @GetMapping("/query/{departmentId}")
    public ResultObject<?> queryById(@PathVariable("departmentId") Long departmentId){

        Department department = departmentService.queryById(departmentId);

        Assert.notNull(department,"科室不存在");

        Map<String,Object> data = new HashMap<>();
        data.put("department",department);

        return ResultObject.success(data);
    }

    @ApiOperation("科室信息列表")
    @GetMapping("/list")
    public ResultObject<?> list(@RequestParam(defaultValue = "1") Long pageNum,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                DepartmentListViewQuery departmentListViewQuery){

        Page<Department> page = departmentService.list(pageNum,pageSize,departmentListViewQuery);

        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        List<Department> records = page.getRecords();

        Map<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("pages",pages);
        data.put("current",current);
        data.put("records",records);

        return ResultObject.success(data);
    }

    @ApiOperation("根据科室id获取病房信息")
    @GetMapping("/getWardsOfDepartment/{departmentId}")
    public ResultObject<?> getWardsOfDepartmentById(@RequestParam(defaultValue = "1") Long pageNum,
                                                    @RequestParam(defaultValue = "5") Long pageSize,
                                                    @PathVariable("departmentId") Long departmentId){

        startPage();
        List<Ward> wards = departmentService.getWardsOfDepartmentById(departmentId);

        TableData tableData = getTableData(wards);

        return ResultObject.success(tableData);

    }
}
