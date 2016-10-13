package com.apress.springosgi.ch9.testsosgi;

import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

import org.osgi.framework.Constants;

import com.apress.springosgi.ch9.testsosgi.testivyprovisions.LocalFileSystemIvyRepository;


public class BootstrapTest extends AbstractConfigurableBundleCreatorTests {

    /* Overrides ./target/test-classes for looking up test classes */
    public String getRootPath() {
	return"file:./classes/";
    }	

    /* Overrides Maven's default location(.m2/repository) for looking up bundles 
       needed to bootstrap Spring-DM's testing framework  */
    public ArtifactLocator getLocator() {
	/*
	  LocalFileSystemIvyRepository is designed to lookup bundles downloaded by Apache Ivy by default in .ivy2/cache, 
	  and can be further configured to use another directory using the localRepository property in JUnit's Ant task  */
	return new LocalFileSystemIvyRepository();
    }	

    
    /* Method invoked when OSGi test environment is started */
    public void testOsgiPlatformStarts() throws Exception {
	/* Print out properties available in the OSGi test environment */
	System.out.println("OSGi Framework Vendor : " + bundleContext.getProperty(Constants.FRAMEWORK_VENDOR));
	System.out.println("OSGi Framework Version : " + bundleContext.getProperty(Constants.FRAMEWORK_VERSION));
	System.out.println("OS Name and Version :  " + bundleContext.getProperty(Constants.FRAMEWORK_OS_NAME) + "-" + bundleContext.getProperty(Constants.FRAMEWORK_OS_VERSION));
    }
}
