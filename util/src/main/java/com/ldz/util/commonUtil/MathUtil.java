package com.ldz.util.commonUtil;

import java.math.BigDecimal;
import java.util.regex.Pattern;

/**
 * @author chenwei
 * @copyright
 * @category
 * @since 2017/11/21
 */
public class MathUtil {
    public static double add(double v1 , double v2){
        return retention(new BigDecimal(v1).add(new BigDecimal(v2)));
    }
    public static double sub(double v1 , double v2){
        return retention(new BigDecimal(v1).subtract(new BigDecimal(v2)));
    }
    public static double mul(double v1 , double v2){
        return retention(new BigDecimal(v1).multiply(new BigDecimal(v2)));
    }
    public static double div(double v1 , double v2){
        return retention(new BigDecimal(v1).divide(new BigDecimal(v2),3,BigDecimal.ROUND_HALF_UP));
    }
    /**
     * 保留2位小数，四舍五入
     * @param v
     * @return
     */
    public static double retention(Number v){
        return new BigDecimal(v.doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static float retatin1(float r){
        return retatin(r,1,4);
    }
    public static float retatin(float r,int num){
        return retatin(r,num,4);
    }
    public static float retatin(float r,int num,int roundingMode){
        BigDecimal bd  =   new  BigDecimal((double)r);
        bd   =  bd.setScale(2,roundingMode);
        return bd.floatValue();
    }

    public static void main(String[] args) {
        System.out.println(isNumeric("12a3"));
    }
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }
    /**
     *  求两个数的最大公约数
     */
    public static int getDivisor(int m , int n){
        int result = 0;
        while (n != 0) {
            result = m % n;
            m = n;
            n = result;
        }
        return m;
    }

}
