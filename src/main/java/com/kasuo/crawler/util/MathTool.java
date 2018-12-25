package com.kasuo.crawler.util;


public class MathTool {
    public MathTool() {
    }


    public static boolean isOdd(int d) {
        return (d & 0x1) != 0;
    }


    public static boolean isOdd(long d) {
        return (d & 1L) != 0L;
    }


    public static boolean isEven(int d) {
        return (d & 0x1) == 0;
    }


    public static boolean isEven(long d) {
        return (d & 1L) == 0L;
    }

    public static void main(String[] args) {
    }
}
