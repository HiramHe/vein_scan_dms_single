package hiram.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: HiramHe
 * @Date: 2020/5/15 19:28
 * @Description: ""
 */

// 只有这个组件是容器中的组件，才能使用容器提供的@ConfigurationProperties功能
@Data
@Component
@ConfigurationProperties(prefix = "token")
public class TokenProperties {
    //分钟
    private long minuteExpireTime = 30;
}

