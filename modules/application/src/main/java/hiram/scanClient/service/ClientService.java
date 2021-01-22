package hiram.scanClient.service;

import hiram.image.pojo.query.BultrasoundAddServiceQuery;
import hiram.image.pojo.query.DescriptionBultrasoundAddServiceQuery;
import hiram.image.pojo.query.InfraredDescriptionAddServiceQuery;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @Author: HiramHe
 * @Date: 2020/9/25 20:51
 * @Description: ""
 */

public interface ClientService {

    Boolean upload(MultipartFile infraredImage,
                   MultipartFile bUltrasoundImage,
                   InfraredAddServiceQuery infraredAddServiceQuery,
                   InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery,
                   BultrasoundAddServiceQuery bUltrasoundAddServiceQuery,
                   DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery
                   );

    Boolean saveFile2Local(MultipartFile file, String fileName, String directoryStr) throws IOException;

    Boolean deleteFileFromLocal(String fileName, String directoryStr);
}
