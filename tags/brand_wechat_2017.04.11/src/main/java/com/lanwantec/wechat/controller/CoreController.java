package com.lanwantec.wechat.controller;

import com.lanwantec.wechat.service.WeChatCoreService;
import com.lanwantec.wechat.utils.SignUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Controller
public class CoreController {
	
	@Autowired
	private WeChatCoreService weChatCoreService;

	/**
	 *  /wechatCore 总入口/出口
	 */
	
	@ResponseBody
	@RequestMapping(value="/wechatCore",method=RequestMethod.GET,produces="text/html;charset=UTF-8")
	public void wechat_core_get(@RequestParam String signature, @RequestParam String timestamp,@RequestParam String nonce, @RequestParam String echostr, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		// 微信加密签名
//		String signature = request.getParameter("signature");
//		//时间戳
//		String timestamp = request.getParameter("timestamp");
//		//随机数
//		String nonce = request.getParameter("nonce");
//		//随机字符串
//		String echostr = request.getParameter("echostr");
		PrintWriter out = response.getWriter();
		// 通过检验 signature 对请求进行校验， 若校验成功则原样返回 echostr， 表示接入成功，否则接入失败
		if (SignUtil.checkSignature(signature, timestamp, nonce)) {
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	// 调用核心业务类接收消息、处理消息
	@ResponseBody
	@RequestMapping(value="/wechatCore",method=RequestMethod.POST,produces="text/html;charset=UTF-8")
	public void wechat_core_post(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 哪个公众号发来的
		String type = request.getParameter("wechatType");
		String respMessage = weChatCoreService.processRequest(request,response,type);
		// 响应消息
		PrintWriter out = response.getWriter();
		out.print(respMessage);
		out.close();
	}

	

}
