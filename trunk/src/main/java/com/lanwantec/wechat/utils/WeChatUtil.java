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


/**
 * 此类所有功能具体内容请参考官方文档
 * 官网链接@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1445241432
 */

/**
 * 分组管理微信可能已经改版成为用户标签管理
 * 如微信分组不能使用请参阅@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837
 */
@SuppressWarnings("deprecation")
public class WeChatUtil {

    private static Logger log = Logger.getLogger(WeChatUtil.class);

    private static final String PUSH_TEMPLATE_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    private static final String GET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=ACCESS_TOKEN";

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

    /**
     * 推送模板消息
     * @param access_token
     * @param jsonData
     * @return
     */
    public static JSONObject pushMessage(String access_token,String jsonData) {
        String url = PUSH_TEMPLATE_MESSAGE_URL.replace("ACCESS_TOKEN", access_token);
        return doPostStr(url,jsonData);
    }

    /**
     * 移动用户到某个分组
     * @param access_token
     * @param groupId
     * @param openId
     * @return
     */
    public static JSONObject moveUserToGroup(String access_token,int groupId,String openId) {
        String url = MOVE_USER_TO_GROUP.replace("ACCESS_TOKEN", access_token);
        String json = "{\"openid\":\""+openId+"\",\"to_groupid\":"+groupId+"}";
        return doPostStr(url,json);
    }

    /**
     * 创建用户分组
     * 分组id默认从100开始,生成自定义菜单可根据分组id让用户看到不同的菜单
     * @param access_token
     * @param name
     * @return
     */
    public static JSONObject createGroup(String access_token,String name) {
        if (name.length()>30){
            return null;
        }
        String url = CREATE_GROUP.replace("ACCESS_TOKEN", access_token);
        String json = "{\"group\":{\"name\":\""+name+"\"}}";
        return doPostStr(url,json);
    }

    /**
     * 获取用户信息
     * 具体请参阅@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140839
     * @param access_token
     * @param openid
     * @return
     */
    public static JSONObject getUserInfo(String access_token, String openid) {
        String url = GET_USER_INFO.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
        return doGetStr(url);
    }


    /**
     * 模板消息相关
     * 具体请参阅@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1433751277
     * @param access_token
     * @return
     */
    public static JSONObject GetIindustry(String access_token) {
        String url = GET_INDUSTRY_URL.replace("ACCESS_TOKEN", access_token);
        return doGetStr(url);
    }

    /**
     * 发送文本消息工具
     * @param fromUser
     * @param toUser
     * @param respContent
     * @return
     */
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

    /**
     * 生成带参数的二维码
     * 具体请参阅@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443433542
     * @param scene_str 场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
     * @param token
     * @return
     */
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

    /**
     * 发送客服消息
     * 具体请参阅@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547
     * @param msg  要发送的信息
     * @param openid  //接收人的OPENID
     * @param token
     */
    public static void sendMSG(String msg,String openid,String token) {
        try {
            String url = SEND_MSG_URL.replace("ACCESS_TOKEN", token);
            String json = "{\"touser\":\""+openid+"\",\"msgtype\":\"text\",\"text\":{\"content\":\""+msg+"\"}}";
            doPostStr(url, json);
        } catch (Exception e) {
            log.error("客服发送信息失败："+e);
        }
    }

    /**
     * 上传资源
     * @param filePath
     * @param accessToken
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
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
     * 可根据这个接口可生成微信JSAPI相关的东西
     * 具体请参考@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421141115
     * 支付相关请参考@https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=7_1
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
     * 具体请参考@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140183
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


    /**
     * 微信授权换取token,openId
     * 具体请参考@https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140842
     * @param code
     * @param appid
     * @param appsecret
     * @return
     */
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

    /**
     * 获取用户信息
     * @param accessToken
     * @param openId
     * @return
     */
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

//redirect_uri

