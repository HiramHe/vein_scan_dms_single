package hiram.acl.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.query.RoleAddViewQuery;
import hiram.acl.pojo.query.RoleListViewQuery;
import hiram.acl.pojo.query.RoleUpdateViewQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 14:43
 * @Description: ""
 */
public interface RoleService {

    SysRole addOne(RoleAddViewQuery roleAddViewQuery);

    Integer deleteByIdPermanently(Long roleId);

    Integer deleteByIdLogically(Long roleId);

    Integer updateById(Long roleId, RoleUpdateViewQuery roleUpdateViewQuery);

    List<SysRole> getRolesByUsername(String username);

    Page<SysRole> list(Long pageNum, Long pageSize, RoleListViewQuery roleListViewQuery);

    SysRole getById(Long roleId);
}
