package com.kasuo.crawler.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTool {
    private static final Logger logger = LoggerFactory.getLogger(DateTool.class);


    public DateTool() {
    }

    public static String getStringDate(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error!", e);
        }
        return null;
    }

    public static String getStringDate() {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error!", e);
        }
        return null;
    }

    public static Date getDate(String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! s=" + d, e);
        }
        return null;
    }

    public static Date getTightDate(String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.parse(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! s=" + d, e);
        }
        return null;
    }

    public static String getStringDateTime(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! s=" + d, e);
        }
        return null;
    }

    public static String getStringDateTime(long d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(new Date(d));
        } catch (Throwable e) {
            logger.error("DateTool format date error! s=" + d, e);
        }
        return null;
    }

    public static String getStringDateTime() {
        try {
            Date d = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! ", e);
        }
        return null;
    }

    public static String getTightStringDate() {
        Date d = new Date();
        return getTightStringDate(d);
    }


    public static String getTightStringDateTime() {
        Date d = new Date();
        return getTightStringDateTime(d);
    }

    public static String getTightStringDate(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! ", e);
        }
        return null;
    }

    public static String getTightStringDateTime(Date d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf.format(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! ", e);
        }
        return null;
    }

    public static Date getDateTime(String d) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(d);
        } catch (Throwable e) {
            logger.error("DateTool format date error! s=" + d, e);
        }
        return null;
    }

    public static String secToTime(long time) {
        String timeStr = null;
        long hour = 0L;
        long minute = 0L;
        long second = 0L;
        if (time <= 0L) {
            return "00:00:00";
        }
        minute = time / 60L;
        if (minute < 60L) {
            second = time % 60L;
            timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
        } else {
            hour = minute / 60L;
            minute %= 60L;
            second = time - hour * 3600L - minute * 60L;
            timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        }

        return timeStr;
    }

    public static String unitFormat(long i) {
        String retStr = null;
        if ((i >= 0L) && (i < 10L)) {
            retStr = "0" + Long.toString(i);
        } else {
            retStr = String.valueOf(i);
        }
        return retStr;
    }

    public static String getStringUTCTime() {
        Calendar cal = Calendar.getInstance();

        int zoneOffset = cal.get(15);

        int dstOffset = cal.get(16);

        cal.add(14, -(zoneOffset + dstOffset));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(cal.getTime());
        } catch (Throwable e) {
            logger.error("DateTool format date error! ", e);
        }
        return null;
    }

    public static String getStringUTCDate() {
        Calendar cal = Calendar.getInstance();

        int zoneOffset = cal.get(15);

        int dstOffset = cal.get(16);

        cal.add(14, -(zoneOffset + dstOffset));
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        } catch (Throwable e) {
            logger.error("DateTool format date error! ", e);
        }
        return null;
    }

    public static String getStringUTCDate(String localTime) {
        Calendar cal = Calendar.getInstance();

        int zoneOffset = cal.get(15);

        int dstOffset = cal.get(16);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            cal.setTime(sdf.parse(localTime));

            cal.add(14, -(zoneOffset + dstOffset));
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        } catch (Throwable e) {
        }
        return null;
    }

    public static String getStringUTCDate(Date localTime) {
        Calendar cal = Calendar.getInstance();

        int zoneOffset = cal.get(15);

        int dstOffset = cal.get(16);
        try {
            cal.setTime(localTime);

            cal.add(14, -(zoneOffset + dstOffset));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(cal.getTime());
        } catch (Throwable e) {
        }
        return null;
    }

    public static boolean dateAfter(Date d1, Date d2) {
        if ((d1 == null) || (d2 == null)) {
            logger.warn("d1=" + d1 + ",d2=" + d2);
            return false;
        }
        d1 = getDayStart(d1);
        d2 = getDayStart(d2);
        return d1.after(d2);
    }

    public static Date getDayStart(Date d) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(11, 0);
        now.set(12, 0);
        now.set(13, 0);
        now.set(14, 0);

        return now.getTime();
    }


    public static boolean dateBeforeEquals(Date d1, Date d2)
    {
        if ((d1 == null) || (d2 == null))
        {
            logger.warn("d1=" + d1 + ",d2=" + d2);
            return false;
        }
        d1 = getDayStart(d1);
        d2 = getDayStart(d2);
        return (d1.before(d2)) || (d1.equals(d2));
    }

    public static boolean dateBefore(Date d1, Date d2)
    {
        if ((d1 == null) || (d2 == null))
        {
            logger.warn("d1=" + d1 + ",d2=" + d2);
            return false;
        }
        d1 = getDayStart(d1);
        d2 = getDayStart(d2);
        return d1.before(d2);
    }

    public static Date addDate(int days)
    {
        return addDate(new Date(), days);
    }

    public static Date addDate(Date d, int days)
    {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(6, days);
        return cal.getTime();
    }
}
