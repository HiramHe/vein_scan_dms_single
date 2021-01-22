package hiram.scanClient.controller;

import hiram.enums.ResultCodeEnum;
import hiram.image.pojo.query.BultrasoundAddServiceQuery;
import hiram.image.pojo.query.DescriptionBultrasoundAddServiceQuery;
import hiram.image.pojo.query.InfraredDescriptionAddServiceQuery;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import hiram.pojo.vo.ResultObject;
import hiram.scanClient.pojo.query.UploadViewQuery;
import hiram.scanClient.service.ClientService;
import hiram.utils.FileUtils;
import hiram.utils.MyStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

/**
 * @Author: HiramHe
 * @Date: 2020/7/20 17:43
 * @Description: ""
 */

/*
@RequestBody：前端的content-type:"application/json",
表单提交：前端的content-type:"application/json",
 */

@Api(tags = "客户端模块-扫描客户端接口")
@RestController
@RequestMapping("/client")
public class ClientController {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    private ClientService clientService;

    @ApiOperation(value = "上传图像")
    @PutMapping("/upload")
    public ResultObject<?> uploadWithSingleBUltra(@RequestParam MultipartFile infraredImage,
                                                  MultipartFile bultrasoundImage,
                                                  @Valid UploadViewQuery uploadViewQuery){

        //红外图像不得为空
        if (infraredImage == null || infraredImage.isEmpty()){
            return ResultObject.failed(ResultCodeEnum.FILE_UPLOAD_INFRARED_EMPTY);
        }

        //校验是否是图片
        String infraredImageFileName = infraredImage.getOriginalFilename();
        if(!FileUtils.isImage(infraredImageFileName)){
            return ResultObject.failed(ResultCodeEnum.FILE_FORMAT_NOT_SUPPORT);
        }

        //校验坐标
        Long descriptionXCoordinate = uploadViewQuery.getDescriptionXCoordinate();
        Long descriptionYCoordinate = uploadViewQuery.getDescriptionYCoordinate();
        if ((descriptionXCoordinate==null && descriptionYCoordinate!=null) || (descriptionXCoordinate!=null && descriptionYCoordinate==null)){
            return ResultObject.failed(ResultCodeEnum.COORDINATE_WRONG);
        }

        Long bUltrasoundXCoordinate = uploadViewQuery.getBultrasoundXCoordinate();
        Long bUltrasoundYCoordinate = uploadViewQuery.getBultrasoundYCoordinate();
        if ((bUltrasoundXCoordinate==null && bUltrasoundYCoordinate!=null) || (bUltrasoundXCoordinate!=null && bUltrasoundYCoordinate==null)){
            return ResultObject.failed(ResultCodeEnum.COORDINATE_WRONG);
        }

        //构建红外图像信息
        InfraredAddServiceQuery infraredAddServiceQuery = new InfraredAddServiceQuery();
        infraredAddServiceQuery.setPerspective(uploadViewQuery.getPerspective());
        infraredAddServiceQuery.setScanTime(uploadViewQuery.getScanTime());
        infraredAddServiceQuery.setPatientId(uploadViewQuery.getPatientId());
        infraredAddServiceQuery.setUserId(uploadViewQuery.getUserId());

        //构建B超图像信息
        BultrasoundAddServiceQuery bultrasoundAddServiceQuery = null;
        if(bultrasoundImage!=null) {
            String bUltrasoundImageFileName = bultrasoundImage.getOriginalFilename();
            if (!FileUtils.isImage(bUltrasoundImageFileName)){
                return ResultObject.failed(ResultCodeEnum.FILE_FORMAT_NOT_SUPPORT);
            }

            bultrasoundAddServiceQuery = new BultrasoundAddServiceQuery();
        }

        //构建红外图像描述信息
        String description = uploadViewQuery.getDescription();
        String severityLevel = uploadViewQuery.getSeverityLevel();
        InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery = null;
        if (!MyStringUtils.isEmpty(description) || !MyStringUtils.isEmpty(severityLevel)
                || (descriptionXCoordinate!=null && descriptionYCoordinate!=null)){

            infraredDescriptionAddServiceQuery = new InfraredDescriptionAddServiceQuery();
            infraredDescriptionAddServiceQuery.setDescription(description);
            infraredDescriptionAddServiceQuery.setSeverityLevel(severityLevel);
            infraredDescriptionAddServiceQuery.setDescriptionXCoordinate(descriptionXCoordinate);
            infraredDescriptionAddServiceQuery.setDescriptionYCoordinate(descriptionYCoordinate);
        }

        //构建描述中的B超信息
        DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery = null;
        if (bultrasoundImage!=null || (bUltrasoundXCoordinate!=null && bUltrasoundYCoordinate!=null)){
            descriptionBUltrasoundAddServiceQuery = new DescriptionBultrasoundAddServiceQuery();
            descriptionBUltrasoundAddServiceQuery.setBultrasoundXCoordinate(bUltrasoundXCoordinate);
            descriptionBUltrasoundAddServiceQuery.setBultrasoundYCoordinate(bUltrasoundYCoordinate);
        }

        //保存数据
        Boolean isSuccessFul = clientService.upload(infraredImage, bultrasoundImage, infraredAddServiceQuery, infraredDescriptionAddServiceQuery, bultrasoundAddServiceQuery, descriptionBUltrasoundAddServiceQuery);

        //返回结果
        if (isSuccessFul){
            return ResultObject.success(ResultCodeEnum.SUCCESS_UPLOAD);
        } else {
            return ResultObject.failed();
        }

    }

//    @ApiOperation(value = "单红外图像多B超图像上传")
//    @PutMapping("/uploadWithMultiBUltra")
//    public void uploadWithMultiBUltra(@RequestParam MultipartFile infraredImage,
//                                      MultipartFile[] bultrasoundImages,
//                                      String perspective,
//                                      @ApiParam(value = "yyyy-MM-dd HH:mm:ss")
//                                      @RequestParam(required = false)
//                                      @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
//                                              LocalDateTime scanTime,
//                                      @RequestParam Long patientId,
//                                      @RequestParam(required = false) Long userId,
//                                      String description,
//                                      String severityLevel,
//                                      Long descriptionXCoordinate,
//                                      Long descriptionYCoordinate
////                                      Coordinate[] bultrasoundCoordinates
//                                      ){
//
//        System.out.println();
//
//    }
}
