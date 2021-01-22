package hiram.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.acl.pojo.po.SysRole;
import hiram.ucenter.pojo.dto.UserSelectDTO;
import hiram.acl.pojo.po.SysUser;
import hiram.acl.pojo.query.UserListServiceQuery;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/28 10:53
 * @Description: ""
 */

/*
查询：结果有可能是多个表连接的结果，因此参数可能是一种dto，返回值是另一种dto
插入：不存在ResultMap，会把数据库自动生成的字段值映射到parameterType对应的类对象中，因此传入的参数直接使用po类，返回的值是数值，表示
影响的函数
 */

@Mapper
@Component
public interface UserMapper extends BaseMapper<SysUser> {

    //校验
    SysUser checkUserNameUnique(String username);

    SysUser checkPhoneUnique(String phoneNumber);

    SysUser checkEmailUnique(String email);

    //删除
    Integer deleteByIdPermanently(Long userId);

    Integer deleteByIdsPermanently(Long[] userIds);

    //恢复
    Long recoverById(Long userId);

    Long recoverByIds(Long[] userIds);

    //更新
    Long updateUser(SysUser sysUser);

    //查询
    List<UserSelectDTO> selectUserList(UserListServiceQuery userListServiceQuery);

    List<SysRole> getRolesByUserId(Long userId);
}
