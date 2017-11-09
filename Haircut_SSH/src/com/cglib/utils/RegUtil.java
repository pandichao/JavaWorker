package com.cglib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:
 * ��װһЩ������صĲ���
 */
public final class RegUtil {

    /**
     * Alphanumeric characters
     */
    public static final String  REG_ALNUM               = "\\p{Alnum}";
    /**
     * Alphabetic characters
     */
    public static final String  REG_ALPHA               = "\\p{Alpha}";
    /**
     * ASCII characters
     */
    public static final String  REG_ASCII               = "\\p{ASCII}";
    /**
     * Space and tab
     */
    public static final String  REG_BLANK               = "\\p{Blank}";
    /**
     * Control characters
     */
    public static final String  REG_CNTRL               = "\\p{Cntrl}";
    /**
     * Digits
     */
    public static final String  REG_DIGITS              = "\\p{Digit}";
    /**
     * SVisible characters (i.e. anything except spaces, control characters, etc.)
     */
    public static final String  REG_GRAPH               = "\\p{Graph}";
    /**
     * Lowercase letters
     */
    public static final String  REG_LOWER               = "\\p{Lower}";
    /**
     * Visible characters and spaces (i.e. anything except control characters, etc.)
     */
    public static final String  REG_PRINT               = "\\p{Print}";
    /**
     * Punctuation and symbols.
     */
    public static final String  REG_PUNCT               = "\\p{Punct}";
    /**
     * All whitespace characters, including line breaks
     */
    public static final String  REG_SPACE               = "\\p{Space}";
    /**
     * Uppercase letters
     */
    public static final String  REG_UPPER               = "\\p{Upper}";
    /**
     * Hexadecimal digits
     */
    public static final String  REG_XDIGIT              = "\\p{XDigit}";
    /**
     * �հ���
     */
    public static final String  REG_SPACE_LINE          = "\\n\\s*\\r";
    /**
     * ��β�հ��ַ�
     */
    public static final String  REG_SPACE_POINT         = "^\\s*|\\s*$";
    /**
     * HTML
     */
    public static final String  REG_HTML                = "<(\\S*?)[^>]*>.*?</\\1>|<.*? />";
    /**
     * Email
     */
    public static final String  REG_EMAIL               = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    /**
     * ���ڹ̶��绰
     */
    public static final String  REG_FIXED_TELEPHONE     = "\\d{3}-\\d{8}|\\d{4}-\\d{7}";
    /**
     * ��������
     */
    public static final String  REG_POSTALCODE          = "[1-9]\\d{5}(?!\\d)";
    /**
     * ���֤����
     */
    public static final String  REG_IDENTIFICATION_CARD = "\\d{15}|\\d{18}";
    /**
     * URL��ַ
     */
    public static final String  REG_URL                 = "^http://([w-]+.)+[w-]+(/[w-./?%&=]*)?$";
    /**
     * �ƶ��绰
     */
    public static final String  REG_MOBILE_TELEPHONE    = "^(13[0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\\d{8}$";
    /**
     * �Ϸ������֣���ĸ��ͷ������5-16�ֽڣ�������ĸ�����»��ߣ�
     */
    public static final String  REG_LEGAL_ACCOUNT       = "^[a-zA-Z][a-zA-Z0-9_]{4,15}$";
    /**
     * i��ַ
     */
    public static final String  REG_IP                  = "((2[0-4]\\d|25[0-5]|[01]?\\d\\d?)\\.){3}(2[0-4]\\d|25[0-5]|[01]?\\d\\d?)";
    private static      Pattern numericPattern          = Pattern.compile("^[0-9\\-]+$");
    private static      Pattern numericStringPattern    = Pattern.compile("^[0-9\\-\\-]+$");
    private static      Pattern floatNumericPattern     = Pattern.compile("^[0-9\\-\\.]+$");
    private static      Pattern abcPattern              = Pattern.compile("^[a-z|A-Z]+$");

    /**
     * �ж��Ƿ����ֱ�ʾ
     *
     * @param src Դ�ַ�
     * @return �Ƿ����ֵı�־
     */
    public final static boolean isNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = numericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * �ж��Ƿ���ĸ���
     *
     * @param src Դ�ַ�
     * @return �Ƿ���ĸ��ϵı�־
     */
    public final static boolean isABC(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = abcPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }


    /**
     * �ж��Ƿ񸡵����ֱ�ʾ
     *
     * @param src Դ�ַ�
     * @return �Ƿ����ֵı�־
     */
    public final static boolean isFloatNumeric(String src) {
        boolean return_value = false;
        if (src != null && src.length() > 0) {
            Matcher m = floatNumericPattern.matcher(src);
            if (m.find()) {
                return_value = true;
            }
        }
        return return_value;
    }

    /**
     * �ж��ַ�str�Ƿ���������ʽreg
     *
     * @param str ��Ҫ������ַ�
     * @param reg ����
     * @return �Ƿ�ƥ��
     */
    public final static boolean isMatche(String str, String reg) {
        Pattern pattern = Pattern.compile(reg);
        Matcher isNum   = pattern.matcher(str);
        return isNum.matches();
    }

    /**
     * ��ȡ���reg������ʽ���ַ���String�г��ֵĴ���
     *
     * @param str ��Ҫ������ַ�
     * @param reg ����
     * @return ���ֵĴ���
     */
    public final static int countSubStrReg(String str, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(str);
        int     i = 0;
        while (m.find()) {
            i++;
        }
        return i;
    }


    /**
     * �ж��Ƿ��Ƿ������
     *
     * @param email �жϵ��ַ�
     * @return �Ƿ��Ƿ�ϵ�����
     */
    public final static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile(REG_EMAIL);
        return pattern.matcher(email).matches();
    }
}
