package hiram.acl.controller;

import hiram.acl.pojo.query.*;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.pojo.vo.TableData;
import hiram.ucenter.pojo.dto.UserSelectDTO;
import hiram.acl.pojo.po.SysUser;
import hiram.acl.pojo.po.UserRole;
import hiram.acl.service.UserService;
import hiram.acl.service.UserRoleService;
import hiram.utils.MyStringUtils;
import hiram.utils.MyObjectUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @Author: HiramHe
 * @Date: 2020/5/14 20:20
 * @Description: ""
 */

@Api( tags = "访问控制模块-用户管理接口")
@RestController
@RequestMapping("/acl/user")
@PreAuthorize("hasRole('common')")
public class UserController extends BaseController {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
    插入
    */

    @ApiOperation(value = "添加系统用户")
    @PostMapping("/add")
    public ResultObject<?> add(@Valid UserAddViewQuery userAddViewQuery){

        ////检查用户名和密码是否为空
        if(MyStringUtils.isEmpty(userAddViewQuery.getUsername())  || MyStringUtils.isEmpty(userAddViewQuery.getPassword())){
            return ResultObject.failed(ResultCodeEnum.USERNAME_PASSWORD_NULL);
        }

        //检查用户名唯一性
        boolean isUserNameUnique = userService.checkUserNameUnique(null, userAddViewQuery.getUsername());
        if(!isUserNameUnique){
            return ResultObject.failed(ResultCodeEnum.USER_EXIST);
        }

        //检查手机号是否已存在
        if(!MyStringUtils.isEmpty(userAddViewQuery.getPhoneNumber()) && !userService.checkPhoneUnique(null, userAddViewQuery.getPhoneNumber())){
            return ResultObject.failed(ResultCodeEnum.PHONENUMBER_NOT_UNIQUE);
        }

        //检查邮箱账号是否已存在
        if(!MyStringUtils.isEmpty(userAddViewQuery.getEmail()) && !userService.checkEmailUnique(null, userAddViewQuery.getEmail())){
            return ResultObject.failed(ResultCodeEnum.EMAIL_NOT_UNIQUE);
        }

        //封装vo到dto中，不修改vo中的值
        UserAddServiceQuery userAddServiceQuery = new UserAddServiceQuery();
        BeanUtils.copyProperties(userAddViewQuery, userAddServiceQuery);
        userAddServiceQuery.setPassword(passwordEncoder.encode(userAddViewQuery.getPassword()));

        SysUser sysUser = userService.add(userAddServiceQuery);

        Map<String,SysUser> data = new HashMap<>();
        data.put("sysUser",sysUser);

        return ResultObject.success(data);
    }

    /*
    删除
    */

    @ApiOperation(value = "根据用户id，删除用户")
    @DeleteMapping("/deleteById/{userId}")
    public ResultObject<?> deleteById(@PathVariable(value = "userId", required = false) Long userId,
                                      @RequestParam(defaultValue = "false") Boolean isPermanent){

        int affectRows;
        if (isPermanent){
            affectRows = userService.deleteByIdPermanently(userId);
        } else {
            affectRows = userService.deleteByIdLogically(userId);
        }

        Assert.state(affectRows>0,"影响行数为0，删除失败");

        Map<String,Object> data = new HashMap<>();
        data.put("affectRows",affectRows);

        return ResultObject.success(data);
    }

    @ApiOperation(value = "根据用户id，批量删除用户")
    @DeleteMapping("/deleteByIds")
    public ResultObject<?> deleteByIds(@RequestParam Long[] userIds,
                                       @RequestParam(defaultValue = "false") Boolean isPermanent){

        //必须控制，否则如果传一个空数组，那么整个表的数据会被删除!
        if(userIds == null || userIds.length == 0){
            return ResultObject.failed(ResultCodeEnum.COLLECTION_NULL);
        }

        int affectRows;
        if (isPermanent){
            affectRows = userService.deleteByIdsPermanently(userIds);
        }else {
            affectRows = userService.deleteByIdsLogically(Arrays.asList(userIds));
        }

        Map<String,Object> data = new HashMap<>();
        data.put("影响行数",affectRows);

        return ResultObject.success(ResultCodeEnum.SUCCESS, data);
    }

    /*
    恢复
    */

