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
public interface WeChatCoreService {

    String processRequest(HttpServletRequest request, HttpServletResponse response, String wechatType);

}
