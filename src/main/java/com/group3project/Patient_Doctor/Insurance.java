package com.group3project.Patient_Doctor;

class Insurance {
	private String name;
	private String insuranceID;
	private String groupNumber;
	private String planType;

	public Insurance(String name, String insuranceID, String groupNumber, String planType) {
		this.name = name;
		this.insuranceID = insuranceID;
		this.groupNumber = groupNumber;
		this.planType = planType;
	}

	// Getters
	public String getName() {
		return name;
	}

	public String getInsuranceID() {
		return insuranceID;
	}

	public String getgroupNumber() {
		return groupNumber;
	}

	public String planType() {
		return planType;
	}

	// Setters

	public void setName(String name) {
		this.name = name;
	}

	public void setInsuranceID(String insuranceID) {
		this.insuranceID = insuranceID;
	}

	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}

	public void setPlanType(String planType) {
		this.planType = planType;
	}

	public String getPlanType() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getGroupNumber() {
		// TODO Auto-generated method stub
		return null;
	}
}
