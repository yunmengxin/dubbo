package com.alibaba.dubbo.common.bytecode;

import javassist.CtClass;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

/**
 * @author rongjie.chen
 * @date 2019/11/6
 */
public class JavassistHelper {
    public static void classPrint(ClassGenerator cc, String name) {
        try {
            Field mCtc = cc.getClass().getDeclaredField("mCtc");
            if (mCtc == null)
                return;
            mCtc.setAccessible(true);
            CtClass ctClassObj = (CtClass) mCtc.get(cc);
            ;
            byte[] byteArr = ctClassObj.toBytecode();
            FileOutputStream fos = new FileOutputStream(
                    new File("/Users/mengxin/Downloads/"
                            + name
                            + ".class")
            );
            fos.write(byteArr);
            fos.close();
        } catch (Exception ignore) {
        }
    }

    public static void fieldPrint(Class<?> wc) {
        try {
            Field[] declaredFields = wc.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                Object object = field.get(wc);
                if (object instanceof String[]) {
                    String[] arr = (String[]) object;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < arr.length; i++) {
                        sb.append(arr[i]);
                        if (i != arr.length - 1)
                            sb.append(",");
                    }
                    System.out.println(field.getName() + ": " + sb.toString());
                } else if (object instanceof Class[]) {
                    Class[] arr = (Class[]) object;
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < arr.length; i++) {
                        sb.append(arr[i].getName());
                        if (i != arr.length - 1)
                            sb.append(",");
                    }
                    System.out.println(field.getName() + ": " + sb.toString());
                } else {
                    System.out.println(field.getName() + ": " + object);
                }
            }
        } catch(Exception ignored) {
        }
    }
}
