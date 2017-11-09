package com.cglib.utils;

/**
 * �ļ����ļ�·����صĲ���
 */
public final class FilePathUtil {

    /**
     * �ж��Ƿ���ǺϷ����ļ�·��
     *
     * @param path ��Ҫ������ļ�·��
     */
    public final static boolean legalFile(String path) {
        //�����������ʽ������
        String regex = "[a-zA-Z]:(?:[/][^/:*?\"<>|.][^/:*?\"<>|]{0,254})+";
        //String regex ="^([a-zA-z]:)|(^\\.{0,2}/)|(^\\w*)\\w([^:?*\"><|]){0,250}";
        return RegUtil.isMatche(commandPath(path), regex);
    }

    /**
     * ����һ��ͨ�õ��ļ�·��
     *
     * @param file ��Ҫ������ļ�·��
     * @return
     * Summary windows��·���ָ�����\��linux����/��windowsҲ֧��/��ʽ ��ȫ��ʹ��/
     */
    public final static String commandPath(String file) {
        return file.replaceAll("\\\\{1,}", "/").replaceAll("\\/{2,}", "/");
    }


}
