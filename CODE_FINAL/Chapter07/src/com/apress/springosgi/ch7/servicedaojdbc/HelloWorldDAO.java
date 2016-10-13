package com.apress.springosgi.ch7.servicedaojdbc;

import java.util.List;
import javax.sql.DataSource;

import com.apress.springosgi.ch7.service.HelloWorldService;
import com.apress.springosgi.ch7.model.HelloWorld;
import com.apress.springosgi.ch7.model.Person;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class HelloWorldDAO implements HelloWorldService {

    private JdbcTemplate jdbcTemplate;

    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    // JDBC-backed implementations of the methods on HelloWorld Service
    public HelloWorld findById(long id) {
	String sql = "select id, language, message, transdate,translator_id from HelloWorld where id = ?";

	RowMapper mapper = new RowMapper() {		
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		    HelloWorld hw = new HelloWorld();
		    hw.setId(rs.getLong("id"));
		    hw.setLanguage(rs.getString("language"));
		    hw.setMessage(rs.getString("message"));
		    hw.setTransdate(rs.getDate("transdate"));
		    // Call auxilary method to obtain Person(Tranlsator) object
		    hw.setTranslator(findPersonById(rs.getLong("translator_id")));
		    return hw;
		}
	    };	
	HelloWorld hw = (HelloWorld) this.jdbcTemplate.queryForObject(sql, new Object[] {Long.valueOf(id)}, mapper);
	return hw;
    }
    
    public List<HelloWorld> findAll() {
	return this.jdbcTemplate.queryForList("select * from HelloWorld");
    }

    public HelloWorld update(HelloWorld hw) {
	// First update the Person object
	Person hwPerson = hw.getTranslator();
	this.jdbcTemplate.update("update Person set FNAME = ?, LNAME = ?, hourlyRate = ? where id = ?", new Object[] {hwPerson.getFirstName(), hwPerson.getLastName(), hwPerson.getHourlyRate(),new Long(hwPerson.getId())});
	// Next, update the HelloWorld object
	this.jdbcTemplate.update("update HelloWorld set language = ?, message = ?, transdate = ? where id = ?", new Object[] {hw.getLanguage(), hw.getMessage(), hw.getTransdate(), new Long(hw.getId())});
	return 	new HelloWorld();
    }
    
    public void save(HelloWorld hw) {	
	// First get the Person object
	Person hwPerson = hw.getTranslator();
	this.jdbcTemplate.update("insert into Person(FNAME, LNAME, hourlyRate) values (?, ?, ?)", new Object[] {hwPerson.getFirstName(), hwPerson.getLastName(), hwPerson.getHourlyRate()});
	// Next, insert the HelloWorld object
	this.jdbcTemplate.update("insert into HelloWorld(language, message, transdate) values (?, ?, ?)", new Object[] {hw.getLanguage(), hw.getMessage(), hw.getTransdate(), new Long(hw.getId())});
    }
    
    public void delete(HelloWorld hw) {
	// First get the Person object
	Person hwPerson = hw.getTranslator(); 
        // Next, delete both objects
	this.jdbcTemplate.update("delete from Person where id = ?", new Object[]{new Long(hwPerson.getId())});
	this.jdbcTemplate.update("delete from HelloWorld where id = ?", new Object[]{new Long(hw.getId())});
    }
    
    public List<HelloWorld> findByTranslatorFirstName(String firstName) {
	return this.jdbcTemplate.queryForList("select * from HelloWorld where translator_id = ( select id from Person where FNAME= ?)", new Object[] {new String(firstName)});
    }

    public List<HelloWorld> findByTranslatorLastName(String lastName) {
	return this.jdbcTemplate.queryForList("select * from HelloWorld where translator_id = ( select id from Person where LNAME= ?)", new Object[] {new String(lastName)});
    }
    
    public List<HelloWorld> findByTranslatorHourlyRateOver(double hourlyRate) {
	return this.jdbcTemplate.queryForList("select * from HelloWorld where translator_id = ( select id from Person where hourlyRate= ?)", new Object[] {new Double(hourlyRate)});
    }

    public List<HelloWorld> findByLanguage(String language) {
	return this.jdbcTemplate.queryForList("select * from HelloWorld where language = ?", new Object[] {new String(language)});
    }
    
    public List<HelloWorld> findByMessage(String message) {
	return this.jdbcTemplate.queryForList("select * from HelloWorld where message = ?", new Object[] {new String(message)});
    }
    
    public void deleteMessage(long id) { 
	this.jdbcTemplate.update("delete from HelloWorld where id = ?", new Object[]{new Long(id)});
    }

    private Person findPersonById(long id) { 
	String sqlPerson = "select id, FNAME, hourlyRate, LNAME from Person where id = ?";

	RowMapper mapperPerson = new RowMapper() {		
		public Object mapRow(ResultSet rsPerson, int rowNumPerson) throws SQLException {
		    Person hwperson = new Person();
		    hwperson.setId(rsPerson.getLong("id"));
		    hwperson.setFirstName(rsPerson.getString("FNAME"));
		    hwperson.setLastName(rsPerson.getString("LNAME"));
		    hwperson.setHourlyRate(rsPerson.getDouble("hourlyRate"));				
		    return hwperson;
		}
	    };
	return (Person) this.jdbcTemplate.queryForObject(sqlPerson, new Object[] {Long.valueOf(id)}, mapperPerson);
    }
}

