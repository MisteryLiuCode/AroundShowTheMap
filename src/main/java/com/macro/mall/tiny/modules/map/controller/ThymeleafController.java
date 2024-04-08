package com.macro.mall.tiny.modules.map.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ThymeleafController {

    @GetMapping("/")
    public ModelAndView getStudent(){

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("searchMap");
        return modelAndView;
    }
}
