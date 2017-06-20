package com.jshiffler.webstore.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Repository;
import com.jshiffler.webstore.domain.Product;
import com.jshiffler.webstore.repository.ProductRepository;



//For now the repository sits between the controller and the persistence layer.
//in order to keep complicated code out of the controller.
@Repository
public class InMemoryProductRepository implements ProductRepository {

	@Autowired
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;    

	}

	//Returns in List format all of the products in the repository
	@Override
	public List <Product> getAllProducts() {
		//create Session factory
		/*SessionFactory factory = new Configuration()
						.configure("hibernate.cfg.xml")
						.addAnnotatedClass(Product.class)
						.buildSessionFactory();*/

		//create session
		Session session = this.sessionFactory.getCurrentSession();
	  //  session.beginTransaction();

		List<Product> result = session.createQuery("from Product").list(); 
		//session.getTransaction().commit();
		return result;
	}


	/* (non-Javadoc)
	 * @see com.jshiffler.webstore.repository.ProductRepository#getProductsByCategory(java.lang.String)
	   Returns a list of products that match a certain category such as Tablet, Laptop, etc...
	 */
	@Override
	public List<Product> getProductsByCategory(String category) {
		//create Session factory
		/*SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Product.class)
								.buildSessionFactory();*/

		//create session
		Session session = this.sessionFactory.getCurrentSession();
		//session.beginTransaction();

		Query query = session.createQuery("from Product where category= :cat"); 
		query.setParameter("cat", category);
		List<Product> results = query.list();
		//session.getTransaction().commit();
		sessionFactory.close();

		return results;
	}


	//Updates the a product with a certain id by a specific number of units
	@Override
	public void updateStock(String productId, long noOfUnits){
		//create Session factory
		/*SessionFactory factory = new Configuration()
						.configure("hibernate.cfg.xml")
						.addAnnotatedClass(Product.class)
						.buildSessionFactory();*/

		//create session
		Session session = sessionFactory.getCurrentSession();
		//session.beginTransaction();
		Query query = session.createQuery("UPDATE Product SET unitsInStock = unitsInStock + :numunits WHERE productId = :id");
		query.setParameter("id", productId);        //set the K/V pair in the query for the product id
		query.setParameter("numunits", noOfUnits);  //set the K/V pair in the query for the no of units
		query.executeUpdate();
		//session.getTransaction().commit();
		//sessionFactory.close();

	}

	// return the product list based on category and manufacturer
	@Override
	public List<Product> getProductsByFilter(String category, String manufacturer) {
		//create Session factory
		/*SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Product.class)
								.buildSessionFactory();*/

		//create session
		Session session = this.sessionFactory.getCurrentSession();
		//session.beginTransaction();

		//create the query
		Query query = session.createQuery("from Product p where category = :cat and manufacturer = :manufacturer"); 

		//set the category and manufacturer K/V pairs
		query.setParameter("cat", category);
		query.setParameter("manufacturer", manufacturer);

		//set our return result to the query result
		List<Product> results = query.list();              

		//Shutdown the transaction
		//session.getTransaction().commit();
		//sessionFactory.close();

		//Send back the results
		return results;	

	}


	//returns a product with a specific id
	@Override
	public Product getProductById(String productID) {
		//create Session factory
		/*SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Product.class)
								.buildSessionFactory();*/

		//create session
		Session session = this.sessionFactory.getCurrentSession();
		//session.beginTransaction();

		Query query = session.createQuery("from Product where id=:id"); 
		query.setParameter("id", productID);
		Product result = (Product) query.uniqueResult();
		//session.getTransaction().commit();
		//sessionFactory.close();

		return result;

	}

	//returns a list of products within a category that are made by a manufacturer within a price range
	@Override
	public List<Product> filterProducts(String category, Map<String, List<String>> filterParams) {
		//create Session factory
		/*SessionFactory factory = new Configuration()
						.configure("hibernate.cfg.xml")
						.addAnnotatedClass(Product.class)
						.buildSessionFactory();*/

		//create session
		Session session = this.sessionFactory.getCurrentSession();
		//session.beginTransaction();

		Query query = session.createQuery("from Product p where p.category=:cat"); 
		query.setParameter("cat", category);

		//session.getTransaction().commit();
		//sessionFactory.close();
		List<Product> results = query.list();
		return results;

	}

	//Allows a new product to be added to the database
	@Override
	public void addProduct(Product product) {

	}

}
