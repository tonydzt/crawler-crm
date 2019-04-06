package com.kasuo.crawler.util;

import com.kasuo.crawler.common.HttpResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;

/**
 * @author dzt
 */
public class HttpUtils {

    private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static final String POST = "POST";

    public static final String GET = "GET";

    public static final String OPTIONS = "OPTIONS";

    public static final String UTF8 = "UTF-8";

    public static final int TIMEOUT = 30000;

    public static HttpResult httpGet(String url, Map<String,String> params) {
        url = getUrlWithQueryString(url, params);
        return httpGet(url);
    }

    public static HttpResult httpGet(String url) {
        return getResponse(url, GET, UTF8, null, null, TIMEOUT);
    }

    public static HttpResult httpPost(String url, Map<String, Object> params) {
        String body = JsonUtils.jsonEncode(params);
        return getResponse(url, POST, UTF8, body, null, TIMEOUT);
    }

    public static HttpResult httpPost(String url, String body) {
        return getResponse(url, POST, UTF8, body, null, TIMEOUT);
    }

    public static HttpResult httpPost(String url, String body, int timeout) {
        return getResponse(url, POST, UTF8, body, null, timeout);
    }

    public static HttpResult getResponse(String url, String method, String encoding, String body, Map<String, String> headers, int timeout) {
        OutputStream out = null;
        InputStream in = null;
        try {
            HttpURLConnection conn = getConnection(url);
            conn.setRequestProperty("Content-Type", "application/json");

            if (headers != null && !headers.isEmpty()) {
                for (Map.Entry<String,String> header : headers.entrySet()) {
                    conn.setRequestProperty(header.getKey(), header.getValue());
                }
            }

            if (!GET.equals(method)) {
                conn.setDoOutput(true);
            }
            conn.setDoInput(true);
            conn.setRequestMethod(method);
            conn.setUseCaches(false);
            conn.setInstanceFollowRedirects(true);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            conn.connect();
            if (!GET.equals(method)) {
                if (body != null && !"".equals(body)) {
                    out = conn.getOutputStream();
                    out.write(body.getBytes());
                    out.flush();
                }
            }
            HttpResult httpResult = new HttpResult();
            httpResult.setResponseCode(conn.getResponseCode());
            httpResult.setResponseMessage(conn.getResponseMessage());
            if (HttpURLConnection.HTTP_OK == httpResult.getResponseCode()) {
                in = conn.getInputStream();
                String result = read(in, encoding);
                httpResult.setResult(result);
            } else {
                in = conn.getErrorStream();
                String result = read(in, encoding);
                httpResult.setResult(result);
                logger.info("请求不成功, url:{}, responsecode:{}, result:{}", url, String.valueOf(httpResult.getResponseCode()), result);
            }
            return httpResult;
        } catch (MalformedURLException e) {
            logger.error("请求地址有误, url:" + url, e);
            return null;
        } catch (Exception e) {
            logger.error("IO错误,url:" + url + ",body:" + body, e);
            return null;
        } finally {
            close(in);
            close(out);
        }
    }

    private static String getUrlWithQueryString(String url, Map<String, String> params) {
        if (params == null) {
            return url;
        }

        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }

        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }

        return builder.toString();
    }

    private static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static void close(InputStream in) {
        if (null != in) {
            try {
                in.close();
            } catch (Exception e) {
                logger.error("InputStream close error", e);
            }
        }
    }

    private static void close(OutputStream out) {
        if (null != out) {
            try {
                out.close();
            } catch (Exception e) {
                logger.error("OutputStream close error", e);
            }

        }
    }

    private static HttpURLConnection getConnection(String path) throws Exception {
        boolean isHttps = path.startsWith("https");

        URL url = new URL(path);

        if (isHttps) {
            SSLContext sslcontext = SSLContext.getInstance("SSL", "SunJSSE");//第一个参数为协议,第二个参数为提供者(可以缺省)
            TrustManager[] tm = {new MyX509TrustManager()};
            sslcontext.init(null, tm, new SecureRandom());
            HostnameVerifier ignoreHostnameVerifier = new HostnameVerifier() {
                @Override
                public boolean verify(String s, SSLSession sslsession) {
                    System.out.println("WARNING: Hostname is not matched for cert.");
                    return true;
                }
            };
            HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
            HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
        }

        return (HttpURLConnection) url.openConnection();
    }

    private static String read(InputStream in, String encoding) throws Exception {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while (null != (line = reader.readLine())) {
                builder.append(line);
            }
            return builder.toString();
        }  finally {
            close(in);
        }
    }

    private static class MyX509TrustManager implements X509TrustManager{

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            // TODO Auto-generated method stub
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            // TODO Auto-generated method stub
            return null;
        }

    }

}
