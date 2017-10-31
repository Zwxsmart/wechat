package com.lanwantec.wechat.utils;

import com.lanwantec.entity.req.SnsUserInfo;
import com.lanwantec.entity.resp.AccessToken;
import com.lanwantec.entity.resp.GetUser;
import com.lanwantec.entity.resp.TextMessage;
import com.lanwantec.entity.resp.WeixinOauth2Token;
import com.lanwantec.menu.Button;
import com.lanwantec.menu.Matchrule;
import com.lanwantec.menu.Menu;
import com.lanwantec.menu.ViewButton;
import com.lanwantec.wechat.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;

@SuppressWarnings("deprecation")
public class WeChatUtil {

    private static Logger log = Logger.getLogger(WeChatUtil.class);

    private static final String CREAT_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private static final String DEL_MENU_URL="https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    private static final String ACCESS_TOKEN_URL="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    public static final String AUTH2 = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String SNS_USERINFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID";

    private static final String GET_ALLUSEROPENID_URL="https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";

    private static final String UPLOADIMAGE_URL="https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";

    private static final String JSSDK_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

    private static final String GET_USER_INFO="https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

    private static final String CREATE_QRCODE_URL = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN";

    private static final String SEND_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";

    private static final String CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";

    private static final String MOVE_USER_TO_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

    private static final String CREATE_MENU_NB = "https://api.weixin.qq.com/cgi-bin/menu/addconditional?access_token=ACCESS_TOKEN";

    private static final String DEL_MENU_NB = "https://api.weixin.qq.com/cgi-bin/menu/delconditional?access_token=ACCESS_TOKEN";

    public static JSONObject moveUserToGroup(String access_token,int groupId,String openId) {
        String url = MOVE_USER_TO_GROUP.replace("ACCESS_TOKEN", access_token);
        String json = "{\"openid\":\""+openId+"\",\"to_groupid\":"+groupId+"}";
        return doPostStr(url,json);
    }

    public static JSONObject createGroup(String access_token,String name) {
        if (name.length()>30){
            return null;
        }
        String url = CREATE_GROUP.replace("ACCESS_TOKEN", access_token);
        String json = "{\"group\":{\"name\":\""+name+"\"}}";
        return doPostStr(url,json);
    }

    public static JSONObject getUserInfo(String access_token, String openid) {
        String url = GET_USER_INFO.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        return doGetStr(url);
    }


    public static String sendTextMSG(String fromUser,String toUser,String respContent){
        TextMessage textMessage = new TextMessage();
        textMessage.setToUserName(fromUser);
        textMessage.setFromUserName(toUser);
        textMessage.setCreateTime(new Date().getTime());
        textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
        textMessage.setFuncFlag(0);
        textMessage.setContent(respContent);
        return MessageUtil.textMessageToXml(textMessage);
    }

    public static String getTicket(String scene_str,String token) {
        String url = CREATE_QRCODE_URL.replace("TOKEN", token);
        String json = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \""+scene_str+"\"}}}";
        JSONObject obj = doPostStr(url, json);
        if(obj != null){
            String ticket = obj.getString("ticket");
            log.info("获取ticket成功："+ticket);
            return ticket;
        }else {
            log.error("获取ticket失败");
            return null;
        }
    }

    public static void sendMSG(String msg,String openid,String token) {
        try {
            String url = SEND_MSG_URL.replace("ACCESS_TOKEN", token);
            String json = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+msg+"\"}}";
            doPostStr(url, json);
        } catch (Exception e) {
            log.error("客服发送信息失败："+e);
        }
    }

