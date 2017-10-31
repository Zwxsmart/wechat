package com.lanwantec.wechat.controller;

import com.lanwantec.wechat.service.WeChatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
@Controller
public class WeChatTypeController {

    @Autowired
    private WeChatTypeService weChatTypeService;

    @RequestMapping("/text")
    @ResponseBody
    public String typeText(@RequestParam String wechatType, @RequestParam String fromUserName, @RequestParam  String toUserName) {
        return weChatTypeService.typeText(wechatType, fromUserName, toUserName);
    }

    //接收image类型
    @RequestMapping("/image")
    @ResponseBody
    public String typeImage(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeImage(wechatType, fromUserName, toUserName);
    }

    //接收location类型
    @RequestMapping("/location")
    @ResponseBody
    public String typeLocation(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeLocation(wechatType, fromUserName, toUserName);
    }
    //接收link类型
    @RequestMapping("/link")
    @ResponseBody
    public String typeLink(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeLink(wechatType, fromUserName, toUserName);
    }

    //接收video类型
    @RequestMapping("/video")
    @ResponseBody
    public String typeVideo(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeVideo(wechatType, fromUserName, toUserName);
    }

    //接收shortVideo类型
    @RequestMapping("/short/video")
    @ResponseBody
    public String typeShortVideo(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeShortVideo(wechatType, fromUserName, toUserName);
    }

    //接收voice类型
    @RequestMapping("/voice")
    @ResponseBody
    public String typeVoice(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeVoice(wechatType, fromUserName, toUserName);
    }

    //接收event类型,订阅
    @RequestMapping("/subscribe")
    @ResponseBody
    public String typeSubscribe(@RequestParam String wechatType,@RequestParam String event,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeSubscribe(wechatType,event, fromUserName, toUserName);
    }

    //接收event类型,取消订阅
    @RequestMapping("/unsubscribe")
    @ResponseBody
    public String typeUNSubscribe(@RequestParam String wechatType,@RequestParam String event,@RequestParam String fromUserName,@RequestParam  String toUserName){
        return weChatTypeService.typeUNSubscribe(wechatType,event, fromUserName, toUserName);
    }

    //接收event类型,点击按钮
    @RequestMapping("/click")
    @ResponseBody
    public String typeClick(@RequestParam String wechatType,@RequestParam String event,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeClick(wechatType,event, fromUserName, toUserName);
    }

    //接收scan类型
    @RequestMapping("/scan")
    @ResponseBody
    public String typeScan(@RequestParam String wechatType,@RequestParam String fromUserName,@RequestParam  String toUserName) {
        return weChatTypeService.typeScan(wechatType, fromUserName, toUserName);
    }

    //扫码推事件且弹出“消息接收中”提示框的事件推送
    @RequestMapping("/scan_waiting")
    @ResponseBody
    public String typeScancode_Waitmsg(@RequestParam String wechatType,@RequestParam String eventKey,@RequestParam String scanResult, @RequestParam String fromUserName, @RequestParam  String toUserName) {
        return weChatTypeService.typeScancode_Waitmsg(wechatType, eventKey,scanResult,fromUserName, toUserName);
    }

}
