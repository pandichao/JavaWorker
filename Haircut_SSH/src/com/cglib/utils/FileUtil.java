package com.cglib.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigInteger;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;


/**
 * ��װ��Щ�ļ���صĲ���
 */
public final class FileUtil {
	
	private static Logger log = Logger.getLogger(FileUtil.class);
	
    /**
     * Buffer�Ĵ�С
     */
    private static Integer BUFFER_SIZE = 1024 * 1024 * 10;

    public static MessageDigest MD5 = null;

    static {
        try {
            MD5 = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ne) {
            ne.printStackTrace();
        }
    }

    /**
     * ��ȡ�ļ���md5
     * @param file
     * @return
     */
    public static String fileMD5(File file) {
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            return new BigInteger(1, MD5.digest()).toString(16);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (fileInputStream != null)
                    fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * ��ȡ�ļ�������
     *
     * @param file ͳ�Ƶ��ļ�
     * @return �ļ�����
     */
    public final static int countLines(File file) {
        try(LineNumberReader rf = new LineNumberReader(new FileReader(file))){
            long fileLength = file.length();
            rf.skip(fileLength);
            return rf.getLineNumber();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * ���б�ķ�ʽ��ȡ�ļ���������
     *
     * @param file ��Ҫ�������ļ�
     * @return �������е�list
     */
    public final static List<String> lines(File file) {
        List<String> list = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new FileReader(file))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ���б�ķ�ʽ��ȡ�ļ���������
     *
     * @param file     ��Ҫ������ļ�
     * @param encoding ָ����ȡ�ļ��ı���
     * @return �������е�list
     */
    public final static List<String> lines(File file, String encoding) {
        List<String> list = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ���б�ķ�ʽ��ȡ�ļ���ָ�����������
     *
     * @param file  ������ļ�
     * @param lines ��Ҫ��ȡ������
     * @return ���ƶ��е�list
     */
    public final static List<String> lines(File file, int lines) {
        List<String> list = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
                if (list.size() == lines) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ���б�ķ�ʽ��ȡ�ļ���ָ�����������
     *
     * @param file     ��Ҫ����ĺ���
     * @param lines    ��Ҫ������л���
     * @param encoding ָ����ȡ�ļ��ı���
     * @return ���ƶ��е�list
     */
    public final static List<String> lines(File file, int lines, String encoding) {
        List<String> list = new ArrayList<>();
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), encoding))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
                if (list.size() == lines) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * ���ļ�ĩβ׷��һ��
     *
     * @param file ��Ҫ����ĺ���
     * @param str  ��ӵ����ַ�
     * @return �Ƿ�ɹ�
     */
//    public final static boolean appendLine(File file, String str) {
//        try (
//                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
//        ) {
//            long fileLength = randomFile.length();
//            randomFile.seek(fileLength);
//            randomFile.writeBytes(SysUtil.FILE_SEPARATOR + str);
//            return true;
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }

    /**
     * ���ļ�ĩβ׷��һ��
     *
     * @param file     ��Ҫ������ļ�
     * @param str      ��ӵ��ַ�
     * @param encoding ָ��д��ı���
     * @return �Ƿ�ɹ�
     */
    public final static boolean appendLine(File file, String str, String encoding) {
        String lineSeparator = System.getProperty("line.separator", "\n");
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write((lineSeparator + str).getBytes(encoding));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ���ַ�д�뵽�ļ���
     */
    public final static boolean write(File file, String str) {
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            randomFile.writeBytes(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ���ַ���׷�ӵķ�ʽд�뵽�ļ���
     */
    public final static boolean writeAppend(File file, String str) {
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.writeBytes(str);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ���ַ����ƶ��ı���д�뵽�ļ���
     */
    public final static boolean write(File file, String str, String encoding) {
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            randomFile.write(str.getBytes(encoding));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ���ַ���׷�ӵķ�ʽ���ƶ��ı���д�뵽�ļ���
     */
    public final static boolean writeAppend(File file, String str, String encoding) {
        try (
                RandomAccessFile randomFile = new RandomAccessFile(file, "rw")
        ) {
            long fileLength = randomFile.length();
            randomFile.seek(fileLength);
            randomFile.write(str.getBytes(encoding));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * �������һ��������ļ�
     *
     * @param file ��Ҫ������ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean cleanFile(File file) {
        try (
                FileWriter fw = new FileWriter(file)
        ) {
            fw.write("");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * ��ȡ�ļ���Mime����
     *
     * @param file ��Ҫ������ļ�
     * @return �����ļ���mime����
     * @throws java.io.IOException
     */
    public final static String mimeType(String file) throws java.io.IOException {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        return fileNameMap.getContentTypeFor(file);
    }


    /**
     * ��ȡ�ļ������޸�ʱ��
     *
     * @param file ��Ҫ������ļ�
     * @return �����ļ����޸�ʱ��
     */
    public final static Date modifyTime(File file) {
        return new Date(file.lastModified());
    }




    /**
     * �����ļ�
     *
     * @param resourcePath Դ�ļ�
     * @param targetPath   Ŀ���ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean copy(String resourcePath, String targetPath) {
        File file = new File(resourcePath);
        return copy(file, targetPath);
    }

    /**
     * �����ļ�
     * ͨ��÷�ʽ�����ļ��ļ�Խ���ٶ�Խ������
     *
     * @param file       ��Ҫ������ļ�
     * @param targetFile Ŀ���ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean copy(File file, String targetFile) {
        try (
                FileInputStream fin = new FileInputStream(file);
                FileOutputStream fout = new FileOutputStream(new File(targetFile))
        ) {
            FileChannel in = fin.getChannel();
            FileChannel out = fout.getChannel();
            //�趨������
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            while (in.read(buffer) != -1) {
                //׼��д�룬��ֹ�����ȡ����ס�ļ�
                buffer.flip();
                out.write(buffer);
                //׼����ȡ����������������ϣ��ƶ��ļ��ڲ�ָ��
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    /**
     * �����༶Ŀ¼
     *
     * @param paths ��Ҫ������Ŀ¼
     * @return �Ƿ�ɹ�
     */
    public final static boolean createPaths(String paths) {
        File dir = new File(paths);
        return !dir.exists() && dir.mkdir();
    }

    /**
     * �����ļ�֧�ֶ༶Ŀ¼
     *
     * @param filePath ��Ҫ�������ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean createFiles(String filePath) {
        File file = new File(filePath);
        File dir = file.getParentFile();
        if (!dir.exists()) {
            if (dir.mkdirs()) {
                try {
                    return file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * ɾ��һ���ļ�
     *
     * @param file ��Ҫ������ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean deleteFile(File file) {
        return file.delete();
    }

    /**
     * ɾ��һ��Ŀ¼
     *
     * @param file ��Ҫ������ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean deleteDir(File file) {
        List<File> files = listFileAll(file);
        if (valid.valid(files)) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteDir(f);
                } else {
                    deleteFile(f);
                }
            }
        }
        return file.delete();
    }


    /**
     * ���ٵ�ɾ�����ļ�
     *
     * @param file ��Ҫ������ļ�
     * @return �Ƿ�ɹ�
     */
    public final static boolean deleteBigFile(File file) {
        return cleanFile(file) && file.delete();
    }


    /**
     * ����Ŀ¼
     *
     * @param filePath   ��Ҫ������ļ�
     * @param targetPath Ŀ���ļ�
     */
    public final static void copyDir(String filePath, String targetPath) {
        File file = new File(filePath);
        copyDir(file, targetPath);
    }

    /**
     * ����Ŀ¼
     *
     * @param filePath   ��Ҫ������ļ�
     * @param targetPath Ŀ���ļ�
     */
    public final static void copyDir(File filePath, String targetPath) {
        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            createPaths(targetPath);
        }
        File[] files = filePath.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                String path = file.getName();
                if (file.isDirectory()) {
                    copyDir(file, targetPath + "/" + path);
                } else {
                    copy(file, targetPath + "/" + path);
                }
            }
        }
    }

    /**
     * ����ָ��·���µ�ȫ���ļ�
     *
     * @param path ��Ҫ������ļ�
     * @return �������ļ��ĵ�list
     */
    public final static List<File> listFile(String path) {
        File file = new File(path);
        return listFile(file);
    }

    /**
     * ����ָ��·���µ�ȫ���ļ�
     * @param path ��Ҫ������ļ�
     * @param child �Ƿ��������ļ�
     * @return �������ļ��ĵ�list
     */
    public final static List<File> listFile(String path,boolean child){
        return listFile(new File(path),child);
    }


    /**
     * ����ָ��·���µ�ȫ���ļ�
     *
     * @param path ��Ҫ������ļ�
     * @return �����ļ��б�
     */
    public final static List<File> listFile(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * ����ָ��·���µ�ȫ���ļ�
     * @param path ָ����·��
     * @param child �Ƿ�������Ŀ¼
     * @return
     */
    public final static List<File> listFile(File path,boolean child){
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (child && file.isDirectory()) {
                    list.addAll(listFile(file));
                } else {
                    list.add(file);
                }
            }
        }
        return list;
    }

    /**
     * ����ָ��·���µ�ȫ���ļ������ļ���
     *
     * @param path ��Ҫ������ļ�
     * @return �����ļ��б�
     */
    public final static List<File> listFileAll(File path) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                list.add(file);
                if (file.isDirectory()) {
                    list.addAll(listFileAll(file));
                }
            }
        }
        return list;
    }

    /**
     * ����ָ��·���µ�ȫ���ļ������ļ���
     *
     * @param path   ��Ҫ������ļ�
     * @param filter �����ļ���filter
     * @return �����ļ��б�
     */
    public final static List<File> listFileFilter(File path, FilenameFilter filter) {
        List<File> list = new ArrayList<>();
        File[] files = path.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, filter));
                } else {
                    if (filter.accept(file.getParentFile(), file.getName())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * ��ȡָ��Ŀ¼�µ��ص��ļ�,ͨ���׺�����
     *
     * @param dirPath  ��Ҫ������ļ�
     * @param postfixs �ļ���׺
     * @return �����ļ��б�
     */
    public final static List<File> listFileFilter(File dirPath, final String postfixs) {
        /*
        ����ڵ�ǰĿ¼��ʹ��Filter��ֻ���е�ǰĿ¼�µ��ļ�������������Ŀ¼�µ��ļ�
        FilenameFilter filefilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.endsWith(postfixs);
            }
        };
        */
        List<File> list = new ArrayList<File>();
        File[] files = dirPath.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(listFileFilter(file, postfixs));
                } else {
                    String fileName = file.getName().toLowerCase();
                    if (fileName.endsWith(postfixs.toLowerCase())) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * ��ָ����Ŀ¼����Ѱ�ĸ��ļ�
     *
     * @param dirPath  ������Ŀ¼
     * @param fileName �������ļ���
     * @return �����ļ��б�
     */
    public final static List<File> searchFile(File dirPath, String fileName) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, fileName));
                } else {
                    String Name = file.getName();
                    if (Name.equals(fileName)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }

    /**
     * ���ҷ��������ʽreg�ĵ��ļ�
     *
     * @param dirPath ������Ŀ¼
     * @param reg     ������ʽ
     * @return �����ļ��б�
     */
    public final static List<File> searchFileReg(File dirPath, String reg) {
        List<File> list = new ArrayList<>();
        File[] files = dirPath.listFiles();
        if (valid.valid(files)) {
            for (File file : files) {
                if (file.isDirectory()) {
                    list.addAll(searchFile(file, reg));
                } else {
                    String Name = file.getName();
                    if (RegUtil.isMatche(Name, reg)) {
                        list.add(file);
                    }
                }
            }
        }
        return list;
    }


    /**
     * ��ȡ�ļ���׺��
     * @param file
     * @return
     */
    public final static String suffix(File file){
        String fileName=file.getName();
        return fileName.substring(fileName.indexOf(".")+1);
    }
    
    /**
     * �ļ�����
     * 
     * @param rsp
     *            HttpServletResponse����
     * @param filePath
     *            �ļ��ڷ������ϵ�·��
     * @param fileName
     *            �û�����ʱ,������ļ���
     * @return
     */
    public static void fileDownLoad(HttpServletResponse rsp, String filePath, String fileName) {
        InputStream fis = null;
        OutputStream fos = null;
        try {
            // ��ȡ��ʱ�ļ�����
            fis = new BufferedInputStream(new FileInputStream(filePath));
            // ÿ�ζ�ȡ�Ļ��������С
            int readBuffer = 10240;
            // ��������
            byte[] buffer = new byte[readBuffer];
            int byteRead = 0;

            // ���response
            rsp.reset();
            // ����response��Header
            rsp.addHeader("Content-Disposition", "attachment;filename="
                    + new String(fileName.getBytes("gb2312"), "ISO-8859-1"));
            // rep.addHeader("Content-Length", "" + savedPath.length());
            rsp.setContentType("application/octet-stream;charset=UTF-8");
            fos = new BufferedOutputStream(rsp.getOutputStream());

            // �����д�����ն�
            while ((byteRead = fis.read(buffer)) != -1) {
                // log.debug(fis.available());
                fos.write(buffer, 0, byteRead);
            }
        } catch (FileNotFoundException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            log.debug(e.getMessage());
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}