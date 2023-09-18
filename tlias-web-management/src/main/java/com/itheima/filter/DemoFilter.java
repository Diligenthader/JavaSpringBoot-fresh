package com.itheima.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
/*
1.定义Filter：定义一个类，实现Filter接口，并重写其所有方法
2.配置Filter：Filter类上加@WebFilter注解，配置拦截资源的路径。
引导类上加@ServletComponenScan开启Servlet组件支持
 */
//@WebFilter(urlPatterns = "/*") //表示所有的路径.
public class DemoFilter implements Filter {
    @Override //初始化方法，仅调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Demo_filter init method has run");
    }

    @Override//拦截到请求之后调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Demo_filter method has run");//当这个过滤器filter拦下了来自客户端的访问请求时，并未直接放行让其访问相关的数据，而是将访问请求拦截下来.
        //放行
        filterChain.doFilter(servletRequest,servletResponse);

        System.out.println("Demo_filter admitted the request!");
    }

    @Override
    public void destroy() {
        System.out.println("Demo_filter destroy method has run");
    }

    //通过阅读源码可知，对于filter的init和destroy均有默认销毁操作.
}
