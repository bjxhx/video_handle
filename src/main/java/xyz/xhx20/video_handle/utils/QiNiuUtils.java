package xyz.xhx20.video_handle.utils;

import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class QiNiuUtils {
    private static String upToken;

    static {
        upToken = QiNiuConfig.getInstance().getUpToken();
    }
    /**
     * 根据文件路径上传七牛
     * @param filePath
     * @param key
     * @return
     */
    public static boolean uploadFileByFilePath(String filePath, String key) {
        UploadManager uploadManager = getUploadManager();
        try {
            Response response = uploadManager.put(filePath, key, upToken);
            return response.isOK();
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件上传七牛
     * @param file
     * @param key
     * @return
     */
    public static boolean uploadFileByFile(File file, String key) {
        UploadManager uploadManager = getUploadManager();
        try {
            Response response = uploadManager.put(file, key, upToken);
            return response.isOK();
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据文件数据流进行上传
     * @param fileBytes
     * @param key
     * @return
     */
    public static boolean uploadFileByBytes(byte[] fileBytes,String key) {
        UploadManager uploadManager = getUploadManager();
        try {
            Response response = uploadManager.put(fileBytes, key, upToken);
            return response.isOK();
        } catch (QiniuException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static String getDownloadUrl(String key, boolean isPrivate) {
        try {
            String encodedFileName  = URLEncoder.encode(key, "utf-8").replace("+", "%20");
            String finalUrl = String.format("%s/%s", QiNiuConfig.getInstance().getDomainOfBucket(), encodedFileName);
            if (!isPrivate) {
                return finalUrl;
            }
            Auth auth = getAuth();
            return auth.privateDownloadUrl(finalUrl);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }




    }


    private static UploadManager getUploadManager() {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Region.regionAs0());
        UploadManager uploadManager = new UploadManager(cfg);
        return uploadManager;
    }
    private static Auth getAuth() {
        return Auth.create(QiNiuConfig.getInstance().getAccessKey(), QiNiuConfig.getInstance().getSecretKey());
    }
}
