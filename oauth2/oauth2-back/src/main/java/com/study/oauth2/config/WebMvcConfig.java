package com.study.oauth2.config;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;

@Configuration 
public class WebMvcConfig implements WebMvcConfigurer {
	
	@Value("${file.path}")
	private String filePath;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*");
	}
	
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		WebMvcConfigurer.super.addResourceHandlers(registry);
		registry.addResourceHandler("/image/**")
				.addResourceLocations("file:///" + filePath)
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
					// 한글을 디코딩해주는 역할을 함! // URL에 한글을 받아오게 해줌!
					@Override
					protected Resource getResource(String resourcePath, Resource location) throws IOException {
						resourcePath = URLDecoder.decode(resourcePath, StandardCharsets.UTF_8);
						return super.getResource(resourcePath, location);
					}
				});
	}
}
