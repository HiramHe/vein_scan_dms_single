package hiram.image.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.image.pojo.po.InfraredDescription;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 18:18
 * @Description: ""
 */

@Mapper
@Component
public interface InfraredDescriptionMapper extends BaseMapper<InfraredDescription> {

    Integer deleteByIdPermanently(Long descriptionId);
}
