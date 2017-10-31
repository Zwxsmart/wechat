package com.lanwantec.wechat.service;

/**
 * Created by Rainy on 2016/12/5/0005.
 */
interface WeChatService {


    //接收text类型
    String typeText(String wechatType,String fromUserName,String toUserName);

    //接收image类型
    String typeImage(String wechatType,String fromUserName,String toUserName);

    //接收location类型
    String typeLocation(String wechatType,String fromUserName,String toUserName);
    //接收link类型
    String typeLink(String wechatType,String fromUserName,String toUserName);

    //接收video类型
    String typeVideo(String wechatType,String fromUserName,String toUserName);

    //接收shortVideo类型
    String typeShortVideo(String wechatType,String fromUserName,String toUserName);

    //接收voice类型
    String typeVoice(String wechatType,String fromUserName,String toUserName);

    //接收event类型,订阅
    String typeSubscribe(String wechatType,String event,String fromUserName,String toUserName);

    //接收event类型,取消订阅
    String typeUNSubscribe(String wechatType,String event,String fromUserName,String toUserName);

    //接收event类型,点击按钮
    String typeClick(String wechatType,String event,String fromUserName,String toUserName);

    //接收scan类型
    String typeScan(String wechatType,String fromUserName,String toUserName);

    //扫码推事件且弹出“消息接收中”提示框的事件推送
    String typeScancode_Waitmsg(String wechatType,String event,String content, String fromUserName, String toUserName);
}