    public static String upload(String filePath, String accessToken) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            log.error("文件不存在!");
            throw new IOException("文件不存在!");
        }

        String url = UPLOADIMAGE_URL.replace("ACCESS_TOKEN", accessToken);

        URL urlObj = new URL(url);

        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        OutputStream out = new DataOutputStream(con.getOutputStream());
        out.write(head);


        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        String typeName = "media_id";
        log.info(jsonObj);
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }




    /**
     * 调用微信JS接口的临时票据
     *
     * @param access_token 接口访问凭证
     * @return
     */
    public static String getJsApiTicket(String access_token) {

        String url = JSSDK_URL.replace("ACCESS_TOKEN", access_token);
        // 发起GET请求获取凭证
        JSONObject jsonObject = doGetStr(url);
        String ticket = null;
        if (null != jsonObject) {
            try {
                ticket = jsonObject.getString("ticket");
            } catch (Exception e) {
                log.error("获取token失败 errcode:"+jsonObject.getInt("errcode")+" errmsg:"+jsonObject.getString("errmsg"));
            }
        }
        return ticket;
    }

    @SuppressWarnings("static-access")
    public static GetUser getUser(String token, String nextopenid) {
        GetUser user = new GetUser();
        String url = GET_ALLUSEROPENID_URL.replace("ACCESS_TOKEN", token).replace("NEXT_OPENID", nextopenid);
        JSONObject jsonObject = doGetStr(url);
        JSONObject object = new JSONObject();
        if(jsonObject != null){
            user.setTotal(jsonObject.getString("total"));
            user.setCount(jsonObject.getString("count"));
            user.setNext_openid(jsonObject.getString("next_openid"));
            String data = jsonObject.getString("data");
            System.out.println(data);
            object=object.fromObject(data);
            user.setData(object.getString("openid"));
        }
        return user;
    }


    /**
     *  get方式
     * @param url
     * @return
     */
    @SuppressWarnings("resource")
    public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity,"UTF-8");
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (Exception e) {
            log.error("发送GET请求失败,URL：" + url);
            log.error("发送GET请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post方式
     * @param url
     * @param outStr
     * @return
     */
    @SuppressWarnings("resource")
    public static JSONObject doPostStr(String url,String outStr) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost post = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            post.setEntity(new StringEntity(outStr, "UTF-8"));
            HttpResponse response = httpClient.execute(post);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (Exception e) {
            log.error("发送POST请求失败,URL：" + url);
            log.error("发送POST请求失败,Excption：" + e);
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(String appId, String appsecret) {
        AccessToken token = new AccessToken();
        String url = ACCESS_TOKEN_URL.replace("APPID", appId).replace("APPSECRET", appsecret);
        JSONObject jsonObject = doGetStr(url);
        if(jsonObject != null){
            token.setAccessToken(jsonObject.getString("access_token"));
        }else{
            log.info("获取access_token失败");
        }
        return token;
    }




    public static WeixinOauth2Token getOauth2AccessToken(String code, String appid, String appsecret){
        String url = AUTH2.replace("APPID", appid).replace("SECRET", appsecret).replace("CODE", code);
        System.out.println(url);
        JSONObject jsonObject = WeChatUtil.doGetStr(url);
        System.out.println(jsonObject.toString());
        WeixinOauth2Token wat = null;
        if (null != jsonObject) {
            try {
                wat = new WeixinOauth2Token();
                wat.setAccessToken(jsonObject.getString("access_token"));
                wat.setExpiresIn(jsonObject.getInt("expires_in"));
                wat.setRefreshToken(jsonObject.getString("refresh_token"));
                wat.setOpenId(jsonObject.getString("openid"));
                wat.setScope(jsonObject.getString("scope"));
            } catch (Exception e) {
                wat = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取网页授权凭证失败 errcode:"+errorCode+" errmsg:"+errorMsg);
                log.error("Exception:" + e);
            }
        }
        return wat;
    }

    @SuppressWarnings("unchecked")
    public static SnsUserInfo getSnsUserInfo(String accessToken, String openId) {
        SnsUserInfo snsUserInfo = null;
        // 拼接请求地址
        String url = SNS_USERINFO_URL.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        System.out.println(url);
        // 通过网页授权获取用户信息
        JSONObject jsonObject = WeChatUtil.doGetStr(url);

        if (null != jsonObject) {
            try {
                snsUserInfo = new SnsUserInfo();
                // 用户的标识
                snsUserInfo.setOpenId(jsonObject.getString("openid"));
                // 昵称
                snsUserInfo.setNickname(jsonObject.getString("nickname"));
                // 性别（1是男性，2是女性，0是未知）
                snsUserInfo.setSex(jsonObject.getInt("sex"));
                // 用户所在国家
                snsUserInfo.setCountry(jsonObject.getString("country"));
                // 用户所在省份
                snsUserInfo.setProvince(jsonObject.getString("province"));
                // 用户所在城市
                snsUserInfo.setCity(jsonObject.getString("city"));
                // 用户头像
                snsUserInfo.setHeadImgUrl(jsonObject.getString("headimgurl"));
                // 用户特权信息
                snsUserInfo.setPrivilegeList(JSONArray.toList(jsonObject.getJSONArray("privilege"), List.class));
            } catch (Exception e) {
                snsUserInfo = null;
                int errorCode = jsonObject.getInt("errcode");
                String errorMsg = jsonObject.getString("errmsg");
                log.error("获取用户信息失败 errcode:"+errorCode+"  errmsg:"+errorMsg);
            }
        }
        return snsUserInfo;
    }


    public static Menu initMenu() throws Exception {

        Menu menu = new Menu();

        Button index = new Button();
        ViewButton product = new ViewButton();
        product.setName("产品");
        product.setType("view");
        product.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,news#wechat_redirect");

        index.setName("首页");
        index.setSub_button(new Button[]{product,brand,store,special});

        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/packing&response_type=code&scope=snsapi_userinfo&state=P00210118,order#wechat_redirect");

        ViewButton detailed = new ViewButton();
        detailed.setName("预购清单");
        detailed.setType("view");
        detailed.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,listing#wechat_redirect");

        ViewButton qrCode = new ViewButton();
        qrCode.setName("我的二维码");
        qrCode.setType("view");
        qrCode.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/packing&response_type=code&scope=snsapi_userinfo&state=P00210118,qrCode#wechat_redirect");

        my.setName("我的");
        my.setSub_button(new Button[]{qrCode,detailed,viewOrder});

        Button together = new Button();
        ViewButton agents = new ViewButton();
        agents.setName("我要代理");
        agents.setType("view");
        agents.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/packing&response_type=code&scope=snsapi_userinfo&state=P00210118,agents#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("http://huahui.jiajushouye.com/public.html");

        together.setName("合作");
        together.setSub_button(new Button[]{subscription,agents});
        menu.setButton(new Button[]{index,my,together});
/*
        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("101");
        menu.setMatchrule(matchrule);*/
        return menu;
    }

    public static Menu DianYuanMenu() throws Exception {

        Menu menu = new Menu();

        Button index = new Button();
        ViewButton product = new ViewButton();
        product.setName("产品");
        product.setType("view");
        product.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,news#wechat_redirect");

        index.setName("首页");
        index.setSub_button(new Button[]{product,brand,store,special});

        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/packing&response_type=code&scope=snsapi_userinfo&state=P00210118,userOrder#wechat_redirect");

        ViewButton guide = new ViewButton();
        guide.setName("产品导购");
        guide.setType("view");
        guide.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/autho&response_type=code&scope=snsapi_userinfo&state=P00210118,guide#wechat_redirect");

        my.setName("我的");
        my.setSub_button(new Button[]{viewOrder});

        Button together = new Button();
        ViewButton scan = new ViewButton();
        scan.setName("扫一扫");
        scan.setType("view");
        scan.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ Constants.APPID_ANYUN+"&redirect_uri=http://moyu.jiajushouye.com/packing&response_type=code&scope=snsapi_userinfo&state=P00210118,scan#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("http://anyun.jiajushouye.com/public.html");

        together.setName("合作");
        together.setSub_button(new Button[]{subscription,scan});
        menu.setButton(new Button[]{index,my,together});

        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("101");
        menu.setMatchrule(matchrule);
        return menu;
    }


    public static int creatMenu(String token,String menu){
        int result = 0;
        String url = CREAT_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static int delMenu(String token,String menu){
        int result = 0;
        String url = DEL_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static int creatMenuNB(String token,String menu){
        int result = 0;
        String url = CREATE_MENU_NB.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }




    public static int delMenuNB(String token,String menu){
        int result = 0;
        String url = DEL_MENU_NB.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }



}
