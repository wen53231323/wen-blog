package com.wen.service.impl;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.wen.exception.SystemException;
import com.wen.pojo.enums.AppHttpCodeEnum;
import com.wen.service.UploadService;
import com.wen.utils.FileUtils;
import com.wen.utils.ResponseResult;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

@Setter
@Getter
@Service
// @ConfigurationProperties注解：Springboot提供读取配置文件的一个注解，需提供其setter和getter方法
@ConfigurationProperties(prefix = "oss")
public class UploadServiceImpl implements UploadService {

    // 获取配置文件中的属性
    private String accessKey;// 密钥AK
    private String secretKey;// 密钥SK
    private String bucket;// 空间名称
    private String testUrl;// 七牛云提供的测试域名

    @Override
    public ResponseResult uploadImg(MultipartFile img) {
        // TODO 判断文件类型
        // 获取图片文件名
        String imgName = img.getOriginalFilename();
        // 对原始文件名后缀进行判断，不符合则抛出异常提示
        if (!FileUtils.isImage(imgName)) {
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // TODO 使用工具类生成日期路径下的文件名
        String filePath = FileUtils.getDatePathUniqueFile(imgName);

        // TODO 判断通过则上传至七牛云并得到外链地址（图片url）
        String url = uploadOss(img, filePath);
        return ResponseResult.okResult(url);
    }

    /**
     * 上传到七牛云返回图片的url
     *
     * @param img      上传的图片
     * @param filePath 上传的图片路径和图片名
     * @return 图片的外链地址（url），即测试域名 + 上传的图片路径和图片名
     */

    public String uploadOss(MultipartFile img, String filePath) {
        //构造一个带指定 Region 对象的配置类，指定存储区域，和存储空间选择的区域一致（autoRegion()方法表示自动）
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本

        // 其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        // 指定文件路径和文件名（默认不指定key的情况下，以文件内容的hash值作为文件名）
        String key = filePath;
        try {
            // 添加本地文件字节数组
            InputStream byteInputStream = img.getInputStream();
            // 从配置文件读取上传凭证，进行认证
            Auth auth = Auth.create(accessKey, secretKey);
            // 认证通过后得到token（令牌）
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

        } catch (IOException e) {

        }
        // 返回测试域名 + 上传的图片路径和图片名
        return testUrl + filePath;
    }
}
