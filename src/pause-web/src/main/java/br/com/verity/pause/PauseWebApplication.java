package br.com.verity.pause;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.multipart.support.MultipartFilter;

@SpringBootApplication
@ImportResource(value = { "classpath:security-config.xml" })
public class PauseWebApplication extends SpringBootServletInitializer {
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		new MultipartFilter();
		return application.sources(PauseWebApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(PauseWebApplication.class, args);
	}
	
	@Bean
	public MultipartConfigElement multipartConfigElement(){
	    MultipartConfigElement config = new MultipartConfigElement("");
	    return config;
	}
	@Bean
	public FilterRegistrationBean multipartFilter() {
	    FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
	    filterRegBean.setFilter(new MultipartFilter());
	    List<String> urlPatterns = new ArrayList<String>();
	    urlPatterns.add("/*");
	    filterRegBean.setUrlPatterns(urlPatterns);
	    return filterRegBean;
	}

}
