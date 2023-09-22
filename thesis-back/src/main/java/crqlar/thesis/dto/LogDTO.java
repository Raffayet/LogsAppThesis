package crqlar.thesis.dto;

import crqlar.thesis.model.Log;
import crqlar.thesis.utils.NumberGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
public class LogDTO {
    @NotBlank
    private Long id;

    @NotBlank
    private String logType;

    @NotBlank
    private LocalDateTime timestamp;

    @NotBlank
    private int logValue;

    public LogDTO(Log log) {
        this.id = log.getId();
        this.logType = log.getLogType().name();
        this.timestamp = log.getTimestamp();
        this.logValue = log.getLogValue();
    }
}
