package com.lanwantec.wechat.utils;

import java.util.UUID;
import java.util.Map;
import java.util.HashMap;
import java.util.Formatter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.io.UnsupportedEncodingException;  

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import net.sf.json.JSONObject;

public class Sign {
	private static Logger log = Logger.getLogger(Sign.class);
    public static String appId="";
    public static String timestamp="";
    public static String nonceStr="";
    public static String signature="";

    public static String getParam(HttpServletRequest request,String jsapi_ticket,String appid){
        String url = getUrl(request);
        Map<String, String> params = sign(jsapi_ticket, url);
        params.put("appid", appid);
        JSONObject jsonObject = JSONObject.fromObject(params);  
        Sign.appId=(String)jsonObject.get("appid");
        Sign.timestamp=(String)jsonObject.get("timestamp");
        Sign.nonceStr=(String)jsonObject.get("nonceStr");
        Sign.signature=(String)jsonObject.get("signature");
        String jsonStr = jsonObject.toString();
        return jsonStr;
    }
    
    
    public static String getUrl(HttpServletRequest request){
    	return request.getHeader("referer"); 
    }
    

    public static Map<String, String> sign(String jsapi_ticket, String url) {
        Map<String, String> ret = new HashMap<String, String>();
        String nonce_str = create_nonce_str();
        String timestamp = create_timestamp();
        String str;
        String signature = "";
        //注意这里参数名必须全部小写，且必须有序
        str = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + nonce_str +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
 
        try
        {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        }
        catch (NoSuchAlgorithmException e)
        {
        	log.error(e);
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
        	log.error(e);
            e.printStackTrace();
        }
 
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("nonceStr", nonce_str);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
 
        return ret;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash)
        {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }

    private static String create_nonce_str() {
        return UUID.randomUUID().toString();
    }

    private static String create_timestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
}
