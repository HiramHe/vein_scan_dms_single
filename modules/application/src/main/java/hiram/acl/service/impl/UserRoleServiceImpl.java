package hiram.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hiram.acl.mapper.UserRoleMapper;
import hiram.acl.pojo.po.UserRole;
import hiram.acl.pojo.query.UserRoleAddServiceQuery;
import hiram.acl.service.UserRoleService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/10/2 16:19
 * @Description: ""
 */

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 插入用户角色
     *
     * @param userRoleAddServiceQuery
     * @return
     */
    @Override
    public UserRole addOne(UserRoleAddServiceQuery userRoleAddServiceQuery) {

        UserRole userRole = new UserRole();
        BeanUtils.copyProperties(userRoleAddServiceQuery,userRole);

        Integer rt = userRoleMapper.insert(userRole);

        return userRole;
    }

    /**
     * 批量插入用户角色
     *
     * @param userRoleList
     * @return
     */
    @Override
    public Integer addBatch(List<UserRole> userRoleList) {

        return userRoleMapper.addBatch(userRoleList);
    }

    @Override
    public Integer deleteByIdPermanently(Long userRoleId) {
        return userRoleMapper.deleteByIdPermanently(userRoleId);
    }

    @Override
    public Integer deleteByIdLogically(Long userRoleId) {
        return userRoleMapper.deleteById(userRoleId);
    }

    @Override
    public Integer deleteByUserIdPermanently(Long userId) {
        return userRoleMapper.deleteByUserIdPermanently(userId);
    }

    @Override
    public Integer deleteByUserIdLogically(Long userId) {

        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(userId!=null,"user_id",userId);

        return userRoleMapper.delete(queryWrapper);
    }

}
