package com.cglib.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.cglib.utils.AbstractEntity;

@Entity
@Table(name = "Member"/*, schema = "dbo", catalog = "test"*/)
public class Member extends AbstractEntity{
	private Integer sid;
	private String member_name;
	private String sex;
	private String tel;
	private double balance;
	private String card_number;
	
	/** default constructor */
	public Member() {
	}

	/** full constructor */
	public Member(String member_name,String sex, String tel,double balance, String card_number) {
		this.member_name = member_name;
		this.sex = sex;
		this.tel = tel;
		this.balance = balance;
		this.card_number = card_number;
	}
	
	@Id
	@GeneratedValue
	@Column(name = "sid", unique = true, nullable = false)
	public Integer getSid() {
		return sid;
	}
	public void setSid(Integer sid) {
		this.sid = sid;
	}
	@Column(name = "member_name", length = 100)
	public String getMember_name() {
		return member_name;
	}
	public void setMember_name(String member_name) {
		this.member_name = member_name;
	}
	@Column(name = "sex", length = 50)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "tel", length = 11)
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	@Column(name = "balance", length = 9)
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	@Column(name = "card_number", length = 50)
	public String getCard_number() {
		return card_number;
	}
	public void setCard_number(String card_number) {
		this.card_number = card_number;
	}

	
}
