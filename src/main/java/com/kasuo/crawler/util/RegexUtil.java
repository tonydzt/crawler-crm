package com.kasuo.crawler.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author douzhitong
 * @date 2018/12/2
 */
public class RegexUtil {

    public static boolean isMobile(String mobile) {
        // "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String regex = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
        Pattern p = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(mobile);
        return m.matches();
    }

//    public static void main(String[] args) {
//        System.out.println(isMobile("051980695178"));
//        System.out.println(isMobile("18515635523"));
//    }
}
