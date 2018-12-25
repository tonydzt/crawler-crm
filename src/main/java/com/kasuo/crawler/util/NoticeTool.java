//package com.etoip.crm.util;
//
//import org.slf4j.Logger;
//
//public class NoticeTool {
//    private static final String SIGNEDCODE = "df3gsfd6x29zm2g";
//
//    public static abstract interface NoticeCode {
//        public static final String SOURCE_MAIL_ARRIVED = "SMS_44225021";
//        public static final String SOURCE_DATA_DUPLICATE = "SMS_44075037";
//        public static final String SOURCE_MAIL_HANDLE_ERROR = "SMS_44500118";
//        public static final String SOURCE_CRAWLER_CU_OVER = "SMS_34570029";
//        public static final String SOURCE_CRAWLER_TM_OVER = "SMS_34580070";
//        public static final String SOURCE_OCR_QUOTA_OVER = "SMS_34785072";
//        public static final String SOURCE_OCR_REJECTED_OVER = "SMS_34625070";
//        public static final String SOURCE_CRAWLER_INIT = "SMS_34575039";
//        public static final String CRM_DISCONNECT = "SMS_44410092";
//        public static final String WEB_DISCONNECT = "SMS_44390183";
//        public static final String SOURCE_HANDLE_INIT_START = "SMS_114825001";
//        public static final String SOURCE_HANDLE_INIT_ERROR = "SMS_114830001";
//        public static final String SOURCE_HANDLE_INIT_OVER = "SMS_114840001";
//        public static final String SOURCE_HANDLE_END = "SMS_114850001";
//        public static final String SOURCE_CRAWLER_CU_VERIFYCODE = "SMS_44310173";
//        public static final String SOURCE_CRAWLER_CU_LOGIN = "SMS_44385113";
//        public static final String SOURCE_CRAWLER_CU_DECLINE = "SMS_44435263";
//        public static final String BUSI_REJECTED = "SMS_34815021";
//    }
//
//    private static String serverSign = "U3xer0Xdz9Z2Ea";
//    private static String serverIp = null;
//    private static long lastVerifyNoticeMailTime = 0L;
//    private static long lastVerifyNoticeSMSTime = 0L;
//
//    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(NoticeTool.class);
//
//    static {
//        String localServer = CrmProperties.getProperty("localServer");
//        if ((localServer != null) && (localServer.equals("true"))) {
//            serverIp = CrmProperties.getProperty("server");
//        } else if ((localServer != null) && (localServer.equals("false")) &&
//                (serverIp == null)) {
//            serverIp = queryServerIp();
//            if (serverIp != null)
//                serverIp = "http://" + serverIp + ":12221/crm/";
//        }
//    }
//
//    public NoticeTool() {
//    }
//
//    public static abstract interface NoticeLevel {
//        public static final int ALL = 0;
//        public static final int ADMIN = 1;
//        public static final int IT = 2;
//    }
//
//    private static String queryServerIp() {
//        org.apache.http.client.methods.HttpGet httpGet = null;
//        org.apache.http.impl.client.CloseableHttpClient httpclient = org.apache.http.impl.client.HttpClients.createDefault();
//        org.apache.http.client.methods.CloseableHttpResponse response = null;
//        org.apache.http.HttpEntity entity = null;
//        String content = null;
//        try {
//            httpGet = new org.apache.http.client.methods.HttpGet("http://123.56.80.95/servlet/ServerIp?action=query&sign=" + serverSign);
//            response = httpclient.execute(httpGet);
//            entity = response.getEntity();
//            if (entity != null) {
//                content = trimAll(org.apache.http.util.EntityUtils.toString(entity, "GBK"));
//
//                logger.debug("server IP: " + content);
//            } else {
//                logger.error("queryServerIp: can't get response entity");
//                return null;
//            }
//            response.close();
//            httpclient.close();
//        } catch (Throwable e) {
//            logger.error("queryServerIp error", e);
//            return null;
//        }
//
//        return content;
//    }
//
//    public static boolean sendMail(int level, String subject, String msg) {
//        if (serverIp != null) {
//            return sendMailToServer(level, subject, msg);
//        }
//
//        return true;
//    }
//
//    public static boolean sendMailAsynchronous(int level, String subject, String msg) {
//        logger.debug("sendMailAsynchronous start...");
//        boolean verifyCode = false;
//        if (subject.contains("账户出现验证码")) {
//            verifyCode = true;
//            if (System.currentTimeMillis() - lastVerifyNoticeMailTime < 300000L) {
//                return true;
//            }
//            lastVerifyNoticeMailTime = System.currentTimeMillis();
//        }
//        try {
//            SendMailThread thread = new SendMailThread(verifyCode, level, subject, msg);
//            thread.start();
//            return true;
//        } catch (Throwable e) {
//            logger.error("sendMailAsynchronous error ", e);
//        }
//        return false;
//    }
//
//    private static class SendMailThread extends Thread {
//        private boolean delay = false;
//        private int level;
//        private String subject;
//        private String msg;
//
//        public SendMailThread(boolean delay, int level, String subject, String msg) {
//            this.delay = delay;
//        }
//
//        public void run() {
//            try {
//                if (delay) {
//                    Thread.sleep(120000L);
//                }
//                NoticeTool.sendMail(level, subject, msg);
//            } catch (Throwable e) {
//                NoticeTool.logger.error("sendMailAsynchronous error ", e);
//            }
//        }
//    }
//
//    public static boolean sendSMSAsynchronous(int level, String code, java.util.Map<String, String> paramMap) {
//        logger.debug("sendSMSAsynchronous start...");
//        try {
//            SendSMSThread thread = new SendSMSThread(false, level, code, paramMap);
//            thread.start();
//            return true;
//        } catch (Throwable e) {
//            logger.error("sendMailAsynchronous error ", e);
//        }
//        return false;
//    }
//
//    private static class SendSMSThread extends Thread {
//        private boolean delay = false;
//        private int level;
//        private String code;
//        private java.util.Map<String, String> paramMap;
//
//        public SendSMSThread(boolean delay, int level, String code, java.util.Map<String, String> paramMap) {
//            this.delay = delay;
//            this.code = code;
//            this.paramMap = paramMap;
//        }
//
//        @Override
//        public void run() {
//            try {
//                if (delay) {
//                    Thread.sleep(120000L);
//                }
//                NoticeTool.sendSMS(level, code, paramMap);
//            } catch (Throwable e) {
//                NoticeTool.logger.error("sendMailAsynchronous error ", e);
//            }
//        }
//    }
//
//    public static boolean sendSMS(int level, String code, java.util.Map<String, String> paramMap) {
//        if (serverIp != null) {
//            return sendSMSToServer(level, code, paramMap);
//        }
//
//        if (System.currentTimeMillis() - lastVerifyNoticeSMSTime < 300000L) {
//            return true;
//        }
//        lastVerifyNoticeSMSTime = System.currentTimeMillis();
//
//
//        return true;
//    }
//
//
//    private static String trimAll(String s) {
//        if ((s == null) || (s.trim().equals(""))) {
//            return null;
//        }
//        StringBuffer ret = new StringBuffer(100);
//        for (int i = 0; i < s.length(); i++) {
//            char c = s.charAt(i);
//            if ((c != ' ') && (c != '\n') && (c != '\r') && (c != '\t') && (c != ' ')) {
//                ret.append(c);
//            }
//        }
//        String ss = ret.toString();
//
//        return ss;
//    }
//
//    private static boolean sendMailToServer(int level, String subject, String msg) {
//        logger.debug("sendMailToServer begin");
//
//        org.apache.http.impl.client.CloseableHttpClient httpclient = null;
//        org.apache.http.client.methods.HttpPost httpPost = null;
//        org.apache.http.client.methods.CloseableHttpResponse response = null;
//        try {
//            String json = "{id:'001',pwd:'001'}";
//            String url = serverIp + "json.action";
//            httpclient = org.apache.http.impl.client.HttpClients.createDefault();
//            httpPost = new org.apache.http.client.methods.HttpPost(url);
//            java.util.List<org.apache.http.NameValuePair> valuePairs = new java.util.ArrayList();
//            org.apache.http.NameValuePair nameValuePair = new org.apache.http.message.BasicNameValuePair("json", json);
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("service", "NoticeTool");
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("method", "sendMail");
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("userName", CrmProperties.getProperty("qxbuser"));
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("level", level);
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("subject", subject);
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("msg", msg);
//            valuePairs.add(nameValuePair);
//
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("signedcode", "df3gsfd6x29zm2g");
//            valuePairs.add(nameValuePair);
//
//            org.apache.http.client.entity.UrlEncodedFormEntity formEntity = new org.apache.http.client.entity.UrlEncodedFormEntity(valuePairs, "UTF-8");
//            httpPost.setEntity(formEntity);
//            response = httpclient.execute(httpPost);
//            String content = org.apache.http.util.EntityUtils.toString(response.getEntity(), "GBK");
//            if (content.equals("")) {
//                logger.info("content is '',sendMail OK");
//            }
//
//            return true;
//        } catch (Throwable e) {
//            logger.error("sendMailToServer httpclient request error", e);
//            return false;
//        } finally {
//            try {
//                response.close();
//                httpclient.close();
//            } catch (Throwable e) {
//                logger.error("sendMailToServer close httpclient error", e);
//            }
//        }
//    }
//
//    private static boolean sendSMSToServer(int level, String code, java.util.Map<String, String> paramMap) {
//        logger.debug("sendSMSToServer begin");
//
//        org.apache.http.impl.client.CloseableHttpClient httpclient = null;
//        org.apache.http.client.methods.HttpPost httpPost = null;
//        org.apache.http.client.methods.CloseableHttpResponse response = null;
//        try {
//            String json = "{id:'001',pwd:'001'}";
//            String url = serverIp + "json.action";
//            httpclient = org.apache.http.impl.client.HttpClients.createDefault();
//            httpPost = new org.apache.http.client.methods.HttpPost(url);
//            java.util.List<org.apache.http.NameValuePair> valuePairs = new java.util.ArrayList();
//            org.apache.http.NameValuePair nameValuePair = new org.apache.http.message.BasicNameValuePair("json", json);
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("service", "NoticeTool");
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("method", "sendSMS");
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("userName", CrmProperties.getProperty("qxbuser"));
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("level", level);
//            valuePairs.add(nameValuePair);
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("code", code);
//            valuePairs.add(nameValuePair);
//            String msg = "";
//            for (java.util.Map.Entry<String, String> entry : paramMap.entrySet()) {
//                msg = msg + (String) entry.getKey() + "=" + (String) entry.getValue() + "@@";
//            }
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("msg", msg);
//            valuePairs.add(nameValuePair);
//
//            nameValuePair = new org.apache.http.message.BasicNameValuePair("signedcode", "df3gsfd6x29zm2g");
//            valuePairs.add(nameValuePair);
//
//            org.apache.http.client.entity.UrlEncodedFormEntity formEntity = new org.apache.http.client.entity.UrlEncodedFormEntity(valuePairs, "UTF-8");
//            httpPost.setEntity(formEntity);
//            response = httpclient.execute(httpPost);
//            String content = org.apache.http.util.EntityUtils.toString(response.getEntity(), "GBK");
//            if (content.equals("")) {
//                logger.info("content is '',sendSMSToServer OK");
//            }
//
//            return true;
//        } catch (Throwable e) {
//            logger.error("sendSMSToServer httpclient request error", e);
//            return false;
//        } finally {
//            try {
//                response.close();
//                httpclient.close();
//            } catch (Throwable e) {
//                logger.error("sendSMSToServer close httpclient error", e);
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//    }
//}
