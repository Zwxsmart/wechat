package com.lanwantec.wechat.service;


import com.lanwantec.wechat.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class WeChatServiceImpl implements WeChatService {

    private static Logger log = Logger.getLogger(WeChatServiceImpl.class);

    //接收text类型
    @Override
    public String typeText(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/text?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收image类型
    @Override
    public String typeImage(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/image?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收location类型
    @Override
    public String typeLocation(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/location?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收link类型
    @Override
    public String typeLink(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/link?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收video类型
    @Override
    public String typeVideo(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/video?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收shortVideo类型
    @Override
    public String typeShortVideo(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/short/video?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收voice类型
    @Override
    public String typeVoice(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/voice?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收event类型,订阅
    @Override
    public String typeSubscribe(String wechatType, String event, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/subscribe?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName+ "&event=" + event;
        System.out.println(url);

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }


    //接收event类型,取消订阅
    @Override
    public String typeUNSubscribe(String wechatType, String event, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/unsubscribe?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收event类型,点击按钮
    @Override
    public String typeClick(String wechatType, String event, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/click?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    //接收scan类型
    @Override
    public String typeScan(String wechatType, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/scan?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public String typeScancode_Waitmsg(String wechatType,String event,String content, String fromUserName, String toUserName) {
        String url = Constants.LOCAL_SERVICE_URL + "/scan_waiting?wechatType=" + wechatType + "&fromUserName=" + fromUserName + "&toUserName=" + toUserName+ "&eventKey=" + event + "&scanResult=" + content;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        String result = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                result = EntityUtils.toString(entity,"UTF-8");
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return result;
    }
}
