package com.lanwantec.wechat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Rainy on 2016/12/22/0022.
 */
@Controller
@RequestMapping("/wx")
public class FileController {

    @RequestMapping("/scan")
    public ModelAndView scan(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/wechat/scan");
        return mv;
    }

}
