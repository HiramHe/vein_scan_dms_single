package hiram.hospitalization.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import hiram.enums.ResultCodeEnum;
import hiram.exception.CustomException;
import hiram.hospitalization.mapper.HospitalizationMapper;
import hiram.hospitalization.pojo.dto.HospitalizationDto;
import hiram.hospitalization.pojo.po.Hospitalization;
import hiram.hospitalization.pojo.query.HospitalizationAddViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationListViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationUpdateViewQuery;
import hiram.hospitalization.service.HospitalizationService;
import io.swagger.models.auth.In;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 14:38
 * @Description: ""
 */

@Service
public class HospitalizationServiceImpl implements HospitalizationService {

    @Autowired
    private HospitalizationMapper hospitalizationMapper;

    @Override
    public Hospitalization add(HospitalizationAddViewQuery hospitalizationAddViewQuery) {

        Hospitalization hospitalization = new Hospitalization();
        BeanUtils.copyProperties(hospitalizationAddViewQuery,hospitalization);

        if (hospitalizationAddViewQuery.getCheckinTime() == null){
            hospitalization.setCheckinTime(LocalDateTime.now());
        }

        int affectRows = hospitalizationMapper.insert(hospitalization);

        Assert.state(affectRows>0,"影响行数为0，插入失败");

        return hospitalization;
    }

    @Override
    public Integer deleteByIdPermanently(Long id) {
        return hospitalizationMapper.deleteByIdPermanently(id);
    }

    @Override
    public Integer deleteByIdLogically(Long id) {
        return hospitalizationMapper.deleteById(id);
    }

    @Override
    public Integer recoverAll() {

        return hospitalizationMapper.recoverAll();
    }

    @Override
    public Integer updateById(Long hospitalizationId, HospitalizationUpdateViewQuery hospitalizationUpdateViewQuery) {

        Hospitalization dbHospitalization = hospitalizationMapper.selectById(hospitalizationId);

        try {
            Assert.notNull(dbHospitalization,"入住记录不存在，更新失败");
        } catch (Exception e) {
            throw new CustomException(ResultCodeEnum.RECORD_NOT_EXIST);
        }

        Hospitalization hospitalization = new Hospitalization();
        BeanUtils.copyProperties(hospitalizationUpdateViewQuery,hospitalization);

        hospitalization.setHospitalizationId(hospitalizationId);
        hospitalization.setVersion(dbHospitalization.getVersion());

        return hospitalizationMapper.updateById(hospitalization);
    }

    @Override
    public List<HospitalizationDto> list(HospitalizationListViewQuery hospitalizationListViewQuery) {
        return hospitalizationMapper.list(hospitalizationListViewQuery);
    }

    @Override
    public HospitalizationDto queryByHospitalizationId(Long hospitalizationId) {
        return hospitalizationMapper.queryByHospitalizationId(hospitalizationId);
    }
}
