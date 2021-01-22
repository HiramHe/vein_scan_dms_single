package hiram.springSecurity.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/15 12:43
 * @Description: ""
 */

@Data
@NoArgsConstructor
public class CustomUserDetails implements UserDetails {

    /**
     * 用户唯一标识
     */
    private String uuid;

    /**
     * 登陆时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginDateTime;

    /**
     * 过期时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime expireDateTime;

    /**
     * 登录IP地址
     */
    private String ipAddr;

    /**
     * 登录地点
     */
    private String loginLocation;

    /**
     * 浏览器类型
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 权限列表
     * private Collection<? extends GrantedAuthority> authorities;
     */
    private List<String> roleNameEns;

    /**
     * 用户信息
     */
    private SecurityUser securityUser;


    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for (String roleNameEn:roleNameEns){
            if (StringUtils.hasLength(roleNameEn)){
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleNameEn);
                authorities.add(authority);
            }
        }

        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return securityUser.getPassword();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return securityUser.getUsername();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return securityUser.getEnabled();
    }
}
