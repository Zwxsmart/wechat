package com.lanwantec.test;

import com.lanwantec.wechat.Constants;
import com.lanwantec.wechat.utils.WeChatUtil;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;

/**
 * Created by Rainy on 2016/12/24/0024.
 */
public class Tests1 {

    public static final String token = "Y5UapnO8Uf0ZrFfBVg_h6exDNDTUNmeQdCAZNsIMdUrcuL7uSup5zzKVq6a1h0xIi1fudAxNS6jLyWZavsWJ-yNfBRw-a0amSycfpBmlJtIPPBgAGALLX";

    @Test
    public void createGroup(){
        System.out.println(WeChatUtil.createGroup(token,"设计师").toString());
    }

    @Test
    public void moveUserGroup(){
        System.out.println(WeChatUtil.moveUserToGroup(token,122,"oRCetxP0gbQrcg_f6PBXHRlaCiyE").toString());
    }


    //默认菜单，普通用户菜单
    @Test
    public void createMenu() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.initMenu(Constants.APPID_HUAHUI_TEST,Constants.WECHAE_SERVICE_URL_TEST1,Constants.BRANDNO_HUAHUI_TEST)).toString();
        System.out.println("..."+menu);
        System.out.println(WeChatUtil.creatMenu(token,menu));
}
    //创建店员菜单
    @Test
    public void createMenuNB() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.DianYuanMenu(Constants.APPID_SINGWAYS,Constants.WECHAE_SERVICE_URL,Constants.BRANDNO_SINGWAYS)).toString();
        System.out.println(menu);
        System.out.println(WeChatUtil.creatMenuNB(token,menu));
    }

    //创建设计师菜单
    @Test
    public void createMenuDes() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.DesiGnerMenu(Constants.APPID_SINGWAYS,Constants.WECHAE_SERVICE_URL,Constants.BRANDNO_SINGWAYS)).toString();
        System.out.println(menu);
        System.out.println(WeChatUtil.creatMenuNB(token,menu));
    }


    @Test
    public void createOuthMenu() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.initOuthMenu(Constants.APPID_HUAHUI_TEST,Constants.WECHAE_SERVICE_URL_TEST1,Constants.BRANDNO_HUAHUI_TEST)).toString();
        System.out.println(menu);
        System.out.println(WeChatUtil.creatMenu(token,menu));
    }


    public static void main(String[] args) {
        System.out.println(WeChatUtil.GetIindustry(token).toString());
    }

}
