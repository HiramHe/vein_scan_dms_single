package hiram.utils;

import hiram.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClaims;

import java.util.Base64;
import java.util.Date;
import java.util.UUID;


/**
 * @Author: HiramHe
 * @Date: 2020/5/5 13:41
 * @Description: "jwt工具类"
 */

/*
 * JwtException继承RuntimeException，
 * 所以JwtException及其子类均为运行时异常。
 */
public class JwtUtils {

    //私钥
    private static final String DEFAULT_SECRET = "hiram";
    private static String sec = null;

    public static String generateToken(long expireTimeMillis, String key, String value, String secret) {

        if (secret == null){
            sec = DEFAULT_SECRET;
        }else {
            sec = secret;
        }

        Claims claims = new DefaultClaims();
        claims.put(key, value);

        return Jwts
                .builder()

                //设置token的header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg",SignatureAlgorithm.HS512.getValue())

                //设置token过期时间
                .setSubject("user uuid")
                .setIssuedAt(new Date())
                .setExpiration( new Date(System.currentTimeMillis() + expireTimeMillis) )

                //设置token主体部分
                .setClaims(claims)

                //token的签名
                .signWith(SignatureAlgorithm.HS512, sec)
                .compact();
    }

    /**
     * 生成 token
     * @param claims
     * @return
     */
    public static String generateToken(Claims claims,String secret) {

        if (secret == null){
            sec = DEFAULT_SECRET;
        }else {
            sec = secret;
        }

        return Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, sec)
                .compact();
    }

    private static String createJTI() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 从数据声明生成 token
     * @param object
     * @param millis
     * @return
     */
    public static String generateTokenExpireInMillis(Object object,long millis){

        Date issued_at = new Date();
        Date expirationTime = new Date(issued_at.getTime()+millis);

        Claims claims = new DefaultClaims();
        claims.setSubject(ResponseUtils.toString(object));
        claims.setIssuedAt(issued_at);
        claims.setExpiration(expirationTime);
        claims.setId(createJTI());

        return generateToken(claims,sec);
    }

    /**
     * 从数据声明生成 token
     * 不用使用Map形式的claims，那种设置的各种时间无效。
     * @param
     * @return
     */
    public static String generateTokenExpireInMinutes(Object object,long minutes) {

        return generateTokenExpireInMillis(object,1000*60*minutes);
    }

    /**
     * 从数据声明生成 token
     * @param
     * @return
     */
    public static String generateTokenExpireInSeconds(Object object,long seconds){

        return generateTokenExpireInMillis(object,1000*seconds);
    }

    /**
     * 解析token
     * @param
     * @param
     * @return
     */
    public static Jws<Claims> getJwsFromToken(String token) throws Exception {

        /**
         * 在解析时可能出现各种异常，
         * 比如token格式错误，token过期异常等，
         * 全部抛出去。
         */
        return Jwts
                .parser()
                .setSigningKey(DEFAULT_SECRET)
                .parseClaimsJws(token);

    }

    public static Claims parseToken(String token,String secret) throws Exception{
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    public static boolean checkToken(String token){
        if (MyStringUtils.isEmpty(token)){
            return false;
        }

        try {
            Jwts.parser().setSigningKey(sec).parseClaimsJws(token);
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * 从token中获取body
     * @param
     * @param
     * @return
     */
    public static Claims getClaimsFromToken(String token) throws Exception {

        return getJwsFromToken(token).getBody();
    }

    /**
     * 获取token中的载荷信息，不包括object
     *
     * @param
     * @param
     * @return
     */
    /*public static <T> Payload<T> getPayloadFromToken(String token) throws Exception {

        Claims body = getClaimsFromToken(token);

        Payload<T> payload = new Payload<T>();
        payload.setId(body.getId());
        payload.setExpiration(body.getExpiration());

        return payload;
    }*/

    /**
     * 获取token中的载荷信息，包括object
     * @param
     * @param
     * @param
     * @return
     */
    /*public static <T> Payload<T> getPayloadFromToken(String token,Class<T> tClass) throws Exception {

        Claims body = getClaimsFromToken(token);

        Payload<T> payload = new Payload<T>();
        payload.setId(body.getId());
        payload.setObject(JsonUtils.toBean(body.getSubject(), tClass));
        payload.setExpiration(body.getExpiration());

        return payload;
    }*/

    /**
     * 刷新token
     *
     * @param token 原令牌
     * @return 新令牌
     */
    public static String refreshToken(String token,long milliseconds) throws Exception {
        String refreshedToken;
        Date issued_at = new Date();
        Date expirationTime = new Date(issued_at.getTime()+milliseconds);

        Claims claims = getClaimsFromToken(token);
        claims.setIssuedAt(issued_at);
        claims.setExpiration(expirationTime);
        refreshedToken = generateToken(claims,sec);

        return refreshedToken;
    }

    /**
     * 判断令牌是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public static boolean isTokenExpired(String token) throws Exception {

        Claims claims = getClaimsFromToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());

    }

}
