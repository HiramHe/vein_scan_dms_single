package hiram.inspectionReport.controller;

import hiram.pojo.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HiramHe
 * @Date: 2021/1/7 11:15
 * @Description: ""
 */

@Api(tags = "报告模块-检查报告接口")
@RestController
@RequestMapping("/inspection/report")
public class InspectionReportController {

    @ApiOperation(value = "新增记录", hidden = true)
    @PostMapping("/add")
    public ResultObject<?> add(){

        return null;
    }

}
