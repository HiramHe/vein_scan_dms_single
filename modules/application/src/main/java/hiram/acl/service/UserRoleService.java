package hiram.acl.service;


import hiram.acl.pojo.po.UserRole;
import hiram.acl.pojo.query.UserRoleAddServiceQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/10/2 16:17
 * @Description: ""
 */

public interface UserRoleService {

    /**
     * 插入用户角色
     * @param userRoleAddServiceQuery
     * @return
     */
    UserRole addOne(UserRoleAddServiceQuery userRoleAddServiceQuery);

    /**
     * 批量插入用户角色
     * @param userRoleList
     * @return
     */
    Integer addBatch(List<UserRole> userRoleList);

    Integer deleteByIdPermanently(Long userRoleId);

    Integer deleteByIdLogically(Long userRoleId);

    Integer deleteByUserIdPermanently(Long userId);

    Integer deleteByUserIdLogically(Long userId);
}
