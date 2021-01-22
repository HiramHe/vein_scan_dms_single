package hiram.image.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.image.pojo.po.DescriptionBultrasound;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/9/28 17:50
 * @Description: ""
 */

@Mapper
@Component
public interface DescriptionBultrasoundMapper extends BaseMapper<DescriptionBultrasound> {

    Integer deleteByIdPermanently(Long id);
}
