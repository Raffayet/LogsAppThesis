package com.example.uberbackend.model.elastic;

import com.example.uberbackend.model.enums.RideStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Document(indexName = "ride")
@Getter
@Setter
@NoArgsConstructor
public class ElasticRide {
    @Id
    private String id;  // Elasticsearch prefers String IDs

    @Field(type = FieldType.Keyword)
    private String driverId;

    @Field(type = FieldType.Keyword)
    private String initiatorId;

//    @Field(type = FieldType.Text)
//    private RideStatus rideStatus;
//
//    @Field(type = FieldType.Double)
//    private double price;
//
//    @Field(type = FieldType.Double)
//    private double pricePerPassenger;
//
//    @Field(type = FieldType.Text)
//    private String vehicleType;
//
//    @Field(type = FieldType.Text)
//    private String routeType;
//
//    @Field(type = FieldType.Boolean)
//    private Boolean reserved;
//
//    @Field(type = FieldType.Date)
//    private LocalDateTime timeOfReservation;
//
//    @Field(type = FieldType.Date)
//    private LocalDateTime timeOfRequestForReservation;
//
//    @Field(type = FieldType.Date)
//    private LocalDateTime startTime;
//
//    @Field(type = FieldType.Date)
//    private LocalDateTime endTime;
//
//    @Field(type = FieldType.Boolean)
//    private Boolean ratingExpired;
}