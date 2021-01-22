package hiram.springSecurity.configuration;

import hiram.redis.RedisService;
import hiram.springSecurity.filters.CustomUsernamePasswordAuthenticationFilter;
import hiram.springSecurity.filters.TokenVerifyFilter;
import hiram.springSecurity.handler.CustomAuthenticationFailureHandler;
import hiram.springSecurity.handler.CustomAuthenticationSuccessHandler;
import hiram.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * @Author: HiramHe
 * @Date: 2021/1/16 20:42
 * @Description: ""
 */

@Configuration
public class SecurityCustomConfiguration {

    /**
     * 角色继承
     * 上级可能具备下级的所有权限
     * @return
     */
    @Bean
    RoleHierarchy roleHierarchy(){
        RoleHierarchyImpl hierarchy = new RoleHierarchyImpl();

        hierarchy.setHierarchy("ROLE_admin > ROLE_test > ROLE_doctor > ROLE_common");

        return hierarchy;
    }
}
