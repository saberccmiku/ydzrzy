package com.thxx.ydzrzy.utils;

import android.content.Context;
import android.content.res.AssetManager;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

/**
 * @author fjy
 * @since 2020-08-14
 */
public class JsonUtil {

    /**
     * 从本地assets文件地区json数据
     *
     * @param context   上下文
     * @param assetName assets中文件的名称
     * @return json字符串
     */
    public static String getJson(Context context, String assetName) {
        StringBuilder sb = new StringBuilder();

        AssetManager assetManager = context.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(assetManager.open(assetName)));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 把json字符串数据转成json
     *
     * @param json  sion
     * @param clazz 转换对象class
     * @param <T>   转换对象
     * @return 转换对象集合
     */
    public static <T> List<T> parseString2List(String json, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        return new Gson().fromJson(json, type);
    }

    /**
     * 把本地assets文件中的json数据转成json
     *
     * @param context   上下文
     * @param assetName 文件名
     * @param clazz     转换对象class
     * @param <T>       转换对象
     * @return 转换对象集合
     */
    public static <T> List<T> parseAssetJson2List(Context context, String assetName, Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        return new Gson().fromJson(getJson(context, assetName), type);
    }
}
