package hiram.scanClient.service.impl;

import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.DescriptionBultrasound;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.BultrasoundAddServiceQuery;
import hiram.image.pojo.query.DescriptionBultrasoundAddServiceQuery;
import hiram.image.pojo.query.InfraredDescriptionAddServiceQuery;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import hiram.image.service.BultrasoundService;
import hiram.image.service.DescriptionBultrasoundService;
import hiram.image.service.InfraredDescriptionService;
import hiram.image.service.InfraredService;
import hiram.oss.pojo.dto.OssResult;
import hiram.oss.service.OssService;
import hiram.properties.ImageProperties;
import hiram.scanClient.service.ClientService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Author: HiramHe
 * @Date: 2020/9/25 20:53
 * @Description: ""
 */

@Service
public class ClientServiceImpl implements ClientService {

    Log logger = LogFactory.getLog(getClass());

    //上传时，需要知道保存在哪里
    @Autowired
    private ImageProperties imageProperties;

    @Autowired
    private InfraredService infraredService;

    @Autowired
    private BultrasoundService bultrasoundService;

    @Autowired
    private InfraredDescriptionService infraredDescriptionService;

    @Autowired
    private DescriptionBultrasoundService descriptionBultrasoundService;

    @Autowired
    private OssService ossService;

    @Override
    @Transactional
    public Boolean upload(MultipartFile infraredImage,
                          MultipartFile bUltrasoundImage,
                          InfraredAddServiceQuery infraredAddServiceQuery,
                          InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery,
                          BultrasoundAddServiceQuery bUltrasoundAddServiceQuery,
                          DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery) {

        infraredAddServiceQuery.setPath(imageProperties.getInfraredDirectory());

        Assert.notNull(infraredImage,"红外图像不得为空");

        //保存红外图像
        String infraredFilename;
        if (imageProperties.getEnableAliyunOss()){

            OssResult ossResult = ossService.saveFile(infraredImage);

            infraredAddServiceQuery.setPath(ossResult.getPath());

            infraredFilename = ossResult.getFilename();
            infraredAddServiceQuery.setFilename(infraredFilename);

            infraredAddServiceQuery.setUrl(ossResult.getUrl());
        }else {

            String infraredDirectory = imageProperties.getInfraredDirectory();

            try {

                infraredFilename = infraredImage.getOriginalFilename();

                saveFile2Local(infraredImage, infraredFilename, infraredDirectory);

            } catch (IOException e) {
                return false;
            }

            infraredAddServiceQuery.setPath(infraredDirectory);

            String infraredUrl = infraredDirectory + infraredAddServiceQuery.getFilename();
            infraredAddServiceQuery.setUrl(infraredUrl);
        }


        //保存B超图像
        String bUltrasoundImageFilename;
        if (bUltrasoundImage != null){
            if (imageProperties.getEnableAliyunOss()){

                OssResult ossResult = ossService.saveFile(bUltrasoundImage);

                bUltrasoundAddServiceQuery.setPath(ossResult.getPath());

                bUltrasoundImageFilename = ossResult.getFilename();
                bUltrasoundAddServiceQuery.setFilename(bUltrasoundImageFilename);

                bUltrasoundAddServiceQuery.setUrl(ossResult.getUrl());
            } else {

                String ultrasoundDirectory = imageProperties.getUltrasoundDirectory();

                try {
                    saveFile2Local(bUltrasoundImage, bUltrasoundAddServiceQuery.getFilename(),ultrasoundDirectory);
                } catch (IOException e) {
                    //删除已保存的红外图片
                    deleteFileFromLocal(infraredAddServiceQuery.getFilename(), infraredAddServiceQuery.getPath());

                    return false;
                }

                bUltrasoundAddServiceQuery.setPath(ultrasoundDirectory);

                String UltrasoundUrl = ultrasoundDirectory + bUltrasoundAddServiceQuery.getFilename();
                bUltrasoundAddServiceQuery.setUrl(UltrasoundUrl);

            }
        }

        //插入红外图像记录
        Infrared infrared = infraredService.add(infraredAddServiceQuery);

        Assert.notNull(infrared,"红外图像记录插入失败");

        //插入B超图像记录
        Bultrasound bultrasound = null;
        if (bUltrasoundImage != null){

            bultrasound = bultrasoundService.add(bUltrasoundAddServiceQuery);

            Assert.notNull(bultrasound,"B超图像记录插入失败");
        }

        //插入红外图像描述
        InfraredDescription infraredDescription = null;
        if (infraredDescriptionAddServiceQuery != null){

            infraredDescriptionAddServiceQuery.setInfraredId(infrared.getInfraredId());

            infraredDescription = infraredDescriptionService.add(infraredDescriptionAddServiceQuery);

            Assert.notNull(infraredDescription,"插入描述失败");
        }

        //插入描述中属于B超的那一部分
        DescriptionBultrasound descriptionBultrasound;
        if (descriptionBUltrasoundAddServiceQuery != null){

            if (bultrasound != null){
                descriptionBUltrasoundAddServiceQuery.setBultrasoundId(bultrasound.getBultrasoundId());
            }

            if (infraredDescription != null){
                descriptionBUltrasoundAddServiceQuery.setDescriptionId(infraredDescription.getDescriptionId());
            }

            descriptionBultrasound = descriptionBultrasoundService.add(descriptionBUltrasoundAddServiceQuery);

            Assert.notNull(descriptionBultrasound,"插入描述之B超部分失败");
        }

        return true;

    }

    @Override
    public Boolean saveFile2Local(MultipartFile file,String fileName, String directoryStr) throws IOException {

        //判断文件夹是否存在，不存在则新建
        File directory = new File(directoryStr);
        if(!directory.exists() && !directory.isDirectory() ){
            boolean isSuccessful = directory.mkdirs();
        }

        //将文件写入磁盘
        File fileDest = new File(directoryStr + fileName);
        file.transferTo(fileDest);

        return true;

    }

    @Override
    public Boolean deleteFileFromLocal(String fileName, String directoryStr)  {

        String fileDest = directoryStr + fileName;
        File file = new File(fileDest);

        boolean deleted = true;
        if (file.exists()){
            deleted = file.delete();
        } else {
            if (logger.isDebugEnabled()){
                logger.debug("文件【" + fileDest + "】不存在");
            }
        }

        return deleted;
    }
}
