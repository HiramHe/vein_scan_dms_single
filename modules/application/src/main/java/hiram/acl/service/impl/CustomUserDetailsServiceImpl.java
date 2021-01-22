package hiram.acl.service.impl;

import hiram.acl.pojo.po.SysRole;
import hiram.acl.pojo.po.SysUser;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.acl.service.RoleService;
import hiram.acl.service.UserService;
import hiram.springSecurity.pojo.dto.SecurityUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: HiramHe
 * @Date: 2020/5/4 14:30
 * @Description: ""
 */

@Service("userDetailsService")
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         done
         1.根据用户名查询数据库
        */
        SysUser sysUser = userService.queryByUsername(username);

        if(sysUser==null){
            /*
             * UsernameNotFoundException是AuthenticationException接口的实现，
             * 凡是AuthenticationException异常，去AuthenticationEntryPoint
             * 中进行统一处理。
            */
            throw new UsernameNotFoundException(String.format("用户[%s]不存在!",username));
        }

        /*
         *
         * "{noop}"+sysUser.getPassword()
         * {noop}后面的密码，spring security会认为是明文
         * done 加密
        */

        List<SysRole> sysRoles = userService.getRolesByUserId(sysUser.getUserId());

        return createUserDetails(sysUser,sysRoles);
    }

    public UserDetails createUserDetails(SysUser sysUser, List<SysRole> sysRoles) {

        CustomUserDetails customUserDetails = new CustomUserDetails();

        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(sysUser,securityUser);
        customUserDetails.setSecurityUser(securityUser);

        List<String> roleNameEns = new ArrayList<>();
        for (SysRole sysRole: sysRoles){
            roleNameEns.add(sysRole.getRoleNameEn());
        }

        customUserDetails.setRoleNameEns(roleNameEns);

        return customUserDetails;
    }
}
