package com.kasuo.crawler.util;


import com.kasuo.crawler.domain.contact.Contact;
import com.kasuo.crawler.domain.contact.ContactBook;
import com.kasuo.crawler.domain.source.SourceUtil;

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


    public static void setContactDefaultTel(Contact contact) {
        String contactAreacode = null;
        String contactTel = null;
        String contactQq = null;
        String contactWx = null;
        String contactEmail = null;

        for (ContactBook cb : contact.getBooks()) {
            if (cb.getType().isTel()) {
                if ((contactTel == null) || (contactTel.trim().equals(""))) {
                    contactTel = cb.getBook();
                } else if (SourceUtil.isPhoneNum(cb.getBook())) {
                    contactTel = cb.getBook();
                }
                if ((contactAreacode == null) || (contactAreacode.trim().equals(""))) {
                    contactAreacode = cb.getAreacode();
                }
            }
            if ((cb.getType().isQQ()) && (
                    (contactQq == null) || (contactQq.trim().equals("")))) {
                contactQq = cb.getBook();
            }

            if ((cb.getType().isWX()) && (
                    (contactWx == null) || (contactWx.trim().equals("")))) {
                contactWx = cb.getBook();
            }

            if ((cb.getType().isEmail()) && (
                    (contactEmail == null) || (contactEmail.trim().equals("")))) {
                contactEmail = cb.getBook();
            }
        }


        if ((contact.getAreacode() == null) || (contact.getAreacode().trim().equals(""))) {
            contact.setAreacode(contactAreacode);
        }
        if ((contact.getTel() == null) || (contact.getTel().trim().equals(""))) {
            contact.setTel(contactTel);
        } else if (SourceUtil.isPhoneNum(contactTel)) {
            contact.setTel(contactTel);
        }
        if ((contact.getQq() == null) || (contact.getQq().trim().equals(""))) {
            contact.setQq(contactQq);
        }
        if ((contact.getWx() == null) || (contact.getWx().trim().equals(""))) {
            contact.setWx(contactWx);
        }
        if ((contact.getEmail() == null) || (contact.getEmail().trim().equals(""))) {
            contact.setEmail(contactEmail);
        }
    }
}
