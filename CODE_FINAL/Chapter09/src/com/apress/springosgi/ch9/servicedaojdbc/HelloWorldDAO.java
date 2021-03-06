package com.apress.springosgi.ch9.servicedaojdbc;

import java.util.List;
import javax.sql.DataSource;

import com.apress.springosgi.ch9.service.HelloWorldService;
import com.apress.springosgi.ch9.model.HelloWorld;
import com.apress.springosgi.ch9.model.Person;

import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;

import org.springframework.dao.EmptyResultDataAccessException;

import java.util.Date;


public class HelloWorldDAO implements HelloWorldService {

    private SimpleJdbcTemplate simpleJdbcTemplate;
    
    public void setDataSource(DataSource dataSource) {
	this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }


    // JDBC-backed implementations of the methods on HelloWorld Service
    public HelloWorld findById(long id) {
	HelloWorld hw;
	try {
	    hw = (HelloWorld) this.simpleJdbcTemplate.queryForObject("select * from HelloWorld where id = ?", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),id);
	    // No ORM, individual lookup for Person object
	    long personIdToAdd = this.simpleJdbcTemplate.queryForLong("select translator_id from HelloWorld where id= ?", new Object[]{new Long(id)});
	    hw.setTranslator(this.simpleJdbcTemplate.queryForObject("select * from Person where id = ?", ParameterizedBeanPropertyRowMapper.newInstance(Person.class),personIdToAdd));
	} catch (EmptyResultDataAccessException ex) {
	    throw new EmptyResultDataAccessException("No HelloWorld by the provided id - " + ex,0);
	}	
	return hw;
    }
    
    public List<HelloWorld> findAll() {
	return this.simpleJdbcTemplate.query("select * from HelloWorld", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class));
    }

    public HelloWorld update(HelloWorld hw) {
	this.simpleJdbcTemplate.update("update HelloWorld set language=:language, message=:message, transdate=:transdate where id=:id", new BeanPropertySqlParameterSource(hw));
	return findById(hw.getId());
    }
    
    public void save(HelloWorld hw) {	
	// No ORM, individual insertion sequence
	// First insert Person
	this.simpleJdbcTemplate.update("insert into Person(FNAME,LNAME,hourlyRate) values(:firstName, :lastName, :hourlyRate)", new BeanPropertySqlParameterSource(hw.getTranslator()));
	
	// Lookup id assigned to new Person
	long personIdToInsert = this.simpleJdbcTemplate.queryForLong("select id from Person where FNAME= ? and LNAME = ?", new Object[]{new String(hw.getTranslator().getFirstName()), new String(hw.getTranslator().getLastName())});
	// Now insert HelloWorld object with reference to Person
	this.simpleJdbcTemplate.update("insert into HelloWorld(language,message,transdate,translator_id) values(?,?,?,?)", new Object[]{hw.getLanguage(), hw.getMessage(), hw.getTransdate(), new Long(personIdToInsert)});
    }
    
    public void delete(HelloWorld hw) {
	// No ORM, individual deletion sequence
	// Lookup Person id assigned to HelloWorld
	long personIdToDelete = this.simpleJdbcTemplate.queryForLong("select translator_id from HelloWorld where id= ?", new Object[]{new Long(hw.getId())});
	// Delete HelloWorld object
	this.simpleJdbcTemplate.update("delete from HelloWorld where id= ?", new Object[]{new Long(hw.getId())});
	// Now delete Person object
	this.simpleJdbcTemplate.update("delete from Person where id= ?", new Object[]{new Long(personIdToDelete)});
    }
    
    public List<HelloWorld> findByTranslatorFirstName(String firstName) {
	Person translator;
	try {
	    translator = this.simpleJdbcTemplate.queryForObject("select * from Person where FNAME= ?",ParameterizedBeanPropertyRowMapper.newInstance(Person.class),firstName);
	} catch (EmptyResultDataAccessException ex) {
	    throw new EmptyResultDataAccessException("No Person by the provided firstName - " + ex,0);
	}
	
	return this.simpleJdbcTemplate.query("select * from HelloWorld where translator_id = ?", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),translator.getId());
    }

    public List<HelloWorld> findByTranslatorLastName(String lastName) {
	Person translator;
	try {
	    translator = this.simpleJdbcTemplate.queryForObject("select * from Person where LNAME= ?",ParameterizedBeanPropertyRowMapper.newInstance(Person.class),lastName);
	} catch (EmptyResultDataAccessException ex) {
	    throw new EmptyResultDataAccessException("No Person by the provided lastName - " + ex,0);
	}	
	return this.simpleJdbcTemplate.query("select * from HelloWorld where translator_id = ?", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),translator.getId());

    }
    
    public List<HelloWorld> findByTranslatorHourlyRateOver(double hourlyRate) {
	return this.simpleJdbcTemplate.query("select * from HelloWorld where translator_id = ( select id from Person where hourlyRate > ?)", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),hourlyRate);
    }

    public List<HelloWorld> findByLanguage(String language) {
	return this.simpleJdbcTemplate.query("select id, language, message, transdate, translator_id from HelloWorld where language = ?", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),language);
    }
    
    public List<HelloWorld> findByMessage(String message) {
	return this.simpleJdbcTemplate.query("select id, language, message, transdate, translator_id from HelloWorld where message = ?", ParameterizedBeanPropertyRowMapper.newInstance(HelloWorld.class),message);
    }
    
    public void deleteMessage(long id) { 
	long personIdToDelete = this.simpleJdbcTemplate.queryForLong("select translator_id from HelloWorld where id= ?", new Object[]{new Long(id)});
	this.simpleJdbcTemplate.update("delete from HelloWorld where id= ?", new Object[]{new Long(id)});
	this.simpleJdbcTemplate.update("delete from Person where id= ?", new Object[]{new Long(personIdToDelete)});

    }

}

