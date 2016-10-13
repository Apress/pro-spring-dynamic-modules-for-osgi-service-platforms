package com.apress.springosgi.ch1.service;

import com.apress.springosgi.ch1.hello.HelloWorld;


public class HelloWorldService implements HelloWorld
{
 public String hello() { 
  return "Hello World 1.0";
      }
}