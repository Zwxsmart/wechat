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
    public Boolean GetPhoneByOpenId(String openId,String brandNo) {
        Map<String, Object> map = userMapper.GetPhoneByOpenId(openId, brandNo);
        if (map == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Boolean IsDesigner(String brandNo, String openId) {
        Map<String, Object> map = userMapper.GetDesigner(brandNo,openId);
        if(map == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Boolean IsOperator(String brandNo, String openId) {
        Map<String, Object> map = userMapper.GetOperator(brandNo,openId);
        if(map == null){
            return false;
        }else{
            return true;
        }
    }
}
