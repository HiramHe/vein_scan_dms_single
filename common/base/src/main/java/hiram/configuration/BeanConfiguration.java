package hiram.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Author: HiramHe
 * @Date: 2021/1/14 15:49
 * @Description: ""
 */

@Configuration
public class BeanConfiguration {

    /**
     * 将BCryptPasswordEncoder注入到ioc容器中,其他地方用@Autowired获取
     * @return
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
