package com.itheima;

import io.jsonwebtoken.*;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

//@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void testUuid(){
        for(int i=0;i<1000;i++){
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }
    /*
    生成JWT
     */
    @Test
    public void testGenJWT(){
        Map<String,Object> claims=new HashMap<>();
        claims.put("id",1);
        claims.put("name","XuZh");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"hello")//签名算法 其中"XuZh"为用户自己添加并生成相应的签名秘钥.
                .setClaims(claims)//自定义内容(载荷)
                .setExpiration(new Date(System.currentTimeMillis()+3600*1000))//设置有效期为1h(这个函数的值类型为毫秒值)
                .compact();
        System.out.println(jwt);
    }
    /*
    解析JWT令牌
     */
    @Test
    public void testParseJwt(){
            Claims claims=Jwts.parser()
                    .setSigningKey("hello")
                    .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiWHVaaCIsImlkIjoxLCJleHAiOjE2OTUwMzQxMDd9.PoWtkWic97vKPVaXUaLViFSTOGa14wE2BiP2ajJoAY0")
                    .getBody();//对于已经签过名的token应该使用parseClaimsJws()方法.
            System.out.println(claims);
    }

}
