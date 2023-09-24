package crqlar.thesis.service.log;

import crqlar.thesis.dto.LogDTO;
import crqlar.thesis.model.Log;
import crqlar.thesis.repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.time.LocalDateTime;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final LogRepository logRepository;

    public LogServiceImpl(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    @Override
    public void createLog(String logType) {
        Log log = new Log(logType);
        logRepository.save(log);
        LogDTO logDTO = new LogDTO(log);
        messagingTemplate.convertAndSend("/topic/messages", logDTO);
    }
}
