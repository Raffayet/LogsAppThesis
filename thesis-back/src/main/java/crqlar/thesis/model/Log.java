package crqlar.thesis.model;

import crqlar.thesis.enums.LogType;
import crqlar.thesis.utils.NumberGenerator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Log {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LogType logType;
    private LocalDateTime timestamp;
    private int logValue;

    public Log(String logType) {
        this.logType = LogType.valueOf(logType);
        this.timestamp = LocalDateTime.now();
        this.logValue = NumberGenerator.generateLogValue();
    }
}