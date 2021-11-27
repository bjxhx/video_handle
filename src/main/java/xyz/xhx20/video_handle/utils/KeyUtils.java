package xyz.xhx20.video_handle.utils;

import cn.hutool.core.lang.UUID;

import java.io.*;

public class KeyUtils {
    public static String createKeyToPath(String path) {
        String uuidStr = getUUIDStr();
        String keyName = uuidStr + ".key";
        String keyPath;
        if (path.charAt(path.length() - 1) == '/' || path.charAt(path.length() - 1) == '\\') {
            keyPath = path + keyName;
        } else if (path.lastIndexOf("\\") != -1) {
            keyPath = path + "\\" + keyName;
        } else {
            keyPath = path + "/" + keyName;
        }
        byte[] bytes = createKey();
//        System.out.println(key);
        try {

//            FileWriter writer = new FileWriter(keyPath);

            FileOutputStream out = new FileOutputStream(keyPath);
            out.write(bytes);
            out.close();
//            writer.write(chars);
//            writer.close();
            return keyName;
        } catch (IOException e) {
            System.out.println("创建" + keyName + "文件失败！原因：" + e.getMessage());
            return null;
        }
    }

    private static byte[] createKey() {
        String uuidStr = getUUIDStr();
        String str = uuidStr.substring(5, 21);
        byte[] bytes = str.getBytes();
        char[] chars = new char[16];
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < chars.length; i++) {
//            chars[i] = (char) (bytes[i] - 128);
            bytes[i] = (byte)(bytes[i] - 128);
        }
//        for (byte aByte : bytes) {
//
//            sb.append((char)(aByte - 128));
//        }
        return bytes;
    }

    public static String getUUIDStr() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
