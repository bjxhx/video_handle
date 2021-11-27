package xyz.xhx20.video_handle.utils;

import cn.hutool.extra.ssh.JschUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.io.File;

public class UploadUtils {

    public static JSONObject getUploadData(String fileName, String size, String token) {
        String apiUrl = "https://unicloud-api.dcloud.net.cn/unicloud/api/file/upload-info?spaceId=bbb1c855-4f1c-4020-9a16-b8c06072ba0a&appid=&provider=aliyun&name=" + fileName + "&size=" + size;
        try {
            HttpResponse response = HttpRequest.get(apiUrl).header("token", token).execute();
            if (response.isOk()) {
                return JSONUtil.parseObj(response.body());
            }
            return null;
        } catch (Exception e) {
//            e.printStackTrace();
            System.out.println("获取上传数据失败！原因：" + e.getMessage());
            return null;
        }

    }

    public static boolean uploadToAiliYun(String uploadUrl, byte[] bodyBytes) {
        try {
            HttpResponse response = HttpRequest.put(uploadUrl).header("Content-Type", "application/octet-stream").body(bodyBytes).execute();
            if (response.isOk()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("上传文件失败！原因：" + e.getMessage());
            return false;
        }

    }

    public static boolean uploadToAiliYun(String uploadUrl, File file) {
        try {
            HttpResponse response = HttpRequest.put(uploadUrl).header("Content-Type", "application/octet-stream").form("file",file).execute();
            System.out.println(response.body());
            if (response.isOk()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("上传文件失败！原因：" + e.getMessage());
            return false;
        }

    }

    public static boolean registerFile(String fileId, String token) {
        String apiUrl = "https://unicloud-api.dcloud.net.cn/unicloud/api/file/register?provider=aliyun&spaceId=bbb1c855-4f1c-4020-9a16-b8c06072ba0a&appid=&fileId=" + fileId;
        try {
            HttpResponse response = HttpRequest.post(apiUrl).header("token", token).execute();
            if (response.isOk()) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("注册文件失败！原因：" + e.getMessage());
//            e.printStackTrace();
            return false;
        }
    }
}
