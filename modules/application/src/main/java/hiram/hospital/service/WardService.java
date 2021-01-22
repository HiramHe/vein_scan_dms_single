package hiram.hospital.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.WardListViewQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 19:26
 * @Description: ""
 */


public interface WardService {

    Ward getWardById(Long wardId);

    List<Bed> getBedsOfWardById(Long wardId);

    Page<Ward> list(Long pageNum, Long pageSize, WardListViewQuery wardListViewQuery);
}
