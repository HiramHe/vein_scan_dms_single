package hiram.image.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.BultrasoundAddServiceQuery;
import hiram.image.pojo.query.BultrasoundListViewQuery;
import hiram.image.pojo.query.BultrasoundUpdateViewQuery;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 16:18
 * @Description: ""
 */

public interface BultrasoundService {

    Bultrasound add(BultrasoundAddServiceQuery bUltrasoundAddServiceQuery);

    Integer deleteByIdPermanently(Long bultrasoundId);

    Integer deleteByIdLogically(Long bultrasoundId);

    Integer updateById(Long bultrasoundId, BultrasoundUpdateViewQuery bultrasoundUpdateViewQuery);

    Bultrasound queryById(Long bultrasoundId);

    Page<Bultrasound> list(Long pageNum, Long pageSize, BultrasoundListViewQuery bultrasoundListViewQuery);
}
