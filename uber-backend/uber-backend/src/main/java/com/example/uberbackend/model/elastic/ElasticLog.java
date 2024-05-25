package com.example.uberbackend.model.elastic;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Document(indexName = "log")
@Getter
@Setter
@NoArgsConstructor
public class ElasticLog {
    @Id
    private String id;

    @Field(type = FieldType.Keyword)
    private String logType;

    @Field(type = FieldType.Date)
    private String timestamp;

    public void setTimestamp(Instant timestamp) {
        this.timestamp = DateTimeFormatter.ISO_INSTANT.format(timestamp);
    }
}