package com.lanwantec.Test;

import com.lanwantec.wechat.utils.WeChatUtil;
import net.sf.json.JSONObject;
import org.testng.annotations.Test;

/**
 * Created by Rainy on 2016/12/24/0024.
 */
public class Tests {

    @Test
    public void createGroup(){
        System.out.println(WeChatUtil.createGroup("_y7Drv7F5TUDd1bwzJh7p2oG2PiXPsdN5Zx5E0we6ng9gZTNoTkcoL6J7DcL2nAa_IF55SCbusDY_StOtf6bbak_B9FkHjXn-gTN4w0GubADVNdAFARJB","店员").toString());
    }

    @Test
    public void moveUserGroup(){
        System.out.println(WeChatUtil.moveUserToGroup("eMJPx_VaEMr0-2UHD2BJ40P9aN0VY35h6uqkwbAO7oAFWqgh4LzfTPCzt7mx0EDW3mevSrzt01n-W7fRIwpLR0rv5UrIlAVeFjHiU4MIrvfOmeXEx7PIuuz6Dnt1djMcKZYeAHABQP",101,"oH1mZjlBrIwUNulq7ExC8JZevKTo").toString());
    }


    @Test
    public void createMenu() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.initMenu()).toString();
        System.out.println(menu);
        // System.out.println(WeChatUtil.creatMenu("_y7Drv7F5TUDd1bwzJh7p2oG2PiXPsdN5Zx5E0we6ng9gZTNoTkcoL6J7DcL2nAa_IF55SCbusDY_StOtf6bbak_B9FkHjXn-gTN4w0GubADVNdAFARJB",menu));
    }

    @Test
    public void createMenuNB() throws Exception {
        String menu = JSONObject.fromObject(WeChatUtil.DianYuanMenu()).toString();
        System.out.println(menu);
        System.out.println(WeChatUtil.creatMenuNB("rs_aTi_Tf3T-v32lsCFeMDHUZR62aHF16_A421Buz966Q0strC7aIp8zWICTXXIZ5DYtf5anLNSCEvHcpHHDs1U_IXSGQWjLGwnN4aJlmDTKeuy3zXgUwQfofvaEc9zNPBJjAJALXF",menu));
    }
}
