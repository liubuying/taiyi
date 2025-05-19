package cn.iocoder.yudao.framework.web.core.filter;

import cn.iocoder.yudao.framework.common.util.servlet.ServletUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;

/**
 *  Request Body 缓存 Wrapper
 *
 *
 */
public class CacheRequestBodyWrapper extends HttpServletRequestWrapper {

    /**
     * 缓存的内容
     */
    private byte[] body;

    public CacheRequestBodyWrapper(HttpServletRequest request) {
        super(request);
        try {
            body = ServletUtils.getBodyBytes(request);
        }catch (Exception e){
            body = new byte[0];
        }

    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {

        // 返回 ServletInputStream
        return new ServletInputStream() {
            final InputStream inputStream = new ByteArrayInputStream(body);
            @Override
            public int read() throws IOException {
                return inputStream.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener readListener) {}

            @Override
            public int available() {
                return body.length;
            }

        };
    }
    @Override
    public java.util.Enumeration<String> getHeaders(String name) {
        return super.getHeaders(name);
    }

    public byte[] getCachedBody() {
        return body;
    }

}
