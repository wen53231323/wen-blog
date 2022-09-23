package com.wen.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件相关的工具类
 * */
public class FileUtils {
    /**
     * 获取文件后缀
     *
     * @param fileName 文件名称
     * @return 文件后缀
     */
    public static String getFileExtension(String fileName) {
        // 获取字符串中最后一个指定字符的位置，即获取点的位置
        int index = fileName.lastIndexOf(".");
        // 获取字符串的子字符串，即获取文件后缀
        String extension = fileName.substring(index);
        return extension;
    }

    /**
     * 根据文件扩展名判断文件是否图片格式
     *
     * @param fileName 文件名称
     * @return 是否是图片
     */
    public static boolean isImage(String fileName) {
        String extension = getFileExtension(fileName);
        String[] imageExtension = new String[]{".png", ".jpg", ".jpeg", ".gif", ".bmp"};

        for (String e : imageExtension) {
            if (extension.toLowerCase().equals(e)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 获取日期路径下的唯一文件名
     *
     * @param fileName 文件名称
     * @return 当前日期格式的路径 + uuid + 文件后缀
     */
    public static String getDatePathUniqueFile(String fileName) {
        // 格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        // 根据日期生成路径（2022/2/22/）
        String datePath = sdf.format(new Date());
        // 生成uuid，作为文件名（此处把uuid生成的 - 符号给替换掉了）
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        // 获取文件后缀
        String fileExtension = getFileExtension(fileName);
        // 连接字符串
        String newFile = new StringBuilder().append(datePath).append(uuid).append(fileExtension).toString();
        return newFile;
    }

}