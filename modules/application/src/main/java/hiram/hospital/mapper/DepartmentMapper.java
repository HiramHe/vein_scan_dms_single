package hiram.hospital.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.hospital.pojo.po.Department;
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
public interface DepartmentMapper extends BaseMapper<Department> {
    Integer deleteByIdPermanently(Long departmentId);

    List<Ward> getWardsOfDepartmentById(Long departmentId);
}
