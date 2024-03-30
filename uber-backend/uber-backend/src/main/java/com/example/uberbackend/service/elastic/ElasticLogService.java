package com.example.uberbackend.service.elastic;

import com.example.uberbackend.model.elastic.ElasticLog;
import com.example.uberbackend.repositories.elastic.ElasticLogRepository;
import com.example.uberbackend.repositories.jpa.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
@Transactional
public class ElasticLogService {

    private final ElasticLogRepository elasticLogRepository;
    public Page<ElasticLog> findLogsByLogType(Pageable paging, String logType) {
        return elasticLogRepository.findAll(paging);
    }
}
