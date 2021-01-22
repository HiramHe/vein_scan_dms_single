package hiram.image.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.image.mapper.InfraredMapper;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.InfraredListViewQuery;
import hiram.image.pojo.query.InfraredAddServiceQuery;
import hiram.image.pojo.query.InfraredUpdateViewQuery;
import hiram.image.service.InfraredService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/21 10:19
 * @Description: ""
 */

@Service
public class InfraredServiceImpl implements InfraredService {

    private Log logger = LogFactory.getLog(getClass());

    @Autowired
    InfraredMapper infraredMapper;

    @Override
    public Infrared add(InfraredAddServiceQuery infraredAddServiceQuery) {

        Infrared infrared = new Infrared();
        BeanUtils.copyProperties(infraredAddServiceQuery,infrared);

        int affectRows = infraredMapper.insert(infrared);

        try {
            Assert.state(affectRows>0,"影响行数为0，插入失败");
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.FAILED_INSERT);
        }

        return infrared;
    }

    @Override
    public Integer deleteByIdPermanently(Long infraredId) {

        return infraredMapper.deleteByIdPermanently(infraredId);
    }

    @Override
    public Integer deleteByIdLogically(Long infraredId) {
        return infraredMapper.deleteById(infraredId);
    }

    @Override
    public Integer updateById(Long infraredId, InfraredUpdateViewQuery infraredUpdateViewQuery) {

        Infrared dbInfrared = infraredMapper.selectById(infraredId);

        Assert.notNull(dbInfrared,"数据不存在");

        Infrared infrared = new Infrared();
        infrared.setInfraredId(infraredId);
        infrared.setVersion(dbInfrared.getVersion());

        BeanUtils.copyProperties(infraredUpdateViewQuery,infrared);

        return infraredMapper.updateById(infrared);
    }

    @Override
    public Infrared queryById(Long infraredId) {

        QueryWrapper<Infrared> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("infrared_id",infraredId);

        return infraredMapper.selectOne(queryWrapper);
    }

    @Override
    public Page<Infrared> list(Long pageNum, Long pageSize, InfraredListViewQuery infraredListViewQuery) {

        Page<Infrared> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        Long patientId = infraredListViewQuery.getPatientId();
        Long userId = infraredListViewQuery.getUserId();
        LocalDateTime scanTimeBegin = infraredListViewQuery.getScanTimeBegin();
        LocalDateTime scanTimeEnd = infraredListViewQuery.getScanTimeEnd();

        QueryWrapper<Infrared> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(patientId!=null,"patient_id",patientId);
        queryWrapper.eq(userId!=null,"user_id",userId);
        queryWrapper.ge(scanTimeBegin!=null,"scan_time",scanTimeBegin);
        queryWrapper.le(scanTimeEnd!=null,"scan_time",scanTimeEnd);

        Page<Infrared> infraredPage = infraredMapper.selectPage(page, queryWrapper);

        return infraredPage;
    }
}
