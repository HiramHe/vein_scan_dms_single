package hiram.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/5/15 19:44
 * @Description: ""
 */

@Data
@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerProperties {
    //默认为false
    private boolean enabled = true;
}
