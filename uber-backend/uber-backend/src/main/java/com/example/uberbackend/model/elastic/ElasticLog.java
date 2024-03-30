package com.example.uberbackend.model.elastic;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.Instant;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Id;

@Document(indexName = "log")
@Getter
@Setter
@NoArgsConstructor
public class ElasticLog {
    @Id
    private String id;  // Elasticsearch prefers String IDs

    @Field(type = FieldType.Keyword)
    private String logType;

    @Field(type = FieldType.Date)
    private Instant timestamp;
}