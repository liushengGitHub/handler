import liusheng.gulixueyuan.OkHttpClientUtils;
import liusheng.gulixueyuan.handler.CourseListHandler;
import liusheng.handler.DefaultPipeline;
import okhttp3.OkHttpClient;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 2020年:  05 月:  09 日:  11小时:  40分钟:
 * 用户名: 👨‍LiuSheng👨‍
 */

public class Test1 {
    @Test
    public void test1() throws Exception {
       /* OkHttpClient okHttpClient = OkHttpClientUtils.guliHttpClient();
        DefaultPipeline defaultPipeline = new DefaultPipeline();


        defaultPipeline.addHandler(new CourseListHandler());

        defaultPipeline.handle("http://www.gulixueyuan.com/course/369");*/
    }

    @Test
    public void test2() throws Exception {
        //0xec0a83c01f09fb7a79a93f8bcea44449
        //8d2051ea91b111ea9e0400163e0cae8e
        //AES-128

        byte[] bytes = IOUtils.toByteArray(Test1.class.getClassLoader().getResourceAsStream("1.ts"));

        bytes    = Decrypt(bytes);

        IOUtils.write(bytes,new FileOutputStream("1.ts"));
    }

    public static final String KEY = "34148cdc9a6811eaa7fc00163e0cae8e";//密钥key
    public static final String IV = "0xdfe90cbc048485fa2e9c93592cdd558d";//向量iv

    //解密
    public static byte[] Decrypt(byte[] content) throws Exception {
        try {
            byte[] raw = KEY.getBytes("utf-8");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec ips = new IvParameterSpec(getIv());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ips);
            byte[] original = cipher.doFinal(content);

            return  original;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static byte[] getIv() {
        String substring = IV.substring(2);

       byte[] bytes = new byte[substring.length() / 2];
        for (int i = 0; i < substring.length() / 2 ; i++) {
            String str  = substring.substring(i * 2 , i * 2 + 2);

            bytes[i] = (byte) Integer.parseInt(str,16);
        }
        
        return bytes;
    }
}
