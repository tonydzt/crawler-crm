package com.kasuo.crawler.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FileTool {
    private static final Logger logger = LoggerFactory.getLogger(FileTool.class);
    private static String MESSAGE = "";


    public FileTool() {
    }


    public static boolean copyFile(String srcFileName, String destFileName, boolean overlay) {
        File srcFile = new File(srcFileName);


        if (!srcFile.exists()) {
            MESSAGE = "源文件：" + srcFileName + "不存在！";
            logger.error(MESSAGE);
            return false;
        }
        if (!srcFile.isFile()) {
            MESSAGE = "复制文件失败，源文件：" + srcFileName + "不是一个文件！";
            logger.error(MESSAGE);
            return false;
        }


        File destFile = new File(destFileName);
        if (destFile.exists()) {
            if (overlay) {
                new File(destFileName).delete();
            }

        } else if (!destFile.getParentFile().exists()) {
            if (!destFile.getParentFile().mkdirs()) {
                return false;
            }
        }


        int byteread = 0;
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] buffer = new byte['Ѐ'];

            while ((byteread = in.read(buffer)) != -1) {
                out.write(buffer, 0, byteread);
            }
            return true;
        } catch (Exception e) {
            logger.error("copyFile error", e);
            return false;
        } finally {
            try {
                if (out != null)
                    out.close();
                if (in != null)
                    in.close();
            } catch (IOException e) {
                logger.error("copyFile error", e);
            }
        }
    }


    public static boolean copyDirectory(String srcDirName, String destDirName, boolean overlay) {
        File srcDir = new File(srcDirName);
        if (!srcDir.exists()) {
            MESSAGE = "复制目录失败：源目录" + srcDirName + "不存在！";
            logger.error(MESSAGE);
            return false;
        }
        if (!srcDir.isDirectory()) {
            MESSAGE = "复制目录失败：" + srcDirName + "不是目录！";
            logger.error(MESSAGE);
            return false;
        }


        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        File destDir = new File(destDirName);

        if (destDir.exists()) {
            if (overlay) {
                new File(destDirName).delete();
            } else {
                MESSAGE = "复制目录失败：目的目录" + destDirName + "已存在！";
                logger.error(MESSAGE);
                return false;
            }

        } else if (!destDir.mkdirs()) {
            MESSAGE = "复制目录失败：目的目录" + destDirName + "创建失败！";
            logger.error(MESSAGE);
            return false;
        }


        boolean flag = true;
        File[] files = srcDir.listFiles();
        for (int i = 0; i < files.length; i++) {
            if (files[i].isFile()) {
                flag = copyFile(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            } else if (files[i].isDirectory()) {
                flag = copyDirectory(files[i].getAbsolutePath(), destDirName + files[i].getName(), overlay);
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            MESSAGE = "复制目录" + srcDirName + "至" + destDirName + "失败！";
            logger.error(MESSAGE);
            return false;
        }
        return true;
    }


    private static void nioTransferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            in.transferTo(0L, in.size(), out);
        } catch (Exception e) {
            logger.error("nioTransferCopy error", e);
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (Exception e1) {
                logger.error("nioTransferCopy error", e1);
            }
        } finally {
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (Exception e) {
                logger.error("nioTransferCopy error", e);
            }
        }
    }

    private static void nioBufferCopy(File source, File target) {
        FileChannel in = null;
        FileChannel out = null;
        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(source);
            outStream = new FileOutputStream(target);
            in = inStream.getChannel();
            out = outStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (in.read(buffer) != -1) {
                buffer.flip();
                out.write(buffer);
                buffer.clear();
            }
        } catch (Exception e) {
            logger.error("nioBufferCopy error", e);
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (Exception e1) {
                logger.error("nioBufferCopy error", e1);
            }
        } finally {
            try {
                inStream.close();
                in.close();
                outStream.close();
                out.close();
            } catch (Exception e) {
                logger.error("nioBufferCopy error", e);
            }
        }
    }

    private static void customBufferBufferedStreamCopy(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new BufferedInputStream(new FileInputStream(source));
            fos = new BufferedOutputStream(new FileOutputStream(target));
            byte[] buf = new byte['က'];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            logger.error("customBufferBufferedStreamCopy error", e);
            try {
                fis.close();
                fos.close();
            } catch (IOException e1) {
                logger.error("customBufferBufferedStreamCopy error", e1);
            }
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                logger.error("customBufferBufferedStreamCopy error", e);
            }
        }
    }

    private static void customBufferStreamCopy(File source, File target) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            fis = new FileInputStream(source);
            fos = new FileOutputStream(target);
            byte[] buf = new byte['က'];
            int i;
            while ((i = fis.read(buf)) != -1) {
                fos.write(buf, 0, i);
            }
        } catch (Exception e) {
            logger.error("customBufferStreamCopy error", e);
            try {
                fis.close();
                fos.close();
            } catch (IOException e1) {
                logger.error("customBufferStreamCopy error", e1);
            }
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (IOException e) {
                logger.error("customBufferStreamCopy error", e);
            }
        }
    }

    public static void deleteAllFilesOfDir(File path, boolean isGrant) {
        if (isGrant) {
            grantFullPriv(path);
        }
        deleteAllFilesOfDir(path);
    }

    public static boolean grantFullPriv(File file) {
        try {
            if (!file.exists()) {
                return false;
            }
            String cmd = "cmd /c c:/windows/system32/icacls.exe " + file.getAbsolutePath() + " /grant \"Authenticated Users\":F";
            logger.debug("grant: " + cmd);
            Process pro = Runtime.getRuntime().exec(cmd);
            return true;
        } catch (Throwable e) {
            logger.error("grantFullPriv error", e);
        }
        return false;
    }


    public static boolean grantFullPrivs(File path) {
        if (!path.exists()) {
            return false;
        }
        if (path.isFile()) {
            grantFullPriv(path);
            return true;
        }

        grantFullPriv(path);
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            grantFullPriv(files[i]);
        }

        return true;
    }


    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            boolean b = path.delete();

            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        boolean b = path.delete();
    }


    public static void deleteAllFilesOfDir2(File path) {
        try {
            if (!path.exists()) {
                return;
            }
            if (path.isFile()) {
                boolean b = Files.deleteIfExists(path.toPath());

                return;
            }
            File[] files = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteAllFilesOfDir2(files[i]);
            }
            boolean b = Files.deleteIfExists(path.toPath());
        } catch (Throwable e) {
            boolean b;
            logger.error("deleteAllFilesOfDir2 error", e);
        }
    }


    public static String getFileName(String filePath) {
        String fileFullName = null;
        if (filePath == null) {
            return null;
        }
        filePath = replaceAll(filePath, '\\', '/');
        int i = filePath.lastIndexOf('/');
        if (i == -1) {
            fileFullName = filePath;
        } else {
            fileFullName = filePath.substring(i + 1);
        }
        int j = fileFullName.lastIndexOf('.');
        if (j == -1) {
            return fileFullName;
        }
        return fileFullName.substring(0, j);
    }


    public static String getFileExtension(String filePath) {
        String fileFullName = null;
        if (filePath == null)
            return null;
        filePath = replaceAll(filePath, '\\', '/');
        int i = filePath.lastIndexOf('/');
        if (i == -1) {
            fileFullName = filePath;
        } else {
            fileFullName = filePath.substring(i + 1);
        }
        int j = fileFullName.lastIndexOf('.');
        if (j == -1) {
            return null;
        }
        return fileFullName.substring(j + 1);
    }


    public static String getFileFullName(String filePath) {
        if (filePath == null)
            return null;
        filePath = replaceAll(filePath, '\\', '/');
        int i = filePath.lastIndexOf('/');
        if (i == -1) {
            return filePath;
        }
        String fileFullName = filePath.substring(i + 1);

        return fileFullName;
    }


    public static String getFileDir(String filePath) {
        if (filePath == null)
            return null;
        filePath = replaceAll(filePath, '\\', '/');
        int i = filePath.lastIndexOf('/');
        if (i == -1) {
            return null;
        }
        return filePath.substring(0, i);
    }

    private static String replaceAll(String src, char source, char target) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < src.length(); i++) {
            char c = src.charAt(i);
            if (c == source) {
                sb.append(target);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String srcDirName = "C:/test/test0/test1";
        String destDirName = "c:/ttt";
        copyDirectory(srcDirName, destDirName, true);
    }
}
