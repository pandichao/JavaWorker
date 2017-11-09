package com.cglib.utils;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

/**
 * Description:
 * ������صķ�װ��
 */
public final class CharsetUtil {
    /**
     * 7λASCII�ַ�Ҳ����ISO646-US��Unicode�ַ�Ļ�������
     */
    public static final String US_ASCII = "US-ASCII";

    /**
     * ISO ������ĸ�� No.1��Ҳ���� ISO-LATIN-1
     */
    public static final String ISO_8859_1 = "ISO-8859-1";

    /**
     * 8 λ UCS ת����ʽ
     */
    public static final String UTF_8 = "UTF-8";

    /**
     * 16 λ UCS ת����ʽ��Big Endian����͵�ַ��Ÿ�λ�ֽڣ��ֽ�˳��
     */
    public static final String UTF_16BE = "UTF-16BE";

    /**
     * 16 λ UCS ת����ʽ��Little-endian����ߵ�ַ��ŵ�λ�ֽڣ��ֽ�˳��
     */
    public static final String UTF_16LE = "UTF-16LE";

    /**
     * 16 λ UCS ת����ʽ���ֽ�˳���ɿ�ѡ���ֽ�˳��������ʶ
     */
    public static final String UTF_16 = "UTF-16";

    /**
     * ���ĳ����ַ�
     */
    public static final String GBK = "GBK";

    /**
     * ���ַ����ת����US-ASCII��
     */
    public final static String toASCII(String str) throws UnsupportedEncodingException {
        return changeCharset(str, US_ASCII);
    }

    /**
     * ���ַ����ת����ISO-8859-1��
     */
    public final static String toISO_8859_1(String str) throws UnsupportedEncodingException {
        return changeCharset(str, ISO_8859_1);
    }

    /**
     * ���ַ����ת����UTF-8��
     */
    public static String toUTF_8(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_8);
    }

    /**
     * ���ַ����ת����UTF-16BE��
     */
    public final static String toUTF_16BE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16BE);
    }

    /**
     * ���ַ����ת����UTF-16LE��
     */
    public final static String toUTF_16LE(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16LE);
    }

    /**
     * ���ַ����ת����UTF-16��
     */
    public final static String toUTF_16(String str) throws UnsupportedEncodingException {
        return changeCharset(str, UTF_16);
    }

    /**
     * ���ַ����ת����GBK��
     */
    public final static String toGBK(String str) throws UnsupportedEncodingException {
        return changeCharset(str, GBK);
    }

    /**
     * �ַ����ת����ʵ�ַ���
     *
     * @param str        ��ת��������ַ�
     * @param newCharset Ŀ�����
     * @return
     * @throws UnsupportedEncodingException
     */
    public final static String changeCharset(String str, String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // ��Ĭ���ַ��������ַ�
            byte[] bs = str.getBytes();
            // ���µ��ַ��������ַ�
            return new String(bs, newCharset);
        }
        return null;
    }

    public final static String getDefaultCharSet() throws UnsupportedEncodingException {
        OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream(), CharsetUtil.UTF_8);
        String             enc    = writer.getEncoding();
        return enc;
    }

    /**
     * �ַ����ת����ʵ�ַ���
     *
     * @param str        ��ת��������ַ�
     * @param oldCharset ԭ����
     * @param newCharset Ŀ�����
     * @return
     * @throws UnsupportedEncodingException
     */
    public final static String changeCharset(String str, String oldCharset,
            String newCharset) throws UnsupportedEncodingException {
        if (str != null) {
            // �þɵ��ַ��������ַ�������ܻ�����쳣��
            byte[] bs = str.getBytes(oldCharset);
            // ���µ��ַ��������ַ�
            return new String(bs, newCharset);
        }
        return null;
    }

    /**
     * Unicodeת����GBK�ַ�
     *
     * @param input ��ת���ַ�
     * @return ת������ַ�
     */
    public final static String toGBKWithUTF8(String input) throws UnsupportedEncodingException {
        if (ComUtil.isEmpty(input)) {
            return "";
        } else {
            String s1;
            s1 = new String(input.getBytes("ISO8859_1"), "GBK");
            return s1;
        }
    }

    /**
     * GBKת����Unicode�ַ�
     *
     * @param input ��ת���ַ�
     * @return ת������ַ�
     */
    public final static String toUnicodeWithGBK(String input) throws UnsupportedEncodingException {
        if (ComUtil.isEmpty(input)) {
            return "";
        } else {
            String s1;
            s1 = new String(input.getBytes("GBK"), "ISO8859_1");
            return s1;
        }
    }

}