package hiram.hospitalization.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.hospitalization.pojo.dto.HospitalizationDto;
import hiram.hospitalization.pojo.po.Hospitalization;
import hiram.hospitalization.pojo.query.HospitalizationListViewQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2021/1/4 10:15
 * @Description: ""
 */

@Mapper
@Component
public interface HospitalizationMapper extends BaseMapper<Hospitalization> {
    Integer deleteByIdPermanently(Long id);

    Integer recoverAll();

    List<HospitalizationDto> list(HospitalizationListViewQuery hospitalizationListViewQuery);

    HospitalizationDto queryByHospitalizationId(Long hospitalizationId);
}
