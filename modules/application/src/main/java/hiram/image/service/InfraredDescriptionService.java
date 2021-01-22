package hiram.image.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.InfraredDescriptionAddServiceQuery;
import hiram.image.pojo.query.InfraredDescriptionListViewQuery;
import hiram.image.pojo.query.InfraredDescriptionUpdateViewQuery;
import io.swagger.models.auth.In;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 16:19
 * @Description: ""
 */

public interface InfraredDescriptionService {

    InfraredDescription add(InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery);

    Integer deleteByIdPermanently(Long descriptionId);

    Integer deleteByIdLogically(Long descriptionId);

    Integer updateById(Long descriptionId, InfraredDescriptionUpdateViewQuery infraredDescriptionUpdateViewQuery);

    InfraredDescription queryById(Long descriptionId);

    Page<InfraredDescription> list(Long pageNum, Long pageSize, InfraredDescriptionListViewQuery infraredDescriptionListViewQuery);
}
