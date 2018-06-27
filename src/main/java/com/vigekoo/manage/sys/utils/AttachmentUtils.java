package com.vigekoo.manage.sys.utils;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * attachment文件处理
 */
public class AttachmentUtils {
    private AttachmentUtils() {
    }

    /**
     * 创建文件名
     *
     * @param suffix
     * @return
     */
    public static String newFileName(String suffix) {
        String webRoot = FileUtils.getTempPath();

        String uuid = UUID.randomUUID().toString().replace("-", "");

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        StringBuilder newFileName = new StringBuilder(webRoot).append(File.separator).append("attachment")
                .append(File.separator).append(df.format(new Date())).append(File.separator).append(uuid)
                .append(suffix);

        File newfile = new File(newFileName.toString());

        if (!newfile.getParentFile().exists()) {
            newfile.getParentFile().mkdirs();
        }

        return newFileName.toString();
    }

    static List<String> imageSuffix = new ArrayList<String>();

    static {
        imageSuffix.add(".jpg");
        imageSuffix.add(".jpeg");
        imageSuffix.add(".png");
        imageSuffix.add(".bmp");
        imageSuffix.add(".gif");
    }

    public static boolean isImage(String path) {
        String sufffix = FileUtils.getSuffix(path);
        if (StringUtils.isNotBlank(sufffix))
            return imageSuffix.contains(sufffix.toLowerCase());
        return false;
    }

}
