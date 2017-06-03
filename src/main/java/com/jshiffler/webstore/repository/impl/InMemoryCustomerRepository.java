package com.jshiffler.webstore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.jshiffler.webstore.domain.Customer;
import com.jshiffler.webstore.repository.CustomerRepository;


@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

	
	//Used to communicate with the database
		@Autowired //create the bean
		private NamedParameterJdbcTemplate jdbcTemplate;

		
		//Returns in List format all of the products in the repository
		@Override
		public List <Customer> getAllCustomers() {
			Map<String, Object> params = new HashMap<String, Object>();
			List<Customer> result = jdbcTemplate.query("SELECT * FROM customers", params, new CustomerMapper());

			return result;
		}

				
		//Maps the columns in the database to the Product attributes
		private static final class CustomerMapper implements RowMapper<Customer>{

			public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {

				Customer customer = new Customer();

				customer.setCustomerId(rs.getString("ID"));
				customer.setName(rs.getString("NAME"));
				customer.setAddress(rs.getString("ADDRESS"));
				customer.setNoOfOrdersmade(rs.getInt("UNITS_IN_ORDER"));
				
				return customer;
			} 
		}

	
}
