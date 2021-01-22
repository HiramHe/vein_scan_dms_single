package hiram.acl.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/4/23 21:13
 * @Description: "构建前端路由时用到"
 */

@Data
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouteVo implements Serializable {

    /**
     * 路由名字
     */
    private String name;

    /**
     * 路由地址
     */
    private String path;

    /**
     * 组件名称
     */
    private String component;

    /**
     * 是否隐藏路由，为 true 时该路由不会在侧边栏出现
     */
    private boolean hidden;

    /**
     * 重定向
     */
    private String redirect = "noRedirect";

    private boolean keepAlive;

    /**
     *  其他信息
     */
    private Meta meta;

    /**
     * 子路由
     */
    private List<RouteVo> children;
}
