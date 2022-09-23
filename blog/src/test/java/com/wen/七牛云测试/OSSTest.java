package com.wen.七牛云测试;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

@Getter
@Setter
@SpringBootTest
// @ConfigurationProperties注解：Springboot提供读取配置文件的一个注解，需提供其setter和getter方法
@ConfigurationProperties(prefix = "oss")
public class OSSTest {
    // 获取配置文件中的属性
    private String accessKey;
    private String secretKey;
    private String bucket;

    @Test
    public void test() {
        //构造一个带指定 Region 对象的配置类，指定存储区域，和存储空间选择的区域一致（autoRegion()方法表示自动）
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传（注释明文填入，改为从配置文件读取）
        // String accessKey = "your access key";
        // String secretKey = "your secret key";
        // String bucket = "your bucket name";

        // 指定文件路径和文件名（默认不指定key的情况下，以文件内容的hash值作为文件名）
        String key = "test/test.png";
        try {
            // byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
            // ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);

            // 添加本地文件字节数组
            InputStream byteInputStream = new FileInputStream("E:\\音、视、图\\图片\\静态图片\\1.png");
            //认证
            Auth auth = Auth.create(accessKey, secretKey);
            //认证通过后得到token（令牌）
            String upToken = auth.uploadToken(bucket);
            try {
                //上传文件，所需参数：字节数组，key，token令牌（key: 建议我们自已生成一个不重复的名称）
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {

                }
            }
        } catch (FileNotFoundException e) {

        }
    }
}
