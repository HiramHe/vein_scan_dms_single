package hiram.acl.service;

import hiram.acl.pojo.po.SysRole;
import hiram.ucenter.pojo.dto.UserSelectDTO;
import hiram.acl.pojo.po.SysUser;
import hiram.acl.pojo.query.UserAddServiceQuery;
import hiram.acl.pojo.query.UserListServiceQuery;
import hiram.acl.pojo.query.UserUpdateServiceQuery;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 10:59
 * @Description: ""
 */

public interface UserService {

    /*插入*/
    SysUser add(UserAddServiceQuery userAddServiceQuery);

    /*校验*/
    boolean checkUserNameUnique(Long userId, String username);

    boolean checkPhoneUnique(Long userId, String phoneNumber);

    boolean checkEmailUnique(Long userId, String email);

    /*删除*/
    Integer deleteByIdLogically(Long userId);

    Integer deleteByIdPermanently(Long userId);

    Integer deleteByIdsLogically(List userId);

    Integer deleteByIdsPermanently(Long[] userIds);

    /*恢复*/
    Long recoverById(Long userId);

    Long recoverByIds(Long[] userIds);

    /*更新*/
    Integer updateById(Long userId, UserUpdateServiceQuery userUpdateServiceQuery);

    /*重置密码*/
    int resetPwd(Long userId, String newPassword);

    /*查询*/
    SysUser queryByUsername(String username);

    List<SysRole> getRolesByUserId(Long userId);

    List<UserSelectDTO> list(UserListServiceQuery userListServiceQuery);

    SysUser queryById(Long userId);
}
