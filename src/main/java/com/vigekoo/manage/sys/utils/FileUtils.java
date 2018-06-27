package com.vigekoo.manage.sys.utils;

import com.vigekoo.manage.core.exception.AppException;
import com.vigekoo.manage.sys.constants.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * @Author : ljx
 * @Description : 文件处理
 * @Param :
 * @Date : 2018/6/21 14:20
 */
public class FileUtils {
    private final static Logger logger = LoggerFactory.getLogger(FileUtils.class);

    private FileUtils() {
    }

    public static String getSuffix(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf('.'));
        }
        return null;
    }

    public static String removePrefix(String src, String prefix) {
        if (src != null && src.startsWith(prefix)) {
            return src.substring(prefix.length());
        }
        return src;
    }

    public static String readString(File file) {
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            for (int len = 0; (len = fis.read(buffer)) > 0; ) {
                baos.write(buffer, 0, len);
            }
            return new String(baos.toByteArray(), Constant.ENCODING_UTF_8);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            close(fis, baos);
        }
        return null;
    }

    public static void writeString(File file, String string) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file, false);
            fos.write(string.getBytes(Constant.ENCODING_UTF_8));
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            close(null, fos);
        }
    }

    private static void close(InputStream is, OutputStream os) {
        if (is != null)
            try {
                is.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        if (os != null)
            try {
                os.close();
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
    }

    public static void unzip(String zipFilePath) throws IOException {
        String targetPath = zipFilePath.substring(0, zipFilePath.lastIndexOf('.'));
        unzip(zipFilePath, targetPath);
    }

    public static void unzip(String zipFilePath, String targetPath) throws IOException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        try {
            Enumeration<?> entryEnum = zipFile.entries();
            if (null != entryEnum) {
                while (entryEnum.hasMoreElements()) {
                    OutputStream os = null;
                    InputStream is = null;
                    try {
                        ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
                        if (!zipEntry.isDirectory()) {
                            File targetFile = new File(targetPath + File.separator + zipEntry.getName());
                            if (!targetFile.getParentFile().exists()) {
                                targetFile.getParentFile().mkdirs();
                            }
                            os = new BufferedOutputStream(new FileOutputStream(targetFile));
                            is = zipFile.getInputStream(zipEntry);
                            byte[] buffer = new byte[4096];
                            int readLen = 0;
                            while ((readLen = is.read(buffer, 0, 4096)) > 0) {
                                os.write(buffer, 0, readLen);
                            }
                        }
                    } finally {
                        if (is != null)
                            is.close();
                        if (os != null)
                            os.close();
                    }
                }
            }
        } finally {
            zipFile.close();
        }
    }

    /**
     * 获取临时目录
     */
    public static String getTempPath() {
        return System.getProperty("java.io.tmpdir");
    }

    /**
     * 文件下载
     *
     * @param path
     * @param fileName
     * @param response
     */
    public static void download(String path, String fileName, HttpServletResponse response) {
        File file = new File(path);
        if (!file.exists()) {
            throw new AppException("文件不存在");
        } else {
            response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
                throw new AppException("系统异常");
            } finally {
                try {
                    if (os != null) {
                        os.close();
                    }
                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage());
                    throw new AppException("系统异常");
                }
            }
        }
    }
}