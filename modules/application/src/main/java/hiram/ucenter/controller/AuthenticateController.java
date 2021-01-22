package hiram.ucenter.controller;

import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import hiram.pojo.vo.ResultObject;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.ucenter.pojo.query.LoginViewQuery;
import hiram.ucenter.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author: HiramHe
 * @Date: 2020/5/19 11:57
 * @Description: ""
 */

@Api(tags = "用户中心模块-认证接口")
@RestController
@RequestMapping("/ucenter")
public class AuthenticateController {

    Log logger = LogFactory.getLog(getClass());

    @Autowired
    private LoginService loginService;

    /*
    关闭spring security后起作用。
     */
    @ApiOperation(value = "登录")
    @PostMapping("/login")
    public ResultObject<?> login(@Valid LoginViewQuery loginViewQuery){

        if(!StringUtils.hasLength(loginViewQuery.getUsername()) || !StringUtils.hasLength(loginViewQuery.getPassword())){
            return ResultObject.failed(ResultCodeEnum.USERNAME_PASSWORD_NULL);
        }

        String token = loginService.login(loginViewQuery.getUsername(), loginViewQuery.getPassword());

        if(token != null){
            Map<String,String> data = new HashMap<>();
            data.put("token",token);

            return ResultObject.success(ResultCodeEnum.SUCCESS_AUTHENTICATE,data);
        }else {
            return ResultObject.failed(ResultCodeEnum.FAILED_AUTHENTICATE);
        }

    }

    @ApiOperation("登出")
    @PutMapping("/logout")
    public ResultObject<?> logout(){

        return null;
    }

}
