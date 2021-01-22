package hiram.image.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.InfraredListViewQuery;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import hiram.image.pojo.query.InfraredUpdateViewQuery;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/21 10:17
 * @Description: ""
 */


public interface InfraredService {

    Infrared add(InfraredAddServiceQuery infraredAddServiceQuery);

    Integer deleteByIdPermanently(Long infraredId);

    Integer deleteByIdLogically(Long infraredId);

    Integer updateById(Long infraredId, InfraredUpdateViewQuery infraredUpdateViewQuery);

    Infrared queryById(Long infraredId);

    Page<Infrared> list(Long pageNum, Long pageSize, InfraredListViewQuery infraredListViewQuery);
}
