package hiram.image.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.image.mapper.BultrasoundMapper;
import hiram.image.pojo.po.Bultrasound;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.BultrasoundAddServiceQuery;
import hiram.image.pojo.query.BultrasoundListViewQuery;
import hiram.image.pojo.query.BultrasoundUpdateViewQuery;
import hiram.image.service.BultrasoundService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 17:49
 * @Description: ""
 */

@Service
public class BultrasoundServiceImpl implements BultrasoundService {

    @Autowired
    private BultrasoundMapper bultrasoundMapper;

    @Override
    public Bultrasound add(BultrasoundAddServiceQuery bultrasoundAddServiceQuery) {

        Bultrasound bultrasound = new Bultrasound();
        BeanUtils.copyProperties(bultrasoundAddServiceQuery,bultrasound);

        int rt = bultrasoundMapper.insert(bultrasound);

        Assert.state(rt>0,"插入失败");

        return bultrasound;
    }

    @Override
    public Integer deleteByIdPermanently(Long bultrasoundId) {
        return bultrasoundMapper.deleteByIdPermanently(bultrasoundId);
    }

    @Override
    public Integer deleteByIdLogically(Long bultrasoundId) {
        return bultrasoundMapper.deleteById(bultrasoundId);
    }

    @Override
    public Integer updateById(Long bultrasoundId, BultrasoundUpdateViewQuery bultrasoundUpdateViewQuery) {

        Bultrasound dbBultrasound = bultrasoundMapper.selectById(bultrasoundId);

        Assert.notNull(dbBultrasound,"数据不存在");

        Bultrasound bultrasound = new Bultrasound();
        bultrasound.setBultrasoundId(bultrasoundId);
        bultrasound.setVersion(dbBultrasound.getVersion());

        BeanUtils.copyProperties(bultrasoundUpdateViewQuery,bultrasound);

        return bultrasoundMapper.updateById(bultrasound);
    }

    @Override
    public Bultrasound queryById(Long bultrasoundId) {
        return bultrasoundMapper.selectById(bultrasoundId);
    }

    @Override
    public Page<Bultrasound> list(Long pageNum, Long pageSize, BultrasoundListViewQuery bultrasoundListViewQuery) {

        Page<Bultrasound> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        String path = bultrasoundListViewQuery.getPath();
        String filename = bultrasoundListViewQuery.getFilename();
        String url = bultrasoundListViewQuery.getUrl();

        QueryWrapper<Bultrasound> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(path!=null,"path",path);
        queryWrapper.like(filename!=null,"filename",filename);
        queryWrapper.like(url!=null,"url",url);

        Page<Bultrasound> bultrasoundPage = bultrasoundMapper.selectPage(page, queryWrapper);

        return bultrasoundPage;
    }
}
