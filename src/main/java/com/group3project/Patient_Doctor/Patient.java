package com.group3project.Patient_Doctor;

public class Patient {
	private String name;
	private int age;
	private String address;
	private Insurance insurance;

	public Patient(String name, int age, String address, Insurance insurance) {
		this.name = name;
		this.age = age;
		this.address = address;
		this.insurance = insurance;

	}

	// Getters
	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String address() {
		return address;
	}

	public boolean hasIsurance() {
		return insurance != null;
	}

	// Setters

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void address(String address) {
		this.address = address;
	}

	public void setInsurance(Insurance insurance) {
		this.insurance = insurance;
	}

	public Insurance getInsurance() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}
}
