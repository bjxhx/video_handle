package xyz.xhx20.video_handle.utils;

import com.qiniu.util.Auth;
import lombok.Data;

import java.util.Properties;

@Data
public class QiNiuConfig {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String domainOfBucket;
    private long expireInSeconds;
    private String upToken;

    private static QiNiuConfig instance = new QiNiuConfig();

    private QiNiuConfig(){
        Properties prop = new Properties();
        try {
            prop.load(QiNiuConfig.class.getResourceAsStream("/qiniu.properties"));
            accessKey = prop.getProperty("qiniu.access-key");
            secretKey = prop.getProperty("qiniu.secret-key");
            bucket = prop.getProperty("qiniu.bucket");
            domainOfBucket = prop.getProperty("qiniu.domain-of-bucket");
            expireInSeconds = Long.parseLong(prop.getProperty("qiniu.expire-in-seconds"));
            upToken = Auth.create(accessKey, secretKey).uploadToken(bucket);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static QiNiuConfig getInstance(){
        return instance;
    }

}
