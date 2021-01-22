package hiram.utils;

import hiram.constant.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: HiramHe
 * @Date: 2020/5/15 16:57
 * @Description: ""
 */

public class ServletUtils {

    /**
     * 获取string参数
     * @param name
     * @return
     */
    public static String getParameter(String name){
        return getRequest().getParameter(name);
    }

    public static Integer getParameterToInt(String name){
        return ConvertUtils.toInt(getRequest().getParameter(name));
    }

    /**
     * 通过RequestAttributes获取request
     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     * 通过RequestAttributes获取response
     */
    public static HttpServletResponse getResponse(){
        return getRequestAttributes().getResponse();
    }


    /**
     * 通过RequestContextHolder获取RequestAttributes
     * @return
     */
    public static ServletRequestAttributes getRequestAttributes() {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }
}
