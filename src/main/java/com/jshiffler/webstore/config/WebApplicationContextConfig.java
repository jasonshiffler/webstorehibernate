package com.jshiffler.webstore.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import com.jshiffler.webstore.domain.Product;


// Create the application context container to manage Beans
@Configuration                  // Indicates the class contains one or more @Bean methods
@EnableWebMvc                   // Imports special Spring MVC config
@ComponentScan("com.jshiffler") // Specifies the base packages for component scans
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}


	// Enable matrix variable support by overriding the configurePathMatchMethod
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {

		UrlPathHelper urlPathHelper = new UrlPathHelper();
		urlPathHelper.setRemoveSemicolonContent(false);
		configurer.setUrlPathHelper(urlPathHelper);
	}

	// Specify a location to serve up image files
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		// /webstore/img/image.png is located under the resources/images dir 
		
		registry.addResourceHandler("/img/**").addResourceLocations("/resources/images/");
				
	}
	
	
	// Allows for multipart http requests in order to upload files for things such
	// as products
	@Bean
	public CommonsMultipartResolver multipartResolver(){
		
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		resolver.setDefaultEncoding("utf-8");
		return resolver;
		
	}
	
	
	// This is the view resolver that builds a path of the .jsp docs
	@Bean
	public InternalResourceViewResolver getInternalResourceViewResolver(){
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/jsp/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	
	
	
	// This is used to externalize the labels in our .jsp files.
	// The messages are stored in the messages.properties file
	@Bean
	public MessageSource messageSource() {
		
		// Create the message source
		ResourceBundleMessageSource resource = new
		ResourceBundleMessageSource();
		
		// Set the name of file with our labels.
		resource.setBasename("messages");
		
		return resource;


	}



}
