package com.lanwantec.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Rainy on 2016/12/6/0006.
 */
@Controller
@RequestMapping("/user")
public class UserController {


@RequestMapping("test")
public ModelAndView test(){
    return new ModelAndView("/templates/wechat/scan1.ftl");
}
}
