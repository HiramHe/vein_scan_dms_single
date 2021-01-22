package hiram.acl.pojo.query;

import io.swagger.annotations.ApiParam;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2021/1/14 16:34
 * @Description: ""
 */

@Data
public class RoleAddViewQuery {

    private String roleNameZh;

    @ApiParam(value = "ROLE_xxx")
    private String roleNameEn;

    @ApiParam(defaultValue = "true")
    private Boolean enabled = true;

    private String remark;

}
