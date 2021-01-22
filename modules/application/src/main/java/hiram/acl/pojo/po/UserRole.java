package hiram.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: HiramHe
 * @Date: 2020/10/1 14:41
 * @Description: ""
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("acl_user_role")
public class UserRole extends BasePO {

    @TableId(value = "user_role_id",type = IdType.AUTO)
    private Long userRoleId;

    private Long userId;

    private Long roleId;

    public UserRole(Long userId,Long roleId){
        this.userId = userId;
        this.roleId = roleId;
    }
}
