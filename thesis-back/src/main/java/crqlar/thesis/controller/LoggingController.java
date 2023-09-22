package crqlar.thesis.controller;

import crqlar.thesis.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/logging")
public class LoggingController {

    Logger logger = LoggerFactory.getLogger(LoggingController.class);

    private final LogService logService;

    public LoggingController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("create-log")
        public ResponseEntity<?> createLog(@RequestParam("logType") String logType) {
        try{
            logService.createLog(logType);
            return ResponseEntity.ok("Success");
        } catch(Exception ex){
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}