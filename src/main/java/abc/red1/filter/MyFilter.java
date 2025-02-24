package abc.red1.filter;


import abc.red1.common.BaseContext;
import abc.red1.entity.R;
import com.alibaba.fastjson2.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



@Slf4j
@WebFilter(filterName = "MyFilter", urlPatterns = "/*")
public class MyFilter implements Filter {
    /**路径匹配器，支持通配符*/
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();
//        log.info("拦截到请求：{}",requestURI);

        /*定义不需要处理的请求路径*/
        String[] urls = new String[]{
                //登录登出
                "/manager/login",
                //css等配置文件
                "/front/css/**",
                "/front/fonts/**",
                "/front/images/**",
                "/front/js/**",
                "/front/lib/**",
                //登录界面
                "/front/pages/login.html",
                //错误界面
                "/front/pages/index/error.html",
                "/pc/**",
                "/excel/**",
                "/webjars/**"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if (check) {
            /*log.info("              请求{}不需要处理", requestURI);*/
            chain.doFilter(request, response);
            return;
        }

        //4-2、判断登录状态，如果已登录，则直接放行
        if (request.getSession().getAttribute("manager") != null) {
            /*log.info("用户已登录，用户id为：{}",request.getSession().getAttribute("manager"));*/

            Long managerId = (Long) request.getSession().getAttribute("manager");
            //全局唯一id
            BaseContext.setCurrentId(managerId);

            chain.doFilter(request, response);
            return;
        } else {
            log.info("用户未登录，请求为 {}", requestURI);
        }
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOT LOGIN")));

        return ;

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     *
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls, String requestURI) {
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
