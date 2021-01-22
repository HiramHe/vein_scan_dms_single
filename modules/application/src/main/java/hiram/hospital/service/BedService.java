package hiram.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.query.BedAddViewQuery;
import hiram.hospital.pojo.query.BedListViewQuery;
import io.swagger.models.auth.In;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 17:05
 * @Description: ""
 */

public interface BedService {

    Bed add(BedAddViewQuery bedAddViewQuery);

    Integer deleteByIdPermanently(Long bedId);

    Integer deleteByIdLogically(Long bedId);

    Integer invertVacantValue(Long bedId);

    Bed getBedById(Long bedId);

    Page list(Long pageNum, Long pageSize, BedListViewQuery bedListViewQuery);

    Boolean checkBedNumberExist(Long bedNumber);

}
