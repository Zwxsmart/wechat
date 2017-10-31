package com.lanwantec.wechat.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix="wechat")
public class ConfigManager {

    private List<Map<String,Object>> wechatList = new ArrayList<>();


    public String GetBrandUrl(String brandNo){
        for (Map<String,Object> map : wechatList) {
            if(map.get("id")!=null && brandNo.equalsIgnoreCase((String) map.get("id"))){
                return (String) map.get("url");
            }
        }
        return null;
    }

    public String GetBrandTemplateId(String brandNo,String templateId){
        for (Map<String,Object> map : wechatList) {
            if(map.get("id")!=null && brandNo.equalsIgnoreCase((String) map.get("id"))){
                return (String) map.get(templateId);
            }
        }
        return null;
    }

    public List<Map<String, Object>> getWechatList() {
        return wechatList;
    }

    public void setWechatList(List<Map<String, Object>> wechatList) {
        this.wechatList = wechatList;
    }
}