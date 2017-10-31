package com.lanwantec.wechat.mapper;

import com.lanwantec.wechat.mybatis.TargetDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
@Mapper
public interface UserMapper {

    @TargetDataSource("primary")
    @Select("SELECT phone FROM tbl_User WHERE weixin_attention_id = #{openid}")
    Map<String,Object> GetPhoneByOpenId(@Param("openid") String openid);

}
