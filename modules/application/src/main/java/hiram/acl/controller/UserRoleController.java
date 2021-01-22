package hiram.acl.controller;

import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.acl.pojo.po.UserRole;
import hiram.acl.pojo.query.UserRoleAddServiceQuery;
import hiram.acl.service.UserRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/10/2 17:08
 * @Description: ""
 */

@Api( tags = "访问控制模块-用户角色管理接口")
@RestController
@RequestMapping(value = "/acl/userRole")
public class UserRoleController {

    @Autowired
    private UserRoleService userRoleService;

    @ApiOperation(value = "添加用户角色")
    @PostMapping("/add")
    public ResultObject<?> add(@RequestParam() Long userId,
                               @RequestParam() Long roleId){

        UserRoleAddServiceQuery userRoleAddServiceQuery = new UserRoleAddServiceQuery(userId,roleId);
        UserRole userRole;

        try {
            userRole = userRoleService.addOne(userRoleAddServiceQuery);

        } catch (Exception e) {

            if (e instanceof DuplicateKeyException){
                return ResultObject.failed(ResultCodeEnum.RECORD_EXIST);
            }else {
                return ResultObject.failed(ResultCodeEnum.FAILED);
            }
        }

        Map<String,UserRole> data = new HashMap<>();
        data.put("userRole",userRole);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation("根据主键id删除用户角色")
    @DeleteMapping("/deleteById/{userRoleId}")
    public ResultObject<?> deleteById(@PathVariable("userRoleId") Long userRoleId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;

        if (isPermanent){
            affectRows = userRoleService.deleteByIdPermanently(userRoleId);
        }else {
            affectRows = userRoleService.deleteByIdLogically(userRoleId);
        }

        Assert.state(affectRows>0,"删除用户角色失败");

        Map<String,Object> data = new HashMap<>();
        data.put("影响行数",affectRows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation("根据用户id删除用户角色")
    @DeleteMapping("/deleteByUserId/{userId}")
    public ResultObject<?> deleteByUserId(@PathVariable("userId") Long userId,
                                          @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;

        if (isPermanent){
            affectRows = userRoleService.deleteByUserIdPermanently(userId);
        }else {
            affectRows = userRoleService.deleteByUserIdLogically(userId);
        }

        Assert.state(affectRows>0,"删除用户角色失败");

        Map<String,Object> data = new HashMap<>();
        data.put("影响行数",affectRows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }
}
