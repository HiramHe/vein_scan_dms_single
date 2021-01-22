package hiram.image.configuration;

import hiram.enums.ResourcePatternLocationEnum;
import hiram.properties.ImageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 16:09
 * @Description: ""
 */

@Configuration
public class ImageWebMvcConfigurer implements WebMvcConfigurer {

    @Autowired
    private ImageProperties imageProperties;

    /**
     * 不要在yml配置文件中配置spring.mvc.static-path-pattern,
     * 否则所有的静态资源都需要走该配置的值才能访问到。
     * 而我希望的的是：swagger走：localhost:9090/swagger-ui.html，
     * 图片资源走：localhost:9090/picture/xxx.png。
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(ResourcePatternLocationEnum.STATIC.getPathPattern())
                .addResourceLocations(ResourcePatternLocationEnum.STATIC.getLocation());

        registry.addResourceHandler(ResourcePatternLocationEnum.IMAGE.getPathPattern())
                .addResourceLocations("file:" + imageProperties.getInfraredDirectory())
                .addResourceLocations("file:" + imageProperties.getUltrasoundDirectory())
        ;

    }

}
