package com.kasuo.crawler.domain.source;

import com.kasuo.crawler.util.StringTool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class SourceUtil {
    private static List<String> countries = new ArrayList();

    static {
        countries.add("美国");
        countries.add("美利坚合众国");
        countries.add("哥伦比亚");
        countries.add("加拿大");
        countries.add("墨西哥");
        countries.add("巴西");
        countries.add("智利");
        countries.add("毛里求斯");
        countries.add("阿根廷");
        countries.add("秘鲁");
        countries.add("爱尔兰");
        countries.add("英国");
        countries.add("大不列颠");
        countries.add("英属");
        countries.add("法国");
        countries.add("德国");
        countries.add("奥地利");
        countries.add("意大利");
        countries.add("挪威");
        countries.add("丹麦");
        countries.add("瑞士");
        countries.add("瑞典");
        countries.add("荷兰");
        countries.add("西班牙");
        countries.add("希腊");
        countries.add("波兰");
        countries.add("芬兰");
        countries.add("土耳其");
        countries.add("保加利亚");
        countries.add("罗马尼亚");
        countries.add("俄罗斯");
        countries.add("印度");
        countries.add("巴基斯坦");
        countries.add("韩国");
        countries.add("大韩民国");
        countries.add("日本");
        countries.add("新加坡");
        countries.add("泰国");
        countries.add("菲律宾");
        countries.add("印度尼西亚");
        countries.add("越南");
        countries.add("马来西亚");
        countries.add("阿拉伯联合酋长国");
        countries.add("阿联酋");
        countries.add("沙特阿拉伯");
        countries.add("利比亚");
        countries.add("开曼群岛");
        countries.add("库克群岛拉罗汤加岛");
        countries.add("马绍尔群岛");
        countries.add("新西兰");
        countries.add("澳大利亚");
        countries.add("南非");
        countries.add("匈牙利");
        countries.add("以色列");
        countries.add("拉脱维亚");
        countries.add("卢森堡");
        countries.add("伊朗");
        countries.add("伊拉克");
        countries.add("也门");
    }


    public SourceUtil() {
    }


    public static void main(String[] args) {
        System.out.println(trimOrgName("重庆市 大宇 ，科技公司[2014.05.27 第1409期变]"));
        System.out.println(trimOrgName("重庆市大宇科技公司 AA, B.C "));
        System.out.println(trimOrgName("四川省(成都市）青羊区青龙街51号1幢16层5号 "));
    }


    public static boolean checkValidSourceOrgName(String orgName, String tmAddress) {
        if (orgName == null) {
            return false;
        }
        if (tmAddress != null) {
            if (isForeignCountry(tmAddress)) {
                return false;
            }
            if (tmAddress.contains("新疆")) {
                if ((orgName.endsWith("公司")) || (orgName.endsWith("厂")) || (orgName.endsWith("中心")) || (orgName.endsWith("学校")) || (orgName.endsWith("协会")) || (orgName.endsWith("合作社")) || (orgName.endsWith("联合社")) || (orgName.endsWith("医院")) || (orgName.endsWith("兵团"))) {
                    return true;
                }
                return false;
            }
            if (isPerson(orgName)) {
                return false;
            }
            return (orgName.length() > 5) && (!orgName.contains(" LIMITED")) && (!orgName.contains("LTD.")) && (!orgName.contains("CORPORATION")) && (!tmAddress.contains("香港")) && (!tmAddress.contains("澳门")) && (!tmAddress.contains("台湾")) && (!isForeignCompany(orgName));
        }

        if (isPerson(orgName)) {
            return false;
        }
        return (orgName.length() > 5) && (!orgName.contains(" LIMITED")) && (!orgName.contains("LTD.")) && (!orgName.contains("CORPORATION")) && (!isForeignCompany(orgName));
    }


    public static boolean isForeignCountry(String tmAddress) {
        for (String s : countries) {
            if (tmAddress.startsWith(s)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsPersonID(String orgName) {
        if (orgName == null) {
            return false;
        }
        boolean digital = false;
        boolean ret = false;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < orgName.length(); i++) {
            char c = orgName.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                sb.append(c);
                digital = true;

            } else if (digital) {
                if (sb.toString().length() > 14) {
                    return true;
                }
                digital = false;
                sb = new StringBuilder();
            }
        }


        if (sb.toString().length() > 14) {
            return true;
        }
        return false;
    }

    public static boolean isPerson(String orgName) {
        if (orgName == null) {
            return false;
        }
        if (containsPersonID(orgName)) {
            return true;
        }


        String s = orgName.replaceAll(" ", "").replaceAll("（", "(").replaceAll("）", ")").replaceAll("\\( *\\)", "");

        return s.length() <= 5;
    }


    public static String trimAddress(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(300);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
                if (c == 65288) {
                    c = '(';
                } else if (c == 65289) {
                    c = ')';
                } else if (c == '{') {
                    c = '(';
                } else if (c == '}') {
                    c = ')';
                } else if (c == '【') {
                    c = '(';
                } else if (c == '】') {
                    c = ')';
                } else if (c == 65292) {
                    c = ',';
                } else if (c == '。') {
                    c = ',';
                } else if (c == 65307) {
                    c = ';';
                }
                ret.append(c);
            }
        }
        String ss = ret.toString();

        int i = ss.indexOf('[');
        if (i != -1) {
            ss = ss.substring(0, i);
        }
        return ss;
    }


    public static String trimOrgName(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(300);
        s = s.trim();

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
                if (c == 65288) {
                    c = '(';
                } else if (c == 65289) {
                    c = ')';
                } else if (c == '{') {
                    c = '(';
                } else if (c == '}') {
                    c = ')';
                } else if (c == '【') {
                    c = '(';
                } else if (c == '】') {
                    c = ')';
                } else if (c == 65292) {
                    c = ',';
                } else if (c == '。') {
                    c = ',';
                } else if (c == 65307) {
                    c = ';';
                }
                ret.append(c);
            }
        }
        String ss = ret.toString();
        ret = new StringBuffer(300);

        for (int i = 0; i < ss.length(); i++) {
            boolean prev = false;
            boolean next = false;
            char c = ss.charAt(i);
            if (c == ' ') {
                if (i - 1 >= 0) {
                    char prevChar = ss.charAt(i - 1);
                    prev = StringTool.isChinese(prevChar);
                }
                if (i + 1 < ss.length()) {
                    char nextChar = ss.charAt(i + 1);
                    next = StringTool.isChinese(nextChar);
                }
                if ((!prev) || (!next)) {
                    ret.append(c);
                }
            } else {
                ret.append(c);
            }
        }
        ss = ret.toString();
        int i = ss.indexOf('[');
        if (i != -1) {
            ss = ss.substring(0, i);
        }
        return ss;
    }


    public static boolean isPhoneNum(String tel) {
        if ((tel == null) || (tel.trim().equals(""))) {
            return false;
        }
        tel = trimTel(tel);
        if (tel.length() != 11) {
            return false;
        }
        if (tel.indexOf("-") != -1) {
            return false;
        }
        if (!tel.startsWith("1")) {
            return false;
        }
        return true;
    }


    public static String trimTel(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(100);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != ' ') && (c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != '-') && (c != '—') && (c != '(') && (c != ')') && (c != 65288) && (c != 65289)) {
                ret.append(c);
            }
        }
        String ss = ret.toString();

        return ss;
    }


    public static String trimAllSpace(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(200);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
                ret.append(c);
            }
        }
        String ss = ret.toString();

        return ss;
    }


    public static boolean isNotFoundOrgName(String orgName) {
        if (orgName == null)
            return false;
        Pattern pattern = Pattern.compile("^[0-9_]*$");
        return pattern.matcher(orgName).find();
    }

    public static boolean isNotFoundOrgName(String orgName, Pattern pattern) {
        return orgName != null && pattern.matcher(orgName).find();
    }


    public static boolean isForeignCompany(String orgName) {
        if (orgName == null) {
            return false;
        }
        if (orgName.length() < 5) {
            return false;
        }
        if ((orgName.contains("?")) || (orgName.contains("？"))) {
            if ((orgName.endsWith("公司")) || (orgName.endsWith("厂")) || (orgName.endsWith("中心")) || (orgName.endsWith("学校")) || (orgName.endsWith("协会")) || (orgName.endsWith("合作社")) || (orgName.endsWith("联合社")) || (orgName.endsWith("医院")) || (orgName.endsWith("兵团"))) {
                return false;
            }
            return true;
        }

        String s = orgName.substring(orgName.length() - 4);

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9\\. ,;，；]*$");
        return pattern.matcher(s).find();
    }

    public static boolean isForeignCompany(String orgName, Pattern pattern) {
        if (orgName == null) {
            return false;
        }
        if (orgName.length() < 5) {
            return false;
        }
        String s = orgName.substring(orgName.length() - 4);

        return pattern.matcher(s).find();
    }


    private static boolean grantMdf(File file) {
        try {
            String cmd = "cmd /c c:/windows/system32/icacls " + file.getAbsolutePath() + " /grant \"Authenticated Users\":F";
            Process pro = Runtime.getRuntime().exec(cmd);

            return true;
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }


    private static String trimCrawledAgentName(String agentName) {
        String s = agentName.replaceAll("（", "(");
        s = s.replaceAll("）", ")").trim();

        String ret = null;
        String flag = null;
        int i = s.lastIndexOf(')');
        if (i == s.length() - 1) {
            i = s.lastIndexOf('(');
            if (i == -1) {
                return null;
            }
            ret = s.substring(0, i);
            flag = s.substring(i);
            if ((flag.contains("已注销")) || (flag.contains("停止代理"))) {
                return null;
            }
            return ret;
        }

        return s;
    }

    public static boolean orgNameIsRegNum(String orgName) {
        Pattern pattern = Pattern.compile("^[0-9]{5,10}_[0-9]{1,2}$");
        return pattern.matcher(orgName).find();
    }
}
