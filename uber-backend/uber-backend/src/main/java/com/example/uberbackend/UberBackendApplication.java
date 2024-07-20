package com.example.uberbackend;

import java.time.Instant;
import com.example.uberbackend.model.elastic.ElasticLog;
import com.example.uberbackend.model.elastic.ElasticRide;
import com.example.uberbackend.repositories.elastic.ElasticLogRepository;
import com.example.uberbackend.repositories.elastic.ElasticRideRepository;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.stream.IntStream;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.example.uberbackend.repositories.jpa")
@EnableElasticsearchRepositories(basePackages = "com.example.uberbackend.repositories.elastic")
public class UberBackendApplication {
	private final ElasticLogRepository elasticLogRepository;
	private final ElasticRideRepository elasticRideRepository;

	private static final Logger logger = LoggerFactory.getLogger(UberBackendApplication.class);

	public UberBackendApplication(ElasticLogRepository elasticLogRepository, ElasticRideRepository elasticRideRepository) {
		this.elasticLogRepository = elasticLogRepository;
		this.elasticRideRepository = elasticRideRepository;
	}

//	@PostConstruct
//	public void initElasticLogs() {
//		try {
//			ElasticLog log = new ElasticLog();
//			log.setId("456");
//			log.setLogType("INFO");
//			log.setTimestamp(Instant.now());
//
//			elasticLogRepository.save(log);
//			System.out.println("Log saved successfully");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	public static void main(String[] args) {
		SpringApplication.run(UberBackendApplication.class, args);
	}
}
