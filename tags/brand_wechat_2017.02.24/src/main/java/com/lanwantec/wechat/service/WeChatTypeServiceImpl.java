package com.lanwantec.wechat.service;

import com.lanwantec.entity.resp.TextMessage;
import com.lanwantec.wechat.utils.MessageUtil;
import com.lanwantec.wechat.utils.WeChatUtil;
import org.springframework.stereotype.Service;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
@Service
public class WeChatTypeServiceImpl implements WeChatTypeService {

    @Override
    public String typeText(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeImage(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeLocation(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeLink(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeVideo(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeShortVideo(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeVoice(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeSubscribe(String wechatType, String event, String fromUserName, String toUserName) {
        String respContent = "";
        if (wechatType.equals("pageHome")) {
            respContent = "感谢关注家居首页。\n听说你是个有趣有品的人呢。\n\n如果你看腻了一成不变的家装风格，受够了网购售后无门的折腾不休，厌倦了为去哪买划算而犹豫不决。\n<a href='http://a.app.qq.com/o/simple.jsp?pkgname=com.lanwan.furnish.mobile.phone'>下载【家居首页】APP</a>\n你将体验到线上线下无缝链接的家居购买体验。\n\n「买什么」、「去哪买」、「怎么买」都不再是问题，让你的家在变美的路上一去不复返。希望你在这里玩得开心。\n\n希望你离理想的生活越来越近。";
            return WeChatUtil.sendTextMSG(fromUserName, toUserName, respContent);
        } else if (wechatType.equals("P00210118")) {
            /*Article article = new Article();
            article.setTitle("扫码注册，抽奖送店！安云家居邀请您参加第36届国际名家具（东莞）展览会");
            article.setPicUrl("http://anyun.jiajushouye.com/p00210118/img/invitation.jpg");
            article.setDescription("逛展会，浏览安云家居公众号，点击任意场景查看大图即可注册，便有机会抽取大奖！！");
            article.setUrl("http://mp.weixin.qq.com/s?__biz=MjM5MjQxNDQ5NQ==&mid=2257495814&idx=1&sn=e7e07139518fa148e332190dfcd766b2&scene=0#wechat_redirect");
            List<Article> articles = new ArrayList<Article>();
            articles.add(article);
            NewsMessage newsMsg = new NewsMessage();
            newsMsg.setToUserName(fromUserName);
            newsMsg.setFromUserName(toUserName);
            newsMsg.setCreateTime(new Date().getTime());
            newsMsg.setArticleCount(1);
            newsMsg.setMsgType("news_lists");
            newsMsg.setArticles(articles);
            String newsMsgs = MessageUtil.newsMessageToXml(newsMsg);*/
            TextMessage textMessage = new TextMessage();
            textMessage.setContent("dsadasda");
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setMsgType(MessageUtil.REQ_MESSAGE_TYPE_TEXT);
            String newsMsgs = MessageUtil.textMessageToXml(textMessage);
            System.out.println(newsMsgs);
            return newsMsgs;
        } else if (wechatType.equals("test")) {
            respContent = "关注TEST！";
            return WeChatUtil.sendTextMSG(fromUserName, toUserName, respContent);
        } else {
            respContent = "感谢您的关注！";
            return WeChatUtil.sendTextMSG(fromUserName, toUserName, respContent);
        }
    }

    @Override
    public String typeUNSubscribe(String wechatType, String event, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeClick(String wechatType, String event, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeScan(String wechatType, String fromUserName, String toUserName) {
        return null;
    }

    @Override
    public String typeScancode_Waitmsg(String wechatType, String event, String content, String fromUserName, String toUserName) {
        if (event.equalsIgnoreCase("V1001_GOOD")) {
            return WeChatUtil.sendTextMSG(fromUserName, toUserName, content);
        } else {
            return null;
        }
    }
}