    @ApiOperation(value = "根据用户id，恢复逻辑删除的用户")
    @PutMapping("/recoverById/{userId}")
    public ResultObject<?> recoverDeletedUserById(@PathVariable("userId") Long userId){

        Long rows = userService.recoverById(userId);

        Map<String,Long> data = new HashMap<>();
        data.put("影响行数:",rows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation(value = "根据用户id，批量恢复逻辑删除的用户")
    @PutMapping("/recoverByIds")
    public ResultObject<?> recoverDeletedUserByIds(@RequestParam(required = false) Long[] userIds){

        Long rows = userService.recoverByIds(userIds);

        Map<String,Long> data = new HashMap<>();
        data.put("影响行数:",rows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    @ApiOperation(value = "重置用户密码")
    @PutMapping(value = "/resetPwd/{userId}")
    public ResultObject<?> resetPwd(@PathVariable("userId") Long userId,
                                    @RequestParam String newPassword){

        int affectRows = userService.resetPwd(userId, newPassword);

        if (affectRows<=0){
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }

        Map<String,Integer> data = new HashMap<>();
        data.put("影响行数",affectRows);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }

    /*
    更新
    */

    @ApiOperation(value = "根据用户id，更新用户基本信息")
    @PutMapping("/update/{userId}")
    public ResultObject<?> updateById(@PathVariable("userId") Long userId,
                                      @Valid UserUpdateViewQuery userUpdateViewQuery){

        //校验userId
        if (userId == null || userId <= 0){
            return ResultObject.failed(ResultCodeEnum.WRONG_USERID);
        }

        //是否所有参数都为null
        if (
                MyStringUtils.isEmpty(userUpdateViewQuery.getNickname())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getRealName())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getSex())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getEmail())
                        && MyObjectUtils.isNull(userUpdateViewQuery.getBirthday())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getPhoneNumber())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getAvatar())
                        && MyStringUtils.isEmpty(userUpdateViewQuery.getRemark())
                        && MyObjectUtils.isNull(userUpdateViewQuery.getEnabled())
        ){
            return ResultObject.success(ResultCodeEnum.SUCCESS_NOACTION);
        }

        //校验用户名唯一性
        String username = userUpdateViewQuery.getUsername();
        if (username!=null){
            boolean isUserNameUnique = userService.checkUserNameUnique(userId,username);

            if (!isUserNameUnique){
                return ResultObject.failed(ResultCodeEnum.USER_EXIST);
            }
        }

        //校验email唯一性
        String email = userUpdateViewQuery.getEmail();
        if (email!=null){
            boolean isEmailUnique = userService.checkEmailUnique(userId,email);

            if (!isEmailUnique){
                return ResultObject.failed(ResultCodeEnum.EMAIL_NOT_UNIQUE);
            }
        }

        //校验手机号唯一性
        String phoneNumber = userUpdateViewQuery.getPhoneNumber();
        if (phoneNumber!=null){
            boolean isPhoneUnique = userService.checkPhoneUnique(userId,phoneNumber);

            if (!isPhoneUnique){
                return ResultObject.failed(ResultCodeEnum.PHONENUMBER_NOT_UNIQUE);
            }
        }

        //将vo转换为dto
        UserUpdateServiceQuery userUpdateServiceQuery = new UserUpdateServiceQuery();
        BeanUtils.copyProperties(userUpdateViewQuery, userUpdateServiceQuery);

        int affectRows;
        affectRows = userService.updateById(userId, userUpdateServiceQuery);

        Assert.state(affectRows>0,"更新用户基本信息失败");

        Map<String,Object> data = new HashMap<>();
        data.put("影响行数",affectRows);

        return ResultObject.success(data);
    }

    /**
     * 为用户分配角色
     * @return
     */
    @ApiOperation("为用户分配角色")
    @PutMapping("/assignRoles/{userId}")
    public ResultObject<?> assignRoles(@PathVariable("userId") Long userId,
                                       @RequestParam(required = false) Long[] roleIds){

        //校验roleIds
        if (roleIds != null && roleIds.length > 0){
            for(Long roleId:roleIds){
                if (roleId<=0){
                    return ResultObject.failed(ResultCodeEnum.FAILED_NOACTION);
                }
            }
        }

        if (roleIds==null || roleIds.length<=0){
            return ResultObject.failed(ResultCodeEnum.FAILED_NOACTION);
        }

        //删除用户与角色的关联
        int rt1 = userRoleService.deleteByUserIdPermanently(userId);

        List<UserRole> userRoleList = new ArrayList<>();
        for (Long roleId : roleIds) {
            userRoleList.add(new UserRole(userId, roleId));
        }

        //新增用户与角色关联
        Integer rt2 = userRoleService.addBatch(userRoleList);
        Assert.state(rt2>0,"更新用户角色信息失败");

        return ResultObject.success();
    }

    /*
    查询
    */

    @ApiOperation(value = "查询用户列表")
    @GetMapping("/list")
    public ResultObject<?> list(
            @RequestParam(defaultValue = "1") int pageNum,
            @RequestParam(defaultValue = "5") int pageSize,
            UserListViewQuery userListViewQuery){

        //将vo转化为dto
        UserListServiceQuery userListServiceQuery = new UserListServiceQuery();
        if (userListViewQuery != null){
            BeanUtils.copyProperties(userListViewQuery, userListServiceQuery);
        }

        this.startPage();
        List<UserSelectDTO> userSelectDTOs = userService.list(userListServiceQuery);

        TableData tableData = this.getTableData(userSelectDTOs);

        return ResultObject.success(ResultCodeEnum.SUCCESS,tableData);
    }

    @ApiOperation(value = "根据用户id获取用户信息")
    @GetMapping("/query/{userId}")
    public ResultObject<?> query(@PathVariable("userId") Long userId){

        SysUser sysUser = userService.queryById(userId);

        UserSelectDTO userSelectDTO = null;
        if (sysUser != null){
            userSelectDTO = new UserSelectDTO();
            BeanUtils.copyProperties(sysUser,userSelectDTO);
        }

        if (userSelectDTO == null){
            return ResultObject.failed(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Map<String,UserSelectDTO> data = new HashMap<>();
        data.put("user",userSelectDTO);

        return ResultObject.success(ResultCodeEnum.SUCCESS,data);
    }
}
