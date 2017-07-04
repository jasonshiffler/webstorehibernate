package com.jshiffler.webstore.repository;

import java.util.List;

import com.jshiffler.webstore.domain.Customer;

//Interface for a Repository of Customer objects
public interface CustomerRepository {

	public List <Customer> getAllCustomers();
	public void addCustomer(Customer newCustomer);
	
}
