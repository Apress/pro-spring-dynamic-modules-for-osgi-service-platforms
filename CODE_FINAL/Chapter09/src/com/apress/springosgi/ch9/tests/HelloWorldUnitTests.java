package com.apress.springosgi.ch9.tests;

import java.util.Date;
import junit.framework.TestCase;

import com.apress.springosgi.ch9.model.HelloWorld;
import com.apress.springosgi.ch9.model.Person;

public class HelloWorldUnitTests extends TestCase {

    private Person  trans1, trans2;
    private HelloWorld   hw1, hw2;
    protected void setUp() throws Exception {

        trans1 = new Person("John","Smith",45.00);

        trans2 = new Person();
        trans2.setFirstName("Carlos");
        trans2.setLastName("Perez");
        trans2.setHourlyRate(40.00);

        hw1 = new HelloWorld("English","Hello World!",new Date(),trans1);
        hw2 = new HelloWorld();
        hw2.setLanguage("Spanish");
        hw2.setMessage("Hola Mundo!");
        hw2.setTransdate(new Date());
        hw2.setTranslator(trans2);
    }
    public void testPerson() throws java.text.ParseException  {
        assertEquals("John", trans1.getFirstName());
        assertEquals("Smith", trans1.getLastName());
        assertEquals(45.00, trans1.getHourlyRate());

        assertEquals("Carlos", trans2.getFirstName());
        assertEquals("Perez", trans2.getLastName());
        assertEquals(40.00, trans2.getHourlyRate());
    }
  
     public void testHelloWorld()  {
        assertEquals("English", hw1.getLanguage());
        assertEquals("Hello World!", hw1.getMessage());
        assertEquals(new Date(), hw1.getTransdate());
        assertEquals(trans1, hw1.getTranslator());

        assertEquals("Spanish", hw2.getLanguage());
        assertEquals("Hola Mundo!", hw2.getMessage());
        assertEquals(new Date(), hw2.getTransdate());
        assertEquals(trans2, hw2.getTranslator());
    }
}
