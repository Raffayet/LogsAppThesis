package com.example.uberbackend.repositories.elastic;

import com.example.uberbackend.model.elastic.ElasticRide;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ElasticRideRepository extends ElasticsearchRepository<ElasticRide, Long> {
}
