package com.apress.springosgi.ch9.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name="HelloWorld")
public class HelloWorld { 

    @Id
    @GeneratedValue
    private long id;
    @Column
    private String language;
    @Column
    private String message;
    @Temporal(TemporalType.DATE)
    private Date transdate;
    @OneToOne(cascade = CascadeType.ALL)
    private Person translator;

    public void setId(long id) { 
	this.id = id;
    }
    
    public long getId() { 
	return id;
    }

    public void setLanguage(String language) { 
	this.language = language;
    }

    public String getLanguage() { 
	return language;
    }
    
    public void setMessage(String message) { 
	this.message = message;
    }

    public String getMessage() { 
	return message;
    }

    public void setTransdate(Date transdate) { 
	this.transdate = transdate;
    }
    
    public Date getTransdate() { 
	return transdate;
    }

    public Person getTranslator() { 
	return translator;
    }

    public void setTranslator(Person translator) { 
	this.translator = translator;
	
    }

    public HelloWorld(String language, String message, Date transdate, Person translator) { 
	this.language = language;
	this.message = message;
	this.transdate = transdate;
	this.translator = translator;	
    }

    public HelloWorld() { 
    }
}
