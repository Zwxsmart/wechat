package com.lanwantec.wechat.service;

import com.lanwantec.wechat.utils.MessageUtil;
import com.lanwantec.wechat.utils.WeChatUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Rainy on 2016/12/5/0005.
 */
@Service
public class WeChatCoreServiceImpl implements WeChatCoreService {

    private static Logger log = Logger.getLogger(WeChatCoreServiceImpl.class);

    /**
     * @param request
     * @param response
     * @param wechatType
     * @return
     */
    @Autowired
    private WeChatService weChatService;

    @Override
    public String processRequest(HttpServletRequest request, HttpServletResponse response, String wechatType) {
        String respMessage = null;

        try {
            // 默认返回的文本消息内容
            String respContent = "请求处理异常，请稍候尝试！";
            // xml 请求解析
            /*String json = MessageUtil.parseXmlToJson(request);
            JSONObject requestMap = JSONObject.fromObject(json);*/
            Map<String, String> requestMap = MessageUtil.parseXmlToMap(request);

            // 发送方帐号（open_id）
            String fromUserName = requestMap.get("FromUserName");
            // 公众帐号
            String toUserName = requestMap.get("ToUserName");
            // 消息类型
            String msgType = requestMap.get("MsgType");
            //接收的文字
            String Content = requestMap.get("Content");

            if (Content != null) {
                log.info("用户发送的信息：" + Content);
            }

            String mediaId = requestMap.get("MediaId");

            String eventKey = requestMap.get("EventKey");


            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                return weChatService.typeText(wechatType, fromUserName, toUserName);
            }
            // 图片消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                return weChatService.typeImage(wechatType, fromUserName, toUserName);
            }
            // 地理位置消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                return weChatService.typeLocation(wechatType, fromUserName, toUserName);
            }
            // 链接消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                return weChatService.typeLink(wechatType, fromUserName, toUserName);
            }
            // 视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
                return weChatService.typeVideo(wechatType, fromUserName, toUserName);
            }
            // 小视频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_SHORTVIDEO)) {
                return weChatService.typeShortVideo(wechatType, fromUserName, toUserName);
            }
            // 音频消息
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                return weChatService.typeVoice(wechatType, fromUserName, toUserName);
            }
            // 事件推送
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                // 事件类型
                String eventType = requestMap.get("Event");
                // 订阅
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    return weChatService.typeSubscribe(wechatType, eventKey, fromUserName, toUserName);
                }
                // 取消订阅
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    return weChatService.typeUNSubscribe(wechatType, eventKey, fromUserName, toUserName);
                }
                // 自定义菜单点击事件
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                    return weChatService.typeClick(wechatType, eventKey, fromUserName, toUserName);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                    return weChatService.typeScan(wechatType, fromUserName, toUserName);
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCANCODE_WAITMSG)) {
                    String scanResult = requestMap.get("ScanResult");
                    return weChatService.typeScancode_Waitmsg(wechatType, eventKey,scanResult,fromUserName, toUserName);
                }
            }
            respMessage = WeChatUtil.sendTextMSG(fromUserName, toUserName, respContent);
        } catch (Exception e) {
            log.error(e);
            e.printStackTrace();
        }
        return respMessage;
    }
}
