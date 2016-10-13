package com.apress.springosgi.ch3.service;

import com.apress.springosgi.ch3.model.HelloWorld;

public interface HelloWorldService {

    public HelloWorld find();

    public HelloWorld update(HelloWorld hw);

    public void save(HelloWorld hw );

    public void delete(HelloWorld hw);

}
