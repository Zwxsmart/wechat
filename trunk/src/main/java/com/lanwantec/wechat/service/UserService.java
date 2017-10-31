package com.lanwantec.wechat.service;

import java.util.Map;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
public interface UserService {

    Boolean GetPhoneByOpenId(String openId,String brandNo);


    Boolean IsDesigner(String brandNo,String openId);


    Boolean IsOperator(String brandNo,String openId);
}
