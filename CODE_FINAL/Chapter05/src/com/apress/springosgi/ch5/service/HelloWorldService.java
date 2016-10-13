package com.apress.springosgi.ch5.service;

import com.apress.springosgi.ch5.model.HelloWorld;

import java.util.List;

public interface HelloWorldService { 

    public HelloWorld findById(long id);
    
    public List<HelloWorld> findAll();
        
    public HelloWorld update(HelloWorld hw);

    public void save(HelloWorld hw);
    
    public void delete(HelloWorld hw);

    public void deleteMessage(long id);

    public List<HelloWorld> findByTranslatorFirstName(String firstName);

    public List<HelloWorld> findByTranslatorLastName(String lastName);

    public List<HelloWorld> findByTranslatorHourlyRateOver(double hourlyRate);

    public List<HelloWorld> findByLanguage(String language);

    public List<HelloWorld> findByMessage(String message);

}
