package edu.miu.blog.topicservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TopicServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TopicServiceApplication.class, args);
	}

}
