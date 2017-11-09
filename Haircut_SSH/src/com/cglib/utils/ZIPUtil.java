package com.cglib.utils;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * ѹ���ĵ���صĹ�����
 */
public final class ZIPUtil {
    /**
     * �ĵ�ѹ��
     *
     * @param file ��Ҫѹ�����ļ���Ŀ¼
     * @param dest ѹ������ļ����
     * @throws Exception
     */
    public final static void deCompress(File file, String dest) throws Exception {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(dest))) {
            zipFile(file, zos, "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public final static void zipFile(File inFile, ZipOutputStream zos, String dir) throws IOException {
        if (inFile.isDirectory()) {
            File[] files = inFile.listFiles();
            if (valid.valid(files)) {
                for (File file : files) {
                    String name = inFile.getName();
                    if (!"".equals(dir)) {
                        name = dir + "/" + name;
                    }
                    zipFile(file, zos, name);
                }
            }
        } else {
            String entryName = null;
            if (!"".equals(dir)) {
                entryName = dir + "/" + inFile.getName();
            } else {
                entryName = inFile.getName();
            }
            ZipEntry entry = new ZipEntry(entryName);
            zos.putNextEntry(entry);
            try (InputStream is = new FileInputStream(inFile)) {
                int len = 0;
                while ((len = is.read()) != -1) {
                    zos.write(len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * �ĵ���ѹ
     *
     * @param source ��Ҫ��ѹ�����ĵ����
     * @param path   ��Ҫ��ѹ����·��
     */
    public final static void unCompress(File source, String path) throws IOException {
        ZipEntry zipEntry = null;
        FileUtil.createPaths(path);
        //ʵ��ZipFile��ÿһ��zipѹ���ļ������Ա�ʾΪһ��ZipFile
        //ʵ��һ��Zipѹ���ļ���ZipInputStream���󣬿������ø����getNextEntry()���������õ�ÿһ��ZipEntry����
        try (
                ZipFile zipFile = new ZipFile(source);
                ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(source))
        ) {
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                String fileName = zipEntry.getName();
                File temp = new File(path + "/" + fileName);
                if (!temp.getParentFile().exists()) {
                    temp.getParentFile().mkdirs();
                }
                try (OutputStream os = new FileOutputStream(temp);
                     //ͨ��ZipFile��getInputStream�����õ������ZipEntry��������
                     InputStream is = zipFile.getInputStream(zipEntry)) {
                    int len = 0;
                    while ((len = is.read()) != -1) {
                        os.write(len);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
