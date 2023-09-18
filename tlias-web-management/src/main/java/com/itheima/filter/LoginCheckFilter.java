package com.itheima.filter;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.itheima.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import utils.JwtUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse resp=(HttpServletResponse) response;
        //1.获取请求的URL
        String url=req.getRequestURL().toString();
        log.info("请求的url:{}",url);


        //2.判断请求URL中是否包含login，如果包含，说明是登录操作，放行
        if(url.contains("login")){
            log.info("登录操作，放行...");
            chain.doFilter(request,response);
            return ;
        }
        //3.获取请求头中的令牌(token)
        String jwt=req.getHeader("token");
        //4.判断令牌是否存在，如果不存在，返回错误结果(未登录)
        if(!StringUtils.hasLength(jwt)){
            log.info("请求头为空，返回未登录的信息...");
            Result error=Result.error("NOT_LOGIN");
            //手动转换 对象--json ------->阿里巴巴fastJSON
            String notlogin=JSONObject.toJSONString(error);
            resp.getWriter().write(notlogin);
            return ;
        }
        //5.解析token，如果解析失败，返回错误结果(未登录)
        try {
            JwtUtils.parseJWT(jwt);
        } catch (Exception e) {//对于这个令牌的解析来说，如果解析失败将会抛出一个错误，那么就可以用catch去捕获这个错误
            e.printStackTrace();
            log.info("解析令牌失败，返回未登录错误信息...");
            Result error=Result.error("NOT_LOGIN");
            //手动转换 对象--json ------->阿里巴巴fastJSON
            String notlogin=JSONObject.toJSONString(error);
            resp.getWriter().write(notlogin);
            return ;

        }
        //Ctrl+ALT+T 表示为生成包围函数
        //6.放行
        log.info("令牌合法，放行...");
        chain.doFilter(request,response);

    }


}
