package hiram.image.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.mapper.InfraredDescriptionMapper;
import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.po.InfraredDescription;
import hiram.image.pojo.query.InfraredDescriptionAddServiceQuery;
import hiram.image.pojo.query.InfraredDescriptionListViewQuery;
import hiram.image.pojo.query.InfraredDescriptionUpdateViewQuery;
import hiram.image.service.InfraredDescriptionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 18:14
 * @Description: ""
 */

@Service
public class InfraredDescriptionServiceImpl implements InfraredDescriptionService {

    @Autowired
    InfraredDescriptionMapper infraredDescriptionMapper;

    @Override
    public InfraredDescription add(InfraredDescriptionAddServiceQuery infraredDescriptionAddServiceQuery) {

        InfraredDescription infraredDescription = new InfraredDescription();
        BeanUtils.copyProperties(infraredDescriptionAddServiceQuery,infraredDescription);

        int rt = infraredDescriptionMapper.insert(infraredDescription);

        Assert.state(rt>0,"插入失败");

        return infraredDescription;
    }

    @Override
    public Integer deleteByIdPermanently(Long descriptionId) {
        return infraredDescriptionMapper.deleteByIdPermanently(descriptionId);
    }

    @Override
    public Integer deleteByIdLogically(Long descriptionId) {
        return infraredDescriptionMapper.deleteById(descriptionId);
    }

    @Override
    public Integer updateById(Long descriptionId, InfraredDescriptionUpdateViewQuery infraredDescriptionUpdateViewQuery) {

        InfraredDescription dbInfraredDescription = infraredDescriptionMapper.selectById(descriptionId);

        Assert.notNull(dbInfraredDescription,"数据不存在");

        InfraredDescription infraredDescription = new InfraredDescription();
        infraredDescription.setDescriptionId(descriptionId);
        infraredDescription.setVersion(dbInfraredDescription.getVersion());

        BeanUtils.copyProperties(infraredDescriptionUpdateViewQuery,infraredDescription);

        return infraredDescriptionMapper.updateById(infraredDescription);
    }

    @Override
    public InfraredDescription queryById(Long descriptionId) {
        return infraredDescriptionMapper.selectById(descriptionId);
    }

    @Override
    public Page<InfraredDescription> list(Long pageNum, Long pageSize, InfraredDescriptionListViewQuery infraredDescriptionListViewQuery) {

        Page<InfraredDescription> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long infraredId = infraredDescriptionListViewQuery.getInfraredId();

        QueryWrapper<InfraredDescription> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(infraredId!=null,"infrared_id",infraredId);

        Page<InfraredDescription> infraredDescriptionPage = infraredDescriptionMapper.selectPage(page, queryWrapper);

        return infraredDescriptionPage;
    }
}
