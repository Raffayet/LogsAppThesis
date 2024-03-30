package com.example.uberbackend;

import com.example.uberbackend.model.elastic.ElasticLog;
import com.example.uberbackend.model.elastic.ElasticRide;
import com.example.uberbackend.repositories.elastic.ElasticLogRepository;
import com.example.uberbackend.repositories.elastic.ElasticRideRepository;
import org.joda.time.Instant;
import org.joda.time.LocalDateTime;
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

	public UberBackendApplication(ElasticLogRepository elasticLogRepository, ElasticRideRepository elasticRideRepository) {
		this.elasticLogRepository = elasticLogRepository;
		this.elasticRideRepository = elasticRideRepository;
	}

	@PostConstruct
	public void initElasticLogs(){
		// First deleting all in order to prevent from redundant data storing in elastic search db
		ElasticLog elasticLog = new ElasticLog();
		elasticLog.setLogType("");
		elasticLog.setTimestamp(Instant.now());

		try {
			elasticLogRepository.save(elasticLog);
		} catch (Exception e) {
			e.printStackTrace();
		}

//		elasticLogRepository.deleteAll();
//
//		IntStream.range(0, 3).mapToObj(i -> {
//			ElasticLog elasticLog = new ElasticLog();
//			elasticLog.setLogType("");
//			elasticLog.setTimestamp(LocalDateTime.now());
//			return elasticLog;
//		}).forEach(elasticLogRepository::save);
	}

	public static void main(String[] args) {
		SpringApplication.run(UberBackendApplication.class, args);
	}
}
