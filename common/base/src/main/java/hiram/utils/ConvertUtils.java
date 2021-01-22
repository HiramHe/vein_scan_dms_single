package hiram.utils;

import org.springframework.util.StringUtils;

/**
 * @Author: HiramHe
 * @Date: 2020/5/22 9:13
 * @Description: "类型转换器"
 */


public class ConvertUtils {

    /**
     * 转换为字符串
     * @param value
     * @param defaultValue
     * @return
     */
    public static String toStr(Object value, String defaultValue){
        if (null == value){
            return defaultValue;
        }
        if (value instanceof String){
            return (String)value;
        }

        return value.toString();
    }

    /**
     * 转换为int
     * @param value
     * @return
     */
    public static Integer toInt(Object value){
        return toInt(value,null);
    }

    /**
     * 转换为int，可指定默认值
     * @param value
     * @param defaultValue
     * @return
     */
    public static Integer toInt(Object value, Integer defaultValue){
        if (null == value){
            return defaultValue;
        }
        if (value instanceof Integer){
            return (Integer)value;
        }
        if (value instanceof Number){
            return ((Number)value).intValue();
        }

        final String valueStr = toStr(value,null);
        if (StringUtils.isEmpty(valueStr)){
            return defaultValue;
        }
        try {
            return Integer.parseInt(valueStr);
        } catch (Exception e){
            return defaultValue;
        }
    }
}
