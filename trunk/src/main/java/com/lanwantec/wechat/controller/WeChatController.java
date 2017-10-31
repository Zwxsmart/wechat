package com.lanwantec.wechat.controller;

import com.lanwantec.entity.req.SnsUserInfo;
import com.lanwantec.entity.resp.AccessToken;
import com.lanwantec.entity.resp.WeixinOauth2Token;
import com.lanwantec.wechat.config.ConfigManager;
import com.lanwantec.wechat.service.TokenService;
import com.lanwantec.wechat.utils.Sign;
import com.lanwantec.wechat.utils.WeChatUtil;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Rainy on 2016/12/9/0009.
 */
@Controller
public class WeChatController {

    private Logger log = Logger.getLogger(WeChatController.class);

    @Autowired
    ConfigManager configManager;

    @Autowired
    private TokenService tokenService;


    @Value("${wechat.dianyuan}")
    private String dianyuan; //店员访问地址

    /**
     * 设计师绑定用户接口
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/bind/authorization", method = RequestMethod.GET)
    public String bindAuthorization(@RequestParam String code, @RequestParam String[] state) {
        //state[0]  是品牌编号
        //state[1]  是设计师编号
        String url = "redirect:http://URL?openId=OPENID&brandNo=BRANDNO&designerId=DESIGNERID&jump=JUMP&ts=TS";
        AccessToken token = tokenService.token(state[0]);
        try {
            if (token != null) {
                if (!"authdeny".equals(code)) {
                    WeixinOauth2Token weixinOauth2Token = WeChatUtil.getOauth2AccessToken(code, token.getAppId(), token.getAppsecret());
                    return url.replace("URL",  dianyuan + "/packing/weChatjump.html").replace("JUMP","bind").replace("OPENID", weixinOauth2Token.getOpenId()).replace("BRANDNO", token.getBrandNo()).replace("DESIGNERID", state[1]).replace("TS", String.valueOf(new Date().getTime()));
                } else {
                    log.error("authorization fail!");
                    return "redirect:http://hl.jiajushouye.com";
                }
            } else {
                log.info("参数不正确");
                return "请刷新重试";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("param send fail,授权失败!");
        return "redirect:http://hl.jiajushouye.com";
    }


    /**
     * 移动用户到某分组
     * @param groupId
     * @param openId
     * @param brandNo
     * @return
     */
    @RequestMapping(value = "/move", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap moveUser(@RequestParam int groupId, @RequestParam String openId,@RequestParam String brandNo) {
        ModelMap mv = new ModelMap();
        AccessToken token = tokenService.token(brandNo);
        //WeChatUtil.moveUserToGroup(token.getAccessToken(),groupId,openId);
        System.out.println(WeChatUtil.moveUserToGroup(token.getAccessToken(),groupId,openId).toString());
        mv.put("state",1);
        return mv;
    }

    /**
     * 推送模板消息接口
     * 用不了，因为官网没有这些模板
     * @param brandNo
     * @param openId
     * @param pushType
     * @param jumpUrl
     * @param dealerName
     * @param rebatesPoint
     * @param closingDate
     * @param invalidDate
     * @return
     */
    @RequestMapping(value = "/push", method = RequestMethod.GET)
    @ResponseBody
    public ModelMap pushMsg(@RequestParam String brandNo, @RequestParam String openId,
                            @RequestParam int pushType, @RequestParam(required = false) String jumpUrl,
                            @RequestParam(required = false) String dealerName, @RequestParam(required = false) Integer rebatesPoint,
                            @RequestParam(required = false) String closingDate, @RequestParam(required = false) String invalidDate) {
        ModelMap mv = new ModelMap();
        AccessToken token = tokenService.token(brandNo);
        String json = "";
        String template_id = configManager.GetBrandTemplateId(brandNo,"templateId_"+pushType);
        if (pushType == 1) {  //合作邀请
            json = "{\"touser\":\"" + openId + "\",\"template_id\":\""+template_id+"\",\"url\":\"" + jumpUrl + "\",\"topcolor\":\"#FF0000\",\"data\":{\"dealerName\":{\"value\":\"" + dealerName + "\",\"color\":\"#010101\"},\"rebatesPoint\":{\"value\":\"" + rebatesPoint + "\",\"color\":\"#010101\"},\"closingDate\":{\"value\":\"" + closingDate + "\",\"color\":\"#010101\"},\"discription\":{\"value\":\"【回复有效期到" + invalidDate + "，若有效期内未回复，即视为不考虑合作】\",\"color\":\"#010101\"},\"text\":{\"value\":\"点击回复\",\"color\":\"#575AFF\"}}}";
        } else if (pushType == 2) { //合作续约
            json = "{\"touser\":\"" + openId + "\",\"template_id\":\""+template_id+"\",\"url\":\"" + jumpUrl + "\",\"topcolor\":\"#FF0000\",\"data\":{\"dealerName\":{\"value\":\"" + dealerName + "\",\"color\":\"#010101\"},\"rebatesPoint\":{\"value\":\"" + rebatesPoint + "\",\"color\":\"#010101\"},\"closingDate\":{\"value\":\"" + closingDate + "\",\"color\":\"#010101\"},\"discription\":{\"value\":\"【回复有效期到" + invalidDate + "，若有效期内未回复，即视为不考虑续约】\",\"color\":\"#010101\"},\"text\":{\"value\":\"点击回复\",\"color\":\"#575AFF\"}}}";
        } else if (pushType == 3) { //达成合作
            json = "{\"touser\":\"" + openId + "\",\"template_id\":\""+template_id+"\",\"url\":\"" + jumpUrl + "\",\"topcolor\":\"#FF0000\",\"data\":{\"dealerName\":{\"value\":\"" + dealerName + "\",\"color\":\"#010101\"},\"rebatesPoint\":{\"value\":\"" + rebatesPoint + "\",\"color\":\"#010101\"},\"closingDate\":{\"value\":\"" + closingDate + "\",\"color\":\"#010101\"}}}";
        } else if (pushType == 4) { //设计师申请审核通过
            json = "{\"touser\":\"" + openId + "\",\"template_id\":\""+template_id+"\",\"url\":\"" + "" + "\",\"topcolor\":\"#FF0000\",\"data\":{\"discription\":{\"value\":\"恭喜您，已成为品牌签约设计师，请在20分钟后，查看公众号的设计师专属菜单栏~\",\"color\":\"#010101\"}}}";
        } else if (pushType == 5) { //设计师申请审核未通过
            json = "{\"touser\":\"" + openId + "\",\"template_id\":\""+template_id+"\",\"url\":\"" + jumpUrl + "\",\"topcolor\":\"#FF0000\",\"data\":{\"discription\":{\"value\":\"再次申请\",\"color\":\"#575AFF\"}}}";
        } else {
            mv.put("state", -1);
            return mv;
        }
        boolean bool = true;
        int i = 0;
        while (bool) {
            i++;
            JSONObject jsonObject = WeChatUtil.pushMessage(token.getAccessToken(), json);
            int errcode = jsonObject.getInt("errcode");
            if (errcode == 0) {
                mv.put("state", 1);
                bool = false;
            } else {
                mv.put("state", -1);
                log.error(jsonObject.toString());
                if (i == 3) {
                    return mv;
                }
            }
        }
        return mv;

    }


    /**
     * 微信菜单授权跳转h5页面接口
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/autho", method = RequestMethod.GET)
    public String autho(@RequestParam String code, @RequestParam String[] state) {
        String url = "redirect:http://URL?jump=JUMP&openId=OPENID&brandNo=BRANDNO&ts=TS";
        AccessToken token = tokenService.token(state[0]);
        try {
            if (token != null) {
                SnsUserInfo snsUserInfo = null;
                // 用户同意授权
                if (!"authdeny".equals(code)) {
                    // 获取网页授权access_token
                    WeixinOauth2Token weixinOauth2Token = WeChatUtil.getOauth2AccessToken(code, token.getAppId(), token.getAppsecret());
                    // 网页授权接口访问凭证
                    String openId = weixinOauth2Token.getOpenId();
                    url = url.replace("URL", token.getJump()).replace("JUMP", state[1]).replace("BRANDNO", token.getBrandNo()).replace("OPENID", openId).replace("TS", String.valueOf(new Date().getTime()));
                    System.out.println("pack---------------" + url);
                    return url;
                } else {
                    log.error("param send fail,授权失败!");
                    return url.replace("URL", token.getJump()).replace("BRANDNO", token.getBrandNo()).replace("JUMP", state[1]).replace("TS", String.valueOf(new Date().getTime()));
                }
            } else {
                log.info("参数不正确");
                return "请刷新重试";
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        log.error("param send fail,授权失败!");
        return url.replace("URL", token.getJump()).replace("BRANDNO", token.getBrandNo()).replace("JUMP", state[1]).replace("TS", String.valueOf(new Date().getTime()));
    }


    /**
     * 微信菜单授权跳转后台接口
     * @param code
     * @param state
     * @return
     */
    @RequestMapping(value = "/packing", method = RequestMethod.GET)
    public String Autho_Packing(@RequestParam(required = true, value = "code") String code, @RequestParam(required = true, value = "state") String [] state) {
        String url = "redirect:http://URL?jump=JUMP&openId=OPENID&brandNo=BRANDNO&ts=TS";
        AccessToken token  = tokenService.token(state[0]);

        try {
            if (token != null) {
                //SnsUserInfo snsUserInfo = null;
                // 用户同意授权
                if (!"authdeny".equals(code)) {
                    // 获取网页授权access_token
                    WeixinOauth2Token weixinOauth2Token = WeChatUtil.getOauth2AccessToken(code, token.getAppId(), token.getAppsecret());
                    // 网页授权接口访问凭证
                   // String accessToken = weixinOauth2Token.getAccessToken();
                    // 用户标识
                    String openId = weixinOauth2Token.getOpenId();
                    // 获取用户信息
                    //snsUserInfo = WeChatUtil.getSnsUserInfo(accessToken, openId); // 设置要传递的参数
                    url = url.replace("URL", dianyuan + "/packing/weChatjump.html").replace("JUMP", state[1]).replace("BRANDNO", token.getBrandNo()).replace("OPENID", openId).replace("TS", String.valueOf(new Date().getTime()));
                    System.out.println("pack---------------" + url);
                    return url;
                } else {
                    log.error("param send fail,授权失败!");
                    return url.replace("URL", dianyuan + "/packing/weChatjump.html").replace("BRANDNO", token.getBrandNo()).replace("JUMP", state[1]).replace("TS", String.valueOf(new Date().getTime())).replace("OPENID","");
                }
            } else {
                log.info("参数不正确");
                return "请刷新重试";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.error("param send fail,授权失败!");
        return url.replace("URL", dianyuan + "/packing/weChatjump.html").replace("BRANDNO", token.getBrandNo()).replace("JUMP", state[1]).replace("TS", String.valueOf(new Date().getTime())).replace("OPENID","");
    }

    /**
     * jsapi权限获取接口
     * @param request
     * @param type
     * @return
     */
    @RequestMapping(value = "/share", method = RequestMethod.GET, produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String share(HttpServletRequest request, @RequestParam String type) {
        if (null != type && !"".equalsIgnoreCase(type)) {
            try {
                AccessToken token = tokenService.token(type);
                if (token != null) {
                    return Sign.getParam(request, token.getJsApiTicket(), token.getAppId());
                } else {
                    log.error("获取参数错误!");
                    return "";
                }
            } catch (Exception e) {
                log.error("分享错误!");
                return "";
            }
        } else {
            log.error("参数不正确！");
            return "";
        }
    }


}
