package com.cglib.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.text.MessageFormat;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;


import javax.xml.bind.Unmarshaller;

import org.apache.log4j.Logger;

/**
 * ����JAXB��XML�����: ����pojo��xml�ļ����໥ת��
 * @author lyn
 * @ClassName XmlBuilder
 * @DateTime 2017��2��16�� ����10:50:17
 */
public abstract class XmlBuilder {

    private final static Logger log= Logger.getLogger(XmlBuilder.class);
    /**
     * ��pojoת��ΪXML�ַ�
     * @param object
     * @return
     */
    public static String convertToXml(Object object) {
        Writer sw = new StringWriter();  
        try {  
            // ����jdk���Դ��ת����ʵ��  
            JAXBContext context = JAXBContext.newInstance(object.getClass());  

            Marshaller marshaller = context.createMarshaller();  
            // ��ʽ��xml����ĸ�ʽ  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  
            // ������ת�����������ʽ��xml  
            marshaller.marshal(object, sw); 
            log.debug("XML�ַ���ɳɹ���");
        } catch (JAXBException e) {  
            e.printStackTrace();  
        } finally {
            if(sw != null) {
                try {
                    sw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sw.toString().replace("standalone=\"yes\"", "");  
    }

    /**
     * ��pojoת��ΪXML�ļ�
     * @param obj
     * @param savePath
     */
    public static File convertToXmlFile(Object obj,String savePath) {
        File file = null;
        String xmlStr = convertToXml(obj);
        if(xmlStr != null && !"".equals(xmlStr)) {

            file = new File(savePath);
            if(!file.exists() && file.isFile()) {
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(file.isDirectory()) {
                System.out.println(MessageFormat.format("{0}������Ч���ļ�·��.", savePath));
                return null;
            }

            Writer writer = null;

            try {
                writer = new FileWriter(file);
                writer.write(xmlStr);
                writer.flush();
                log.debug("XML�ļ���ɳɹ���");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return file;
    }


    /**
     * ��XML�ļ�ת��Ϊָ�����͵�pojo
     * @param clazz
     * @param xmlPath
     * @return
     */
    public static Object xmlFileToObject(Class<?> clazz, String xmlPath) {
        Object xmlObject = null;
        Reader fr = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            fr = new FileReader(xmlPath);
            xmlObject = unmarshaller.unmarshal(fr);
        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (null != fr) {
                try {
                    fr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlObject;
    }

    /**
     * ��XML�ַ�ת��Ϊָ�����͵�pojo
     * 
     * @param clazz
     * @param xmlStr
     * @return
     */
    public static Object xmlStrToObject(Class<?> clazz, String xmlStr) {
        Object xmlObject = null;
        Reader reader = null;
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            // ���н�Xmlת�ɶ���ĺ��Ľӿ�
            Unmarshaller unmarshaller = context.createUnmarshaller();
            reader = new StringReader(xmlStr);
            xmlObject = unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            e.printStackTrace();
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return xmlObject;
    }
}