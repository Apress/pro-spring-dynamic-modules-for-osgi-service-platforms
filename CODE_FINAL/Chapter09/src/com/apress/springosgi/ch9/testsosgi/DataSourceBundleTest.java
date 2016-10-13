package com.apress.springosgi.ch9.testsosgi;

import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;


import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


import com.apress.springosgi.ch9.testsosgi.testivyprovisions.LocalFileSystemIvyRepository;


public class DataSourceBundleTest extends AbstractConfigurableBundleCreatorTests {

    /* Overrides ./target/test-classes for looking up test classes */
    protected String getRootPath() {
	return"file:./classes/";
    }	

    /* Overrides Maven's default location(.m2/repository) for looking up bundles 
       needed to bootstrap Spring-DM's testing framework  */
    protected ArtifactLocator getLocator() {
	/*
	  LocalFileSystemIvyRepository is designed to lookup bundles downloaded by Apache Ivy by default in .ivy2/cache, 
	  and can be further configured to use another directory using the localRepository property in JUnit's Ant task  */
	return new LocalFileSystemIvyRepository();
    }	
    

    /* In addition to the bundles required by the Spring-DM testing framework(loaded by default)  
       getTestBundlesNames loads additional bundles from the same ArtifactLocator (Custom Apache Ivy in this case)
       the list follows a convention group,id, version for locating bundles */     
    protected String[] getTestBundlesNames() {
	List col = new ArrayList();    
	col.add("com.mysql.jdbc, com.springsource.com.mysql.jdbc, 5.1.6");
	col.add("org.apache.commons, com.springsource.org.apache.commons.dbcp, 1.2.2.osgi");
	col.add("org.apache.commons, com.springsource.org.apache.commons.pool, 1.3.0");
	return (String[]) col.toArray(new String[col.size()]);
    }

    /* Since an application's bundles are not located in the same repository (ArtifcatLocator)
       getTestBundles loads additional bundles by file system directories 
       NOTE:getTestBundles takes precedence over getTestBundlesNames, therefore its necessary to make an explicit call 
       to getTestBundlesName if its required to load bundles from the ArtifactLocator */       
    protected Resource[] getTestBundles() {
	// Explicit call to getTestBundlesNames to obtain dependencies from repository
	Resource[] repoBundles = locateBundles(getTestBundlesNames());
	// Declare test bundles that aren't located in the repository(ArtifactLocator)
	Resource[] testBundles = new Resource[] { 
	    new FileSystemResource("dist/ch9/helloworld-db.jar")
	};
	// Join both repository(ArtifcatLocator) bundles and those outside the repository
	List<Resource> allBundles = new ArrayList<Resource>();
	Collections.addAll(allBundles, repoBundles);
	Collections.addAll(allBundles, testBundles);
	return allBundles.toArray(new Resource[] {});
    }    
    

    /* Test method for DataSource service */ 
    public void testDataSourceService() {	
	//Wait until the dataSource bundle context is generated, to guarantee the DataSource service is published
	waitOnContextCreation("com.apress.springosgi.ch9.db");
	//The superclass provides access to the testing bundle's context, which can then get a hold of published services
        ServiceReference ref = bundleContext.getServiceReference(DataSource.class.getName());
        assertNotNull("Service Reference is null", ref);	
        try {
            DataSource dataSourceService = (DataSource) bundleContext.getService(ref);
	    assertNotNull("Cannot find the service", dataSourceService);
	    // Start connection and query sequence to the DB using OSGi backed service
	    Connection dataSourceConnection = dataSourceService.getConnection();
	    Statement statement = dataSourceConnection.createStatement();
	    ResultSet result = statement.executeQuery("select * from HelloWorld where language = 'Spanish'");	    
	    // Verify existence of first and only row in set
	    boolean rs = result.next();
	    // If the row exists in the DB, check the message value against the expected value
	    if (rs) { 
		assertEquals("Hola Mundo!",result.getString("message"));
	    } else { 
		throw new Exception("No record in DB belonging to Spanish tranlsation");
	    }
	    result.close();
	    statement.close();
	    dataSourceConnection.close();
	} catch (Exception exc) { 
	    exc.printStackTrace();
	} finally {
	    bundleContext.ungetService(ref);	    
	}
    }
}
