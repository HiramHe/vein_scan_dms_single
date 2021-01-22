package hiram.oss.service;

import hiram.oss.pojo.dto.OssResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 21:00
 * @Description: ""
 */

public interface OssService {

    /**
     * 返回url
     * @param file
     * @return
     */
    OssResult saveFile(MultipartFile file);

}
