package com.example.sqltest.util;

import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by T016 on 2019/3/7.
 */
public class MyUtils {

    public static void copyProperties(Object source, Object target, boolean filterNull, String... ignoreProperties) throws IllegalAccessException {
        if (filterNull){
            List<String> nullNameList = new ArrayList<>();
            if(ignoreProperties != null && ignoreProperties.length > 0){
                nullNameList.addAll(Arrays.asList(ignoreProperties));
            }
            // 得到源Class
            Class sourceClass = source.getClass();
            // 得到所有属性
            Field[] fields = sourceClass.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                String value = (String)field.get(source);
                if(StringUtils.isEmpty(value)){
                    nullNameList.add(field.getName());
                }
            }
            if(nullNameList.size() > 0) {
                String[] nullNames = nullNameList.toArray(new String[0]);
                BeanUtils.copyProperties(source, target, nullNames);
            }
        } else {
            BeanUtils.copyProperties(source, target, ignoreProperties);
        }
    }

}
