package com.cgxt.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "User")
public class User implements java.io.Serializable {

	private Integer id;
	private String age;
	private String userName;
	private String password;
	private Long roseCode;

    public User(){
    }
	
	public User(Integer id, String age, String userName, String password,
			Long roseCode) {
		this.id = id;
		this.age = age;
		this.userName = userName;
		this.password = password;
		this.roseCode = roseCode;
	}

	@Id
	@GeneratedValue
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "age", length = 50)
	public String getAge() {
		return this.age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	@Column(name = "userName", length = 50)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "password", length = 50)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
    @Column(name = "roseCode",length = 15)
	public Long getRoseCode() {
		return roseCode;
	}

	public void setRoseCode(Long roseCode) {
		this.roseCode = roseCode;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", age=" + age + ", userName=" + userName
				+ ", password=" + password + ", roseCode=" + roseCode + "]";
	}

}