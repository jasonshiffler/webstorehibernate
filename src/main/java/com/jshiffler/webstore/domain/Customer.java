package com.jshiffler.webstore.domain;

//Class the represents a single customer
public class Customer {

	private String customerId;     //integer representing the customer number
	private String name;        //name of the customer
	private String address;     //customer's address
	private int noOfOrdersmade; //number of orders the customer has placed
	
	
	//Constructor to create a customer
	public Customer(String customerId, String name, String address) {
		this.customerId = customerId;
		this.name = name;
		this.address = address;
        this.noOfOrdersmade = 0; //a new customer has zero orders
	}


	public Customer() {
	}


	/**
	 * @return the customerId
	 */
	public String getCustomerId() {
		return customerId;
	}


	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}


	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}


	/**
	 * @return the noOfOrdersmade
	 */
	public int getNoOfOrdersmade() {
		return noOfOrdersmade;
	}


	/**
	 * @param noOfOrdersmade the noOfOrdersmade to set
	 */
	public void setNoOfOrdersmade(int noOfOrdersmade) {
		this.noOfOrdersmade = noOfOrdersmade;
	}
	
	
	
	
	
}
