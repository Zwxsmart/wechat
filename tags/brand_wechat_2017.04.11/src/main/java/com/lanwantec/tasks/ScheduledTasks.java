//package com.lanwantec.tasks;
//
//import com.lanwantec.entity.resp.AccessToken;
//import com.lanwantec.wechat.Constants;
//import com.lanwantec.wechat.mapper.TokenMapper;
//import com.lanwantec.wechat.utils.WeChatUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Rainy on 2016/12/7/0007.
// */
//@Component
//public class ScheduledTasks {
//
//    private static List<AccessToken> tokens = new ArrayList<>();
//
//    @Autowired
//    private TokenMapper tokenMapper;
//
//    @Scheduled(fixedRate = 7000 * 1000)
//    public void AN_YUN() {
//        AccessToken at = tokenMapper.accessToken(Constants.BRANDNO_ANYUN);
//        long tokenDate = at.getAccessUpdate().getTime() + 7000 * 1000;
//        long nowDate = new Date().getTime();
//        if (nowDate > tokenDate) {
//            AccessToken act = WeChatUtil.getAccessToken(Constants.APPID_ANYUN, Constants.APPSECRET_ANYUN);
//            String jsApi = WeChatUtil.getJsApiTicket(act.getAccessToken());
//            act.setBrandNo(Constants.BRANDNO_ANYUN);
//            act.setJsApiTicket(jsApi);
//            act.setAccessUpdate(new Date());
//            act.setJsApiUpdate(new Date());
//            act.setAppId(Constants.APPID_ANYUN);
//            act.setAppsecret(Constants.APPSECRET_ANYUN);
//            act.setJump("anyun.jiajushouye.com/wechatjump.html");
//            tokenMapper.updateToken(act);
//            checkedList(act);
//        }else{
//            checkedList(at);
//        }
//    }
//
//    @Scheduled(fixedRate = 7000 * 1000)
//    public void HUA_HUI() {
//        AccessToken at = tokenMapper.accessToken(Constants.BRANDNO_HUAHUI);
//        long tokenDate = at.getAccessUpdate().getTime() + 7000 * 1000;
//        long nowDate = new Date().getTime();
//        if (nowDate > tokenDate) {
//            AccessToken act = WeChatUtil.getAccessToken(Constants.APPID_HUAHUI, Constants.APPSECRET_HUAHUI);
//            String jsApi = WeChatUtil.getJsApiTicket(act.getAccessToken());
//            act.setBrandNo(Constants.BRANDNO_HUAHUI);
//            act.setJsApiTicket(jsApi);
//            act.setAccessUpdate(new Date());
//            act.setJsApiUpdate(new Date());
//            act.setAppId(Constants.APPID_HUAHUI);
//            act.setAppsecret(Constants.APPSECRET_HUAHUI);
//            act.setJump("huahui.jiajushouye.com/weChatjump.html");
//            tokenMapper.updateToken(act);
//            checkedList(act);
//        }else{
//            checkedList(at);
//        }
//    }
//
//    @Scheduled(fixedRate = 7000 * 1000)
//    public void SHOU_YE() {
//        AccessToken at = tokenMapper.accessToken(Constants.BRANDNO_SHOUYE);
//        long tokenDate = at.getAccessUpdate().getTime() + 7000 * 1000;
//        long nowDate = new Date().getTime();
//        if (nowDate > tokenDate) {
//            AccessToken act = WeChatUtil.getAccessToken(Constants.APPID_SHOUYE, Constants.APPSECRET_SHOUYE);
//            String jsApi = WeChatUtil.getJsApiTicket(act.getAccessToken());
//            act.setBrandNo(Constants.BRANDNO_SHOUYE);
//            act.setJsApiTicket(jsApi);
//            act.setAccessUpdate(new Date());
//            act.setJsApiUpdate(new Date());
//            act.setAppId(Constants.APPID_SHOUYE);
//            act.setAppsecret(Constants.APPSECRET_SHOUYE);
//            act.setJump("h5test.jiajushouye.com/p00210118/weChatjump.html");
//            tokenMapper.updateToken(act);
//            checkedList(act);
//        }else{
//            checkedList(at);
//        }
//    }
//
//
//    public static void checkedList(AccessToken token){
//        for (int i = 0; i < tokens.size(); i++) {
//            if(tokens.get(i).getBrandNo().equals(token.getBrandNo())){
//                tokens.remove(i);
//            }
//        }
//        tokens.add(token);
//    }
//
//
//    public static AccessToken GetTokenEntity (String brandNo){
//        for (int i = 0; i < tokens.size(); i++) {
//            if(tokens.get(i).getBrandNo().equals(brandNo)){
//               return tokens.get(i);
//            }
//        }
//        return null;
//    }
//
//}
