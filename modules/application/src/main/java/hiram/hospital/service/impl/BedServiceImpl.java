package hiram.hospital.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospital.mapper.BedMapper;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.query.BedAddViewQuery;
import hiram.hospital.pojo.query.BedListViewQuery;
import hiram.hospital.service.BedService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 17:06
 * @Description: ""
 */

@Service
public class BedServiceImpl implements BedService {

    @Autowired
    private BedMapper bedMapper;

    @Override
    public Bed add(BedAddViewQuery bedAddViewQuery) {

        Bed bed = new Bed();

        BeanUtils.copyProperties(bedAddViewQuery,bed);

        int affectRows = bedMapper.insert(bed);

        try {
            Assert.state(affectRows>0,"影响行数为0，插入失败");
        } catch (Exception e){
            throw new CustomException(ResultCodeEnum.FAILED_INSERT);
        }

        return bed;
    }

    @Override
    public Integer deleteByIdPermanently(Long bedId) {
        return bedMapper.deleteByIdPermanently(bedId);
    }

    @Override
    public Integer deleteByIdLogically(Long bedId) {
        return bedMapper.deleteById(bedId);
    }

    @Override
    public Integer invertVacantValue(Long bedId){

        Bed dbBed = bedMapper.selectById(bedId);

        UpdateWrapper<Bed> updateWrapper = new UpdateWrapper<>();

        //设置字段的值取反
        updateWrapper.setSql("vacant=ABS(vacant-1)");

        //设置条件
        updateWrapper.eq("bed_id",bedId);

        Bed bed = new Bed();
        bed.setVersion(dbBed.getVersion());

        return bedMapper.update(bed, updateWrapper);
    }

    @Override
    public Bed getBedById(Long bedId) {
        return bedMapper.selectById(bedId);
    }

    @Override
    public Page<Bed> list(Long pageNum, Long pageSize, BedListViewQuery bedListViewQuery) {

        Page<Bed> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);

        QueryWrapper<Bed> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(!ObjectUtils.isEmpty(bedListViewQuery.getBedNumber()),"bed_number",bedListViewQuery.getBedNumber());
        queryWrapper.eq(!ObjectUtils.isEmpty(bedListViewQuery.getVacant()),"vacant",bedListViewQuery.getVacant());

        return bedMapper.selectPage(page, queryWrapper);
    }

    @Override
    public Boolean checkBedNumberExist(Long bedNumber) {

        QueryWrapper<Bed> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("bed_number",bedNumber);

        List<Bed> beds = bedMapper.selectList(queryWrapper);

        return beds.size() >= 1;
    }

}
