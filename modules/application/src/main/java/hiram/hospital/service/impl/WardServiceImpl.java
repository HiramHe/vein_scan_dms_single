package hiram.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.hospital.mapper.WardMapper;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.po.Ward;
import hiram.hospital.pojo.query.WardListViewQuery;
import hiram.hospital.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 19:26
 * @Description: ""
 */

@Service
public class WardServiceImpl implements WardService {

    @Autowired
    private WardMapper wardMapper;

    @Override
    public Ward getWardById(Long wardId) {
        return wardMapper.selectById(wardId);
    }

    @Override
    public List<Bed> getBedsOfWardById(Long wardId) {
        return wardMapper.getBedsOfWardById(wardId);
    }

    @Override
    public Page<Ward> list(Long pageNum, Long pageSize, WardListViewQuery wardListViewQuery) {

        Page<Ward> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Ward> queryWrapper = new QueryWrapper<>();

        Long wardId = wardListViewQuery.getWardId();
        Long wardNumber = wardListViewQuery.getWardNumber();
        String wardName = wardListViewQuery.getWardName();

        queryWrapper.eq(wardId != null,"ward_id",wardId);
        queryWrapper.eq(wardNumber!=null,"ward_number",wardNumber);
        queryWrapper.like(StringUtils.hasLength(wardName),"ward_name",wardName);

        Page<Ward> pageRes = wardMapper.selectPage(page, queryWrapper);

        return pageRes;
    }
}
