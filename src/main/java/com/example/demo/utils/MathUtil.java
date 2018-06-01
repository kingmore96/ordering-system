package com.example.demo.utils;

/**
 * 判断金额是否一致
 */
public class MathUtil {

    //用于判断两个double相减以后的判定区间
    private static final Double MONEY_RANGE = 0.01;

    /**
     * 判断传入的两个double是否一致
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        double result = Math.abs(d1 - d2);
        if(result < MONEY_RANGE){
            return true;
        }
        else{
            return false;
        }
    }
}
