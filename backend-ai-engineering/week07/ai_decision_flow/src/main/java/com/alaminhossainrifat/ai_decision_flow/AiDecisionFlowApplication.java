package com.alaminhossainrifat.ai_decision_flow;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration;

// Temporarily excluding database auto-configuration
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class AiDecisionFlowApplication {

	public static void main(String[] args) {
		SpringApplication.run(AiDecisionFlowApplication.class, args);
	}
}