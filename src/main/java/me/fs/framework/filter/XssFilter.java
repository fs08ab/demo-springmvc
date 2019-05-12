package me.fs.framework.filter;

import me.fs.framework.filter.wrapper.XssRequestWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 配置xss防御过滤器
 *
 * @author FS
 */
@WebFilter(urlPatterns = "/*", filterName = "filter02")
public class XssFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
