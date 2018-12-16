package pers.fs.framework.filter.wrapper;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 包装request对象，使之可以重复读取内容
 *
 * @author FS
 */
public class RereadRequestWrapper extends HttpServletRequestWrapper {
    private final byte[] body;

    public RereadRequestWrapper(HttpServletRequest request) {
        super(request);
        body = readBytes(request);
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream arrayInputStream = new ByteArrayInputStream(body == null ? new byte[0] : body);
        return new ServletInputStream() {
            @Override
            public int read() {
                return arrayInputStream.read();
            }

            @Override
            public boolean isFinished() {
                return arrayInputStream.available() == 0;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {
            }
        };
    }

    @Override
    public BufferedReader getReader() {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    public String getContent() {
        if (this.body != null) {
            return new String(this.body);
        } else {
            return null;
        }
    }

    private byte[] readBytes(ServletRequest request) {
        try (ServletInputStream inputStream = request.getInputStream()) {
            int contentLength = request.getContentLength();
            if (contentLength <= 0) {
                return null;
            }

            byte[] buffer = new byte[contentLength];
            inputStream.read(buffer, 0, contentLength);

            return buffer;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
