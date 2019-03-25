package com.ldz.util.commonUtil;

import org.apache.commons.lang.StringUtils;

import java.util.Random;

/**
 *生成随机数
 * Created by Administrator on 2018/5/21.
 */
public class StringDivUtils {
    /**
     * 产生随机的六位数
     * @return
     */
    public static String getSix(){
        Random rad=new Random();
        String result  = rad.nextInt(1000000) +"";
        if(result.length()!=6){
            return getSix();
        }
        return result;
    }
    /**
     * 获得0-9的随机数字符串
     *
     * @param length 返回字符串的长度
     * @return String
     */
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < length; i++) {
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }

    public static void main(String[] args) {
        for (int j = 0; j < 1000; j++) {
            System.out.println(getSix());
        }
    }
    /**
     * 判断手机号是否合格
     *
     * @param phonenumber
     * @return
     */
    public static boolean isPhoneValid(String phonenumber) {
        try {
            if (StringUtils.isBlank(phonenumber)) {
                return false;
            }
            if (!phonenumber.matches("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$")) {
                return false;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }




    /**
     *
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     *
     * @param cardNo
     * @return
     *
     */
    public static String hideCardNo(String cardNo) {
        if(StringUtils.isBlank(cardNo)) {
            return cardNo;
        }

        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for(int i=0; i<length; i++) {
            if(i < beforeLength || i >= (length - afterLength)) {
                sb.append(cardNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }
}
