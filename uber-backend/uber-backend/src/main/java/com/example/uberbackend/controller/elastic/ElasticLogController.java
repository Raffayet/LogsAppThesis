package com.example.uberbackend.controller.elastic;

import com.example.uberbackend.model.Ride;
import com.example.uberbackend.model.elastic.ElasticLog;
import com.example.uberbackend.service.RideService;
import com.example.uberbackend.service.elastic.ElasticLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class ElasticLogController {

    private final ElasticLogService elasticLogService;
    @GetMapping("get-logs-by-type")
    public Page<ElasticLog> getEndedDriversRides(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam String logType
    ) {
        Pageable paging = PageRequest.of(page, size);
        return elasticLogService.findLogsByLogType(paging, logType);
    }
}
