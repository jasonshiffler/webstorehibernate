package com.jshiffler.webstore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Repository;

import com.jshiffler.webstore.domain.Customer;
import com.jshiffler.webstore.repository.CustomerRepository;

@Repository
public class CustomerRepositoryImpl implements CustomerRepository {
	
	//Create the reference for the SessionFactory
		@Autowired
		private SessionFactory sessionFactory;
		
		public void setSessionFactory(SessionFactory sessionFactory) {
			this.sessionFactory = sessionFactory;    

		}
					
		//Returns in List format all of the products in the repository
		@Override
		public List <Customer> getAllCustomers() {
						
			Session session = this.sessionFactory.getCurrentSession();
			
			List<Customer> theCustomers = session.createQuery("from Customer").list(); 
						
			//Send back the results of the query
			return theCustomers;  
						
		}

		//Saves a customer object to the database
		@Override
		public void addCustomer(Customer newCustomer) {
			//Grab the session from the Session Factory Singleton
			Session session = this.sessionFactory.getCurrentSession();
			
			//Persist the object in the database
			session.save(newCustomer);
			
		}

		
		
		

	
}
