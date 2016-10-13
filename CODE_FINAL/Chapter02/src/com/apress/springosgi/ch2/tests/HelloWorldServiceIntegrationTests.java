package com.apress.springosgi.ch2.tests;

import java.util.Date;
import java.util.List;

import com.apress.springosgi.ch2.hello.HelloWorld;
import com.apress.springosgi.ch2.hello.Person;
import com.apress.springosgi.ch2.hello.HelloWorldService;

import org.springframework.test.jpa.AbstractJpaTests;


public class HelloWorldServiceIntegrationTests extends AbstractJpaTests {

    private HelloWorldService helloWorldService;

    private long EnglishId;
    private long SpanishId;
    private long FrenchId;

    public void setHelloWorldService(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    protected String[] getConfigLocations() {
        return new String[] { "classpath:/com/apress/springosgi/ch2/tests/helloworld-service.xml" };
    }

    protected void onSetUpInTransaction() throws Exception {
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

   public void testFindById() {
        HelloWorld hw = helloWorldService.findById(EnglishId);
        assertNotNull(hw);
        assertEquals("English", hw.getLanguage());
    }

    public void testFindByDoesNotExistId() {
        HelloWorld hw = helloWorldService.findById(10000);
        assertNull(hw);
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
        List<HelloWorld> hws = helloWorldService.findByTranslatorFirstName("Bill");
        assertEquals(0, hws.size());
    }

    public void testFindByTranslatorLastNameDoesNotExist() {
        List<HelloWorld> hws = helloWorldService.findByTranslatorLastName("Matsusaka");
        assertEquals(0, hws.size());
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
        List<HelloWorld>  hws = helloWorldService.findByTranslatorFirstName(transFirstName);
        assertEquals(0, hws.size());
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
