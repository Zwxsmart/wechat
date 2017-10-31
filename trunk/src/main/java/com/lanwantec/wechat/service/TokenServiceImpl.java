package com.lanwantec.wechat.service;

import com.lanwantec.entity.resp.AccessToken;
import com.lanwantec.wechat.mapper.TokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Rainy on 2017/1/3/0003.
 */
@Service
public class TokenServiceImpl implements TokenService{

    @Autowired
    private TokenMapper tokenMapper;

    @Override
    public AccessToken token(String brandNo) {
        return tokenMapper.accessToken(brandNo);
    }
}
