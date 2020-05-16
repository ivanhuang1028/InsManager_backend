package com.hl.common.util;

import org.springframework.web.multipart.MultipartFile;

import java.io.*;

public class Base64ChangeMultipartFile
        implements MultipartFile
{
    private final byte[] imgContent;
    private final String header;

    public Base64ChangeMultipartFile(byte[] imgContent, String header)
    {
        this.imgContent = imgContent;
        this.header = header.split(";")[0];
    }

    public String getName()
    {
        return System.currentTimeMillis() + Math.random() + "." + this.header.split("/")[1];
    }

    public String getOriginalFilename()
    {
        return System.currentTimeMillis() + (int)Math.random() * 10000 + "." + this.header.split("/")[1];
    }

    public String getContentType()
    {
        return this.header.split(":")[1];
    }

    public boolean isEmpty()
    {
        return (this.imgContent == null) || (this.imgContent.length == 0);
    }

    public long getSize()
    {
        return this.imgContent.length;
    }

    public byte[] getBytes()
            throws IOException
    {
        return this.imgContent;
    }

    public InputStream getInputStream()
            throws IOException
    {
        return new ByteArrayInputStream(this.imgContent);
    }

    public void transferTo(File file)
            throws IOException, IllegalStateException
    {
        new FileOutputStream(file).write(this.imgContent);
    }
}
