package xyz.xhx20.video_handle.utils;

import lombok.Data;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//@Data
public class VideoHandleUtils {
    public static boolean conversionVideo(String inputFilePath,String fileName) {
        String date = new SimpleDateFormat("yyyy_MM_dd").format(new Date());
        String ffmpegExePath = "D:\\test_video\\ffmpeg\\bin\\ffmpeg.exe";
//        String inputFilePath = "D:\\test_video\\test.mp4";
        String outputFilePath = "E:\\tempVideo\\"+date+"\\";
        File file = new File(outputFilePath);
        if (!file.exists()) {
            file.mkdirs();
        }
//        System.out.println(outputFilePath);
        List<String> command = new ArrayList<String>();
        command.add(ffmpegExePath);
        command.add("-i");
        command.add(inputFilePath);
        command.add("-force_key_frames");
        command.add("\"expr:gte(t,n_forced*6)\"");
//        command.add("-vcodec");
//        command.add("copy");
//        command.add("-acodec");
//        command.add("copy");
        command.add("-codec");
        command.add("copy");
        command.add("-vbsf");
        command.add("h264_mp4toannexb");
        command.add("-hls_playlist_type");
        command.add("vod");
        command.add("-hls_flags");
        command.add("independent_segments");

        command.add("-hls_time");
        command.add("6");
        command.add("-hls_list_size");
        command.add("0");

//        command.add("-hls_flags");
//        command.add("split_by_time");


        command.add(outputFilePath + fileName);
        ProcessBuilder builder = new ProcessBuilder();
        builder.command(command);
        //正常信息和错误信息合并输出
        builder.redirectErrorStream(true);
        try {
            //开始执行命令
            Process process = builder.start();
            //如果你想获取到执行完后的信息，那么下面的代码也是需要的
            StringBuffer sbf = new StringBuffer();
            String line = null;
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                sbf.append(line);
                sbf.append(" \n");
            }
            String resultInfo = sbf.toString();
            System.out.println(resultInfo);
            if (resultInfo.indexOf("Conversion failed!") != -1 || resultInfo.indexOf("Error") != -1) {
                System.out.println("转码失败！日志：\n"+resultInfo);
                return false;
            } else {
                System.out.println("转码成功！");
                return true;
            }
        } catch (IOException e) {
            System.out.println("转码失败！日志：\n"+e.getMessage());
            return false;

        }
    }




}
