package com.lanwantec.entity.resp;

import java.util.Date;

public class AccessToken {

	private Integer id;

	private String appId;

	private String appsecret;

	private String brandNo;

	private String accessToken;

	private Date accessUpdate;

	private String jsApiTicket;

	private Date jsApiUpdate;

	private String jump;

	private Integer isValid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getBrandNo() {
		return brandNo;
	}

	public void setBrandNo(String brandNo) {
		this.brandNo = brandNo;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public Date getAccessUpdate() {
		return accessUpdate;
	}

	public void setAccessUpdate(Date accessUpdate) {
		this.accessUpdate = accessUpdate;
	}

	public String getJsApiTicket() {
		return jsApiTicket;
	}

	public void setJsApiTicket(String jsApiTicket) {
		this.jsApiTicket = jsApiTicket;
	}

	public Date getJsApiUpdate() {
		return jsApiUpdate;
	}

	public void setJsApiUpdate(Date jsApiUpdate) {
		this.jsApiUpdate = jsApiUpdate;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public String getAppsecret() {
		return appsecret;
	}

	public void setAppsecret(String appsecret) {
		this.appsecret = appsecret;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getJump() {
		return jump;
	}

	public void setJump(String jump) {
		this.jump = jump;
	}
}
