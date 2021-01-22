package hiram.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.hospital.pojo.po.Bed;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 18:52
 * @Description: ""
 */

@Mapper
@Component
public interface BedMapper extends BaseMapper<Bed> {
    Integer deleteByIdPermanently(Long bedId);
}
