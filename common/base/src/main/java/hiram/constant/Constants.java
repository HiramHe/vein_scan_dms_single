package hiram.constant;

/**
 * @Author: HiramHe
 * @Date: 2020/5/7 15:11
 * @Description: ""
 */
public class Constants {

    public static final String LOGIN_USER_KEY = "login_user_key";

    public static final int HTTPCODE_DEFAULT = 200;

    public static final long SECOND_MILLIS = 1000;

    public static final long MINUTE_MILLIS = 60 * SECOND_MILLIS;

    public static final String TOKEN_HEADER = "Authorization";

    public static final String LOGIN_URL = "/ucenter/login";

    public static final String LOGOUT_URL = "/ucenter/logout";

    /**
     * 令牌前缀
     */
    public static final String TOKEN_PREFIX = "Bearer";

    public static final String TOKEN_SECRET = "hiram.he@qq.com";

    public static Boolean UNIQUE = true;

    public static Boolean NOT_UNIQUE = false;

    public static String POINT = ".";

    public static String HTTPS = "https://";

    public static String LEFT_SLASH = "/";

    public static Long DEFAULT_ROLE_ID = 1L;
}
