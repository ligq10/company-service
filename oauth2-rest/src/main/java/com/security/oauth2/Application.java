package com.security.oauth2;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.oauth2.config.TokenAuthFilter;
import com.security.oauth2.utils.JsonObjectMapper;

import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.servlet.DispatcherType;

import java.util.Collections;
import java.util.EnumSet;

@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    private static final int DEFAULT_MAX_TOTAL_CONNECTIONS = 1000;

    private static final int DEFAULT_MAX_CONNECTIONS_PER_ROUTE = 100;

	@Autowired
	private Environment env;
    
	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

    @Bean
    @DependsOn("tokenAuthFilter")
    public FilterRegistrationBean tokenAuthFilterRegisterBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(tokenAuthFilter());
        filterRegistrationBean.setEnabled(false);
        filterRegistrationBean.setDispatcherTypes(EnumSet.allOf(DispatcherType.class));
        return filterRegistrationBean;
    }

    @Bean
    TokenAuthFilter tokenAuthFilter(){
        return new TokenAuthFilter();
    }

	@Bean
	@Primary
	RestTemplate restTemplate() {
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
		connectionManager.setMaxTotal(
				env.getProperty("httpclient.maxtotal", Integer.class, 1000));
		connectionManager.setDefaultMaxPerRoute(
				env.getProperty("httpclient.maxroute", Integer.class, 100));
		HttpClientBuilder httpClient = HttpClientBuilder.create()
				.setConnectionManager(connectionManager);
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				httpClient.build());
		requestFactory.setConnectionRequestTimeout(
				env.getProperty("httpclient.timeout", Integer.class, 1000));
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(requestFactory);
		MappingJackson2HttpMessageConverter converter = converter();
		restTemplate.setMessageConverters(
				Collections.<HttpMessageConverter<?>> singletonList(converter));
		return restTemplate;
	}

	@Bean
	MappingJackson2HttpMessageConverter converter() {
		MappingJackson2HttpMessageConverter jsonMessageConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper jsonObjectMapper = new JsonObjectMapper();
		jsonObjectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		jsonObjectMapper.registerModule(new Jackson2HalModule());
		jsonMessageConverter.setSupportedMediaTypes(MediaType.parseMediaTypes(
				"application/json,application/*+json,application/hal+json"));
		jsonMessageConverter.setObjectMapper(jsonObjectMapper);
		return jsonMessageConverter;
	}
}
