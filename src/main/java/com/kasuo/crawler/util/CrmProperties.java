package com.kasuo.crawler.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrmProperties {

    private static Logger logger = LoggerFactory.getLogger(CrmProperties.class);

    private static Properties prop = null;

    public static boolean refresh() {
        prop = new Properties();
        try {
            InputStream in = new BufferedInputStream(CrmProperties.class.getClassLoader().getResourceAsStream("application.properties"));
            prop.load(new InputStreamReader(in, "utf-8"));
            in.close();
            return true;
        } catch (Exception e) {
            logger.error("PropertiesTool init error", e);
        }
        return false;
    }

    public static String getProperty(String name) {
        String s = prop.getProperty(name);
        if (s != null) {
            s = s.trim();

        } else {
            s = "";
        }

        return s;
    }

    static {
        refresh();
    }

    public CrmProperties() {
    }

    public static void main(String[] args) {
        refresh();
        System.out.println(getProperty("crawlerIniWait"));
    }
}
