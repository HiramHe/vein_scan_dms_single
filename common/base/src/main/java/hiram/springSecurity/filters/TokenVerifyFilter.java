package hiram.springSecurity.filters;

import hiram.constant.Constants;
import hiram.enums.ResultCodeEnum;
import hiram.redis.RedisService;
import hiram.springSecurity.exception.CustomAuthenticationException;
import hiram.springSecurity.pojo.dto.CustomUserDetails;
import hiram.token.TokenService;
import hiram.utils.ResponseUtils;
import hiram.utils.ServletUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * @Author: HiramHe
 * @Date: 2020/4/29 17:17
 * @Description: "身份校验"
 */

/*
 * 非登录业务
 * token校验过滤器
 * BasicAuthenticationFilter 继承 OncePerRequestFilter
 */

/*
 * 不妨参照BasicAuthenticationFilter的doFilterInternal方法，
 * 看看它是怎么捕获并处理异常的。
 */

/*
只要请求中携带了token，且token不为空，本过滤器就会去验证token；
否则，本过滤器对系统不产生任何影响，换言之，如果没带token，或者token为空，
则本过滤器直接掠过，继续走过滤器链。
 */

public class TokenVerifyFilter extends BasicAuthenticationFilter {

    private AuthenticationEntryPoint authenticationEntryPoint;
    private TokenService tokenService;
    private RedisService redisService;

    /**
     * Creates an instance which will authenticate against the supplied
     * {@code AuthenticationManager} and which will ignore failed authentication attempts,
     * allowing the request to proceed down the filter chain.
     *
     * @param authenticationManager the bean to submit authentication requests to
     */
    public TokenVerifyFilter(AuthenticationManager authenticationManager,
                             AuthenticationEntryPoint authenticationEntryPoint,
                             TokenService tokenService,
                             RedisService redisService) {

        super(authenticationManager);

        this.authenticationEntryPoint = authenticationEntryPoint;
        this.tokenService = tokenService;
        this.redisService = redisService;
    }

    /**
     * 本项目使用redis保存认证状态，使用自定义的OncePerRequestFilter每次从redis获取认证状态，
     * 手动构建Authentication，将其放入SecurityContextHolder中。
     * 好处是，服务器重启后，用户认证状态保存在redis中，只要前端有token，就可以在SecurityContextHolder中重建Authentication，
     * 用户不用重新登录。
     *
     * @param request
     * @param response
     * @param chain
     * @throws IOException
     * @throws ServletException
     */

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        Authentication authentication = null;

        /*
        登录成功后，如果session没有被禁用，那么框架的过滤器会向session中存入一个Authentication，可以断点查看。
        参照这个Authentication，在本filter中手动构造一个自己的Authentication。
        */
        Authentication authenticationInSessionBefore = SecurityContextHolder.getContext().getAuthentication();
        Authentication authenticationInSessionAfter = null;

        try{

            String token = tokenService.getTokenFromRequest(request, Constants.TOKEN_HEADER);

            if (StringUtils.hasLength(token)) {

                String uuid = tokenService.getValueFromToken(token, Constants.LOGIN_USER_KEY);
                CustomUserDetails customUserDetails = redisService.getCacheObject(uuid);

                if (customUserDetails != null) {

                    Collection<? extends GrantedAuthority> authorities = customUserDetails.getAuthorities();

                    authentication = new UsernamePasswordAuthenticationToken(customUserDetails, token, authorities);
                }

            } else {
                SecurityContextHolder.clearContext();

                this.authenticationEntryPoint.commence(
                        request,
                        response,
                        new CustomAuthenticationException(ResultCodeEnum.TOKEN_NULL));

                return;
            }

        } catch (ExpiredJwtException expiredJwtException) {
        /*
        此处参考了BasicAuthenticationFilter对异常的处理
         */
            /*
             * 视作认证异常，交给authenticationEntryPoint去处理。
             */
            SecurityContextHolder.clearContext();

            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.TOKEN_EXPIRED));

            return;

        } catch (UsernameNotFoundException usernameNotFoundException) {

            SecurityContextHolder.clearContext();

            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.USER_NOT_EXIST));

            return;

        } catch (IllegalArgumentException e) {

            SecurityContextHolder.clearContext();

            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.TOKEN_NULL)
            );

            return;

        } catch (MalformedJwtException e){

            SecurityContextHolder.clearContext();

            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.TOKEN_UNTRUSTED)
            );

            return;

        }catch (Exception e) {

            SecurityContextHolder.clearContext();

        /*
        UnsupportedJwtException,SignatureException
         */
            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.FAILED)
            );

            return;

        }

        if (authentication != null){

            SecurityContextHolder.getContext().setAuthentication(authentication);

            authenticationInSessionAfter = SecurityContextHolder.getContext().getAuthentication();

        } else {

            SecurityContextHolder.clearContext();

            this.authenticationEntryPoint.commence(
                    request,
                    response,
                    new CustomAuthenticationException(ResultCodeEnum.LOGIN_FIRST));
        }

        chain.doFilter(request,response);

    }

}
