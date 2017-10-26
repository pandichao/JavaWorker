package com.cgxt.utils;

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
 * 基于JAXB的XML生成器: 用于pojo与xml文件的相互转换
 * @author lyn
 * @ClassName XmlBuilder
 * @DateTime 2017年2月16日 上午10:50:17
 */
public abstract class XmlBuilder {

    private final static Logger log= Logger.getLogger(XmlBuilder.class);
    /**
     * 将pojo转换为XML字符串
     * @param object
     * @return
     */
    public static String convertToXml(Object object) {
        Writer sw = new StringWriter();  
        try {  
            // 利用jdk中自带的转换类实现  
            JAXBContext context = JAXBContext.newInstance(object.getClass());  

            Marshaller marshaller = context.createMarshaller();  
            // 格式化xml输出的格式  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);  
            // 将对象转换成输出流形式的xml  
            marshaller.marshal(object, sw); 
            log.debug("XML字符串生成成功！");
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
     * 将pojo转换为XML文件
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
                System.out.println(MessageFormat.format("{0}不是有效的文件路径.", savePath));
                return null;
            }

            Writer writer = null;

            try {
                writer = new FileWriter(file);
                writer.write(xmlStr);
                writer.flush();
                log.debug("XML文件生成成功！");
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
     * 将XML文件转换为指定类型的pojo
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
     * 将XML字符串转换为指定类型的pojo
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
            // 进行将Xml转成对象的核心接口
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