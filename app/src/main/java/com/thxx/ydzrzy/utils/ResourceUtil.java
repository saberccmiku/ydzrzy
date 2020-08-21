package com.thxx.ydzrzy.utils;

import com.thxx.ydzrzy.R;

import java.lang.reflect.Field;

public class ResourceUtil {

    public static Integer getResourceId(String srourceName) throws NoSuchFieldException, IllegalAccessException {
        Field field = R.drawable.class.getField(srourceName);
        return field.getInt(field.getName());
    }

}
