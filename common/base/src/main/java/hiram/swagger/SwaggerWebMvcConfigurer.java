package hiram.swagger;

import hiram.enums.ResourcePatternLocationEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 16:09
 * @Description: ""
 */

@Configuration
public class SwaggerWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        /*
        swagger静态资源映射
         */
        registry.addResourceHandler(ResourcePatternLocationEnum.SWAGGER_UI_HTML.getPathPattern())
                .addResourceLocations(ResourcePatternLocationEnum.SWAGGER_UI_HTML.getLocation())
        ;
        registry.addResourceHandler(ResourcePatternLocationEnum.WEBJARS.getPathPattern())
                .addResourceLocations(ResourcePatternLocationEnum.WEBJARS.getLocation())
        ;

    }

}
