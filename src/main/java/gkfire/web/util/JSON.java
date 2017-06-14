/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gkfire.web.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import org.hibernate.proxy.HibernateProxy;

/**
 *
 * @author Jhoan Brayam
 */
public class JSON {

    public static String convert(Object o) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        String json = "{";
        if (o.getClass().isArray()) {

        } else if (o instanceof List) {

        } else if (o instanceof HibernateProxy) {

        } else if (o.getClass().isEnum()) {
            Method[] methods = o.getClass().getDeclaredMethods();
            List<Method> avMethods = new ArrayList();
            for (Method m : methods) {
                if (m.getName().startsWith("get")) {
                    avMethods.add(m);
                }
            }
            if (avMethods.isEmpty()) {
                return "\"" + ((Enum) (o)).name() + "\"";
            } else {
                json += node("name", ((Enum) (o)).name());
                for (Method m : avMethods) {
                    String name = m.getName().replaceFirst("get", "");
                    name = (name.charAt(0) + "").toLowerCase() + name.substring(1, name.length());
                    json += node(name, m.invoke(o));
                }
                if (json.charAt(json.length() - 1) == ',') {
                    json = json.substring(0, json.length() - 1);
                }
            }
        } else {
            Field[] fields = o.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
                Object fieldValue = field.get(o);
                field.setAccessible(false);
                json += node(field.getName(), fieldValue);

            }
            if (json.charAt(json.length() - 1) == ',') {
                json = json.substring(0, json.length() - 1);
            }
        }
        json += "}";
        return json;
    }
    public static Object[] toArray(ScriptObjectMirror value){
        Set<String> keys = value.keySet();
        List objects = new ArrayList();
        for(String key : keys){
            objects.add(value.get(key));
        }
        return objects.toArray();
    }
    private static String node(String name, Object fieldValue) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        if(fieldValue == null){
            return "";
        }
        if (fieldValue instanceof String) {
            return "\"" + name + "\" : \"" + ((String) fieldValue).replace("\"", "\\\"") + "\",";
        } else if (fieldValue instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return "\"" + name + "\" : \"" + sdf.format(fieldValue) + "\",";
        } else if (fieldValue instanceof Number) {
            return "\"" + name + "\" : " + fieldValue + ",";
        } else if (fieldValue instanceof HibernateProxy) {

        } else if(fieldValue instanceof Boolean) {
            return "\"" + name + "\" : " + fieldValue + ",";
        }else if (fieldValue.getClass().isEnum()) {
            return "\"" + name + "\" : " + convert(fieldValue) + ",";
        } else {
            return "\"" + name + "\" : " + convert(fieldValue) + ",";
        }
        return "";
    }
//
//    public static void main(String[] args)
//            throws Exception {
//        Class<?> clz = Class.forName("test.PropertyEnum");
//        /* Use method added in Java 1.5. */
//        Object[] consts = clz.getEnumConstants();
//        /* Enum constants are in order of declaration. */
//        Class<?> sub = consts[0].getClass();
//        Method mth = sub.getDeclaredMethod("getDefaultValue");
//        String val = (String) mth.invoke(consts[0]);
//        /* Prove it worked. */
//        System.out.println("getDefaultValue "
//                + val.equals(PropertyEnum.SYSTEM_PROPERTY_ONE.getDefaultValue()));
//    }
}
