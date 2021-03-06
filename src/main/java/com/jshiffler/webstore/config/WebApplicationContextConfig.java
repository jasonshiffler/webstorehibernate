package com.jshiffler.webstore.config;

import java.util.ArrayList;

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
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;
import org.springframework.web.util.UrlPathHelper;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.jshiffler.webstore.domain.Customer;
import com.jshiffler.webstore.domain.Product;
import com.jshiffler.webstore.interceptor.ProcessingTimeLogInterceptor;
import com.jshiffler.webstore.interceptor.PromoCodeInterceptor;


// Create the application context container to manage Beans
@Configuration                  // Indicates the class contains one or more @Bean methods
@EnableWebMvc                   // Imports special Spring MVC config
@ComponentScan("com.jshiffler") // Specifies the base packages for component scans
public class WebApplicationContextConfig extends WebMvcConfigurerAdapter{

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer){
		configurer.enable();
	}

	@Bean
	public HandlerInterceptor promoCodeInterceptor() {
		
		PromoCodeInterceptor promoCodeInterceptor = new 
				PromoCodeInterceptor();
		promoCodeInterceptor.setPromoCode("OFF3R");
		promoCodeInterceptor.setOfferRedirect("/market/products");
		promoCodeInterceptor.setErrorRedirect("invalidPromoCode");
		
		return promoCodeInterceptor;
		
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
	// as product pictures
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

	// View used to return JSON data as an alternative to JSP
	// For example /webstore/customers.json or /webstore/products.json
	@Bean
	public MappingJackson2JsonView jsonView() {
		MappingJackson2JsonView jsonView = new
				MappingJackson2JsonView();
		jsonView.setPrettyPrint(true);
		
		return jsonView;
		
	}
	
    // View used to return XML data as an alternative to JSP 
	@Bean 
	public MarshallingView xmlView(){
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Product.class);
		MarshallingView xmlView = new MarshallingView(marshaller);
		return xmlView;
		
	}
	
	// Is a traffic cop for the XML and JSON View Beans
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		
		ContentNegotiatingViewResolver resolver = new
				ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);
		
		ArrayList<View> views = new ArrayList<>();
		views.add(jsonView());
		views.add(xmlView());
		
		resolver.setDefaultViews(views);
		return resolver;
		
	}
	
	// Pull in the interceptors we've created for pre+post event processing
	@Override
	public void addInterceptors(InterceptorRegistry registry){
		registry.addInterceptor(new ProcessingTimeLogInterceptor());
		
	}
	


}
