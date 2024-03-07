package java.com.bestrookie.springframework.core.io;

import cn.hutool.core.lang.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author bestrookie
 * @Date 2023/12/7 15:16
 * @Desc url读取文件
 */
public class UrlResource  implements Resource {
    private final URL url;

    public UrlResource(URL url){
        Assert.notNull(url, "URL must not be null");
        this.url = url;
    }
    @Override
    public InputStream getInputStream() throws Exception {
        URLConnection con = this.url.openConnection();
        try {
            return con.getInputStream();
        }catch (IOException e){
            if (con instanceof HttpURLConnection){
                ((HttpURLConnection) con).disconnect();
            }
        throw e;
        }
    }
}
