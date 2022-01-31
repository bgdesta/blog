package miu.edu.cs544.reaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReactionApplication {

	public static void main(String[] args) {

		SpringApplication.run(ReactionApplication.class, args);
	}

}
