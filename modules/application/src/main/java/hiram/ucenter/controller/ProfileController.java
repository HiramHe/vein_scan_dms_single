package hiram.ucenter.controller;

import hiram.constant.Constants;
import hiram.controller.BaseController;
import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.po.SysUser;
import hiram.redis.RedisService;
import hiram.springSecurity.pojo.dto.SecurityUser;
import hiram.ucenter.pojo.query.ProfileUpdateViewQuery;
import hiram.acl.pojo.query.UserUpdateServiceQuery;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.acl.pojo.vo.RoleVO;
import hiram.acl.pojo.vo.UserVO;
import hiram.acl.service.RoleService;
import hiram.acl.service.UserService;
import hiram.token.TokenService;
import hiram.utils.MyStringUtils;
import hiram.utils.MyObjectUtils;
import hiram.utils.ServletUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: HiramHe
 * @Date: 2020/5/14 21:32
 * @Description: ""
 */

@RestController
@RequestMapping("/profile")
@Api(tags = "用户中心模块-个人信息接口")
public class ProfileController extends BaseController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder encoder;

    /*
    done 功能实现
    done 错误提示
     */
    @ApiOperation(value = "获取个人信息")
    @GetMapping("/getInfo")
    public ResultObject<?> profile() throws Exception {

        /*
        如果有token，则已经在request中携带了。
        抛出的异常会被全局异常处理器捕获并处理。
         */
        String token = tokenService.getTokenFromRequest(request, Constants.TOKEN_HEADER);
        String uuid = tokenService.getValueFromToken(token, Constants.LOGIN_USER_KEY);

        CustomUserDetails customUserDetails = redisService.getCacheObject(uuid);

        ResultObject<Map<String,Object>> resultObject;
        if(customUserDetails != null){
            SecurityUser securityUser = customUserDetails.getSecurityUser();

            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(securityUser, userVO);

            List<SysRole> roles = roleService.getRolesByUsername(securityUser.getUsername());
            List<RoleVO> roleVOs = new ArrayList<>();
            for (SysRole sysRole:roles){
                RoleVO roleVO = new RoleVO();
                BeanUtils.copyProperties(sysRole,roleVO);
                roleVOs.add(roleVO);
            }

            Map<String,Object> data = new HashMap<>();
            data.put("user",userVO);
            data.put("roles",roleVOs);

            resultObject = ResultObject.success(ResultCodeEnum.SUCCESS,data);
        } else {
            resultObject = ResultObject.failed(ResultCodeEnum.LOGIN_EXPIRED);
        }

        return resultObject;
    }

    @ApiOperation(value = "修改密码")
    @PutMapping(value = "/updatePassword")
    public ResultObject<?> updatePassword(String oldPassword, String newPassword) throws Exception {
        CustomUserDetails customUserDetails = tokenService.getCustomUserDetailsByTokenInRequest(request);

        Long userId = customUserDetails.getSecurityUser().getUserId();
        String password = customUserDetails.getPassword();
        String uuid = customUserDetails.getUuid();

        if (!encoder.matches(oldPassword,password)) {
            return ResultObject.failed(ResultCodeEnum.OLDPASSWORD_ERROR);
        }
        if( encoder.matches(newPassword,password)){
            return ResultObject.failed(ResultCodeEnum.NEWPASSWORD_SAME_ERROR);
        }

        if( userService.resetPwd(userId,newPassword) > 0){
            //更新缓存用户密码
            customUserDetails.getSecurityUser().setPassword(encoder.encode(newPassword));

            refreshRedis(uuid, customUserDetails);

            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }

        return ResultObject.failed(ResultCodeEnum.RESETPASSWORD_ERROR);
    }

    /*
    done 修改数据库
    done 修改缓存
    done 错误处理
     */
    @ApiOperation(value = "修改个人信息")
    @PutMapping(value = "/updateProfile")
    public ResultObject<?> updateProfile(@Valid ProfileUpdateViewQuery profileUpdateViewQuery) throws Exception {

        //校验所有参数是否都为null
        if (
                MyStringUtils.isEmpty(profileUpdateViewQuery.getNickname())
                        && MyStringUtils.isEmpty(profileUpdateViewQuery.getRealName())
                        && MyStringUtils.isEmpty(profileUpdateViewQuery.getSex())
                        && MyStringUtils.isEmpty(profileUpdateViewQuery.getEmail())
                        && MyObjectUtils.isNull(profileUpdateViewQuery.getBirthday())
                        && MyStringUtils.isEmpty(profileUpdateViewQuery.getPhoneNumber())
                        && MyStringUtils.isEmpty(profileUpdateViewQuery.getRemark())
        ){
            return ResultObject.success(ResultCodeEnum.SUCCESS_NOACTION);
        }

        CustomUserDetails customUserDetails = tokenService.getCustomUserDetailsByTokenInRequest(request);
        Long userId = customUserDetails.getSecurityUser().getUserId();
        String uuid = customUserDetails.getUuid();

        //校验用户名唯一性
        String username = profileUpdateViewQuery.getUsername();
        if (username!=null){
            boolean isUserNameUnique = userService.checkUserNameUnique(userId,username);

            if (!isUserNameUnique){
                return ResultObject.failed(ResultCodeEnum.USER_EXIST);
            }
        }

        //校验email唯一性
        String email = profileUpdateViewQuery.getEmail();
        if (email!=null){
            boolean isEmailUnique = userService.checkEmailUnique(userId,email);

            if (!isEmailUnique){
                return ResultObject.failed(ResultCodeEnum.EMAIL_NOT_UNIQUE);
            }
        }

        //校验手机号唯一性
        String phoneNumber = profileUpdateViewQuery.getPhoneNumber();
        if (phoneNumber!=null){
            boolean isPhoneUnique = userService.checkPhoneUnique(userId,phoneNumber);

            if (!isPhoneUnique){
                return ResultObject.failed(ResultCodeEnum.PHONENUMBER_NOT_UNIQUE);
            }
        }

        //将vo转换为dto
        UserUpdateServiceQuery userUpdateServiceQuery = new UserUpdateServiceQuery();
        BeanUtils.copyProperties(profileUpdateViewQuery, userUpdateServiceQuery);

        int rt;
        try {
            rt = userService.updateById(userId, userUpdateServiceQuery);
        } catch (Exception e) {
            return ResultObject.failed(ResultCodeEnum.FAILED);
        }

        //更新缓存
        if (rt > 0){

            String nickname = profileUpdateViewQuery.getNickname();
            String realName = profileUpdateViewQuery.getRealName();
            String sex = profileUpdateViewQuery.getSex();
            LocalDate birthday = profileUpdateViewQuery.getBirthday();
            String remark = profileUpdateViewQuery.getRemark();

            if (!MyStringUtils.isEmpty(username)){
                customUserDetails.getSecurityUser().setUsername(username);
            }
            if (!MyStringUtils.isEmpty(nickname)){
                customUserDetails.getSecurityUser().setNickname(nickname);
            }
            if (!MyStringUtils.isEmpty(realName)){
                customUserDetails.getSecurityUser().setRealName(realName);
            }
            if (!MyStringUtils.isEmpty(sex)){
                customUserDetails.getSecurityUser().setSex(sex);
            }
            if (!MyObjectUtils.isNull(birthday)){
                customUserDetails.getSecurityUser().setBirthday(birthday);
            }
            if (!MyStringUtils.isEmpty(email)){
                customUserDetails.getSecurityUser().setEmail(email);
            }
            if (!MyStringUtils.isEmpty(phoneNumber)){
                customUserDetails.getSecurityUser().setPhoneNumber(phoneNumber);
            }
            if (!MyStringUtils.isEmpty(remark)){
                customUserDetails.getSecurityUser().setRemark(remark);
            }

            refreshRedis(uuid, customUserDetails);

            return ResultObject.success(ResultCodeEnum.SUCCESS);
        }

        return ResultObject.failed(ResultCodeEnum.FAILED);
    }

    /*
    todo 功能实现
     */
    @ApiOperation(value = "用户头像",hidden = true)
    @PutMapping("/avatar")
    public ResultObject<?> avatar(@RequestParam MultipartFile file){


        return ResultObject.failed(ResultCodeEnum.FUNCTION_TODO);
    }


    private void refreshRedis(String key, CustomUserDetails customUserDetails){

        LocalDateTime expireDateTime = customUserDetails.getExpireDateTime();
        LocalDateTime now = LocalDateTime.now();
        ZoneOffset offset = OffsetDateTime.now().getOffset();

        long expireMillis = expireDateTime.toInstant(offset).toEpochMilli();
        long nowMillis = now.toInstant(offset).toEpochMilli();

        long timeout = expireMillis - nowMillis;

        if (timeout>0){
            redisService.setCacheObject(key, customUserDetails, timeout, TimeUnit.MILLISECONDS);
        } else {
            redisService.setCacheObject(key, customUserDetails,0L, TimeUnit.MILLISECONDS);
        }
    }
}
