package com.example.uberbackend.repositories.elastic;

import com.example.uberbackend.model.Ride;
import com.example.uberbackend.model.elastic.ElasticLog;
import com.example.uberbackend.model.elastic.ElasticRide;
import com.example.uberbackend.model.enums.RideStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticLogRepository extends ElasticsearchRepository<ElasticLog, Long> {
    Page<ElasticLog> findByLogType(String logType, Pageable pageable);
}
