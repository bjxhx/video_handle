package xyz.xhx20.video_handle;


import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.KeyUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import xyz.xhx20.video_handle.utils.KeyUtils;
import xyz.xhx20.video_handle.utils.M3U8Utils;
import xyz.xhx20.video_handle.utils.UploadUtils;
import xyz.xhx20.video_handle.utils.VideoHandleUtils;

import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
//import org.junit.Test;

//@SpringBootTest
class VideoHandleApplicationTests {
    @Test
    public void test_handleVideo() throws IOException {
       String url = "http://tsm.couponto.cn/api/manage/cos/upload";
        Map<String,Object> formMap = new HashMap<>();
        formMap.put("type","3001");
        formMap.put("appId","L1nH81dwgL23FL4i");
        formMap.put("apiVersion","v1");
        formMap.put("timestamp","1637919203000");
//        formMap.put("accessToken","487faac29d9e49f1ac65e50b8a13c972_b20510b0543a4f17becf3cbbe6791d21_85bd4e6fb5b5452cba1c681355ce850e#c14f173b273e49759b440ea201ddde4a_1637916287061_597f35e94c477ea898e719b9e8a35c19");
        formMap.put("sign","b1479b06cc1b04863d4a29e7ccd383fa");

        HttpResponse response = HttpRequest.post(url).form(formMap).header("Content-Type", "multipart/form-data; boundary=----WebKitFormBoundary0jjA64beNliGgEUw").form("file", new File("D:\\test_video\\key\\key.jpg"), "key.jpg").execute();
        System.out.println(response);

    }


    @Test
    public void contextLoads() throws IOException {
        File dirFile = new File("D:/test_video/");
        File[] files = dirFile.listFiles();

        for (File file : files) {
            if (file.getName().indexOf("ts") != -1 || file.getName().indexOf("m3u8") != -1) {

                byte[] resultBytes = Files.readAllBytes(file.toPath());

                String fileSize = String.valueOf(file.length());
                String fileName = file.getName();
                String token = "90890d14dcc575bfd71d774ca0e88694";

                JSONObject uploadData = UploadUtils.getUploadData(fileName, fileSize, token);
                System.out.println("uploadData:" + uploadData.toString());
                if (uploadData != null && !uploadData.isEmpty()) {
                    JSONObject data = uploadData.getJSONObject("data");
                    String fileId = data.getStr("Id");
                    String cdnUrl = data.getStr("cdnUrl");
                    String uploadUrl = data.getStr("SignUrl");

                    boolean uploadFlag = UploadUtils.uploadToAiliYun(uploadUrl, resultBytes);
                    if (uploadFlag) {
                        boolean registerFlag = UploadUtils.registerFile(fileId, token);
                        if (registerFlag) {
                            System.out.println(fileName + ": 上传成功！");
                            file.delete();
                        }
                    }
                }
            }

        }
    }

}
