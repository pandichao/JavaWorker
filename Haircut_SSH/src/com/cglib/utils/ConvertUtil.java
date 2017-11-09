package com.cglib.utils;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ��������ת��
 */
public final class ConvertUtil {

    private final static String hexStr = "0123456789ABCDEF";

    /**
     *����ת�ַ�
     */
    public final static String toDate14(Date date){
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
    	return sdf.format(date);
    }
    
    /**
     * �ַ�ת����
     */
    public final static Date string14toDate(String datestr){
    	Date date = null;
    	try {
    	SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");//Сд��mm��ʾ���Ƿ���  
			date = sdf.parse(datestr);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
    	return date;
    }
    
    /**
     * ���������ֽڵ�ת��
     */
    public final static byte[] shortToByte(short number) {
        int temp = number;
        byte[] b = new byte[2];
        for (int i = 0; i < b.length; i++) {
            // �����λ���������λ
            b[i] = new Integer(temp & 0xff).byteValue();
            // ������8λ
            temp = temp >> 8;
        }
        return b;
    }

    /**
     * �ֽڵ�ת���������
     */
    public final static short byteToShort(byte[] b) {
        short s;
        // ���λ
        short s0 = (short) (b[0] & 0xff);
        short s1 = (short) (b[1] & 0xff);
        s1 <<= 8;
        s = (short) (s0 | s1);
        return s;
    }

    /**
     * �������ֽ������ת��
     */
    public final static byte[] intToByte(int i) {
        byte[] bt = new byte[4];
        bt[0] = (byte) (0xff & i);
        bt[1] = (byte) ((0xff00 & i) >> 8);
        bt[2] = (byte) ((0xff0000 & i) >> 16);
        bt[3] = (byte) ((0xff000000 & i) >> 24);
        return bt;
    }

    /**
     * ��������ת��Ϊ�ֽ������ת��
     *
     * @param arr ��������
     */
    public final static byte[] intToByte(int[] arr) {
        byte[] bt = new byte[arr.length * 4];
        for (int i = 0; i < arr.length; i++) {
            byte[] t = intToByte(arr[i]);
            System.arraycopy(t, 0, bt, i + 4, 4);
        }
        return bt;
    }

    public final static byte[] encodeBytes(byte[] source, char split) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(source.length);
        for (byte b : source) {
            if (b < 0) {
                b += 256;
            }
            bos.write(split);
            char hex1 = Character.toUpperCase(Character.forDigit((b >> 4) & 0xF, 16));
            char hex2 = Character.toUpperCase(Character.forDigit(b & 0xF, 16));
            bos.write(hex1);
            bos.write(hex2);
        }
        return bos.toByteArray();
    }

    /**
     * bytes����תchar����
     * bytes to chars
     *
     * @param bytes bytes����
     */
    public final static char[] bytesToChars(byte[] bytes) {
        char[] chars = new char[]{};
        if (valid.valid(bytes)) {
            chars = new char[bytes.length];
            for (int i = 0; i < bytes.length; i++) {
                chars[i] = (char) bytes[i];
            }
        }
        return chars;
    }

    /**
     * �ֽ���������͵�ת��
     */
    public final static int bytesToInt(byte[] bytes) {
        int num = bytes[0] & 0xFF;
        num |= ((bytes[1] << 8) & 0xFF00);
        num |= ((bytes[2] << 16) & 0xFF0000);
        num |= ((bytes[3] << 24) & 0xFF000000);
        return num;
    }

    /**
     * �ֽ�����ͳ����͵�ת��
     */
    public final static byte[] longToByte(long number) {
        long temp = number;
        byte[] b = new byte[8];
        for (int i = 0; i < b.length; i++) {
            b[i] = new Long(temp & 0xff).byteValue();
            // �����λ���������λ
            temp = temp >> 8;
            // ������8λ
        }
        return b;
    }

    /**
     * �ֽ�����ͳ����͵�ת��
     */
    public final static long byteToLong(byte[] b) {
        long s;
        long s0 = b[0] & 0xff;// ���λ
        long s1 = b[1] & 0xff;
        long s2 = b[2] & 0xff;
        long s3 = b[3] & 0xff;
        long s4 = b[4] & 0xff;// ���λ
        long s5 = b[5] & 0xff;
        long s6 = b[6] & 0xff;
        long s7 = b[7] & 0xff; // s0����
        s1 <<= 8;
        s2 <<= 16;
        s3 <<= 24;
        s4 <<= 8 * 4;
        s5 <<= 8 * 5;
        s6 <<= 8 * 6;
        s7 <<= 8 * 7;
        s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
        return s;
    }

    /**
     * ��byteת��Ϊ��Ӧ�Ķ������ַ�
     *
     * @param src Ҫת���ɶ������ַ��byteֵ
     */
    public final static String byteToBinary(byte src) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            result.append(src % 2 == 0 ? '0' : '1');
            src = (byte) (src >>> 1);
        }
        return result.reverse().toString();
    }

    /**
     * ��ʮ������ַ�תΪ�������ַ�
     *
     * @param hexStr ʮ������ַ�
     */
    public final static String hexStringtoBinarg(String hexStr) {
        hexStr = hexStr.replaceAll("\\s", "").replaceAll("0x", "");
        char[] achar = hexStr.toCharArray();
        String result = "";
        for (char a : achar) {
            result += Integer.toBinaryString(
                    Integer.valueOf(String.valueOf(a), 16)) + " ";
        }
        return result;
    }

    /**
     * ��������ת��Ϊʮ������ַ����
     *
     * @param bytes bytes����
     */
    public final static String bytesToHexString(byte[] bytes) {
        String result = "";
        String hex;
        for (byte b : bytes) {
            //�ֽڸ�4λ
            hex = String.valueOf(hexStr.charAt((b & 0xF0) >> 4));
            //�ֽڵ�4λ
            hex += String.valueOf(hexStr.charAt(b & 0x0F));
            result += hex + " ";
        }
        return result;
    }

    /**
     * ��16�����ַ�ת�����ֽ�����
     *
     * @param hexString 16�����ַ�
     * @return byte[]
     */
    public final static byte[] hexStringToByte(String hexString) {
        int len = (hexString.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hexString.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private final static int toByte(char c) {
        return (byte) hexStr.indexOf(c);
    }
}
