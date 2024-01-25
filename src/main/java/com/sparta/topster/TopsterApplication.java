package com.sparta.topster;

import com.sparta.topster.global.config.ScheduledConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Import;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class) // Spring Security 인증 기능 제외
@Import(ScheduledConfig.class)
public class TopsterApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopsterApplication.class, args);
	}

}
