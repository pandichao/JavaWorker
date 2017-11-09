package com.cglib.utils;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * <h6>Description:<h6>
 * <p>Java Class�뷴����ص�һЩ������</p>
 */
public final class ClassUtil {


    /**
     * ��ȡ�������
     */
    public static ClassLoader overridenClassLoader;

    public static ClassLoader getContextClassLoader() {
        return overridenClassLoader != null ?
                overridenClassLoader : Thread.currentThread().getContextClassLoader();
    }

    private static Logger logger = Logger.getLogger(ClassUtil.class);

    /**
     * ��ȡָ�����ȫ�������ֶ�
     *
     * @param className    ��Ҫ��ȡ������
     * @param extendsField �Ƿ��ȡ�ӿڻ����еĹ�������
     * @return �����ֶ�����
     */
    public final static String[] getField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Field[] fields = classz.getFields();
        Set<String> set = new HashSet<String>();
        if (fields != null) {
            for (Field f : fields) {
                set.add(f.getName());
            }
        }
        if (extendsField) {
            Field[] fieldz = classz.getDeclaredFields();
            if (fieldz != null) {
                for (Field f : fieldz) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ���еĹ�������
     *
     * @param className    ��Ҫ��ȡ������
     * @param extendsField �Ƿ��ȡ�ӿڻ����еĹ�������
     * @return �����ֶ�����
     */
    public final static String[] getPublicField(String className, boolean extendsField) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<String>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }
        if (extendsField) {
            Field[] fieldz = classz.getFields();
            if (fieldz != null) {
                for (Field f : fieldz) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ���ж����protected���͵������ֶ�
     *
     * @param className ��Ҫ��ȡ������
     * @return protected���͵������ֶ�����
     */
    public final static String[] getProtectedField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<String>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ���ж����private���͵������ֶ�
     *
     * @param className ��Ҫ��ȡ������
     * @return private���͵������ֶ�����
     */
    public final static String[] getPrivateField(String className) {
        Class classz = loadClass(className);
        Set<String> set = new HashSet<String>();
        Field[] fields = classz.getDeclaredFields();
        if (fields != null) {
            for (Field f : fields) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ�����ȫ��public���ͷ���
     *
     * @param className     ��Ҫ��ȡ������
     * @param extendsMethod �Ƿ��ȡ�̳����ķ���
     * @return ����������
     */
    public final static String[] getPublicMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<String>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("public")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }


    /**
     * ��ȡ�����ȫ��protected���ͷ���
     *
     * @param className     ��Ҫ��ȡ������
     * @param extendsMethod �Ƿ��ȡ�̳����ķ���
     * @return ����������
     */
    public final static String[] getProtectedMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<String>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("protected")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ�����ȫ��private���ͷ���
     *
     * @param className ��Ҫ��ȡ������
     * @return ����������
     */
    public final static String[] getPrivateMethod(String className) {
        Class classz = loadClass(className);
        Method[] methods = classz.getDeclaredMethods();
        Set<String> set = new HashSet<String>();
        if (methods != null) {
            for (Method f : methods) {
                String modifier = Modifier.toString(f.getModifiers());
                if (modifier.startsWith("private")) {
                    set.add(f.getName());
                }
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * ��ȡ�����ȫ������
     *
     * @param className     ��Ҫ��ȡ������
     * @param extendsMethod �Ƿ��ȡ�̳����ķ���
     * @return ����������
     */
    public final static String[] getMethod(String className, boolean extendsMethod) {
        Class classz = loadClass(className);
        Method[] methods;
        if (extendsMethod) {
            methods = classz.getMethods();
        } else {
            methods = classz.getDeclaredMethods();
        }
        Set<String> set = new HashSet<String>();
        if (methods != null) {
            for (Method f : methods) {
                set.add(f.getName());
            }
        }
        return set.toArray(new String[set.size()]);
    }


    /**
     * ���ö����setter����
     *
     * @param obj   ����
     * @param att   ������
     * @param value ����ֵ
     * @param type  ��������
     */
    public final static void setter(Object obj, String att, Object value, Class<?> type)
            throws InvocationTargetException, IllegalAccessException {
        try {
            String name = att.substring(0, 1).toUpperCase() + att.substring(1);
            Method met = obj.getClass().getMethod("set" + name, type);
            met.invoke(obj, value);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

    }


    /**
     * ��ȡָ��Ŀ¼�����е�����
     *
     * @param path         ����
     * @param childPackage �Ƿ��ȡ�Ӱ�
     */
    public final static List<String> getClassName(String path, boolean childPackage) {
        List<String> fileNames = new ArrayList<String>();
        if (path.endsWith(".jar")) {
            fileNames.addAll(getClassNameByJar(path));
        } else {
            fileNames = getClassNameByFile(path, childPackage);
        }
        return fileNames;
    }

    /**
     * ����Ŀ�ļ���ȡĳ����������
     *
     * @param filePath     �ļ�·��
     * @param childPackage �Ƿ�����Ӱ�
     * @return ����������
     */
    public final static List<String> getClassNameByFile(String filePath, boolean childPackage) {
        List<String> myClassName = new ArrayList<String>();
        List<File> files = FileUtil.listFile(filePath, childPackage);
        for (File file : files) {
            if (file.getName().endsWith(".class")) {
                String childFilePath = file.getPath();
                int index = filePath.replaceAll("\\\\", ".").length();
                childFilePath = childFilePath.replaceAll("\\\\", ".").substring(index, childFilePath.length());
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }

    /**
     * ��jar��ȡĳ����������
     *
     * @param jarPath jar�ļ�·��
     * @return ����������
     */
    public final static List<String> getClassNameByJar(String jarPath) {
        List<String> myClassName = new ArrayList<String>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(".class")) {
                    entryName = entryName.replace("/", ".").substring(0, entryName.lastIndexOf("."));
                    myClassName.add(entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myClassName;
    }


    /**
     * ����ָ������
     *
     * @param className ��Ҫ���ص���
     * @return ���غ����
     */
    public final static Class loadClass(String className) {
        Class theClass = null;
        try {
            theClass = Class.forName(className);
        } catch (ClassNotFoundException e1) {
            logger.error("load class error:" + e1.getMessage());
            e1.printStackTrace();
        }
        return theClass;
    }

    /**
     * ��ȡjar���еķ�*.class���ȫ����Դ�ļ�����
     *
     * @param jarPath jar�ļ�·��
     * @return ������Դ�������
     */
    public final static List<String> getResourceNameByJar(String jarPath) {
        List<String> resource = new ArrayList<String>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (!entryName.endsWith(".class") && !entryName.endsWith("/")) {
                    resource.add(FilePathUtil.commandPath(jarPath) + "!" + entryName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resource;
    }

    /**
     * ��ȡjar���еķ�*.class���ȫ������suffix��β����Դ�ļ�
     *
     * @param jarPath jar���·��
     * @param suffix  ��׺���
     * @return ������Դ�������
     */
    public final static List<String> getResourceNameByJar(String jarPath, String suffix) {
        List<String> resource = new ArrayList<String>();
        try (JarFile jarFile = new JarFile(jarPath)) {
            Enumeration<JarEntry> entrys = jarFile.entries();
            while (entrys.hasMoreElements()) {
                JarEntry jarEntry = entrys.nextElement();
                String entryName = jarEntry.getName();
                if (entryName.endsWith(suffix)) {
                    resource.add(FilePathUtil.commandPath(jarPath) + "!" + entryName);
                }
            }
        } catch (IOException e) {
            logger.error(ExceptionUtil.stackTraceToString(e, UtilConstant.APP_PACKAGE));
        }
        return resource;
    }

    /**
     * ��ȡһ����ĸ���
     *
     * @param className ��Ҫ��ȡ����
     * @return ��������
     */
    public final static String getSuperClass(String className) {
        Class classz = loadClass(className);
        Class superclass = classz.getSuperclass();
        return superclass.getName();
    }

    /**
     * ��ȡһ���׵ļ̳���
     *
     * @param className ��Ҫ��ȡ����
     * @return �̳����������
     */
    public final static String[] getSuperClassChian(String className) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList<String>();
        Class superclass = classz.getSuperclass();
        String superName = superclass.getName();
        if (!"java.lang.Object".equals(superName)) {
            list.add(superName);
            list.addAll(Arrays.asList(getSuperClassChian(superName)));
        } else {
            list.add(superName);
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * ��ȡһ��ʵ�ֵ�ȫ���ӿ�
     *
     * @param className         ��Ҫ��ȡ����
     * @param extendsInterfaces ��˵getInterfaces��ȫ����ȡ���Ŷԣ�Ȼ����Ե�ʱ����Ľӿڲ�û��
     *                          ��˾Ͷ���������
     * @return ʵ�ֽӿ���Ƶ�����
     */
    public final static String[] getInterfaces(String className, boolean extendsInterfaces) {
        Class classz = loadClass(className);
        List<String> list = new ArrayList<String>();
        Class[] interfaces = classz.getInterfaces();
        if (interfaces != null) {
            for (Class inter : interfaces) {
                list.add(inter.getName());
            }
        }
        if (extendsInterfaces) {
            String[] superClass = getSuperClassChian(className);
            for (String c : superClass) {
                list.addAll(Arrays.asList(getInterfaces(c, false)));
            }
        }
        return list.toArray(new String[list.size()]);
    }
}
