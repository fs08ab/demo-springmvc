package me.fs.framework.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author FS
 */
public abstract class WebUtils {
    /**
     * 通过用户请求获取用户的IP
     *
     * @param request 用户请求
     * @param strict  对客户端校验比较严格的场景（比如投票系统防止刷票）要获取客户端ip时，可将该参数置为true
     * @return 用户IP
     */
    public static String getUserIP(HttpServletRequest request, boolean strict) {
        String tempIp = null;
        String unknownFlag = "unknown";
        if (!strict) {
            // 1、这些请求头都不是http协议里的标准请求头，是各个代理服务器自己规定的表示客户端地址的请求头
            // 2、代理服务器一般都可以自定义请求头设置，不一定会带上这些请求头
            // 3、不同的网络架构，判断请求头的顺序是不一样的
            // 4、最重要的一点，请求头都是可以伪造的
            tempIp = request.getHeader("X-FORWARDED-FOR");
            if (tempIp != null && !"".equals(tempIp) && !unknownFlag.equalsIgnoreCase(tempIp)) {
                // X-FORWARDED-FOR：只有在通过了HTTP代理或者负载均衡服务器时才会添加该项
                // 格式为X-Forwarded-For:client1,proxy1,proxy2
                // 一般情况下，第一个ip为客户端真实ip，后面的为经过的代理服务器ip
                tempIp = tempIp.split(",")[0];
            }
            if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
                tempIp = request.getHeader("Proxy-Client-IP");
                if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
                    tempIp = request.getHeader("WL-Proxy-Client-IP");
                    if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
                        tempIp = request.getHeader("HTTP_Client_IP");
                        if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
                            tempIp = request.getHeader("HTTP_X_FORWARDED_FOR");
                            if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
                                tempIp = request.getHeader("X-Real-IP");
                            }
                        }
                    }
                }
            }
        }
        if (tempIp == null || "".equals(tempIp) || unknownFlag.equalsIgnoreCase(tempIp)) {
            // 大部分情况下都是有效的，但是在通过了反向代理软件就不能获取到客户端的真实IP地址
            // 虽然获取到的可能是代理的IP而不是客户端的IP，但这个获取到的IP基本上是不可能伪造的
            // 在投票场景下，就杜绝了刷票的可能
            tempIp = request.getRemoteAddr();
        }
        return tempIp;
    }
}
