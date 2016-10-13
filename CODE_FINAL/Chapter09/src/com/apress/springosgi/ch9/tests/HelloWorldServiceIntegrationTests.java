package com.apress.springosgi.ch9.tests;

import java.util.Date;
import java.util.List;

import com.apress.springosgi.ch9.model.HelloWorld;
import com.apress.springosgi.ch9.model.Person;
import com.apress.springosgi.ch9.service.HelloWorldService;


import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.ObjectRetrievalFailureException;

public class HelloWorldServiceIntegrationTests extends AbstractTransactionalDataSourceSpringContextTests {


    protected HelloWorldService helloWorldService;

    public void setHelloWorldService(HelloWorldService helloWorldService) {
	this.helloWorldService = helloWorldService;
    }


    private static long EnglishId;
    private static long SpanishId;
    private static long FrenchId;

    private static boolean databasePopulated = false;
    
    
    protected void populateDatabase() {
	executeSqlScript("classpath:/com/apress/springosgi/ch9/tests/helloworld-hsqldb.sql", false);
        databasePopulated = true;
    }
    

    protected String[] getConfigLocations() {
        return new String[] { "classpath:/com/apress/springosgi/ch9/tests/helloworld-service.xml" };
    }

    protected void onSetUpBeforeTransaction() throws Exception {
        // This method is executed before each test method
        // Database tables and data only inserted once
	if (!databasePopulated) {
	    populateDatabase();

	    HelloWorld hw1 = new HelloWorld("English",
					    "Hello World!", new Date(),
					    new Person("John","Smith",45.00));       
	    HelloWorld hw2 = new HelloWorld("Spanish",
					    "Hola Mundo!", new Date(),
					    new Person("Carlos","Perez",40.00));
	    HelloWorld hw3 = new HelloWorld("French",
					    "Bonjour Monde!", new Date(),
					    new Person("Pierre","LeClair",40.00));
	    helloWorldService.save(hw1);
	    helloWorldService.save(hw2);
	    helloWorldService.save(hw3);

	    
	    EnglishId = helloWorldService.findByLanguage("English").get(0).getId();
	    SpanishId = helloWorldService.findByLanguage("Spanish").get(0).getId();
	    FrenchId = helloWorldService.findByLanguage("French").get(0).getId();

        }

    }

    public void testFindById() {
        HelloWorld hw = helloWorldService.findById(EnglishId);
        assertNotNull(hw);
        assertEquals("English", hw.getLanguage());
    }


    public void testFindByDoesNotExistId() {
	try { 
	    HelloWorld hw = helloWorldService.findById(10000);
	} catch(EmptyResultDataAccessException expected) {
	    assertTrue(true);
	}
    }
    
    public void testFindByLanguage() {
        List<HelloWorld> hws = helloWorldService.findByLanguage("Spanish");
        assertEquals(1, hws.size());
        HelloWorld hw = hws.get(0);
        assertEquals("Hola Mundo!", hw.getMessage());
    }

    public void testFindByBadLanguage() {
        List<HelloWorld> hws = helloWorldService.findByLanguage("Catalan");
	assertEquals(0, hws.size());
    }
    
    public void testFindByTranslatorFirstName() {
        List<HelloWorld> hws = helloWorldService.findByTranslatorFirstName("John");
	assertEquals(1, hws.size());
        HelloWorld hw = hws.get(0);
	assertEquals(EnglishId, hw.getId());
    }
    
    public void testFindByTranslatorLastName() {
        List<HelloWorld> hws = helloWorldService.findByTranslatorLastName("LeClair");
        assertEquals(1, hws.size());
        HelloWorld hw = hws.get(0);
        assertEquals(FrenchId, hw.getId());
    }

    public void testFindByTranslatorFirstNameDoesNotExist() {
	try{ 
	    List<HelloWorld> hws = helloWorldService.findByTranslatorFirstName("Bill");
	} catch(EmptyResultDataAccessException expected) {
	    assertTrue(true);
	}
    }
    
    public void testFindByTranslatorLastNameDoesNotExist() {
	try { 
	    List<HelloWorld> hws = helloWorldService.findByTranslatorLastName("Matsusaka");
	} catch(EmptyResultDataAccessException expected) {
	    assertTrue(true);
	}
    }
    
    public void testFindByTranslatorHourlyRateOver() {
        List<HelloWorld> hws = helloWorldService.findByTranslatorHourlyRateOver(42.00);
	assertEquals(1, hws.size());
	
    }
    public void testModifyHelloWorldMessage() {
        String oldHelloMessage = "Bonjour Monde!";
        String newHelloMessage = "Bonjour Le Monde!";
        HelloWorld hw = helloWorldService.findByLanguage("French").get(0);
        hw.setMessage(newHelloMessage);
	
        HelloWorld hw2 = helloWorldService.update(hw);
	assertEquals(newHelloMessage, hw2.getMessage());
	
        List<HelloWorld> hw3 = helloWorldService.findByMessage(oldHelloMessage);
	assertEquals(0, hw3.size());
	
        hw3 = helloWorldService.findByMessage(newHelloMessage);
	assertEquals(1, hw3.size());
        HelloWorld newhw3 = hw3.get(0);
	assertEquals(newHelloMessage, newhw3.getMessage());
    }

    public void testDeleteHelloWorldCascade() {
        String transFirstName = "Carlos";
        HelloWorld hw = helloWorldService.findByTranslatorFirstName(transFirstName).get(0);
        int transCountBefore = countRowsInTable("person");
        int helloCountBefore = countRowsInTable("helloworld");
        helloWorldService.delete(hw);
	try { 
	    List<HelloWorld>  hws = helloWorldService.findByTranslatorFirstName(transFirstName);
	} catch(EmptyResultDataAccessException expected) {
	    assertTrue(true);
	}
        int transCountAfter = countRowsInTable("person");
        int helloCountAfter = countRowsInTable("helloworld");
	assertEquals(transCountBefore -1, transCountAfter);
	assertEquals(helloCountBefore -1, helloCountAfter);
    }
    
    public void testFindAll() {
        List<HelloWorld> hws = helloWorldService.findAll();
	assertEquals(3, hws.size());
    }
}
