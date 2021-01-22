package hiram.enums;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 16:56
 * @Description: ""
 */

public enum ResourcePatternLocationEnum {

    IMAGE("images","/images/**",null),
    STATIC("static","/static/**","classpath:/static/"),

    SWAGGER_UI_HTML("swagger_ui_html","/swagger-ui.html","classpath:/META-INF/resources/"),
    SWAGGER_UI("swagger-ui","/swagger-ui.html/**",null),
    WEBJARS("webjars","/webjars/**","classpath:/META-INF/resources/webjars/"),
    V2("v2","/v2/**",null),
    SWAGGER_RESOURCES("swagger-resources","/swagger-resources/**", null),
    ERROR("error","/error",null),
    NULL_SWAGGER_RESOURCES("null_swagger-resources","/null/swagger-resources/**",null),

    CONTEXT_PATH("contextPath","/",null),
    CSRF("csrf","/csrf",null),
    FAVICON("favicon","/favicon.ico",null)
    ;

    private final String name;
    private final String pathPattern;
    private final String location;

    ResourcePatternLocationEnum(String name, String pathPattern, String location) {
        this.name = name;
        this.pathPattern = pathPattern;
        this.location = location;
    }

    public String getName(){
        return this.name;
    }

    public String getPathPattern(){
        return this.pathPattern;
    }

    public String getLocation(){
        return this.location;
    }
}
