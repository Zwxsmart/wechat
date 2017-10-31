package com.lanwantec.entity.resp;
	
	/**
	 * 消息基类（公众帐号 -> 普通用户）
	 *
	 * @author redrain
	 * @date 2016-2-4
	 */
	public class BaseMessage {
		public String getToUserName() {
			return ToUserName;
		}
		public void setToUserName(String toUserName) {
			ToUserName = toUserName;
		}
		public String getFromUserName() {
			return FromUserName;
		}
		public void setFromUserName(String fromUserName) {
			FromUserName = fromUserName;
		}
		public long getCreateTime() {
			return CreateTime;
		}
		public void setCreateTime(long createTime) {
			CreateTime = createTime;
		}
		public String getMsgType() {
			return MsgType;
		}
		public void setMsgType(String msgType) {
			MsgType = msgType;
		}
		public int getFuncFlag() {
			return FuncFlag;
		}
		public void setFuncFlag(int funcFlag) {
			FuncFlag = funcFlag;
		}
		// 接收方帐号（收到的 OpenID）
		private String ToUserName;
		// 开发者微信号
		private String FromUserName;
		// 消息创建时间 （整型）
		private long CreateTime;
		// 消息类型（text/music/news_lists）
		private String MsgType;
		// 位 0x0001 被标志时，星标刚收到的消息
		private int FuncFlag;
	}
