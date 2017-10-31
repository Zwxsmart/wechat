//package com.lanwantec.wechat.utils;
//
//
//import org.apache.poi.ss.formula.functions.T;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//
//import com.lanwantec.entity.resp.AccessToken;
//import com.lanwantec.wechat.thread.TokenThread;
//import org.springframework.context.annotation.Configuration;
//
///**
// * 获取spring信息的工具类
// *
// */
//@Configuration
//public class SpringUtil implements ApplicationContextAware{
//
//	private static ApplicationContext applicationContext = null;
//
//	@Autowired
//	private TokenThread tokenThread;
//
//	public synchronized void setApplicationContext(ApplicationContext applicationContext)
//			throws BeansException {
//		if(SpringUtil.applicationContext == null){
//			SpringUtil.applicationContext  = applicationContext;
//			AccessToken accessToken = new AccessToken();
//			if("".equals(accessToken.getAccessToken())){
//				System.out.println("appid and appsecret configuration error, please check carefully.");
//			}else{
//				new Thread(tokenThread).start();
//			}
//		}
//	}
//
//
//	public static ApplicationContext getApplicationContext() {
//		return applicationContext;
//	}
//
//
//	public static Object getBean(String name){
//		return getApplicationContext().getBean(name);
//	}
//
//	public static Object getBean(String name, Class<T> entity)
//            throws BeansException {
//        return getApplicationContext().getBean(name, entity);
//    }
//
//}
