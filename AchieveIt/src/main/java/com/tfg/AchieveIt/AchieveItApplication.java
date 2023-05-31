package com.tfg.AchieveIt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AchieveItApplication {

	public static void main(String[] args) {
		SpringApplication.run(AchieveItApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer configure(){
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("http://localhost:5173")
						.allowedMethods("*")
						.allowedHeaders("*")
						.allowCredentials(true);
			}
		};
	}
}
