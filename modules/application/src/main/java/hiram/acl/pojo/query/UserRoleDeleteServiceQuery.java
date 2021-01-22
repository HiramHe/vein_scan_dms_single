package hiram.acl.pojo.query;

import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HiramHe
 * @Date: 2020/10/2 17:15
 * @Description: ""
 */

@Data
@NoArgsConstructor
public class UserRoleDeleteServiceQuery extends BasePO {

    private Long userRoleId;

    /** 用户ID */

    private Long userId;

    /** 角色ID */
    private Long roleId;


    public UserRoleDeleteServiceQuery(Long userId, Long roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
}
