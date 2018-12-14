package com.belatrix.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="log")
public class Log {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private int id;

	@Column(name="date")
	private Date date;
	
	@Column(name="details", columnDefinition="TEXT")
	private String details;
	
	@Column(name="message")
	private boolean message;
	
	@Column(name="t_level")
	private int t_level;
	

	public Log(Date date, String details, boolean message, int t_level) {
		super();
		this.date = date;
		this.details = details;
		this.message = message;
		this.t_level = t_level;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public boolean isMessage() {
		return message;
	}

	public void setMessage(boolean message) {
		this.message = message;
	}

	public int getT_level() {
		return t_level;
	}

	public void setT_level(int t_level) {
		this.t_level = t_level;
	}

	@Override
	public String toString() {
		return "Log [id=" + id + ", date=" + date + ", details=" + details + ", message=" + message + ", t_level="
				+ t_level + "]";
	}
	
	
	


	
	
	
}
