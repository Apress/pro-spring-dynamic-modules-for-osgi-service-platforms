package com.apress.springosgi.ch3.servicedao;

import com.apress.springosgi.ch3.service.HelloWorldService;

import java.util.Date;

import com.apress.springosgi.ch3.model.HelloWorld;


public class HelloWorldDAO implements HelloWorldService {

    public HelloWorld find() {
        // Model object will be create here
        HelloWorld hw = new HelloWorld("Hello World",new Date(),1.0);
        return hw;
    }

    public HelloWorld update(HelloWorld hw) {
        hw.setCurrentTime(new Date());
        return hw;
    }

    public void save(HelloWorld hw) {
        throw new UnsupportedOperationException("Can't save anything, no RDBMS back here");
    }

    public void delete(HelloWorld hw) {
      throw new UnsupportedOperationException("Can't delete anything, no RDBMS back here");
    }

}
