package com.kasuo.crawler.util;

import java.util.Random;


public class CodecTool {
    public CodecTool() {
    }

    public static int random(int iMin, int iInc) {
        Random rand = new Random();
        return rand.nextInt(iInc) + iMin;
    }


    public static void main(String[] args)
            throws Exception {
        String infoTel = "235";
        String tel = "010235";
        if ((infoTel != null) && (!infoTel.endsWith(tel)) && (!tel.endsWith(infoTel))) {
            System.out.println("add book");
        }
    }
}
