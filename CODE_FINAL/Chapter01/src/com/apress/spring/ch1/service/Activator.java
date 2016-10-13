package com.apress.springosgi.ch1.service;

import com.apress.springosgi.ch1.hello.HelloWorld;


import java.util.Properties;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator
{
 public void start(BundleContext context)
 {
  Properties props = new Properties();
  context.registerService(
   HelloWorld.class.getName(), new HelloWorldService(),props);
 }
	
 public void stop(BundleContext context)
 {
  //Note: The service is automatically unregistered
 }
}
