package hiram.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: HiramHe
 * @Date: 2020/6/24 16:09
 * @Description: ""
 */

@Configuration
public class CsrfWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST","DELETE","PUT","PATCH")
                .allowCredentials(false)
                .maxAge(3600 * 24)
        ;
    }
}
