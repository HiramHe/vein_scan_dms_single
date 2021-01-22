package hiram.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.hospital.pojo.po.Bed;
import hiram.hospital.pojo.po.Ward;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/12/30 20:20
 * @Description: ""
 */

@Mapper
@Component
public interface WardMapper extends BaseMapper<Ward> {
    List<Bed> getBedsOfWardById(Long wardId);
}
