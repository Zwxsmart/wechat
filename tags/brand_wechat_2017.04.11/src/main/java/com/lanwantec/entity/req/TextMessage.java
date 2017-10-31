package com.lanwantec.entity.req;

	/**
	 * 文本消息
	 *
	 * @author redrain
	 * @date 2016-2-4
	 */
	public class TextMessage extends BaseMessage {
		// 消息内容
		private String Content;
	
		public String getContent() {
			return Content;
		}
	
		public void setContent(String content) {
			Content = content;
		}
	}