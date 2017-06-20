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

			
		//Returns in List format all of the products in the repository
		@Override
		public List <Customer> getAllCustomers() {
			
			SessionFactory factory = new Configuration()
					.configure("hibernate.cfg.xml")
					.addAnnotatedClass(Customer.class)
					.buildSessionFactory();
			
			//create session + begin the transaction 
			Session session = factory.getCurrentSession();
			session.beginTransaction();
			
			List<Customer> theCustomers = session.createQuery("from Customer").list(); 
			
			//shutdown the transaction+factory
			session.getTransaction().commit();
			factory.close();
			
			//Send back the results of the query
			return theCustomers;  
						
		}

		
		
		

	
}
