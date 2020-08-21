package com.thxx.ydzrzy.utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ParameterizedTypeImpl implements ParameterizedType {

    Class clazz;

    ParameterizedTypeImpl(Class clz) {
        clazz = clz;
    }

    @NotNull
    @Override
    public Type[] getActualTypeArguments() {
        return new Type[]{clazz};
    }

    @NotNull
    @Override
    public Type getRawType() {
        return List.class;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}
