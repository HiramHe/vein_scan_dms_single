package hiram.image.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.image.pojo.po.Infrared;
import hiram.image.pojo.query.InfraredListViewQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/21 10:20
 * @Description: ""
 */

@Mapper
@Component
public interface InfraredMapper extends BaseMapper<Infrared> {

    Integer deleteByIdPermanently(Long infraredId);

}
