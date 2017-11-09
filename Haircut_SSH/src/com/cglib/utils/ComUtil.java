package com.cglib.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.RandomAccess;


public class ComUtil {
	/**
	 * <p>
	 * <li>�ж϶����Ƿ�Ϊ��</li>
	 * <li>һ�����Ϊnull����true</li>
	 * <li>String����Ϊnull����ַ���ȥ�ո񣩷���ture</li>
	 * <li>����,����,MapΪ�գ���û��Ԫ�أ���Ԫ��ֵȫ��Ϊ�գ�����ture</li>
	 * </p>
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj){
		if(obj == null)
			return true;
		if(obj instanceof String){
			if(!"".equals(obj))
				return false;
		}else if(obj instanceof StringBuffer){
			return isEmpty(obj.toString());
		}else if(obj instanceof Map){
			if(!isEmpty(((Map)obj).values()))
				return false;
		}else if(obj instanceof Enumeration){
			Enumeration enumeration = (Enumeration) obj;
			while(enumeration.hasMoreElements()){
				if(!isEmpty(enumeration.nextElement()))
					return false;
			}
		}else if(obj instanceof Iterable){
			if(obj instanceof List && obj instanceof RandomAccess){
				List<Object> objList = (List)obj;
				for(int i = 0 ; i < objList.size() ; i ++ ){
					if(!isEmpty(objList.get(i)))
						return false;
				}

			}else if(!isEmpty(((Iterable)obj).iterator()))
				return false;
		}else if(obj instanceof Iterator){
			Iterator it = (Iterator)obj;
			while(it.hasNext()){
				if(!isEmpty(it.next()))
					return false;
			}
		}else if(obj instanceof Object[]){
			Object[] objs = (Object[])obj;
			for(Object elem : objs){
				if(!isEmpty(elem))
					return false;
			}
		}else if(obj instanceof int[]){
			for(Object elem : (int[])obj){
				if(!isEmpty(elem))
					return false;
			}
		}else{
			return false;
		}
		return true;
	}
	/**
	 * @param temp
	 * @return
	 */
	public static String arrayToSqlCond(String[] temp) {
		StringBuffer st = new StringBuffer("");
		if (temp != null) {
			st = new StringBuffer();
			int len = temp.length;
			if(len ==0){
				st.append("='errorCond'");
			}else if(len == 1){
				st.append(" ='").append(temp[0]).append("'");
			}else if(len >1){
				st.append(" in (");
				for (int i = 0; i < len; i++) {
					st.append("'");
					st.append(temp[i]);
					st.append("'");
					if(i<len-1)
						st.append(",");
				}
				st.append(")");
			}
		}else{
			st.append("='errorCond'");
		}
		return st.toString();
	}
	/**
	 * 
	 * <p>������getBillCode</p>
	 * <p>�����������������</p>
	 * <p>�����˼�ʱ�䣺shijun 2015-3-30 ����6:50:05</p>
	 * @param beforeStr������ǰ׺
	 * @param maxValue�����ֵ
	 * @param num����׺���볤��
	 * @return
	 */
	public static String getBillCode(String beforeStr,int maxValue,int num){
        int lenth = String.valueOf(maxValue).length();
        if(num>lenth){
            for(int i = 0 ; i < num-lenth; i++){
            	beforeStr = beforeStr + "0";
            }
        }
        beforeStr = beforeStr + String.valueOf(maxValue);
        return beforeStr;
    }
	public static String transUTF(String string){
		try {
			return new String(string.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 
	 * @Title: initDate �ַ�ת����
	 * @Description: TODO
	 * @return Date 
	 * @throws
	 */
	public static Date initDate(String dateStr){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {
			return dateStr==null?null:sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @Title: initTime �ַ�תʱ��
	 * @Description: TODO
	 * @return Date 
	 * @throws
	 */
	public static Date initTime(String dateStr){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateStr==null?null:sdf.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 *
	 * @description ��ʼ���ַ�
	 * @author zhangyun1
	 *
	 * @param o
	 * @return
	 * @return String
	 */
	public static String initStr(Object o) {
		return o == null ? "" : o.toString().trim();
	}
	public static String initStr(Object o,String value) {
		return o == null ? value : o.toString().trim();
	}
	
	
	public static Long initLong(Object o){
		return o == null ? null : new Long(o.toString());
	}
	
	public static Double initDouble(Object o){
		if(o.equals("")||o == null){
			return new Double(0);
		}
		return new Double(o.toString());
	}
	/**
	 * <p>������isDigit</p>
	 * <p>�����������ж�һ���ַ��Ƿ�Ϊ����  </p>
	 * <p>�����˼�ʱ�䣺luqianbin 2015-5-22 ����3:20:05</p>
	 * @param strNum���ַ�
	 * @return boolean 
	 * @throws
	 */
	public static boolean isDigit(String strNum) {
		return strNum.matches("[0-9]{1,}");
	}  
	
	/**
	 * 
	 * <p>������getDateStr</p>
	 * <p>��������������ת�����ַ�</p>
	 * <p>�����˼�ʱ�䣺shijun 2015-7-1 ����11:46:59</p>
	 * @param date
	 * @return
	 */
	public static String getDateStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = format.format(date);
		return dateStr;
	}
	public static String getTimeStr(Date date){
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String dateStr = format.format(date);
		return dateStr;
	}
	/**
	 * 
	 * <p>������getSms</p>
	 * <p>�������������6λ���������֤��</p>
	 * <p></p>
	 * @param 
	 * @return
	 */
	public static String getSms(){
		String sms="";
		Random ran=new Random();
		for(int i=0;i<6;i++){
			sms+=ran.nextInt(10);
		}
		return sms;
	}
	/**
	 * add by shijun 2015-08-25 ��double���������������
	 * @param num
	 * @return
	 */
	public static double getDecimal(double num,int bitnum) {
		   if (Double.isNaN(num)) {
		    return 0;
		   }
		   BigDecimal bd = new BigDecimal(num);
		   num = bd.setScale(bitnum, BigDecimal.ROUND_HALF_UP).doubleValue();
		   return num;
		 }
	 /**
	  * add by shijun Date->String
	  * @param date
	  * @return
	  */
	 public static String dateToString(Date date) {  
		if(isEmpty(date))
			return "";
		String str = null;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// 2007-1-18
		format = DateFormat.getDateInstance(DateFormat.MEDIUM);
		str = format.format(date);
		return str;
	    }  
	 
	 public static Date getWorkDate(Date currentDate,int days){
	        Calendar calendar=Calendar.getInstance();
	        calendar.setTime(currentDate);
	        int i=0;
	        while(i<days){
	            calendar.add(Calendar.DATE,1);
	            i++;
	            if(calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SATURDAY || 
	                    calendar.get(Calendar.DAY_OF_WEEK)==Calendar.SUNDAY){
	                i--;
	            }
	        }
	        System.out.println(calendar.getTime());
	        return calendar.getTime();
	    }
	 
	 /**
	  * add by luqianbin ��С��ת���ɰٷ���
	  * @param double
	  * @return
	  */
	 public static String numberFormat(double csdn){
		 NumberFormat num = NumberFormat.getPercentInstance(); 
			num.setMaximumIntegerDigits(3); 
			num.setMaximumFractionDigits(2); 
			return num.format(csdn);
	 }
}
