package me.fs.framework.util;

import com.alibaba.fastjson.JSONObject;
import me.fs.framework.filter.wrapper.RereadRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 日志工具类
 *
 * @author FS
 */
public abstract class LogUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogUtils.class);

    /**
     * 记录系统访问日志，并传递过滤器
     */
    public static void logAccessAndDoFilter(RereadRequestWrapper requestWrapper, HttpServletResponse response, FilterChain chain) {
        AccessInfo accessInfo = getAccessInfo(requestWrapper);
        try {
            chain.doFilter(requestWrapper, response);

            accessInfo.setStatus(response.getStatus());
            accessInfo.setCost(System.currentTimeMillis() - accessInfo.getAccessTime());
        } catch (ServletException | IOException e) {
            accessInfo.setException(e.getMessage());
        } finally {
            accessInfo.log();
        }
    }

    /**
     * 记录系统访问日志
     */
    public static void logAccess(RereadRequestWrapper requestWrapper) {
        AccessInfo accessInfo = getAccessInfo(requestWrapper);
        accessInfo.log();
    }

    /**
     * 从请求对象中获取请求信息
     */
    private static AccessInfo getAccessInfo(RereadRequestWrapper requestWrapper) {
        Date accessTime = new Date();

        String ip = WebUtils.getUserIP(requestWrapper, false);
        String uri = requestWrapper.getRequestURI();
        String protocol = requestWrapper.getProtocol();
        String method = requestWrapper.getMethod();
        String contentType = requestWrapper.getContentType();
        String content = LogUtils.getRequestParameters(requestWrapper);
        String sessionId = requestWrapper.getSession().getId();
        String userAgent = requestWrapper.getHeader("User-Agent");
        String referrer = requestWrapper.getHeader("Referer");

        return new AccessInfo(ip, uri, accessTime, protocol, method, contentType, content, sessionId, userAgent, referrer);
    }

    /**
     * 获取请求参数
     */
    private static String getRequestParameters(RereadRequestWrapper requestWrapper) {
        String result;
        Map<String, String[]> parameterMap = requestWrapper.getParameterMap();
        if (parameterMap != null && !parameterMap.isEmpty()) {
            result = JSONObject.toJSONString(parameterMap);
        } else {
            int contentLength = requestWrapper.getContentLength();
            int limit = 10000;
            if (contentLength <= limit) {
                result = requestWrapper.getContent();
            } else {
                result = "parameter is very large:" + contentLength;
            }
        }

        if (result != null) {
            result = result.replaceAll("[\f\n\r\t]+| {2,}", " ");
        }
        return result;
    }

    /**
     * 封装请求、响应信息
     */
    static class AccessInfo {
        private Date accessTime;
        private String ip;
        private String uri;
        private String protocol;
        private String method;
        private String contentType;
        private String content;
        private String sessionId;
        private String userAgent;
        private String referrer;
        private Integer status;
        private Long cost;
        private String exception;

        AccessInfo(String ip, String uri, Date accessTime, String protocol, String method,
                   String contentType, String content,
                   String sessionId, String userAgent, String referrer) {
            this.ip = ip;
            this.uri = uri;
            this.accessTime = accessTime;
            this.protocol = protocol;
            this.method = method;
            this.contentType = contentType;
            this.content = content;
            this.sessionId = sessionId;
            this.userAgent = userAgent;
            this.referrer = referrer;
        }

        /**
         * 打印系统被访问的日志
         */
        void log() {
            String log = String.format("【%s】 request URI[%s] at %tT", ip, uri, accessTime);
            if (status != null) {
                log = String.format("%s cost %sms result [%s]", log, cost, status);
            }
            log = String.format("%s by %s(%s) {%s--%s--%s}%n%-20s%s %s",
                    log, protocol, method, sessionId, userAgent, referrer, " ", contentType, content);
            if (exception != null && !"".equals(exception)) {
                log = String.format("%s%n%s", log, exception);
                LOGGER.error(log);
            } else {
                LOGGER.info(log);
            }
        }

        void setStatus(int status) {
            this.status = status;
        }

        void setCost(long cost) {
            this.cost = cost;
        }

        void setException(String exception) {
            this.exception = exception;
        }

        Long getAccessTime() {
            if (accessTime != null) {
                return accessTime.getTime();
            }
            return null;
        }
    }
}
