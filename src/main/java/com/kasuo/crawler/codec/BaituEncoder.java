package com.kasuo.crawler.codec;

import java.util.HashMap;
import java.util.Map;
//import com.kasuo.crawler.service.BaituService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BaituEncoder {
    private static final Logger logger = LoggerFactory.getLogger(BaituEncoder.class);
    public Map<String, String> encodeMap = new HashMap();
    public Map<String, String> decodeMap = new HashMap();
    public Map<String, String> encodeMap2 = new HashMap();
    public Map<String, String> decodeMap2 = new HashMap();

    private static final String regNumCodeVersion = "2";

    @Autowired
//    private BaituService service;

    public void getInstance() {
        if (encodeMap2.size() == 0) {
//            service.queryBaituEncode(this);
//            service.queryBaituEncode2(this);
        }
    }

    public String getEncode(String regNum)
    {
        try
        {
            if ((regNumCodeVersion != null) && (regNumCodeVersion.equals("2"))) {
                return getEncodeV2(regNum);
            }
            if ((regNum == null) || (regNum.trim().length() == 0)) {
                return null;
            }
            int regNumLen = regNum.length();
            if (regNum.equals("1")) {
                return "2A066603686A6ACC";
            }
            if (regNum.equals("3")) {
                return "AB066604686A6ACD";
            }
            char spec = getSpec(regNum);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < regNumLen; i++)
            {
                String c = regNum.substring(i, i + 1);
                String key = null;
                if (i == regNumLen - 1) {
                    key = patch2pad(regNumLen) + patch2pad(regNumLen - i) + "0" + c;
                } else {
                    key = patch2pad(regNumLen) + patch2pad(regNumLen - i) + spec + c;
                }
                String encode = (String)this.encodeMap.get(key);
                if (encode != null)
                {
                    sb.append(encode);
                }
                else
                {
                    String msg = "baitu encode MAP NOT FOUND value<br>regNum=" + regNum + ",key=" + key;
                    logger.error(msg);
                    return null;
                }
            }
            if (regNumLen == 2)
            {
                if (spec == '0') {
                    return sb.toString() + "686A6ACD";
                }
                if (spec == '1') {
                    return sb.toString() + "686A6ACC";
                }
                return null;
            }
            if (regNumLen == 3)
            {
                if (spec == '0') {
                    return sb.toString() + "6ACD";
                }
                if (spec == '1') {
                    return sb.toString() + "6ACC";
                }
                return null;
            }
            return sb.toString();
        }
        catch (Throwable e)
        {
            logger.error("getEncode error! regNum=" + regNum, e);
        }
        return null;
    }

    private String getEncodeV2(String regNum)
    {
        try
        {
            if ((regNum == null) || (regNum.trim().length() == 0)) {
                return null;
            }
            int regNumLen = regNum.length();
            if (regNumLen < 3)
            {
                logger.warn("getEncodeV2 NOT support 1~2���regNum���������regNum=" + regNum);
                return null;
            }
            if (regNumLen % 2 == 1)
            {
                regNum = regNum + "$";
                regNumLen++;
            }
            StringBuilder sb = new StringBuilder();
            String remain = null;
            for (int i = 0; i < regNumLen; i++)
            {
                String c = regNum.substring(i, i + 1);
                String key = patch2pad(i + 1) + c;
                String encode = (String)this.encodeMap2.get(key);
                if (encode == null)
                {
                    String msg = "baitu encode2 MAP NOT FOUND value<br>regNum=" + regNum + ",key=" + key;
                    logger.error(msg);
                    return null;
                }
                if (i % 2 == 0)
                {
                    remain = new String(encode);
                }
                else
                {
                    sb.append(encode);
                    sb.append(remain);
                }
            }
            return sb.toString();
        }
        catch (Throwable e)
        {
            logger.error("getEncodeV2 error! regNum=" + regNum, e);
        }
        return null;
    }

    public String getRegNumV2(String tmID)
    {
        try
        {
            if ((tmID == null) || (tmID.trim().length() == 0)) {
                return null;
            }
            int regNumLen = tmID.length() / 4;
            StringBuilder sb = new StringBuilder();
            String remain = null;
            for (int i = 0; i < regNumLen; i++)
            {
                String c = tmID.substring(i * 4, i * 4 + 4);
                String key = null;
                if (i % 2 == 0) {
                    key = patch2pad(i + 2) + c;
                } else {
                    key = patch2pad(i) + c;
                }
                String code = (String)this.decodeMap2.get(key);
                if (code == null)
                {
                    String msg = "baitu decode2 MAP NOT FOUND value<br>tmID=" + tmID + ",key=" + key;

                    return null;
                }
                if (i % 2 == 0)
                {
                    remain = new String(code);
                }
                else
                {
                    if (!code.equals("$")) {
                        sb.append(code);
                    }
                    if (!remain.equals("$")) {
                        sb.append(remain);
                    }
                }
            }
            return sb.toString();
        }
        catch (Throwable e)
        {
            logger.error("getRegNumV2 error! tmID=" + tmID, e);
        }
        return null;
    }

    private String patch2pad(int i)
    {
        String s = String.valueOf(i);
        if (s.length() == 1) {
            s = "0" + s;
        }
        return s;
    }

    public String getRegNum(String tmID)
    {
        tmID = tmID.toUpperCase();
        if ((regNumCodeVersion != null) && (regNumCodeVersion.equals("2"))) {
            return getRegNumV2(tmID);
        }
        return getRegNum(tmID, true);
    }

    public String getRegNum(String tmID, boolean isAll)
    {
        try
        {
            if ((tmID == null) || (tmID.trim().length() == 0)) {
                return null;
            }
            int regNumLen = tmID.length() / 4;
            if (tmID.length() == 16)
            {
                if (tmID.equals("2A066603686A6ACC")) {
                    return "1";
                }
                if (tmID.equals("AB066604686A6ACD")) {
                    return "3";
                }
            }
            if (tmID.length() == 16)
            {
                if ((tmID.endsWith("686A6ACC")) || (tmID.endsWith("686A6ACD"))) {
                    return getRegNum(tmID.substring(0, 8), isAll);
                }
                if ((tmID.endsWith("6ACC")) || (tmID.endsWith("6ACD"))) {
                    return getRegNum(tmID.substring(0, 12), isAll);
                }
            }
            char spec = '\000';

            String encode = tmID.substring(tmID.length() - 4);

            String key = patch2pad(regNumLen) + "010" + encode;
            String code = null;
            String lastCode = (String)this.decodeMap.get(key);
            if (lastCode == null)
            {
                String msg = "baitu decode MAP NOT FOUND lastCode value<br>tmID=" + tmID + ",key=" + key;
                logger.error(msg);
                return null;
            }
            spec = getSpec(lastCode.charAt(0), tmID);

            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < regNumLen - 1; i++)
            {
                String c = tmID.substring(i * 4, i * 4 + 4);

                key = patch2pad(regNumLen) + patch2pad(regNumLen - i) + spec + c;

                code = (String)this.decodeMap.get(key);
                if (code != null)
                {
                    sb.append(code);
                }
                else
                {
                    if (isAll)
                    {
                        String msg = "baitu decode MAP NOT FOUND value<br>tmID=" + tmID + ",key=" + key;
                        logger.error(msg);
                        return null;
                    }
                    sb.append("?");
                }
            }
            return sb.toString() + lastCode;
        }
        catch (Throwable e)
        {
            logger.error("getRegNum error! tmID=" + tmID, e);
        }
        return null;
    }

    public static char getSpec(String regNum)
    {
        char lastCode = regNum.charAt(regNum.length() - 1);
        return getSpec(lastCode, regNum);
    }

    public static char getSpec(char lastCode, String msg)
    {
        char spec = '\000';
        if ((lastCode >= '2') && (lastCode <= '9')) {
            spec = '0';
        } else if ((lastCode == '0') || (lastCode == '1')) {
            spec = '1';
        } else {
            return lastCode;
        }
        return spec;
    }

}
