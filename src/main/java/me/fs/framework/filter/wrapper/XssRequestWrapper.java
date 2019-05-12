package me.fs.framework.filter.wrapper;

import org.springframework.util.StringUtils;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 防御跨站脚本攻击(Cross Site Scripting)
 *
 * @author FS
 */
public class XssRequestWrapper extends HttpServletRequestWrapper {
    public XssRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getHeader(String name) {
        String header = super.getHeader(name);
        if (!StringUtils.isEmpty(header)) {
            return HtmlUtils.htmlEscape(header);
        }
        return header;
    }

    @Override
    public Object getAttribute(String name) {
        Object attribute = super.getAttribute(name);
        if (attribute instanceof String) {
            return HtmlUtils.htmlEscape((String) attribute);
        }
        return attribute;
    }

    @Override
    public String getParameter(String name) {
        String parameter = super.getParameter(name);
        if (!StringUtils.isEmpty(parameter)) {
            return HtmlUtils.htmlEscape(parameter);
        }
        return parameter;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] parameterValues = super.getParameterValues(name);
        if (parameterValues != null) {
            int length = parameterValues.length;
            if (length > 0) {
                String[] pureValues = new String[length];
                for (int i = 0; i < length; i++) {
                    pureValues[i] = HtmlUtils.htmlEscape(parameterValues[i]);
                }
                return pureValues;
            }
        }
        return parameterValues;
    }
}
