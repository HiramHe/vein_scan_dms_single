package hiram.page;

import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * @Author: HiramHe
 * @Date: 2020/5/21 21:33
 * @Description: ""
 */

@Data
public class PageDomain {

    /*
    起始索引
     */
    private Integer pageNum;

    /*
    每页显示记录数
     */
    private Integer pageSize;

    /*
    排序列
     */
    private String orderByColumn;

    /*
    排序方向：desc、asc
     */
    private String order="asc";

    public String getOrderBy(){
        if (StringUtils.isEmpty(orderByColumn)){
            return "";
        }
        return orderByColumn + " " + order;
    }
}
