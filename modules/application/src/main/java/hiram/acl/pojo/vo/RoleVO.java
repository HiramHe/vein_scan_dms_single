package hiram.acl.pojo.vo;

import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/10/3 9:40
 * @Description: ""
 */

@Data
public class RoleVO {

    /** 角色ID */
    private Long roleId;

    /** 角色名称 */
    private String roleNameEn;

    /** 角色名称 */
    private String roleNameZh;

    /** 角色状态 */
    private boolean enabled;

    private String remark;
}
