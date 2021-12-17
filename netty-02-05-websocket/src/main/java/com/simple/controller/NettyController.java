package com.simple.controller;

import com.simple.server.NettyServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 功能描述:
 *
 * @author: WuChengXing
 * @create: 2021-12-18 00:08
 **/
@Controller
public class NettyController {

    @Resource
    private NettyServer nettyServer;

    @RequestMapping("/index")
    public String index(Model model) {
        model.addAttribute("name", "test");
        return "index";
    }
}
