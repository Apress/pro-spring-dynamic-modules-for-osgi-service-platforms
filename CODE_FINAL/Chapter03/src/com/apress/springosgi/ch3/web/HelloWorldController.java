package com.apress.springosgi.ch3.web;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.ModelMap;

import com.apress.springosgi.ch3.model.HelloWorld;
import com.apress.springosgi.ch3.service.HelloWorldService;



@Controller
public class HelloWorldController {

    private HelloWorldService helloWorldService;

   
    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public HelloWorldService getHelloWorldService() {
        return helloWorldService;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ModelAttribute("helloworld")
    public HelloWorld home() {
        return this.helloWorldService.find();
    }
}
