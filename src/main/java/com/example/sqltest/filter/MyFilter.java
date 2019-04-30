package com.example.sqltest.filter;

import com.example.sqltest.service.UserSer;
import org.springframework.context.ApplicationContext;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import javax.servlet.Filter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class MyFilter implements Filter {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest srequest, ServletResponse sresponse, FilterChain filterChain) throws IOException, ServletException {
        // TODO Auto-generated method stub
        HttpServletRequest request = (HttpServletRequest) srequest;
        System.out.println("this is MyFilter,url :"+request.getRequestURI());
        if(!StringUtils.isEmpty(request.getHeader("Authorization"))){

            // 由于Filter中无法使用@Autowrid注解,故通过这种方式拿到spring容器中的service实例
            ServletContext context = request.getServletContext();
            ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
            UserSer userSer = ctx.getBean(UserSer.class);
            userSer.checkJjwt(request.getHeader("Authorization"));
            filterChain.doFilter(srequest, sresponse);
        } else {
            throw new RuntimeException("请先登录");
        }
    }

}