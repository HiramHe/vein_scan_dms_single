package hiram.utils;

/**
 * @Author: HiramHe
 * @Date: 2020/7/17 11:15
 * @Description: ""
 */

public class MyStringUtils {

    /** 空字符串 */
    private static final String NULLSTR = "";

    /** 下划线 */
    private static final char SEPARATOR = '_';

    public static boolean isEmpty(String str){
        return str == null || str.trim().length() == 0;
    }
}
