package com.cglib.base;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cglib.utils.ExceptionUtil;

public class BaseController {
	private static final Logger LOGGER = Logger.getLogger(BaseController.class);
	 /**
     *  AJAX���ʷ��ز����ɹ�״̬��ʶ��
     */
    protected static final String STATUS_SUCCESS = "success";
    /**
     *  AJAX���ʷ��ز���ʧ��״̬��ʶ��
     */
    protected static final String STATUS_ERROR = "error";
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    /**
     * ÿ��controller����ִ��ǰ��ִ��
     */
    @ModelAttribute
    public void setReqAndRes(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
        this.session = request.getSession();
    }
    
    /**
     * ȡ��session�е�ֵ
     * @param name session����
     * @return �����Ӧ��ֵ
     */
    public Object getSession(String name) {
        return session.getAttribute(name);
    }
    
    /**
     * ��session����Ӽ�ֵ��
     * @param name session����
     * @param obj sessionֵ
     */
    public void setSession(String name, Object obj) {
        session.setAttribute(name, obj);
    }
    
    /**
     * ���cookie
     * @param key cookie����
     * @param value cookieֵ
     */
    public void addCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");// ���Ҫ����  
        cookie.setMaxAge(60*60*24*30);//����һ���� ����Ϊ��λ  
        response.addCookie(cookie);
    }
    
    /**
     * ɾ��cookie
     * @param key cookie����
     */
    public void deleteCookie(String key){
        Cookie cookies[] = request.getCookies();  
        if (cookies != null) {  
            for (int i = 0; i < cookies.length; i++) {  
                if (cookies[i].getName().equals(key)) {  
                    Cookie cookie = new Cookie(key,null);
                    cookie.setPath("/");//���óɸ�д��cookiesһ���  
                    cookie.setMaxAge(0);  
                    response.addCookie(cookie);  
                }  
            }  
        }  
    }
    
    /**
     * ȡ��cookie��ֵ
     * @param key cookie����
     */
    public String getCookieValue(String key) {
        String cookieVal = null;
        for(Cookie cookie : request.getCookies()){
            if (cookie.getName().equals(key)) {   
                try {
                    cookieVal = URLDecoder.decode(cookie.getValue(), "UTF-8");
                } catch (Exception e) {} 
                break;
            }  
        }
        return cookieVal;
    }
    
    /**
     * ȡ�÷�����Ŀ��url
     */
    public String getBaseUrl(){
        return request.getScheme() + "://"
                + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath() ;
    }
    
    /**
     * ajax���
     * @param content ������ı�����
     * @param type ������ı�������
     */
    public void ajax(String content, String type) {
        try {
            response.setContentType(type + ";charset=UTF-8");
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.getWriter().write(content);
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ��Ӧjson��ʽ���ַ�
     * @param content json��ʽ���ַ�
     */
    public void ajax(String content) {
        ajax(content, "application/json");
    }
    
    /**
     * ��Ӧ��״̬��json��ʽ�����
     * @param status
     * @param content
     */
    public void ajaxJson(String status, Object content){
        String json = "{\"status\" : \""+status+"\", " +
                "\"message\" : "+JSON.toJSONString(content)+" }";
        ajax(json, "application/json");
    }
    
    /**
     * ��Ӧjson��ʽ�����
     * @param content
     */
    public void ajaxJson(Object content) {    
        String json = JSON.toJSONString(content, SerializerFeature.WriteDateUseDateFormat);
        ajax(json, "application/json");
    }
    /**
	 * ������ת����JSON�ַ�����Ӧ��ǰ̨
	 * 
	 * @param object
	 */
	public void writeJson(HttpServletResponse response, Object object) {
		try {
			String json = JSON.toJSONStringWithDateFormat(object,
					"yyyy-MM-dd HH:mm:ss");
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write(json);
			response.getWriter().flush();
		} catch (IOException e) {
			LOGGER.debug(ExceptionUtil.getExceptionMessage(e));
		}
	}
}
