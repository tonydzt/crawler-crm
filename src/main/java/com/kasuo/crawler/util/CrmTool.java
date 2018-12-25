package com.kasuo.crawler.util;

import java.io.File;
import java.util.Arrays;


public class CrmTool {
    public CrmTool() {
    }

    public static void sortAscFileArray(File[] files) {
        Arrays.sort(files, new FileAscComprator());
    }


    public static void sortDescFileArray(File[] files) {
        Arrays.sort(files, new FileDescComprator());
    }

    public static void main(String[] args) {
        File[] files = new File[4];
        files[0] = new File("1.jpg");
        files[1] = new File("10.jpg");
        files[2] = new File("2.jpg");
        files[3] = new File("11.jpg");

        sortAscFileArray(files);
        for (File file : files) {
            System.out.println(file.getName());
        }

        System.out.println("*****************************");

        sortDescFileArray(files);
        for (File file : files) {
            System.out.println(file.getName());
        }
    }
}
