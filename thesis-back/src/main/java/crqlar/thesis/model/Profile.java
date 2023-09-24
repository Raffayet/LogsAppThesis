package crqlar.thesis.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String languageCode;
    private LocalDateTime birthDate;
    private String vipCode;
    private double averageRevenuePerDay;
    private double lastStayRevenueRoomStay;
    private double lastStayServicesRevenue;
    private double lastStayTotalRevenue;
    private int totalNumberOfNights;
    private int totalNumberOfStays;
    private double totalRevenue;
    private double totalRevenueRoomStays;
    private double totalRevenueServices;
}
