package hiram.hospitalization.service;

import hiram.hospitalization.pojo.dto.HospitalizationDto;
import hiram.hospitalization.pojo.po.Hospitalization;
import hiram.hospitalization.pojo.query.HospitalizationAddViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationListViewQuery;
import hiram.hospitalization.pojo.query.HospitalizationUpdateViewQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 14:37
 * @Description: ""
 */

public interface HospitalizationService {
    Hospitalization add(HospitalizationAddViewQuery hospitalizationAddViewQuery);

    Integer deleteByIdPermanently(Long hospitalizationId);

    Integer deleteByIdLogically(Long hospitalizationId);

    Integer recoverAll();

    Integer updateById(Long id, HospitalizationUpdateViewQuery hospitalizationUpdateViewQuery);

    List<HospitalizationDto> list(HospitalizationListViewQuery hospitalizationListViewQuery);

    HospitalizationDto queryByHospitalizationId(Long hospitalizationId);
}
