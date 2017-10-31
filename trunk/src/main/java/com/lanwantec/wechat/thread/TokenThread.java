//package com.lanwantec.wechat.thread;
//
//import com.lanwantec.config.WeChatConfig;
//import com.lanwantec.entity.resp.AccessToken;
//import com.lanwantec.wechat.Constants;
//import com.lanwantec.wechat.mapper.TokenMapper;
//import com.lanwantec.wechat.utils.WeChatUtil;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//
///**
// * 定时获取微信access_token的线程
// */
//@Service
//public class TokenThread implements Runnable {
//
//    private Logger log = Logger.getLogger(this.getClass());
//
//    private static List<AccessToken> tokens = new ArrayList<>();
//
//    private static long sleepDate = 0;
//
//    @Autowired
//    private TokenMapper tokenMapper;
//
//    @Autowired
//    private WeChatConfig weChatConfig;
//
//    public void run() {
//
//        while (true) {
//            /*
//            try {
//				Thread.sleep(300);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}*/
//
//            List<Map<String, Object>> list = weChatConfig.getWechatList();
//            AccessToken token = null;
//            for (Map<String, Object> map : list) {
//                token = new AccessToken();
//                AccessToken at = tokenMapper.accessToken(map.get("id").toString());
//                if (at == null || at.getAccessToken() == null || at.getAccessToken().equalsIgnoreCase("")) {
//                    token = WeChatUtil.getAccessToken(map.get("app_id").toString(), map.get("appsecret").toString());
//                    token.setAccessUpdate(new Date());
//                    String jsApi = WeChatUtil.getJsApiTicket(token.getAccessToken());
//                    token.setJsApiTicket(jsApi);
//                    token.setJsApiUpdate(new Date());
//                    token.setBrandNo(map.get("app_id").toString());
//                    tokenMapper.addToken(token);
//                    tokens.add(token);
//                } else {
//                    long tokenDate = at.getAccessUpdate().getTime() + 7000 * 1000;
//                    long nowDate = new Date().getTime();
//                    if (nowDate > tokenDate) {
//                        AccessToken act = WeChatUtil.getAccessToken(Constants.APPID, Constants.APPSECRET);
//                        String jsApi = WeChatUtil.getJsApiTicket(act.getAccessToken());
//                        act.setBrandNo(Constants.BRANDNO);
//                        act.setJsApiTicket(jsApi);
//                        act.setAccessUpdate(new Date());
//                        act.setJsApiUpdate(new Date());
//                        tokenMapper.updateToken(act);
//                        token = tokenMapper.accessToken(Constants.BRANDNO);
//                        sleepDate = 7000 * 1000;
//                        try {
//                            System.out.println("------------睡：" + sleepDate + "------------");
//                            Thread.sleep(sleepDate);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                            log.error("定时任务ERROR：" + e);
//                        }
//                    }
//                }
//
//            }
//            sleepDate = 7000 * 1000;
//            try {
//                System.out.println("------------睡：" + sleepDate + "------------");
//                Thread.sleep(sleepDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//                log.error("定时任务ERROR：" + e);
//            }
//        }
//    }
//}
