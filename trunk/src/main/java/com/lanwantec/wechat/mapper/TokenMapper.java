package com.lanwantec.wechat.mapper;

import com.lanwantec.entity.resp.AccessToken;
import com.lanwantec.wechat.mybatis.TargetDataSource;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

/**
 * Created by Rainy on 2016/12/5/0005.
 */
@Component
@Mapper
public interface TokenMapper {

    @TargetDataSource("primary")
    @Select("SELECT * FROM tbl_Token WHERE brandNo = #{brandNo} AND isValid = 1")
    AccessToken accessToken(@Param("brandNo") String brandNo);

    @TargetDataSource("primary")
    @Insert("INSERT INTO tbl_Token (brandNo, accessToken, accessUpdate, jsApiTicket, jsApiUpdate) VALUES " +
            "(#{token.brandNo},#{token.accessToken},#{token.accessUpdate},#{token.jsApiTicket},#{token.jsApiUpdate})")
    Integer addToken(@Param("token") AccessToken token);

    @TargetDataSource("primary")
    @Update("UPDATE tbl_Token SET accessToken = #{token.accessToken},accessUpdate = #{token.accessUpdate},jsApiTicket = #{token.jsApiTicket},jsApiUpdate = #{token.jsApiUpdate} WHERE brandNo = #{token.brandNo}")
    Integer updateToken(@Param("token") AccessToken token);

}
