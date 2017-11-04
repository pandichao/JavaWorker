package com.cgxt.bean;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "rose")
public class Rose implements Serializable{
	private Long id;
	private String roseName;

	public Rose() {
	}
	
	public Rose(Long id, String roseName) {
		this.id = id;
		this.roseName = roseName;
	}

	@Id
	@GeneratedValue
	@Column(name = "id",length = 15,unique = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="roseName",length = 50)
	public String getRoseName() {
		return roseName;
	}
	public void setRoseName(String roseName) {
		this.roseName = roseName;
	}

	@Override
	public String toString() {
		return "Rose [id=" + id + ", roseName=" + roseName + "]";
	}
	
}