    /**
     *
     * @param appId
     * @param redirect_uri
     * @param brandNo
     * @return
     * @throws Exception
     */
    public static Menu initOuthMenu(String appId,String redirect_uri,String brandNo) throws Exception {

        Menu menu = new Menu();

        ViewButton index = new ViewButton();
        index.setName("产品");
        index.setType("view");
        index.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",news#wechat_redirect");


        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",order#wechat_redirect");

        ViewButton detailed = new ViewButton();
        detailed.setName("心愿单");
        detailed.setType("view");
        detailed.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",listing#wechat_redirect");

        ViewButton qrCode = new ViewButton();
        qrCode.setName("我的二维码");
        qrCode.setType("view");
        qrCode.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",qrCode#wechat_redirect");

        my.setName("店铺");
        my.setSub_button(new Button[]{store,detailed});

        Button together = new Button();
        ViewButton agents = new ViewButton();
        agents.setName("我要代理");
        agents.setType("view");
        agents.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",agents#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("http://suntisbo.jiajushouye.com/public.html");   //菜单真实地址

        together.setName("品牌");
        together.setSub_button(new Button[]{subscription,brand,special});
        menu.setButton(new Button[]{index,my,together});
/*
        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("101");
        menu.setMatchrule(matchrule);*/
        return menu;
    }


    /**
     * 初始化基础菜单
     * @param appId
     * @param redirect_uri  //微信服务地址，也就是本项目的网页授权接口地址，在Constants类里有
     * @param brandNo  //品牌编号
     * @return
     * @throws Exception
     */
    public static Menu initMenu(String appId,String redirect_uri,String brandNo) throws Exception {

        Menu menu = new Menu();

        Button index = new Button();
        ViewButton product = new ViewButton();
        product.setName("产品");
        product.setType("view");
        product.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",news#wechat_redirect");

        index.setName("产品");
        index.setSub_button(new Button[]{product,brand,store,special});

        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",order#wechat_redirect");

        ViewButton detailed = new ViewButton();
        detailed.setName("预购清单");
        detailed.setType("view");
        detailed.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",listing#wechat_redirect");

        ViewButton qrCode = new ViewButton();
        qrCode.setName("我的二维码");
        qrCode.setType("view");
        qrCode.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",qrCode#wechat_redirect");

        my.setName("我的");
        my.setSub_button(new Button[]{qrCode,detailed,viewOrder});

        Button together = new Button();
        ViewButton agents = new ViewButton();
        agents.setName("我要代理");
        agents.setType("view");
        agents.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",agents#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("http://singways.jiajushouye.com/public.html");   //二维码真实地址


        ViewButton invitation = new ViewButton();
        invitation.setName("邀请函");
        invitation.setType("view");
        invitation.setUrl("http://invitation.jiajushouye.com/huahui37/index.html");   //邀请函真实地址

        ViewButton designer = new ViewButton();
        designer.setName("申请设计师");
        designer.setType("view");
        designer.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",stylistApply#wechat_redirect");

        together.setName("合作");
        together.setSub_button(new Button[]{subscription,agents,designer});
        menu.setButton(new Button[]{index,my,together});
