package hiram.acl.controller;

import hiram.pojo.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HiramHe
 * @Date: 2021/1/13 21:49
 * @Description: ""
 */

@Api(tags = "访问控制模块-菜单管理接口")
@RestController
@RequestMapping("/acl/menu")
public class MenuController {

    @ApiOperation(value = "新增菜单", hidden = true)
    @PostMapping("/add")
    public ResultObject<?> add(){
        return null;
    }
}
