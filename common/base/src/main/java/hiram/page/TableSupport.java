package hiram.page;


import hiram.utils.ServletUtils;

/**
 * @Author: HiramHe
 * @Date: 2020/5/22 9:59
 * @Description: ""
 */

public class TableSupport {
    public static final String PAGE_NUM = "pageNum";
    public static final String PAGE_SIZE = "pageSize";
    public static final String ORDER_BY_COLUMN = "orderByColumn";
    public static final String ORDER = "order";

    /**
     * 封装分页对象
     */
    public static PageDomain getPageDomain(){
        PageDomain pageDomain = new PageDomain();

        pageDomain.setPageNum(ServletUtils.getParameterToInt(PAGE_NUM));
        pageDomain.setPageSize(ServletUtils.getParameterToInt(PAGE_SIZE));
        pageDomain.setOrderByColumn(ServletUtils.getParameter(ORDER_BY_COLUMN));
        pageDomain.setOrder(ServletUtils.getParameter(ORDER));

        return pageDomain;
    }
}
