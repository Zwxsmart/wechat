package com.lanwantec.wechat.controller;

import com.lanwantec.entity.req.SnsUserInfo;
import com.lanwantec.entity.resp.AccessToken;
import com.lanwantec.entity.resp.WeixinOauth2Token;
import com.lanwantec.wechat.service.TokenService;
import com.lanwantec.wechat.utils.Sign;
import com.lanwantec.wechat.utils.WeChatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by Rainy on 2016/12/9/0009.
 */
@Controller
public class WeChatController {

    private Logger log = Logger.getLogger(WeChatController.class);

    @Autowired
    private TokenService tokenService;

    @Value("${wechat.dianyuan}")
    private String dianyuan; //店员访问地址

    @RequestMapping(value="/autho",method= RequestMethod.GET)
    public String autho(@RequestParam(required=true,value="code") String code,@RequestParam(required=true,value="state") String state){
        String url = "redirect:http://URL?jump=JUMP&openId=OPENID&brandNo=BRANDNO&ts=TS";
        String type="",page="";
        AccessToken token = null;
        if(state.split(",").length==2){
            type = state.split(",")[0];
            page = state.split(",")[1];
            synchronized (type){
                token = tokenService.token(type);
            }
        }
        try {
            if(token!=null){
                SnsUserInfo snsUserInfo = null;
                // 用户同意授权
                if (!"authdeny".equals(code)) {
                    // 获取网页授权access_token
                    WeixinOauth2Token weixinOauth2Token = WeChatUtil.getOauth2AccessToken(code,token.getAppId(),token.getAppsecret());
                    // 网页授权接口访问凭证
                    String accessToken = weixinOauth2Token.getAccessToken();
                    // 用户标识
                    String openId = weixinOauth2Token.getOpenId();
                    // 获取用户信息
                    snsUserInfo = WeChatUtil.getSnsUserInfo(accessToken, openId); // 设置要传递的参数
                    url= url.replace("URL",token.getJump()).replace("JUMP",page).replace("BRANDNO",token.getBrandNo()).replace("OPENID",snsUserInfo.getOpenId()).replace("TS",String.valueOf(new Date().getTime()));
                    System.out.println("pack---------------"+url);
                    return url;
                }else{
                    log.error("param send fail,授权失败!");
                    return url.replace("URL",token.getJump()).replace("BRANDNO",token.getBrandNo()).replace("JUMP",page).replace("TS",String.valueOf(new Date().getTime()));
                }
            }else {
                log.info("参数不正确");
                return "请刷新重试";
            }
        }catch (Exception e){
            log.error("param send fail,授权失败!");
            return url.replace("URL",token.getJump()).replace("BRANDNO",token.getBrandNo()).replace("JUMP",page).replace("TS",String.valueOf(new Date().getTime()));
        }
    }


    @RequestMapping(value="/packing",method= RequestMethod.GET)
    public String Autho_Packing(@RequestParam(required=true,value="code") String code,@RequestParam(required=true,value="state") String state){
        String url = "redirect:http://URL?jump=JUMP&openId=OPENID&brandNo=BRANDNO&ts=TS";
        String type="",page="";
        AccessToken token = null;
        if(state.split(",").length==2){
            type = state.split(",")[0];
            page = state.split(",")[1];
            synchronized (type){
                token = tokenService.token(type);
            }
        }
        try {
            if(token!=null){
                SnsUserInfo snsUserInfo = null;
                // 用户同意授权
                if (!"authdeny".equals(code)) {
                    // 获取网页授权access_token
                    WeixinOauth2Token weixinOauth2Token = WeChatUtil.getOauth2AccessToken(code,token.getAppId(),token.getAppsecret());
                    // 网页授权接口访问凭证
                    String accessToken = weixinOauth2Token.getAccessToken();
                    // 用户标识
                    String openId = weixinOauth2Token.getOpenId();
                    // 获取用户信息
                    snsUserInfo = WeChatUtil.getSnsUserInfo(accessToken, openId); // 设置要传递的参数
                    url = url.replace("URL",dianyuan+"/packing/weChatjump.html").replace("JUMP",page).replace("BRANDNO",token.getBrandNo()).replace("OPENID",snsUserInfo.getOpenId()).replace("TS",String.valueOf(new Date().getTime()));
                    System.out.println("pack---------------"+url);
                    return url;
                }else{
                    log.error("param send fail,授权失败!");
                    return url.replace("URL",dianyuan+"/packing/weChatjump.html").replace("BRANDNO",token.getBrandNo()).replace("JUMP",page).replace("TS",String.valueOf(new Date().getTime()));
                }
            }else {
                log.info("参数不正确");
                return "请刷新重试";
            }
        }catch (Exception e){
            log.error("param send fail,授权失败!");
            return url.replace("URL",dianyuan+"/packing/weChatjump.html").replace("BRANDNO",token.getBrandNo()).replace("JUMP",page).replace("TS",String.valueOf(new Date().getTime()));
        }
    }

    @RequestMapping(value="/share",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
    @ResponseBody
    public String share(HttpServletRequest request, @RequestParam String type){
        if(null!=type && !"".equalsIgnoreCase(type)){
            try{
                AccessToken token = tokenService.token(type);
                if(token!=null){
                    return Sign.getParam(request,token.getJsApiTicket(),token.getAppId());
                }else {
                    log.error("获取参数错误!");
                    return  "";
                }
            }catch (Exception e){
                log.error("分享错误!");
                return  "";
            }
        }else{
            log.error("参数不正确！");
            return  "";
        }
    }

}
