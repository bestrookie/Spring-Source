package com.bestrookie.springframework.core.io;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * @Author bestrookie
 * @Date 2023/12/7 15:11
 * @Desc 文件读取
 */
public class FileSystemResource implements Resource{
    private final File file;

    private final String path;

    public FileSystemResource(File file){
        this.file = file;
        this.path = file.getPath();
    }

    public FileSystemResource(String path){
        this.file = new File(path);
        this.path = path;
    }
    @Override
    public InputStream getInputStream() throws Exception {
        return Files.newInputStream(this.file.toPath());
    }

    public final String getPath(){
        return this.path;
    }
}
