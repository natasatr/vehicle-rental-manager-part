package com.etfbl.ip.rentalcompany.beans;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.etfbl.ip.rentalcompany.util.ConnectionPool;
import com.etfbl.ip.rentalcompany.util.DBUtil;

public class UserBean {
	private Long id;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String role;
	private boolean loggedIn = false;
	
	private static final String MANAGER = "MANAGER";
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	public void setLoggedIn(boolean loggedIn) {
		this.loggedIn = loggedIn;
	}

	
	public boolean isRoleManager() {
		return MANAGER.equals(this.role);
	}

}
