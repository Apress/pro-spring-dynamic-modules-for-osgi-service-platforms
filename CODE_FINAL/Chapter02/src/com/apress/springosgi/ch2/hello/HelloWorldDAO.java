package com.apress.springosgi.ch2.hello;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import org.springframework.orm.jpa.support.JpaDaoSupport;

public class HelloWorldDAO extends JpaDaoSupport implements HelloWorldService {

    public HelloWorld findById(long id) {
        return getJpaTemplate().find(HelloWorld.class, id);
    }

    public List<HelloWorld> findAll() {
        return getJpaTemplate().find("select e from HelloWorld e");
    }
        
    public HelloWorld update(HelloWorld emp) {
        return getJpaTemplate().merge(emp);
    }

    public void save(HelloWorld emp) {
        getJpaTemplate().persist(emp);
    }

    public void delete(HelloWorld emp) {
        getJpaTemplate().remove(emp);
    }


    public List<HelloWorld> findByTranslatorFirstName(String firstName) {
        return getJpaTemplate().find("select e from HelloWorld e where e.translator.firstName = ?1", firstName);
    }

    public List<HelloWorld> findByTranslatorLastName(String lastName) {
        return getJpaTemplate().find("select e from HelloWorld e where e.translator.lastName = ?1", lastName);
    }

    public List<HelloWorld> findByTranslatorHourlyRateOver(double hourlyRate) {
        return getJpaTemplate().find("select e from HelloWorld e where e.translator.hourlyRate > ?1", hourlyRate);
    }

    public List<HelloWorld> findByLanguage(String language) {
        return getJpaTemplate().find("select e from HelloWorld e where e.language = ?1", language);
    }

    public List<HelloWorld> findByMessage(String message) {
        return getJpaTemplate().find("select e from HelloWorld e where e.message = ?1", message);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMessage(long id) {
        HelloWorld hw = getJpaTemplate().find(HelloWorld.class, id);
        getJpaTemplate().remove(hw);

    }

}
