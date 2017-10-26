package com.cgxt.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**  
 * ����ʱ�䣺2017-2-14 10:21:54
 * @author luqianbin  
 * @version 1.0
 * ������ MD5����
 */

public class MD5Util {

	
	public static String encode(String src){
		
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] result = digest.digest(src.getBytes());
			StringBuilder builder = new StringBuilder();
			for (byte b : result) {
				int num = b & 0xff;
				String str = Integer.toHexString(num);
				if(str.length() == 1){
					builder.append(0);
				}
				
				builder.append(str);
			}
			
			return builder.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public static String encodeHapPassword(String userName,String passWord){
		String src = userName+"{"+passWord+"}";
		return encode(src);
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String md5 = MD5Util.encodeHapPassword("luqianbin","1");
		System.out.println(md5);

	}

}
