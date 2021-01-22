package hiram.acl.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.acl.mapper.RoleMapper;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.query.RoleAddViewQuery;
import hiram.acl.pojo.query.RoleListViewQuery;
import hiram.acl.pojo.query.RoleUpdateViewQuery;
import hiram.acl.service.RoleService;
import hiram.controller.BaseController;
import hiram.pojo.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2021/1/13 21:14
 * @Description: ""
 */

@Api(tags = "访问控制模块-角色管理接口")
@RestController
@RequestMapping("/acl/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    /**
     * 增
     */
    @ApiOperation(value = "增加角色", hidden = true)
    @PostMapping("/add")
    public ResultObject<?> add(RoleAddViewQuery roleAddViewQuery){

        SysRole sysRole = roleService.addOne(roleAddViewQuery);

        Assert.notNull(sysRole,"插入角色失败");

        Map<String,Object> data = new HashMap<>();
        data.put("role",sysRole);

        return ResultObject.success(data);
    }

    /**
     * 删
     */
    @ApiOperation(value = "根据id，删除角色", hidden = true)
    @DeleteMapping("/delete/{roleId}")
    public ResultObject<?> delete(@PathVariable("roleId") Long roleId,
                                  @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = roleService.deleteByIdPermanently(roleId);
        } else {
            affectRows = roleService.deleteByIdLogically(roleId);
        }

        Assert.state(affectRows>0,"影响行数为0，删除失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    /**
     * 改
     */
    @ApiOperation(value = "根据id，更新角色信息", hidden = true)
    @PutMapping("/update/{roleId}")
    public ResultObject<?> update(@PathVariable("roleId") Long roleId,
                                  RoleUpdateViewQuery roleUpdateViewQuery){

        int rt = roleService.updateById(roleId,roleUpdateViewQuery);

        Assert.state(rt>0,"影响行数为0，更新角色失败");

        return ResultObject.success();
    }

    /**
     * 查
     */
    @ApiOperation("条件查询列表")
    @GetMapping("/list")
    public ResultObject<?> list(@RequestParam(defaultValue = "1") Long pageNum,
                                @RequestParam(defaultValue = "5") Long pageSize,
                                RoleListViewQuery roleListViewQuery){

        Page<SysRole> page = roleService.list(pageNum,pageSize,roleListViewQuery);

        Map<String, Object> data = getPageData(page);

        return ResultObject.success(data);
    }

    @ApiOperation("根据id，查询角色信息")
    @GetMapping("/query/{roleId}")
    public ResultObject<?> queryById(@PathVariable("roleId") Long roleId){

        SysRole role = roleService.getById(roleId);

        Assert.notNull(role,"角色不存在");

        Map<String,Object> data = new HashMap<>();
        data.put("role",role);

        return ResultObject.success(data);
    }

    /*
     * todo
     * 为角色分配菜单
     */

    /*
     * todo
     * 为角色分配权限
     */
}
