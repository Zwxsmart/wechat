package com.lanwantec.wechat.service;

import com.lanwantec.entity.resp.AccessToken;

/**
 * Created by Rainy on 2017/1/3/0003.
 */
public interface TokenService {

    AccessToken token(String brandNo);

}
