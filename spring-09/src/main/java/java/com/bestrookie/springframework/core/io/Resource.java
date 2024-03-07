package java.com.bestrookie.springframework.core.io;

import java.io.InputStream;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:31
 * @Desc 提供湖区InputStream流的方法
 */
public interface Resource {

    /**
     * 获取InputStream流
     * @return InputStream流
     */
    InputStream getInputStream() throws Exception;
}
