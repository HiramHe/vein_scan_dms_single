package hiram.acl.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author: HiramHe
 * @Date: 2020/4/23 21:11
 * @Description: "路由显示信息"
 */

@Data
@AllArgsConstructor
public class Meta implements Serializable {

    /**
     * 该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    private boolean requireAuth;

    /**
     * 当一个路由下面的 children 声明的路由大于1个时，会自动变成嵌套的模式--如组件页面
     */
    private boolean alwaysShow;

}
