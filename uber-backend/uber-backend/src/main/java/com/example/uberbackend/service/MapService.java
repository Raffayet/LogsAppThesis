package com.example.uberbackend.service;

import com.example.uberbackend.dto.MapSearchResultDto;
import com.example.uberbackend.dto.PathInfoDto;
import com.example.uberbackend.exception.NotEnoughPointsForRouteException;
import com.example.uberbackend.exception.TooManyPointsForRouteException;
import com.example.uberbackend.model.Point;
import com.example.uberbackend.model.Ride;
import com.example.uberbackend.repositories.jpa.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;
import org.json.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
public class MapService {

    private final RideRepository rideRepository;

    @Value("${graphhopper.api.key}")
    private String graphhopperApiKey;

    private static final Logger logger = LoggerFactory.getLogger("stash");

    public MapService(RideRepository rideRepository) {
        this.rideRepository = rideRepository;
    }

    public PathInfoDto getOptimalRoute(List<Point> points) throws IOException {
        OkHttpClient client = new OkHttpClient();
        String queryParams = new String();

        if(points.size() < 2)
            throw new NotEnoughPointsForRouteException("More locations are needed in order to create route.");
        if(points.size() > 5)
            throw new TooManyPointsForRouteException("There is too many locations for route creation.");

        for(Point p : points){
            queryParams = insertPointString(p, queryParams);
        }

        Request request = new Request.Builder()
                .url("https://graphhopper.com/api/1/route?".concat(queryParams).concat("points_encoded=false&profile=car&locale=de&key=").concat(graphhopperApiKey))
                .get()
                .build();
        Response response = client.newCall(request).execute();

        PathInfoDto dto = getPathInfoGraphhoperResponse(response, 0);
        return dto;
    }

    private PathInfoDto getPathInfoGraphhoperResponse(Response response, int index) throws IOException {
        JSONObject obj = new JSONObject(response.body().string());
        JSONArray paths = (JSONArray) obj.get("paths");
        if(index == 1 && paths.length()<2)
            index = 0;
        double distance = paths.getJSONObject(index).getDouble("distance");
        JSONArray coordinates = paths.getJSONObject(index).getJSONObject("points").getJSONArray("coordinates");
        List<Point> retList = new ArrayList<>();

        for (int i = 0; i < coordinates.length(); i++) {
            JSONArray v = (JSONArray) (coordinates.get(i));
//            ovde je promena
            retList.add(new Point(v.get(1).toString(), v.get(0).toString()));
        }
        return new PathInfoDto(retList, distance);
    }

    private String insertPointString(Point p, String queryParams){
        queryParams = queryParams.concat("point=").concat(p.getLat().toString()).concat(",")
                .concat(p.getLng().toString()).concat("&");
        return queryParams;
    }

    public PathInfoDto getAlternativeRoute(List<Point> points) throws IOException {
        OkHttpClient client = new OkHttpClient();
        List<Point> retList = new ArrayList<>();
        double distance = 0;
        int i = 0;

        if(points.size() < 2)
            throw new NotEnoughPointsForRouteException("More locations are needed in order to create route.");
        if(points.size() > 5)
            throw new TooManyPointsForRouteException("There is too many locations for route creation.");

        while(i < points.size() - 1){
            String queryParams = new String();
            queryParams = insertPointString(points.get(i), queryParams);
            queryParams = insertPointString(points.get(i + 1), queryParams);

            Request request = new Request.Builder()
                    .url("https://graphhopper.com/api/1/route?".concat(queryParams).concat("points_encoded=false&algorithm=alternative_route&alternative_route.max_weight_factor=7&key=").concat(graphhopperApiKey))
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            PathInfoDto dto = getPathInfoGraphhoperResponse(response, 1);
            retList.addAll(dto.getAtomicPoints());
            distance += dto.getDistance();
            i++;
        }
        return new PathInfoDto(retList, distance);
    }

    public PathInfoDto getCustomRoute(List<Point> points) throws IOException {
        OkHttpClient client = new OkHttpClient();
        List<Point> retList = new ArrayList<>();
        double distance = 0;
        int i = 0;

        if(points.size() < 2)
            throw new NotEnoughPointsForRouteException("More locations are needed in order to create route.");
        if(points.size() > 5)
            throw new TooManyPointsForRouteException("There is too many locations for route creation.");

        while(i < points.size() - 1){
            String queryParams = new String();
            queryParams = insertPointString(points.get(i), queryParams);
            queryParams = insertPointString(points.get(i + 1), queryParams);

            Request request = new Request.Builder()
                    .url("https://graphhopper.com/api/1/route?".concat(queryParams).concat("points_encoded=false&algorithm=alternative_route&alternative_route.max_weight_factor=1&key=").concat(graphhopperApiKey))
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            PathInfoDto dto = getPathInfoGraphhoperResponse(response, 0);
            retList.addAll(dto.getAtomicPoints());
            distance += dto.getDistance();
            i++;
        }
        return new PathInfoDto(retList, distance);
    }

    public void transferMapsToKibana(Long rideId) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
        Ride ride = this.rideRepository.findById(rideId).orElseThrow();

        // Start location
        MapSearchResultDto startLocation = ride.getLocations().get(0);
        // End location
        MapSearchResultDto endLocation = ride.getLocations().get(ride.getLocations().size() - 1);

        // Log start location as structured data
        logger.info("Transferring start location to Kibana",
                kv("location", Map.of("lat", startLocation.getLat(), "lon", startLocation.getLon())),
                kv("displayName", startLocation.getDisplayName()));

        // Log end location as structured data
        logger.info("Transferring end location to Kibana",
                kv("location", Map.of("lat", endLocation.getLat(), "lon", endLocation.getLon())),
                kv("displayName", endLocation.getDisplayName()));
    }


}
