package com.apress.springosgi.ch5.web;

import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.ModelMap;

import com.apress.springosgi.ch5.model.HelloWorld;
import com.apress.springosgi.ch5.model.Person;
import com.apress.springosgi.ch5.service.HelloWorldService;


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
    @ModelAttribute("helloworlds")
    public List<HelloWorld> home() {
        return this.helloWorldService.findAll();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String translator(@RequestParam("id") Long id, ModelMap model) {
     Person translator = helloWorldService.findById(id).getTranslator();
     List<HelloWorld> hws = helloWorldService.findAll();
     model.addAttribute("helloworlds",hws);
     model.addAttribute("translator",translator);
     return "home";
    }

    @RequestMapping(method = RequestMethod.GET)
    public String deleteMessage(@RequestParam("id")
    Long id) {
        helloWorldService.deleteMessage(id);
        return "redirect:home";
    }
}
