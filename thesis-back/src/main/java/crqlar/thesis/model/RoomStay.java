package crqlar.thesis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomStay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    private Date fromDay;
    private Date toDay;
    private String roomIdCrq;
    private String ratePlanCodeCrq;
    private double amountAfterTax;
    private String roomStayStatus;
}
