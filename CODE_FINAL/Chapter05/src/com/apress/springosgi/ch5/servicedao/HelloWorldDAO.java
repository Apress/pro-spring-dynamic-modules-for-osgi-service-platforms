package com.apress.springosgi.ch5.servicedao;

import java.util.List;

import com.apress.springosgi.ch5.service.HelloWorldService;
import com.apress.springosgi.ch5.model.HelloWorld;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


@Repository
@Transactional
public class HelloWorldDAO implements HelloWorldService {

    @PersistenceContext
    private EntityManager em;

    public HelloWorld findById(long id) {
        return this.em.find(HelloWorld.class, id);
    }

    public List<HelloWorld> findAll() {
        return this.em.createQuery("select e from HelloWorld e").getResultList();
    }
   public HelloWorld update(HelloWorld hw) {
        return this.em.merge(hw);
    }

    public void save(HelloWorld hw) {
        this.em.persist(hw);
    }

    public void delete(HelloWorld hw) {
        this.em.remove(hw);
    }

    public List<HelloWorld> findByTranslatorFirstName(String firstName) {
        Query query = this.em.createQuery("select e from HelloWorld e where e.translator.firstName like :firstName");
        query.setParameter("firstName", firstName + "%");
        return query.getResultList();
    }

    public List<HelloWorld> findByTranslatorLastName(String lastName) {
        Query query = this.em.createQuery("select e from HelloWorld e where e.translator.lastName like :firstName");
        query.setParameter("lastName", lastName + "%");
        return query.getResultList();
    }

    public List<HelloWorld> findByTranslatorHourlyRateOver(double hourlyRate) {
        Query query = this.em.createQuery("select e from HelloWorld e where e.translator.hourlyRate > :hourlyRate");
        query.setParameter("hourlyRate", hourlyRate + "%");
        return query.getResultList();
    }

    public List<HelloWorld> findByLanguage(String language) {
        Query query = this.em.createQuery("select e from HelloWorld e where e.language like :language");
        query.setParameter("language", language + "%");
        return query.getResultList();
    }
  public List<HelloWorld> findByMessage(String message) {
        Query query = this.em.createQuery("select e from HelloWorld e where e.message like :message");
        query.setParameter("message", message + "%");
        return query.getResultList();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteMessage(long id) {
        HelloWorld hw = this.em.find(HelloWorld.class, id);
        this.em.remove(hw);
    }
}
