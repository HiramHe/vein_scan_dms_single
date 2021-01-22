package hiram.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import hiram.constant.Constants;
import hiram.oss.pojo.dto.OssResult;
import hiram.oss.properties.AliyunOssFileProperties;
import hiram.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 21:00
 * @Description: ""
 */

@Service
public class OssServiceImpl implements OssService {

    @Autowired
    private AliyunOssFileProperties aliyunOssFileProperties;

    @Override
    public OssResult saveFile(MultipartFile file) {

        String endpoint = aliyunOssFileProperties.getEndpoint();
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = aliyunOssFileProperties.getAccessKeyId();
        String accessKeySecret = aliyunOssFileProperties.getAccessKeySecret();
        String bucketName = aliyunOssFileProperties.getBucketName();

        OSS ossClient = null;
        try {
            // 创建OSSClient实例。
            ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            String filename = file.getOriginalFilename();

            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            filename = uuid + "-" + filename;

            //把文件按照日期进行分类
            LocalDate now = LocalDate.now();
            String pattern = "yyyy/MM/dd";
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);

            String datePath = now.format(formatter) + Constants.LEFT_SLASH;

            String key = datePath + filename;

            //第二个参数：上传到oss的文件路径和文件名称
            ossClient.putObject(bucketName, key, inputStream);

            // 把上传之后的文件路径返回，只能自己手动拼接
            // (eg:https://vein-scan-system-010.oss-cn-beijing.aliyuncs.com/tmp_scan.png)
            String path = Constants.HTTPS + bucketName + Constants.POINT + endpoint + Constants.LEFT_SLASH + datePath;
            String url = path + filename;

            return new OssResult(path,filename,url);

        } catch (Exception e) {
            e.printStackTrace();

            return null;
        } finally {
            // 关闭OSSClient。
            if (ossClient != null){
                ossClient.shutdown();
            }
        }

    }

}
