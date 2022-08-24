package com.kborid.common.util;

import com.orhanobut.logger.Logger;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * ReflectUtil
 *
 * @description: 反射工具类
 * @date: 2019/9/25
 * @author: duanwei
 * @email: duanwei@thunisoft.com
 * @version: 1.0.0
 */
public class ReflectUtil {

    /**
     * 判断类是否存在
     *
     * @param className 类名-全路径
     * @return boolean
     */
    public static boolean hasClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 反射调用静态方法
     *
     * @param className   类名。全路径
     * @param methodName  方法名
     * @param paramTypes  参数类型
     * @param paramValues 参数
     * @return 方法调用结果
     */
    public static Object invokeStaticMethod(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Class<?> clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(null, paramValues);
        } catch (Exception e) {
            Logger.e(e, "反射调用方法出错");
        }
        return null;
    }

    /**
     * 反射调用非静态方法
     *
     * @param className   类名。全路径
     * @param methodName  方法名
     * @param paramTypes  参数类型
     * @param paramValues 参数
     * @return 方法调用结果
     */
    public static Object invokeMethod(String className, String methodName, Class<?>[] paramTypes, Object[] paramValues) {
        try {
            Class<?> clazz = Class.forName(className);
            Object obj = clazz.newInstance();
            Method method = clazz.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(obj, paramValues);
        } catch (Exception e) {
            Logger.e(e, "反射调用方法出错");
        }
        return null;
    }

    /**
     * 反射实例化对象
     *
     * @param className 类名。全路径
     * @param paramType 参数类型
     * @param param     参数
     * @param <T>
     * @return obj
     */
    public static <T> T newInstance(String className, Class<?> paramType, Object param) {
        Class<?> clazz = null;
        try {
            clazz = Class.forName(className);
            Constructor<?> cons = clazz.getDeclaredConstructor(paramType);
            return (T) cons.newInstance(param);
        } catch (Exception e) {
            Logger.e(e, "新建实例失败(%s)", className);
            return null;
        }
    }

    /**
     * 反射获取字段值
     *
     * @param className 类名。全路径
     * @param fieldName 字段名
     * @param isStatic  是否静态字段
     * @return obj
     */
    public static Object getField(String className, String fieldName, boolean isStatic) {
        Object ret = null;
        try {
            Class<?> clazz = Class.forName(className);
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            if (isStatic) {
                ret = field.get(null);
            } else {
                Object obj = clazz.newInstance();
                ret = field.get(obj);
            }
        } catch (Exception e) {
            Logger.e(e, "反射获取字段值出错");
        }
        return ret;
    }
}
