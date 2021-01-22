package hiram.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import hiram.pojo.po.BasePO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.GrantedAuthority;

/**
 * @Author: HiramHe
 * @Date: 2020/4/24 10:35
 * @Description: ""
 */

@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "acl_role")
public class SysRole extends BasePO {

    /** 角色ID */
    @TableId(value = "role_id",type = IdType.AUTO)
    private Long roleId;

    /** 角色名称 */
    private String roleNameEn;

    /** 角色名称 */
    private String roleNameZh;

    /** 角色状态 */
    private Boolean enabled;

    private String remark;

}
