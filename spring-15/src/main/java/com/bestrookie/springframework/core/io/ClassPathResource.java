package com.bestrookie.springframework.core.io;

import cn.hutool.core.lang.Assert;
import com.bestrookie.springframework.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * @Author bestrookie
 * @Date 2023/12/7 11:35
 * @Desc 路径读取
 */
public class ClassPathResource implements Resource {
    private final String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path){
        this(path, (ClassLoader) null);
    }

    public ClassPathResource(String path, ClassLoader classLoader){
        Assert.notNull(path, "Path must not be null");
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }
    @Override
    public InputStream getInputStream() throws Exception {
        InputStream is = classLoader.getResourceAsStream(path);
        if (is == null){
            throw new FileNotFoundException(this.path + "cannot be opened because it does not exist");
        }
        return is;
    }
}
