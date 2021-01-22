package hiram.acl.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import hiram.acl.pojo.po.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/10/2 16:23
 * @Description: ""
 */

/*
注意，在spring中，java.mysql.SQLException及其子类 都被映射为
org.springframework.dao.DataAccessException及其子类，因此捕获异常时，我们需要捕获DataAccessException相关的异常.

例如：出现 java.sql.SQLIntegrityConstraintViolationException，要捕获时，我们应该捕获
org.springframework.dao.DuplicateKeyException
 */

@Mapper
@Component
public interface UserRoleMapper extends BaseMapper<UserRole> {

    /**
     * 批量插入用户角色
     *
     * @param userRoleList
     * @return
     */
    Integer addBatch(List<UserRole> userRoleList);

    Integer deleteByIdPermanently(Long userRoleId);

    Integer deleteByUserIdPermanently(Long userId);
}
