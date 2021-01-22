package hiram.image.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.mapper.DescriptionBultrasoundMapper;
import hiram.image.pojo.po.DescriptionBultrasound;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.DescriptionBultrasoundAddServiceQuery;
import hiram.image.pojo.query.DescriptionBultrasoundListViewQuery;
import hiram.image.pojo.query.DescriptionBultrasoundUpdateViewQuery;
import hiram.image.service.DescriptionBultrasoundService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author: HiramHe
 * @Date: 2021/1/6 16:38
 * @Description: ""
 */

@Service
public class DescriptionBultrasoundServiceImpl implements DescriptionBultrasoundService {

    @Autowired
    private DescriptionBultrasoundMapper descriptionBUltrasoundMapper;

    @Override
    public DescriptionBultrasound add(DescriptionBultrasoundAddServiceQuery descriptionBUltrasoundAddServiceQuery) {

        DescriptionBultrasound descriptionBUltrasound = new DescriptionBultrasound();
        BeanUtils.copyProperties(descriptionBUltrasoundAddServiceQuery,descriptionBUltrasound);

        int rt = descriptionBUltrasoundMapper.insert(descriptionBUltrasound);

        Assert.state(rt>0,"插入失败");

        return descriptionBUltrasound;
    }

    @Override
    public Integer deleteByIdPermanently(Long id) {
        return descriptionBUltrasoundMapper.deleteByIdPermanently(id);
    }

    @Override
    public Integer deleteByIdLogically(Long id) {
        return descriptionBUltrasoundMapper.deleteById(id);
    }

    @Override
    public Integer updateById(Long id, DescriptionBultrasoundUpdateViewQuery descriptionBultrasoundUpdateViewQuery) {

        DescriptionBultrasound dbDescriptionBultrasound = descriptionBUltrasoundMapper.selectById(id);

        Assert.notNull(dbDescriptionBultrasound,"数据不存在");

        DescriptionBultrasound descriptionBultrasound = new DescriptionBultrasound();
        descriptionBultrasound.setId(id);
        descriptionBultrasound.setVersion(dbDescriptionBultrasound.getVersion());

        BeanUtils.copyProperties(descriptionBultrasoundUpdateViewQuery,descriptionBultrasound);

        return descriptionBUltrasoundMapper.updateById(descriptionBultrasound);
    }

    @Override
    public DescriptionBultrasound queryById(Long id) {
        return descriptionBUltrasoundMapper.selectById(id);
    }

    @Override
    public Page<DescriptionBultrasound> list(Long pageNum, Long pageSize, DescriptionBultrasoundListViewQuery descriptionBultrasoundListViewQuery) {

        Page<DescriptionBultrasound> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long descriptionId = descriptionBultrasoundListViewQuery.getDescriptionId();
        Long bultrasoundId = descriptionBultrasoundListViewQuery.getBultrasoundId();

        QueryWrapper<DescriptionBultrasound> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(descriptionId!=null,"description_id",descriptionId);
        queryWrapper.eq(bultrasoundId!=null,"bultrasound_id",bultrasoundId);

        Page<DescriptionBultrasound> descriptionBultrasoundPage = descriptionBUltrasoundMapper.selectPage(page, queryWrapper);

        return descriptionBultrasoundPage;
    }
}
