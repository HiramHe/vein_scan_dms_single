package hiram.image.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.pojo.po.DescriptionBultrasound;
import hiram.image.pojo.query.DescriptionBultrasoundAddServiceQuery;
import hiram.image.pojo.query.DescriptionBultrasoundListViewQuery;
import hiram.image.pojo.query.DescriptionBultrasoundUpdateViewQuery;

/**
 * @Author: HiramHe
 * @Date: 2021/1/6 16:38
 * @Description: ""
 */

public interface DescriptionBultrasoundService {
    DescriptionBultrasound add(DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery);

    Integer deleteByIdPermanently(Long id);

    Integer deleteByIdLogically(Long id);

    Integer updateById(Long id, DescriptionBultrasoundUpdateViewQuery descriptionBultrasoundUpdateViewQuery);

    DescriptionBultrasound queryById(Long id);

    Page<DescriptionBultrasound> list(Long pageNum, Long pageSize, DescriptionBultrasoundListViewQuery descriptionBultrasoundListViewQuery);
}
