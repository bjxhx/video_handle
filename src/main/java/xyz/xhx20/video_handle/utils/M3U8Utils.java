package xyz.xhx20.video_handle.utils;

import java.io.*;

public class M3U8Utils {

    /**
     * 修改文件内容
     *
     * @param filePath
     * @param oldStr
     * @param newStr
     * @return
     */
    public static boolean modifyFileContent(String filePath, String oldStr, String newStr) throws IOException {
        File inFile = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(inFile));
        String line = "";
        while (line != null) {
            line = reader.readLine();
        }



        return true;
    }
}
