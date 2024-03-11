package com.example.uberbackend;

import com.example.uberbackend.model.elastic.ElasticRide;
import com.example.uberbackend.repositories.elastic.ElasticRideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.uberbackend.repositories.jpa")
@EnableElasticsearchRepositories(basePackages = "com.example.uberbackend.repositories.elastic")
public class UberBackendApplication {
	private final ElasticRideRepository elasticRideRepository;

	@Autowired
	public UberBackendApplication(ElasticRideRepository elasticRideRepository) {
		this.elasticRideRepository = elasticRideRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(UberBackendApplication.class, args);
	}
}
