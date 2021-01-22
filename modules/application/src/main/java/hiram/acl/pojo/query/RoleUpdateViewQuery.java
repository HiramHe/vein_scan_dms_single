package hiram.acl.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2021/1/14 20:45
 * @Description: ""
 */

@Data
public class RoleUpdateViewQuery {
    private String roleNameZh;

    @ApiParam(value = "ROLE_xxx")
    private String roleNameEn;

    private Boolean enabled;
    private String remark;
}
