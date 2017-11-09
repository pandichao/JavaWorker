package com.cglib.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * �ṩ������صĹ�����
 */
public final class Password {

    //��ѡ�����֡���д��ĸ��Сд��ĸ�������ַ�����8��15λ
    private static final String SEC_PASSWORD =
            "^(?=.*?[0-9])(?=.*?[a-z])(?=.*?[A-Z])(?=.*?[@!#$%^&*()_+\\.\\-\\?<>'\"|=]+).{8,15}$";
    /**
     * �ַ���ܺ���MD5ʵ��
     */
    public final static String md5(String password){
        MessageDigest md;
        try {
            // ���һ��MD5���ܼ���ժҪ
            md = MessageDigest.getInstance("MD5");
            // ����md5����
            md.update(password.getBytes());
            // digest()���ȷ������md5 hashֵ������ֵΪ8Ϊ�ַ���Ϊmd5 hashֵ��16λ��hexֵ��ʵ���Ͼ���8λ���ַ�
            // BigInteger������8λ���ַ�ת����16λhexֵ�����ַ�����ʾ���õ��ַ���ʽ��hashֵ
            String pwd = new BigInteger(1, md.digest()).toString(16);
            return pwd;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return password;
    }

    /**
     * �ж�һ�������Ƿ�׳
     * ��ѡ�����֡���д��ĸ��Сд��ĸ�������ַ�����8��15λ
     * @param password
     * @return
     */
    public final static  boolean isSec(String password){
        return RegUtil.isMatche(password,SEC_PASSWORD);
    }
}
