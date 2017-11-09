package com.cglib.utils;


import java.util.*;

public final class RandomUtil {
    public static final String ALLCHAR
            = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String LETTERCHAR
            = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String NUMBERCHAR
            = "0123456789";


    /**
     * ����ƶ���Χ�ڵ������
     *
     * @param scopeMin
     * @param scoeMax
     * @return
     */
    public static int integer(int scopeMin, int scoeMax) {
        Random random = new Random();
        return (random.nextInt(scoeMax) % (scoeMax - scopeMin + 1) + scopeMin);
    }

    /**
     * ���ع̶����ȵ�����
     *
     * @param length
     * @return
     */
    public static String number(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(NUMBERCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * ����һ������������ַ�(ֻ���Сд��ĸ������)
     *
     * @param length ����ַ���
     * @return ����ַ�
     */
    public static String String(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * ����һ�������������ĸ�ַ�(ֻ���Сд��ĸ)
     *
     * @param length ����ַ���
     * @return ����ַ�
     */
    public static String MixString(int length) {
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
        }
        return sb.toString();
    }

    /**
     * ����һ������������д��ĸ�ַ�(ֻ���Сд��ĸ)
     *
     * @param length ����ַ���
     * @return ����ַ�
     */
    public static String LowerString(int length) {
        return MixString(length).toLowerCase();
    }

    /**
     * ����һ�����������Сд��ĸ�ַ�(ֻ���Сд��ĸ)
     *
     * @param length ����ַ���
     * @return ����ַ�
     */
    public static String UpperString(int length) {
        return MixString(length).toUpperCase();
    }

    /**
     * ���һ�������Ĵ�0�ַ�
     *
     * @param length �ַ���
     * @return ��0�ַ�
     */
    public static String ZeroString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append('0');
        }
        return sb.toString();
    }

    /**
     * ����������һ���������ַ����Ȳ���ǰ�油0
     *
     * @param num       ����
     * @param fixdlenth �ַ���
     * @return �������ַ�
     */
    public static String toFixdLengthString(long num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(ZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("������" +
                    num + "ת��Ϊ����Ϊ" + fixdlenth + "���ַ����쳣��");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * ����������һ���������ַ����Ȳ���ǰ�油0
     *
     * @param num       ����
     * @param fixdlenth �ַ���
     * @return �������ַ�
     */
    public static String toFixdLengthString(int num, int fixdlenth) {
        StringBuffer sb = new StringBuffer();
        String strNum = String.valueOf(num);
        if (fixdlenth - strNum.length() >= 0) {
            sb.append(ZeroString(fixdlenth - strNum.length()));
        } else {
            throw new RuntimeException("������" +
                    num + "ת��Ϊ����Ϊ" + fixdlenth + "���ַ����쳣��");
        }
        sb.append(strNum);
        return sb.toString();
    }

    /**
     * ÿ����ɵ�lenλ����ͬ
     *
     * @param param
     * @return ����������
     */
    public static int getNotSimple(int[] param, int len) {
        Random rand = new Random();
        for (int i = param.length; i > 1; i--) {
            int index = rand.nextInt(i);
            int tmp = param[index];
            param[index] = param[i - 1];
            param[i - 1] = tmp;
        }
        int result = 0;
        for (int i = 0; i < len; i++) {
            result = result * 10 + param[i];
        }
        return result;
    }

    /**
     * ��ָ������������������е�ĳ��Ԫ��
     */
    public static <T> T randomItem(T[] param) {
        int index = integer(0, param.length);
        return param[index];
    }

    /**
     * ʵ��һ���򵥵��ַ�˷�
     * @param str
     * @param multiplication
     * @return
     */
    private static String strMultiplication(String str,int multiplication){
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < multiplication; i++) {
            buffer.append(str);
        }
        return buffer.toString();
    }
    /**
     * ��ָ���������а���ָ�������ָ�������Ԫ��
     * @param param
     * @param percentum
     * @param <T>
     * @return
     */
    public static <T> T randomItem(T[] param,double[] percentum){
        int length = percentum.length;
        Integer[] ints = ArrayUtil.doubleBitCount(percentum);
        int max = Collections.max(Arrays.asList(ints));
        int[] arr = new int[length];
        int sum = 0;
        Map map = new HashMap(length);
        int multiple = Integer.parseInt("1"+strMultiplication("0",max));
        for (int i = 0; i < length; i++) {
            int temp = (int)(percentum[i] * multiple);
            arr[i] = temp;
            if(i == 0){
                map.put(i,new int[]{1,temp});
            }else{
                map.put(i,new int[]{sum,sum+temp});
            }
            sum += temp;
        }
        int indexSum = integer(1,sum);
        int index =-1;
        for (int i = 0; i < length; i++) {
            int[]  scope = (int[]) map.get(i);
            if(indexSum ==1 ){
                index = 0;
                break;
            }
            if(indexSum > scope[0] && indexSum <= scope[1]){
                index =i;
                break;
            }
        }
        if(index == -1){
            throw new RuntimeException("���ʧ��");
        }else{
            return param[index];
        }
    }
    /**
     * ����һ��UUID
     *
     * @return Сд��UUID
     */
    public static String uuid() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        return s.substring(0, 8) + s.substring(9, 13) +
                s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
    }

    /**
     * ����һ��UUID
     *
     * @return ��д��UUID
     */
    public static String UUID() {
        UUID uuid = UUID.randomUUID();
        String s = uuid.toString();
        String temp = s.substring(0, 8) + s.substring(9, 13) +
                s.substring(14, 18) + s.substring(19, 23) + s.substring(24);
        return temp.toUpperCase();
    }

    /**
     * ����һ�������е�uuid����
     * ǰ11λΪʱ��(����)
     * �м�4λΪ����������
     * ʣ�µı�֤��Ψһ��
     *
     * @return
     */
    public static String squid() {
        Long date = new Date().getTime();
        String s = UUID.randomUUID().toString();
        String str = Long.toHexString(date);
        String result = str + OpslabConfig.HOST_FEATURE
                + s.substring(17, 18) + s.substring(19, 23) + s.substring(24);
        return result.toUpperCase();
    }
}
