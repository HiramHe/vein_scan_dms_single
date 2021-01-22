package hiram.acl.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import hiram.pojo.po.BasePO;
import lombok.Data;

/**
 * @Author: HiramHe
 * @Date: 2020/4/24 9:56
 * @Description: ""
 */

@Data
public class SysMenu extends BasePO {

    @TableId(value = "menu_id",type = IdType.AUTO)
    private Long menuId;

    private String menuNameZh;
    private String menuNameEn;
    private Long parentId;
    private Boolean visible;
    private Boolean requireAuth;
    private String icon;
    /**
     *  路由地址
     */
    private String path;
    private String component;
    private Boolean hidden;
    private String redirect;
    private Boolean keepAlive;
    private String remark;
    private boolean enabled = true;
}