/*
        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("101");
        menu.setMatchrule(matchrule);*/
        return menu;
    }


    /**
     * 店员菜单
     * @param appId
     * @param redirect_uri  //微信服务地址，也就是本项目的网页授权接口地址，在Constants类里有
     * @param brandNo  //品牌编号
     * @return
     * @throws Exception
     */
    public static Menu DianYuanMenu(String appId,String redirect_uri, String brandNo) throws Exception {

        Menu menu = new Menu();

        Button index = new Button();
        ViewButton product = new ViewButton();
        product.setName("产品");
        product.setType("view");
        product.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",news#wechat_redirect");

        index.setName("产品");
        index.setSub_button(new Button[]{product,brand,store,special});

        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",userOrder#wechat_redirect");

        ViewButton guide = new ViewButton();
        guide.setName("产品导购");
        guide.setType("view");
        guide.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",guide#wechat_redirect");

        my.setName("我的");
        my.setSub_button(new Button[]{viewOrder});

        Button together = new Button();
        ViewButton scan = new ViewButton();
        scan.setName("扫一扫");
        scan.setType("view");
        scan.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",scan#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("http://singways.jiajushouye.com/public.html");  //每次生成菜单是应修改为其要生成 公众号的域名+/public.html

        together.setName("合作");
        together.setSub_button(new Button[]{subscription,scan});
        menu.setButton(new Button[]{index,my,together});

        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("101");  //分组相关  最好要和数据库分组表对应
        menu.setMatchrule(matchrule);
        return menu;
    }


    /**
     * 设计师
     * @param appId
     * @param redirect_uri  //微信服务地址，也就是本项目的网页授权接口地址，在Constants类里有
     * @param brandNo  //品牌编号
     * @return
     * @throws Exception
     */
    public static Menu DesiGnerMenu(String appId,String redirect_uri,String brandNo) throws Exception {

        Menu menu = new Menu();

        Button index = new Button();
        ViewButton product = new ViewButton();
        product.setName("产品");
        product.setType("view");
        product.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",scheme#wechat_redirect");

        ViewButton brand = new ViewButton();
        brand.setName("品牌");
        brand.setType("view");
        brand.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",brand#wechat_redirect");

        ViewButton store = new ViewButton();
        store.setName("店铺");
        store.setType("view");
        store.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",stores#wechat_redirect");

        ViewButton special = new ViewButton();
        special.setName("专题");
        special.setType("view");
        special.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",news#wechat_redirect");

        index.setName("产品");
        index.setSub_button(new Button[]{product,brand,store,special});


        Button my = new Button();
        ViewButton viewOrder = new ViewButton();
        viewOrder.setName("查看订单");
        viewOrder.setType("view");
        viewOrder.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",styOrderList#wechat_redirect");

        ViewButton detailed = new ViewButton();
        detailed.setName("预购清单");
        detailed.setType("view");
        detailed.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"autho&response_type=code&scope=snsapi_userinfo&state="+brandNo+",listing#wechat_redirect");

        ViewButton qrCode = new ViewButton();
        qrCode.setName("我的二维码");
        qrCode.setType("view");
        qrCode.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",qrCode#wechat_redirect");

        ViewButton myUser = new ViewButton();
        myUser.setName("我的客源");
        myUser.setType("view");
        myUser.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",styUserList#wechat_redirect");

        ViewButton myIntegral = new ViewButton();
        myIntegral.setName("我的积分");
        myIntegral.setType("view");
        myIntegral.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",styIntegral#wechat_redirect");

        my.setName("我的");
        my.setSub_button(new Button[]{qrCode,detailed,viewOrder,myUser,myIntegral});

        Button together = new Button();
        ViewButton agents = new ViewButton();
        agents.setName("我要代理");
        agents.setType("view");
        agents.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",agents#wechat_redirect");

        ViewButton subscription = new ViewButton();
        subscription.setName("公众号二维码");
        subscription.setType("view");
        subscription.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",styQRCode#wechat_redirect");   //设计师的二维码

        ViewButton designer = new ViewButton();
        designer.setName("我是设计师");
        designer.setType("view");
        designer.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid="+ appId+"&redirect_uri="+redirect_uri+"packing&response_type=code&scope=snsapi_userinfo&state="+brandNo+",designer#wechat_redirect");

        together.setName("合作");
        together.setSub_button(new Button[]{subscription,agents,designer});
        menu.setButton(new Button[]{index,my,together});

        Matchrule matchrule = new Matchrule();
        matchrule.setGroup_id("102");  //设置分组

        menu.setMatchrule(matchrule);
        return menu;
    }

    /**
     * 生成基础微信菜单
     * @param token
     * @param menu
     * @return
     */
    public static int creatMenu(String token,String menu){
        int result = 0;
        String url = CREAT_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * 删除微信菜单  每次重新生成微信菜单时删除一次菜单
     * 注意，删除基础菜单时，其他自定义菜单也会一并删除
     * @param token
     * @param menu
     * @return
     */
    public static int delMenu(String token,String menu){
        int result = 0;
        String url = DEL_MENU_URL.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    /**
     * 生成自定义菜单
     * @param token
     * @param menu
     * @return
     */
    public static int creatMenuNB(String token,String menu){
        int result = 0;
        String url = CREATE_MENU_NB.replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = WeChatUtil.doPostStr(url, menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }


    /**
     * 删除自定义菜单
     * @param token
     * @param menu
     * @return
     */
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
