package com.lanwantec.wechat.mybatis;

import java.lang.annotation.*;

/**
 * 
 * @ClassName: TargetDataSource 
 * @Description: TODO
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface TargetDataSource {
	String value();
}
