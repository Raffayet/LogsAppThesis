package com.example.uberbackend.unit;

import com.example.uberbackend.dto.*;
import com.example.uberbackend.exception.DriveRequestNotFoundException;
import com.example.uberbackend.exception.DriverNotFoundException;
import com.example.uberbackend.exception.NoAvailableDriversException;
import com.example.uberbackend.exception.RideNotFoundException;
import com.example.uberbackend.model.*;
import com.example.uberbackend.model.enums.DrivingStatus;
import com.example.uberbackend.repositories.jpa.*;
import com.example.uberbackend.service.DriverService;
import com.example.uberbackend.service.MapService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class DriverServiceTests {

    @InjectMocks
    DriverService driverService;

    @Mock
    DriverRepository driverRepository;

    @Mock
    MapService mapService;

    @Mock
    DriveRequestRepository driveRequestRepository;

    @Mock
    RideRepository rideRepository;

    @Mock
    SimpMessagingTemplate simpMessagingTemplate;

    @Mock
    RejectionRepository rejectionRepository;

    @Mock
    PointRepository pointRepository;

    @Test
    void findDriverForRequestTestSuccessFoundAvailable() throws IOException {
        //input
        DriveRequest request = new DriveRequest();

        Client client = new Client();
        request.setInitiator(client);

        request.setPeople(new ArrayList<Client>());
        request.setPrice(5);
        request.setPricePerPassenger(5);
        request.setVehicleType("Standard");
        request.setIsReserved(false);
        request.setTimeOfReservation(LocalDateTime.now());
        request.setTimeOfRequestForReservation(LocalDateTime.now());

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );
        request.setLocations(locations);

        request.setDriversThatRejected(new ArrayList<Driver>());

        List<Driver> availableDrivers = new ArrayList<Driver>();
        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setDrivingStatus(DrivingStatus.ONLINE);
        driver.setBlocked(false);
        driver.setCurrentLocation(new Point(45.2530233, 19.7916443));
        driver.setDailyActiveInterval(350.0);
        availableDrivers.add(driver);
        List<Point> points = new ArrayList<>();

        PathInfoDto pathInfoDto = new PathInfoDto();
        pathInfoDto.setDistance(9.5);

        List<Point> pointsForOptimalRoute = Arrays.asList(
                new Point(45.2630233, 19.7916443),
                new Point(45.2530233, 19.7716443)
        );

        pathInfoDto.setAtomicPoints(pointsForOptimalRoute);

        DriverFoundDto expectedResult = new DriverFoundDto();
        expectedResult.setFound(true);
        expectedResult.setDriverEmail(driver.getEmail());

        Mockito.when(driverRepository.findByDrivingStatusEquals(DrivingStatus.ONLINE)).thenReturn(availableDrivers);
        Mockito.when(mapService.getOptimalRoute(anyList())).thenReturn(pathInfoDto);

        DriverFoundDto actualResult = driverService.findDriverForRequest(request);
        Assertions.assertEquals(expectedResult.getDriverEmail(), actualResult.getDriverEmail());
        Assertions.assertEquals(expectedResult.isFound(), actualResult.isFound());
    }

    @Test
    void findDriverForRequestTestSuccessFoundBusy() throws IOException {
        //input
        DriveRequest request = new DriveRequest();

        Client client = new Client();
        request.setInitiator(client);

        request.setPeople(new ArrayList<Client>());
        request.setPrice(5);
        request.setPricePerPassenger(5);
        request.setVehicleType("Standard");
        request.setIsReserved(false);
        request.setTimeOfReservation(LocalDateTime.now());
        request.setTimeOfRequestForReservation(LocalDateTime.now());

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );
        request.setLocations(locations);

        request.setDriversThatRejected(new ArrayList<Driver>());

        List<Driver> availableDrivers = new ArrayList<Driver>();
        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setDrivingStatus(DrivingStatus.ONLINE_BUSY);
        driver.setBlocked(false);
        driver.setCurrentLocation(new Point(45.2530233, 19.7916443));
        driver.setDailyActiveInterval(350.0);
        List<Ride> driversRides = new ArrayList<>();
        Ride ride = new Ride();
        ride.setReserved(false);
        ride.setTimeOfReservation(LocalDateTime.now());
        ride.setLocations(locations);
        driversRides.add(ride);
        driver.setRides(driversRides);
        availableDrivers.add(driver);
        List<Point> points = new ArrayList<>();

        PathInfoDto pathInfoDto = new PathInfoDto();
        pathInfoDto.setDistance(9.5);

        List<Point> pointsForOptimalRoute = Arrays.asList(
                new Point(45.2630233, 19.7916443),
                new Point(45.2530233, 19.7716443)
        );

        pathInfoDto.setAtomicPoints(pointsForOptimalRoute);

        DriverFoundDto expectedResult = new DriverFoundDto();
        expectedResult.setFound(true);
        expectedResult.setDriverEmail(driver.getEmail());

        Mockito.when(driverRepository.findByDrivingStatusEquals(DrivingStatus.ONLINE_BUSY)).thenReturn(availableDrivers);
        Mockito.when(mapService.getOptimalRoute(anyList())).thenReturn(pathInfoDto);

        DriverFoundDto actualResult = driverService.findDriverForRequest(request);
        Assertions.assertEquals(expectedResult.getDriverEmail(), actualResult.getDriverEmail());
        Assertions.assertEquals(expectedResult.isFound(), actualResult.isFound());
    }

    @Test
    void findDriverForRequestTestFailureFoundBusy() throws IOException {
        //input
        DriveRequest request = new DriveRequest();

        Client client = new Client();
        request.setInitiator(client);

        request.setPeople(new ArrayList<Client>());
        request.setPrice(5);
        request.setPricePerPassenger(5);
        request.setVehicleType("Standard");
        request.setIsReserved(false);
        request.setTimeOfReservation(LocalDateTime.now());
        request.setTimeOfRequestForReservation(LocalDateTime.now());

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );
        request.setLocations(locations);

        request.setDriversThatRejected(new ArrayList<Driver>());

        List<Driver> availableDrivers = new ArrayList<Driver>();
        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setDrivingStatus(DrivingStatus.ONLINE_BUSY);
        driver.setBlocked(false);
        driver.setCurrentLocation(new Point(45.2530233, 19.7916443));
        driver.setDailyActiveInterval(350.0);
        List<Ride> driversRides = new ArrayList<>();
        Ride ride = new Ride();
        ride.setReserved(true);
        ride.setTimeOfReservation(LocalDateTime.now().plusHours(3));
        ride.setLocations(locations);
        driversRides.add(ride);
        driver.setRides(driversRides);
        availableDrivers.add(driver);
        List<Point> points = new ArrayList<>();

        PathInfoDto pathInfoDto = new PathInfoDto();
        pathInfoDto.setDistance(9.5);

        List<Point> pointsForOptimalRoute = Arrays.asList(
                new Point(45.2630233, 19.7916443),
                new Point(45.2530233, 19.7716443)
        );

        pathInfoDto.setAtomicPoints(pointsForOptimalRoute);

        Mockito.when(driverRepository.findByDrivingStatusEquals(DrivingStatus.ONLINE_BUSY)).thenReturn(availableDrivers);
        Mockito.when(mapService.getOptimalRoute(anyList())).thenReturn(pathInfoDto);

        Assertions.assertThrows(NoAvailableDriversException.class,
                () -> driverService.findDriverForRequest(request));
    }

    @Test
    void findDriverForRequestTestAllDriversOffline() throws IOException {
        //input
        DriveRequest request = new DriveRequest();

        Client client = new Client();
        request.setInitiator(client);

        request.setPeople(new ArrayList<Client>());
        request.setPrice(5);
        request.setPricePerPassenger(5);
        request.setVehicleType("Standard");
        request.setIsReserved(false);
        request.setTimeOfReservation(LocalDateTime.now());
        request.setTimeOfRequestForReservation(LocalDateTime.now());

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );
        request.setLocations(locations);

        request.setDriversThatRejected(new ArrayList<Driver>());

        List<Driver> availableDrivers = new ArrayList<Driver>();
        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setDrivingStatus(DrivingStatus.OFFLINE);
        driver.setBlocked(false);
        driver.setCurrentLocation(new Point(45.2530233, 19.7916443));
        driver.setDailyActiveInterval(350.0);
        availableDrivers.add(driver);
        List<Point> points = new ArrayList<>();

        PathInfoDto pathInfoDto = new PathInfoDto();
        pathInfoDto.setDistance(9.5);

        List<Point> pointsForOptimalRoute = Arrays.asList(
                new Point(45.2630233, 19.7916443),
                new Point(45.2530233, 19.7716443)
        );

        pathInfoDto.setAtomicPoints(pointsForOptimalRoute);

        Mockito.when(driverRepository.findByDrivingStatusEquals(DrivingStatus.OFFLINE)).thenReturn(availableDrivers);
        Mockito.when(mapService.getOptimalRoute(anyList())).thenReturn(pathInfoDto);

        Assertions.assertThrows(NoAvailableDriversException.class,
                () -> driverService.findDriverForRequest(request));
    }

    @Test
    void assignDriveToDriverTestSuccess()
    {
        //input
        DriveAssignatureDto driveAssignatureDto = new DriveAssignatureDto();
        driveAssignatureDto.setDriverEmail("dejanmatic@gmail.com");
        driveAssignatureDto.setInitiatorEmail("sasalukic@gmail.com");
        driveAssignatureDto.setRequestId(1L);

        Driver driver = new Driver();
        driver.setRides(new ArrayList<Ride>());

        DriveRequest driveRequest = new DriveRequest();
        driveRequest.setPeople(new ArrayList<Client>());
        driveRequest.setInitiator(new Client());
        driveRequest.setPrice(3);
        driveRequest.setPricePerPassenger(3);
        driveRequest.setVehicleType("Standard");
        driveRequest.setRouteType("Custom");
        driveRequest.setIsReserved(true);
        driveRequest.setTimeOfReservation(LocalDateTime.now());
        driveRequest.setTimeOfRequestForReservation(LocalDateTime.now());

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );
        driveRequest.setLocations(locations);

        Mockito.when(driverRepository.findByEmail(driveAssignatureDto.getDriverEmail())).thenReturn(Optional.of(driver));
        Mockito.when(driveRequestRepository.findById(driveAssignatureDto.getRequestId())).thenReturn(Optional.of(driveRequest));

        driverService.assignDriveToDriver(driveAssignatureDto);

        //assert
        verify(rideRepository, times(1)).save(any(Ride.class));
        verify(driverRepository, times(1)).save(any(Driver.class));
    }

    @Test
    void assignDriveToDriverTestDriverNotFound()
    {
        //input
        DriveAssignatureDto driveAssignatureDto = new DriveAssignatureDto();
        driveAssignatureDto.setDriverEmail("dejanmatic@gmail.com");
        driveAssignatureDto.setInitiatorEmail("sasalukic@gmail.com");
        driveAssignatureDto.setRequestId(1L);

        DriveRequest driveRequest = new DriveRequest();
        driveRequest.setPeople(new ArrayList<Client>());
        driveRequest.setInitiator(new Client());
        driveRequest.setPrice(3);
        driveRequest.setPricePerPassenger(3);
        driveRequest.setVehicleType("Standard");
        driveRequest.setRouteType("Custom");
        driveRequest.setIsReserved(true);
        driveRequest.setTimeOfReservation(LocalDateTime.now());
        driveRequest.setTimeOfRequestForReservation(LocalDateTime.now());

        Mockito.when(driverRepository.findByEmail(driveAssignatureDto.getDriverEmail())).thenReturn(Optional.empty());

        //assert
        Assertions.assertThrows(DriverNotFoundException.class,
                () -> driverService.assignDriveToDriver(driveAssignatureDto));

        verify(rideRepository, times(0)).save(any(Ride.class));
        verify(driverRepository, times(0)).save(any(Driver.class));
    }

    @Test
    void assignDriveToDriverTestDriveRequestNotFound()
    {
        //input
        DriveAssignatureDto driveAssignatureDto = new DriveAssignatureDto();
        driveAssignatureDto.setDriverEmail("dejanmatic@gmail.com");
        driveAssignatureDto.setInitiatorEmail("sasalukic@gmail.com");
        driveAssignatureDto.setRequestId(1L);

        Driver driver = new Driver();
        driver.setRides(new ArrayList<Ride>());

        Mockito.when(driverRepository.findByEmail(driveAssignatureDto.getDriverEmail())).thenReturn(Optional.of(driver));
        Mockito.when(driveRequestRepository.findById(driveAssignatureDto.getRequestId())).thenReturn(Optional.empty());

        //assert
        Assertions.assertThrows(DriveRequestNotFoundException.class,
                () -> driverService.assignDriveToDriver(driveAssignatureDto));

        verify(rideRepository, times(0)).save(any(Ride.class));
        verify(driverRepository, times(0)).save(any(Driver.class));
    }


    // RejectDrive - SW-1-2019
    @Test
    public void givenNonexistentDriver_whenRejectDrive_thenThrowDriverNotFoundException(){
        DriverRejectionDto dto = new DriverRejectionDto();
        dto.setDriverEmail("jovancevic@gmail.com");

        Mockito.when(driverRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(DriverNotFoundException.class,
                () -> driverService.rejectDrive(dto));
    }

    @Test
    public void givenNonexistentDriveRequest_whenRejectDrive_thenThrowDriveRequestNotFoundException(){
        DriverRejectionDto dto = new DriverRejectionDto();
        dto.setDriverEmail("jovancevic@gmail.com");
        dto.setRequestId(1L);

        Mockito.when(driverRepository.findByEmail(anyString())).thenReturn(Optional.of(new Driver()));
        Mockito.when(driveRequestRepository.findById(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrows(DriveRequestNotFoundException.class,
                () -> driverService.rejectDrive(dto));
    }

    @Test
    public void whenRejectDrive_thenReturnSuccess(){
        DriverRejectionDto dto = new DriverRejectionDto();
        dto.setDriverEmail("jovancevic@gmail.com");
        dto.setRequestId(1L);

        DriveRequest dr = new DriveRequest();
        dr.setDriversThatRejected(new ArrayList<>());
        dr.setPeople(new ArrayList<>());

        Client c = new Client();
        c.setEmail("marko@gmail.com");
        dr.setInitiator(c);
        Driver d = new Driver();

        Mockito.when(driverRepository.findByEmail(anyString())).thenReturn(Optional.of(d));
        Mockito.when(driveRequestRepository.findById(anyLong())).thenReturn(Optional.of(dr));

        driverService.rejectDrive(dto);

        verify(driveRequestRepository, times(1)).save(dr);
        verify(simpMessagingTemplate, times(1)).convertAndSendToUser(anyString(), anyString(), any(ResponseToIniciatorDto.class));
    }

    @Test
    public void givenMoreThanOnePassenger_whenRejectDrive_thenReturnSuccess(){
        DriverRejectionDto dto = new DriverRejectionDto();
        dto.setDriverEmail("jovancevic@gmail.com");
        dto.setRequestId(1L);

        DriveRequest dr = new DriveRequest();
        dr.setDriversThatRejected(new ArrayList<>());

        List<Client> people = new ArrayList<>();
        Client firstPassenger = new Client();
        firstPassenger.setEmail("milica@gmail.com");
        people.add(firstPassenger);
        dr.setPeople(people);

        Client c = new Client();
        c.setEmail("marko@gmail.com");
        dr.setInitiator(c);
        Driver d = new Driver();

        Mockito.when(driverRepository.findByEmail(anyString())).thenReturn(Optional.of(d));
        Mockito.when(driveRequestRepository.findById(anyLong())).thenReturn(Optional.of(dr));

        driverService.rejectDrive(dto);

        verify(driveRequestRepository, times(1)).save(dr);
        verify(simpMessagingTemplate, times(2)).convertAndSendToUser(anyString(), anyString(), any(ResponseToIniciatorDto.class));
    }

    @Test
    void updateDriverLocationTestSuccess()
    {
        long id = 1L;
        double latitude = 45.31;
        double longitude = 19.23;

        Driver driver = new Driver();

        Mockito.when(driverRepository.findById(anyLong())).thenReturn(Optional.of(driver));

        Point newPoint = new Point();
        newPoint.setLat(latitude);
        newPoint.setLng(longitude);

        driver.setCurrentLocation(newPoint);

        Driver actualResult = driverService.updateDriverLocation(id, latitude, longitude);
        Assertions.assertEquals(driver, actualResult);

        verify(pointRepository, times(1)).save(newPoint);
        verify(driverRepository, times(1)).save(driver);
    }

    @Test
    void updateDriverLocationTestDriverNotFound()
    {
        long id = 1L;
        double latitude = 45.31;
        double longitude = 19.23;

        Mockito.when(driverRepository.findById(anyLong())).thenReturn(Optional.empty());
        Assertions.assertThrows(DriverNotFoundException.class,
                () -> driverService.updateDriverLocation(id, latitude, longitude));
    }

    @Test
    void rejectDriveAfterAcceptingSuccessTest()
    {
        DriverRejectionDto driverRejectionDto = new DriverRejectionDto();
        driverRejectionDto.setDriverEmail("dejanmatic@gmail.com");
        driverRejectionDto.setRequestId(1L);
        driverRejectionDto.setInitiatorEmail("sasalukic@gmail.com");
        driverRejectionDto.setReasonForRejection("Can't continue");

        Client initiator = new Client();
        initiator.setEmail("sasalukic@gmail.com");
        initiator.setTokens(15);

        Client passenger1 = new Client();
        passenger1.setEmail("milicamatic@gmail.com");
        passenger1.setTokens(8);
        Client passenger2 = new Client();
        passenger2.setEmail("strahinjapavlovic@gmail.com");
        passenger2.setTokens(5);

        Ride ride = new Ride();
        ride.setId(1L);
        ride.setInitiator(initiator);
        ride.setClients(List.of(passenger1, passenger2));

        Ride ride1 = new Ride();
        ride1.setId(2L);
        ride1.setInitiator(initiator);
        ride1.setClients(List.of(passenger1, passenger2));
        List<Ride> rides = new ArrayList<>(List.of(ride,ride1));

        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setId(1L);
        driver.setRides(rides);

        Mockito.when(driverRepository.findByEmail(driverRejectionDto.getDriverEmail())).thenReturn(Optional.of(driver));
        Mockito.when(rideRepository.findById(driverRejectionDto.getRequestId())).thenReturn(Optional.of(ride));

        driverService.rejectDriveAfterAccepting(driverRejectionDto);

        verify(driverRepository, times(1)).save(any(Driver.class));
        verify(rideRepository, times(1)).save(any(Ride.class));
        verify(rejectionRepository, times(1)).save(any(Rejection.class));
    }

    @Test
    void rejectDriveAfterAcceptingDriverNotFoundExTest()
    {
        DriverRejectionDto driverRejectionDto = new DriverRejectionDto();
        driverRejectionDto.setDriverEmail("dejanmatic@gmail.com");
        driverRejectionDto.setRequestId(1L);
        driverRejectionDto.setInitiatorEmail("sasalukic@gmail.com");
        driverRejectionDto.setReasonForRejection("Can't continue");

        Client initiator = new Client();
        initiator.setEmail("sasalukic@gmail.com");
        initiator.setTokens(15);

        Client passenger1 = new Client();
        passenger1.setEmail("milicamatic@gmail.com");
        passenger1.setTokens(8);
        Client passenger2 = new Client();
        passenger2.setEmail("strahinjapavlovic@gmail.com");
        passenger2.setTokens(5);

        Ride ride = new Ride();
        ride.setId(1L);
        ride.setInitiator(initiator);
        ride.setClients(List.of(passenger1, passenger2));

        Ride ride1 = new Ride();
        ride1.setId(2L);
        ride1.setInitiator(initiator);
        ride1.setClients(List.of(passenger1, passenger2));
        List<Ride> rides = new ArrayList<>(List.of(ride,ride1));


        Mockito.when(driverRepository.findByEmail(driverRejectionDto.getDriverEmail())).thenReturn(Optional.empty());
        Mockito.when(rideRepository.findById(driverRejectionDto.getRequestId())).thenReturn(Optional.of(ride));

        Assertions.assertThrows(DriverNotFoundException.class,
                () -> driverService.rejectDriveAfterAccepting(driverRejectionDto));
    }

    @Test
    void rejectDriveAfterAcceptingRideNotFoundExTest()
    {
        DriverRejectionDto driverRejectionDto = new DriverRejectionDto();
        driverRejectionDto.setDriverEmail("dejanmatic@gmail.com");
        driverRejectionDto.setRequestId(1L);
        driverRejectionDto.setInitiatorEmail("sasalukic@gmail.com");
        driverRejectionDto.setReasonForRejection("Can't continue");

        Client initiator = new Client();
        initiator.setEmail("sasalukic@gmail.com");
        initiator.setTokens(15);

        Client passenger1 = new Client();
        passenger1.setEmail("milicamatic@gmail.com");
        passenger1.setTokens(8);
        Client passenger2 = new Client();
        passenger2.setEmail("strahinjapavlovic@gmail.com");
        passenger2.setTokens(5);

        Ride ride = new Ride();
        ride.setId(1L);
        ride.setInitiator(initiator);
        ride.setClients(List.of(passenger1, passenger2));

        Ride ride1 = new Ride();
        ride1.setId(2L);
        ride1.setInitiator(initiator);
        ride1.setClients(List.of(passenger1, passenger2));
        List<Ride> rides = new ArrayList<>(List.of(ride,ride1));

        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setId(1L);
        driver.setRides(rides);


        Mockito.when(driverRepository.findByEmail(driverRejectionDto.getDriverEmail())).thenReturn(Optional.of(driver));
        Mockito.when(rideRepository.findById(driverRejectionDto.getRequestId())).thenReturn(Optional.empty());

        Assertions.assertThrows(RideNotFoundException.class,
                () -> driverService.rejectDriveAfterAccepting(driverRejectionDto));
    }

    @Test
    void rejectDriveAfterAcceptingNullPointerExTest()
    {
        DriverRejectionDto driverRejectionDto = new DriverRejectionDto();
        driverRejectionDto.setDriverEmail("dejanmatic@gmail.com");
        driverRejectionDto.setRequestId(1L);
        driverRejectionDto.setInitiatorEmail("sasalukic@gmail.com");
        driverRejectionDto.setReasonForRejection("Can't continue");

        Client initiator = new Client();
        initiator.setEmail("sasalukic@gmail.com");
        initiator.setTokens(15);

        Client passenger1 = new Client();
        passenger1.setEmail("milicamatic@gmail.com");
        passenger1.setTokens(8);
        Client passenger2 = new Client();
        passenger2.setEmail("strahinjapavlovic@gmail.com");
        passenger2.setTokens(5);

        Ride ride = new Ride();
        ride.setId(1L);
        ride.setClients(List.of(passenger1, passenger2));

        Ride ride1 = new Ride();
        ride1.setId(2L);
        ride1.setInitiator(initiator);
        ride1.setClients(List.of(passenger1, passenger2));
        List<Ride> rides = new ArrayList<>(List.of(ride,ride1));

        Driver driver = new Driver();
        driver.setEmail("dejanmatic@gmail.com");
        driver.setId(1L);
        driver.setRides(rides);


        Mockito.when(driverRepository.findByEmail(driverRejectionDto.getDriverEmail())).thenReturn(Optional.of(driver));
        Mockito.when(rideRepository.findById(driverRejectionDto.getRequestId())).thenReturn(Optional.of(ride));

        Assertions.assertThrows(NullPointerException.class,
                () -> driverService.rejectDriveAfterAccepting(driverRejectionDto));
    }
}
