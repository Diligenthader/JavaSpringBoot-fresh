package utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
用户登陆成功后，系统会自动下发JWT令牌，然后在后续的每次请求中，
都需要在请求头header中携带到服务端，请求头的名称为token，
值为登录时下发的JWT令牌
 */
public class JwtUtils {
    private static String singKey="XuZh";
    private static Long expire=3600000L;
    public static String generateJwt (Map<String,Object> claims){
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,singKey)//签名算法 其中"XuZh"为用户自己添加并生成相应的签名秘钥.
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis()+expire))//设置有效期为1h(这个函数的值类型为毫秒值)
                .compact();
        return jwt;
        //该函数将返回一个用base64编码的令牌用于返回.
    }
    /*
    解析JWT令牌
     */
    public static Claims parseJWT(String jwt){
        Claims claims=Jwts.parser()
                .setSigningKey(singKey)
                .parseClaimsJws(jwt)
                .getBody();//对于已经签过名的token应该使用parseClaimsJws()方法.
        return claims;
    }
}
