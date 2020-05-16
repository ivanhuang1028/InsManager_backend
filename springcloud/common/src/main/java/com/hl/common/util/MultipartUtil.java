package com.hl.common.util;

import com.google.common.base.Strings;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Decoder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class MultipartUtil
{
    Logger logger = LoggerFactory.getLogger(MultipartUtil.class);

    public static MultipartFile base64ToMultipart(String base64)
            throws Exception
    {
        try
        {
            String[] baseStr = base64.split(",");
            BASE64Decoder base64Decoder = new BASE64Decoder();
            byte[] b = new byte[0];
            b = base64Decoder.decodeBuffer(baseStr[1]);
            for (int i = 0; i < b.length; i++) {
                if (b[i] < 0)
                {
                    int tmp47_45 = i; byte[] tmp47_44 = b;tmp47_44[tmp47_45] = ((byte)(tmp47_44[tmp47_45] + 256));
                }
            }
            return new Base64ChangeMultipartFile(b, baseStr[0]);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean decryptByBase64(String base64, String filePath)
    {
        if ((Strings.isNullOrEmpty(base64)) && (Strings.isNullOrEmpty(filePath))) {
            return Boolean.FALSE;
        }
        try
        {
            Files.write(Paths.get(filePath, new String[0]),
                    Base64.decodeBase64(base64.substring(base64.indexOf(",") + 1)), new OpenOption[] { StandardOpenOption.CREATE });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return Boolean.TRUE;
    }

    public static String encryptToBase64(String filePath)
    {
        if (!Strings.isNullOrEmpty(filePath)) {
            try
            {
                byte[] bytes = Files.readAllBytes(Paths.get(filePath, new String[0]));
                return Base64.encodeBase64String(bytes);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static void main(String[] args)
    {
        String filePath = "D:\\logo.png";
        String base64Str = encryptToBase64(filePath);
        System.out.println(encryptToBase64(filePath));
        String targetFile = "D:\\logo1.png";
        decryptByBase64(base64Str, targetFile);

    }
}