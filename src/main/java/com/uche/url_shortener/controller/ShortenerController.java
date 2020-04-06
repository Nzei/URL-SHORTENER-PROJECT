package com.uche.url_shortener.controller;

import com.uche.url_shortener.utility.Utils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/url")
public class ShortenerController {



    @GetMapping("/browser")
    public void browserVersion (HttpServletRequest httpServletRequest) {

        System.out.println(Utils.getBrowserName(httpServletRequest));
    }
}
