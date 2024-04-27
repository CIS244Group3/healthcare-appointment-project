package com.group3project.Patient_Doctor;

import java.time.LocalDate;

public class Patient {
	private String name;
	private String address;
	private Insurance insurance;
	private String username;
	private String email;
	private int id;
	private LocalDate dob;
	private String phoneNumber;
	private String gender;

	public Patient(int id, String name, LocalDate dob, String username, String email, String phoneNumber,
			String gender) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.id = id;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.gender = gender;

	}

	// Getters
	public String getName() {
		return this.name;
	}

	public String getUsername() {
		return this.username;
	}

	public String getEmail() {
		return this.email;
	}

	public String address() {
		return address;
	}

	public String getAddress() {
		return this.address;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public String getGender() {
		return this.gender;
	}

	public int getId() {
		return this.id;
	}

	public LocalDate getDob() {
		return this.dob;
	}

	public boolean hasIsurance() {
		return insurance != null;
	}

	public Insurance getInsurance() {
		return this.insurance;
	}

	// Setters

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
