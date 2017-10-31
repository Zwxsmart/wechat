package com.lanwantec.entity.resp;

import java.util.List;
public class NewsMessage extends BaseMessage {
	
	private int ArticleCount;
	
	public int getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}
	public List<Article> getArticles() {
		return Articles;
	}
	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
	private List<Article> Articles;
}
