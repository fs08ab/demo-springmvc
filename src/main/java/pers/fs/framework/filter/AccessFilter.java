package pers.fs.framework.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pers.fs.framework.filter.wrapper.RereadRequestWrapper;
import pers.fs.framework.util.LogUtils;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 在用户请求时的统一操作，比如：将请求信息记录日志<br/>
 * 经测试，“@WebFilter”配置的filter的执行顺序与类的全路径名有关，即与“包名+类名”有关，与filterName无关
 *
 * @author FS
 */
@WebFilter(urlPatterns = "/*", filterName = "filter03")
public class AccessFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessFilter.class);

    @Override
    public void init(FilterConfig filterConfig) {
        LOGGER.info(">>>>>>>>>>Startup System<<<<<<<<<<");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {
        if (request instanceof HttpServletRequest) {
            RereadRequestWrapper requestWrapper = new RereadRequestWrapper((HttpServletRequest) request);
            LogUtils.logAccessAndDoFilter(requestWrapper, (HttpServletResponse) response, chain);
        }
    }

    @Override
    public void destroy() {
        LOGGER.info(">>>>>>>>>>Shutdown System<<<<<<<<<<");
    }
}
