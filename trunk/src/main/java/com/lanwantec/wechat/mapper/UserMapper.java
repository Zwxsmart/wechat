package com.lanwantec.wechat.mapper;

import com.lanwantec.wechat.mybatis.TargetDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by Rainy on 2016/12/7/0007.
 */
@Component
@Mapper
public interface UserMapper {

    @TargetDataSource("primary")
    @Select("SELECT * FROM tbl_UserFromBrand WHERE brandNo = #{brandNo} AND openId =  #{openId}")
    Map<String,Object> GetPhoneByOpenId(@Param("brandNo") String brandNo,@Param("openId") String openId);

    @TargetDataSource("primary")
    @Select("SELECT * FROM tbl_Designer WHERE brandNo = #{brandNo} AND openId = #{openId} AND checkState = 2 ")
    Map<String,Object> GetDesigner(@Param("brandNo") String brandNo,@Param("openId") String openId);

    @TargetDataSource("primary")
    @Select("SELECT * FROM tbl_Operator a LEFT JOIN tbl_OperToBrand b ON a.operNo = b.operNo WHERE a.openId = #{openId} AND b.brandNo = #{brandNo}")
    Map<String,Object> GetOperator(@Param("brandNo") String brandNo,@Param("openId") String openId);

    @TargetDataSource("primary")
    @Select("SELECT * FROM tbl_WechatGroup WHERE brandNo = #{brandNo} AND groupName LIKE CONCAT('%',#{groupName},'%') AND isValid = 1")
    Map<String,Object> GetWeChatGroupId(@Param("brandNo") String brandNo,@Param("groupName") String groupName);

}
