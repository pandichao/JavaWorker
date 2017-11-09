package com.cglib.utils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

/**
 * 
* ����ƣ�ExceptionUtil    
* ��������Exception������  
* �޸ı�ע��  
* @version
 */
public class ExceptionUtil {

	private static final Logger logger = Logger.getLogger(ExceptionUtil.class);

	/**
	 * ���ش�����Ϣ�ַ�
	 * 
	 * @param ex
	 *            Exception
	 * @return ������Ϣ�ַ�
	 */
	public static String getExceptionMessage(Exception ex) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		ex.printStackTrace(pw);
		String errorMessage = sw.toString();
		pw.close();
		try {
			sw.close();
		} catch (IOException e) {
			logger.error(e);
		}
		return errorMessage;
	}
	
    /**
     * ֻ����ָ�����е��쳣��ջ��Ϣ
     *
     * @param e
     * @param packageName
     * @return
     */
    public final static String stackTraceToString(Throwable e, String packageName) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        String str = sw.toString();
        if(packageName == null ){
            return str;
        }
        String[] arrs = str.split("\n");
        StringBuffer sbuf = new StringBuffer();
        sbuf.append(arrs[0] + "\n");
        for (int i = 0; i < arrs.length; i++) {
            String temp = arrs[i];
            if (temp != null && temp.indexOf(packageName) > 0) {
                sbuf.append(temp + "\n");
            }
        }
        return sbuf.toString();
    }

    /**
     * ��ȡ�쳣��Ϣ
     * @param e
     * @return
     */
    public final static String stackTraceToString(Throwable e){
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

}
