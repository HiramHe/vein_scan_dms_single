package hiram.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import hiram.page.PageDomain;
import hiram.page.TableSupport;
import hiram.pojo.vo.TableData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: HiramHe
 * @Date: 2020/5/14 20:48
 * @Description: ""
 */

/*
web层通用数据处理
 */

public class BaseController {

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /*
    可被子类继承
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 设置请求分页数据
     */
    protected void startPage(){
        PageDomain pageDomain = TableSupport.getPageDomain();

        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();

        if (null !=pageNum && null !=pageSize){
            String orderBy = pageDomain.getOrderBy();
            PageHelper.startPage(pageNum,pageSize,orderBy);
        }
    }

    /**
     * 响应分页数据
     */
    protected TableData getTableData(List<?> list){

        PageInfo pageInfo = new PageInfo(list);

        TableData tableData = new TableData();

        tableData.setTotal(pageInfo.getTotal());
        tableData.setPages(pageInfo.getPages());
        tableData.setPageNum(pageInfo.getPageNum());
        tableData.setPageSize(pageInfo.getPageSize());
        tableData.setSize(pageInfo.getSize());
        tableData.setResults(list);

        return tableData;
    }

    /**
     * 获取mybatisplus分页中的数据
     * @param page
     * @param <T>
     * @return
     */
    protected <T> Map<String,Object> getPageData(Page<T> page){
        long total = page.getTotal();
        long pages = page.getPages();
        long current = page.getCurrent();
        long size= page.getSize();
        List<T> records = page.getRecords();

        Map<String,Object> data = new HashMap<>();
        data.put("total",total);
        data.put("pages",pages);
        data.put("pageNum",current);
        data.put("pageSize",size);
        data.put("records",records);

        return data;
    }

    @ModelAttribute
    public void setReqAndRes(HttpServletRequest req, HttpServletResponse res){
        this.request = req;
        this.response = res;
        this.session = req.getSession();
    }

}
