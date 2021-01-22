package hiram.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.query.RoleListViewQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 14:42
 * @Description: ""
 */

@Mapper
@Component
public interface RoleMapper extends BaseMapper<SysRole> {

    Integer deleteByIdPermanently(Long roleId);

    List<SysRole>  selectRolesByUsername(String username);

    List<SysRole>  selectRoleByUserId(Long userId);
}
