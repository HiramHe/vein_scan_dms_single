package hiram.image.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.image.pojo.po.Bultrasound;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 17:50
 * @Description: ""
 */

@Mapper
@Component
public interface BultrasoundMapper extends BaseMapper<Bultrasound> {

    Integer deleteByIdPermanently(Long bultrasoundId);
}
