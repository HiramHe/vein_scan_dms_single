package hiram.oss.controller;

import hiram.oss.pojo.dto.OssResult;
import hiram.oss.service.OssService;
import hiram.pojo.vo.ResultObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 21:37
 * @Description: ""
 */

@Api(tags = "OSS模块-对象存储接口")
@RestController
@RequestMapping("/oss")
public class OssController {

    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传文件", hidden = true)
    @PostMapping("/upload")
    public ResultObject<?> saveFile(@RequestParam MultipartFile file){
        OssResult ossResult = ossService.saveFile(file);

        return ResultObject.success(ossResult);
    }

}
