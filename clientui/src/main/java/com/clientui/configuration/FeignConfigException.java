package com.clientui.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.clientui.exceptions.CustomErrorDecoder;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfigException {
	
	@Bean
	public ErrorDecoder errorDecoder() {
		return new CustomErrorDecoder();
	}
	
}
