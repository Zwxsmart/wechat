package com.lanwantec.wechat.service;

import com.lanwantec.wechat.Constants;
import com.lanwantec.wechat.mapper.UserMapper;
import com.lanwantec.wechat.utils.RC4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String GetPhoneByOpenId(String openId) {
        Map<String, Object> map = userMapper.GetPhoneByOpenId(openId);
        if (map == null || map.get("phone") == null || map.get("phone").equals("")) {
            return null;
        } else {
            return RC4.ENCRY_RC4(map.get("phone").toString(), Constants.RC4_KEY);
        }
    }
}
