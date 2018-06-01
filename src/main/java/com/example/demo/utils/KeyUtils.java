package com.example.demo.utils;

import java.util.Random;

/**
 *生成主键的util
 */
public class KeyUtils {

    /**
     * 生成主键
     * 格式：时间+随机数
     * @return
     */
    public static String genUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(9000000) + 1000000;
        return System.currentTimeMillis() + String.valueOf(number);
    }
}
