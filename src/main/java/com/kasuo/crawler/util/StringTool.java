package com.kasuo.crawler.util;

import com.kasuo.crawler.domain.source.SourceUtil;

public class StringTool {
    public StringTool() {
    }

    public static void main(String[] args) {
        String web = "http://www.niteer.\\com";
        System.out.println(web);
        web = SourceUtil.trimAllSpace(web);
        web = web.replaceAll("：", ":");
        web = web.replaceAll("\\\\", "/");


        System.out.println(web);
    }

    public static double SimilarDegree(String strA, String strB) {
        String newStrA = removeSign(strA);

        String newStrB = removeSign(strB);

        int temp = Math.max(newStrA.length(), newStrB.length());

        int temp2 = longestCommonSubstring(newStrA, newStrB).length();

        return temp2 * 1.0D / temp;
    }

    private static String removeSign(String str) {
        StringBuffer sb = new StringBuffer();

        for (char item : str.toCharArray()) {
            if (charReg(item)) {


                sb.append(item);
            }
        }

        return sb.toString();
    }

    private static boolean charReg(char charValue) {
        if ((charValue < '一') || (charValue > 40869)) {
            if ((charValue < 'a') || (charValue > 'z')) {
                if ((charValue < 'A') || (charValue > 'Z')) {
                    if ((charValue < '0') || (charValue > '9')) return false;
                }
            }
        }
        return true;
    }

    private static String longestCommonSubstring(String strA, String strB) {
        char[] chars_strA = strA.toCharArray();

        char[] chars_strB = strB.toCharArray();

        int m = chars_strA.length;

        int n = chars_strB.length;

        int[][] matrix = new int[m + 1][n + 1];

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (chars_strA[(i - 1)] == chars_strB[(j - 1)]) {
                    matrix[i][j] = (matrix[(i - 1)][(j - 1)] + 1);
                } else {
                    matrix[i][j] = Math.max(matrix[i][(j - 1)], matrix[(i - 1)][j]);
                }
            }
        }

        char[] result = new char[matrix[m][n]];

        int currentIndex = result.length - 1;

        while (matrix[m][n] != 0) {
            if (matrix[n] == matrix[(n - 1)]) {
                n--;
            } else if (matrix[m][n] == matrix[(m - 1)][n]) {
                m--;
            } else {
                result[currentIndex] = chars_strA[(m - 1)];

                currentIndex--;

                n--;

                m--;
            }
        }


        return new String(result);
    }

    public static boolean isSimilar(CharSequence s, CharSequence t) {
        if ((s == null) || (t == null)) {
            return false;
        }
        int result = getLevenshteinDistance(s, t);
        int len = Math.max(s.length(), t.length());

        if (result * 1.0D / len < 0.7D) {
            return true;
        }
        return false;
    }

    public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
        if ((s == null) || (t == null)) {
            throw new IllegalArgumentException("Strings must not be null");
        }

        int n = s.length();
        int m = t.length();

        if (n == 0) {
            return m;
        }
        if (m == 0) {
            return n;
        }

        if (n > m) {
            CharSequence tmp = s;
            s = t;
            t = tmp;
            n = m;
            m = t.length();
        }


        int[] p = new int[n + 1];
        int[] d = new int[n + 1];


        for (int i = 0; i <= n; i++) {
            p[i] = i;
        }

        for (int j = 1; j <= m; j++) {
            char t_j = t.charAt(j - 1);
            d[0] = j;

            for (int i = 1; i <= n; i++) {
                int cost = s.charAt(i - 1) == t_j ? 0 : 1;

                d[i] = Math.min(Math.min(d[(i - 1)] + 1, p[i] + 1), p[(i - 1)] + cost);
            }


            int[] _d = p;
            p = d;
            d = _d;
        }


        return p[n];
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if ((ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) || (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B) || (ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION) || (ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) || (ub == Character.UnicodeBlock.GENERAL_PUNCTUATION)) {
            return true;
        }
        return false;
    }

    public static String trimAddress(String s) {
        if ((s == null) || (s.trim().equals("")))
            return null;
        StringBuffer ret = new StringBuffer(200);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != ' ') && (c != '<') && (c != '>') && (c != '!') && (c != '-')) {
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
                } else if (c == '】')
                    c = ')';
                ret.append(c);
            }
        }
        String ss = ret.toString();

        return ss;
    }

    public static String trimTel(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(100);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c >= '0') && (c <= '9')) {
                ret.append(c);
            }
        }
        String ss = ret.toString();

        return ss;
    }

    public static String trimAll(String s) {
        if ((s == null) || (s.trim().equals(""))) {
            return null;
        }
        StringBuffer ret = new StringBuffer(100);
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if ((c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
                ret.append(c);
            }
        }
        String ss = ret.toString();

        return ss;
    }
}
