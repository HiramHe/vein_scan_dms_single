package hiram.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.po.UserRole;
import hiram.constant.Constants;
import hiram.acl.mapper.UserMapper;
import hiram.acl.mapper.UserRoleMapper;
import hiram.ucenter.pojo.dto.UserSelectDTO;
import hiram.acl.pojo.po.SysUser;
import hiram.acl.pojo.query.UserAddServiceQuery;
import hiram.acl.pojo.query.UserListServiceQuery;
import hiram.acl.pojo.query.UserUpdateServiceQuery;
import hiram.acl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 11:19
 * @Description: ""
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /*
    插入
    */

    @Override
    @Transactional
    public SysUser add(UserAddServiceQuery userAddServiceQuery) {

        SysUser sysUser = new SysUser();
        BeanUtils.copyProperties(userAddServiceQuery,sysUser);

        int rt = userMapper.insert(sysUser);

        Assert.state(rt>0,"用户新增失败");

        UserRole userRole = new UserRole();

        userRole.setUserId(sysUser.getUserId());
        userRole.setRoleId(Constants.DEFAULT_ROLE_ID);

        int rt1 = userRoleMapper.insert(userRole);

        Assert.state(rt1>0,"为新用户分配默认角色失败");

        return sysUser;
    }

    /*
    校验
    */

    @Override
    public boolean checkUserNameUnique(Long userId, String username) {
        userId = userId==null ? -1:userId;

        SysUser db_user = userMapper.checkUserNameUnique(username);

        if (db_user!=null && !db_user.getUserId().equals(userId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    @Override
    public boolean checkPhoneUnique(Long userId,String phoneNumber) {

        userId = userId==null ? -1:userId;

        SysUser db_user = userMapper.checkPhoneUnique(phoneNumber);

        if (db_user!=null && !db_user.getUserId().equals(userId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    @Override
    public boolean checkEmailUnique(Long userId,String email) {

        userId = userId==null ? -1:userId;

        SysUser db_user = userMapper.checkEmailUnique(email);

        if (db_user!=null && !db_user.getUserId().equals(userId)){
            return Constants.NOT_UNIQUE;
        }

        return Constants.UNIQUE;
    }

    /*
    删除
    */

    @Override
    public Integer deleteByIdLogically(Long userId) {
        return userMapper.deleteById(userId);
    }

    @Override
    public Integer deleteByIdPermanently(Long userId) {
        return userMapper.deleteByIdPermanently(userId);
    }

    @Override
    public Integer deleteByIdsLogically(List userIds) {
        return userMapper.deleteBatchIds(userIds);
    }

    @Override
    public Integer deleteByIdsPermanently(Long[] userIds) {
        return userMapper.deleteByIdsPermanently(userIds);
    }

    /*
    恢复
    */

    @Override
    public Long recoverById(Long userId) {
        return userMapper.recoverById(userId);
    }

    @Override
    public Long recoverByIds(Long[] userIds) {
        return userMapper.recoverByIds(userIds);
    }

    /*
    更新
    */

    @Override
    public Integer updateById(Long userId, UserUpdateServiceQuery userUpdateServiceQuery) {

        SysUser dbSysUser = userMapper.selectById(userId);

        SysUser sysUser = new SysUser();

        sysUser.setUserId(userId);
        sysUser.setVersion(dbSysUser.getVersion());

        BeanUtils.copyProperties(userUpdateServiceQuery,sysUser);

        //更新用户
        int affectRows = userMapper.updateById(sysUser);

        Assert.state(affectRows>0,"更新用户失败");

        return affectRows;
    }

    /*
    重置密码
    */

    @Override
    public int resetPwd(Long userId, String newPassword) {

        SysUser dbSysUser = userMapper.selectById(userId);

        String newPasswordEncoded = passwordEncoder.encode(newPassword);

        SysUser sysUser = new SysUser();

        sysUser.setUserId(userId);
        sysUser.setPassword(newPasswordEncoded);
        sysUser.setVersion(dbSysUser.getVersion());

        return userMapper.updateById(sysUser);
    }

    /*
    查询
    */

    @Override
    public SysUser queryByUsername(String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);

        return userMapper.selectOne(queryWrapper);
    }

    @Override
    public List<SysRole> getRolesByUserId(Long userId) {
        return userMapper.getRolesByUserId(userId);
    }

    @Override
    public List<UserSelectDTO> list(UserListServiceQuery userListServiceQuery) {

        List<UserSelectDTO> userSelectDTOS = userMapper.selectUserList(userListServiceQuery);

        return userSelectDTOS;
    }

    @Override
    public SysUser queryById(Long userId) {

        SysUser dbUser = userMapper.selectById(userId);

        Assert.notNull(dbUser,"用户不存在");

        return dbUser;
    }
}
