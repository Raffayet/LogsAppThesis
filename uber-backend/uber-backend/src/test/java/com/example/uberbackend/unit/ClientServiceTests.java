package com.example.uberbackend.unit;
import com.example.uberbackend.dto.DriveInvitationDto;
import com.example.uberbackend.dto.DriveRequestDto;
import com.example.uberbackend.dto.DriverFoundDto;
import com.example.uberbackend.dto.MapSearchResultDto;
import com.example.uberbackend.exception.*;
import com.example.uberbackend.model.*;
import com.example.uberbackend.model.enums.RideInviteStatus;
import com.example.uberbackend.dto.*;
import com.example.uberbackend.exception.DriveRequestNotFoundException;
import com.example.uberbackend.exception.EmptyStringException;
import com.example.uberbackend.exception.RideInviteNotFoundException;
import com.example.uberbackend.exception.UserNotFoundException;
import com.example.uberbackend.repositories.jpa.*;
import com.example.uberbackend.service.ClientService;
import com.example.uberbackend.service.DriverService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class ClientServiceTests {

    @InjectMocks
    ClientService clientService;

    @Mock
    DriverService driverService;
    @Mock
    ClientRepository clientRepository;
    @Mock
    DriveRequestRepository driveRequestRepository;
    @Mock
    RideInviteRepository rideInviteRepository;
    @Mock
    RideRepository rideRepository;
    @Mock
    SimpMessagingTemplate simpMessagingTemplate;
    @Mock
    FavoriteRouteRepository favoriteRouteRepository;
    @Mock
    MapSearchResultRepository mapSearchResultRepository;

    @Test
    void createDriveRequestSuccessTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(250);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.now());
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.now());
        driveRequestDto.setLocations(locations);

        DriverFoundDto driverFoundDto = new DriverFoundDto();
        driverFoundDto.setFound(false);
        driverFoundDto.setDriverEmail("dejanmatic@gmail.com");

        Client client = new Client();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(driverFoundDto);

        clientService.createDriveRequest(driveRequestDto);

        verify(driveRequestRepository, times(1)).save(any(DriveRequest.class));
    }

    @Test
    void createDriveRequestUsernameNotFoundExTest(){
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,()->clientService.createDriveRequest(driveRequestDto));
        assertEquals(0, driveRequestDto.getPeople().size());
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
    }

    @Test
    void createDriveRequestDriverNotFoundExTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(250);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.now());
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.now());
        driveRequestDto.setLocations(locations);

        Client client = new Client();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(null);

        assertThrows(NullPointerException.class,()->clientService.createDriveRequest(driveRequestDto));
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
        verify(driveRequestRepository, times(1)).save(any(DriveRequest.class));
    }

    @Test
    void createDriveRequestNotEnoughTokensExTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(250);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.now());
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.now());
        driveRequestDto.setLocations(locations);

        Client client = new Client();
        client.setTokens(10);

        DriverFoundDto driverFoundDto = new DriverFoundDto();
        driverFoundDto.setFound(true);
        driverFoundDto.setDriverEmail("dejanmatic@gmail.com");

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(driverFoundDto);

        assertThrows(PaymentFailedException.class,()->clientService.createDriveRequest(driveRequestDto));
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
        verify(driveRequestRepository, times(1)).save(any(DriveRequest.class));
    }

    @Test
    void createDriveRequestPriceLowerThanPricePerPassengerExTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(300);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.now());
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.now());
        driveRequestDto.setLocations(locations);

        Client client = new Client();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(null);

        assertThrows(PriceNotValidException.class,()->clientService.createDriveRequest(driveRequestDto));
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
    }

    @Test
    void createDriveRequestOneLocationExTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = List.of(
                new MapSearchResultDto(1L, "Rumenacka", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(250);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.now());
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.now());
        driveRequestDto.setLocations(locations);

        Client client = new Client();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(null);

        assertThrows(NotEnoughLocationsException.class,()->clientService.createDriveRequest(driveRequestDto));
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
    }

    @Test
    void createDriveRequestTimeOfReservationExTest() throws IOException {
        DriveRequestDto driveRequestDto = new DriveRequestDto();

        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto(1L,"Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto(2L,"Futoska", "45.11", "19.00")
        );

        driveRequestDto.setInitiatorEmail("sasalukic@gmail.com");
        driveRequestDto.setPeople(new ArrayList<>());
        driveRequestDto.setPrice(250);
        driveRequestDto.setPricePerPassenger(250);
        driveRequestDto.setVehicleType("Standard");
        driveRequestDto.setRouteType("Custom");
        driveRequestDto.setIsReserved(false);
        driveRequestDto.setTimeOfReservation(LocalDateTime.of(2022,11,2,5,0));
        driveRequestDto.setTimeOfRequestForReservation(LocalDateTime.of(2023,1,2,5,0));
        driveRequestDto.setLocations(locations);

        Client client = new Client();

        Mockito.when(clientRepository.findByEmail(driveRequestDto.getInitiatorEmail())).thenReturn(Optional.of(client));
        Mockito.when(driverService.findDriverForRequest(any(DriveRequest.class))).thenReturn(null);

        assertThrows(NotValidDateTime.class,()->clientService.createDriveRequest(driveRequestDto));
        verify(clientRepository, times(1)).findByEmail(driveRequestDto.getInitiatorEmail());
    }

    @Test
    void createDriveInvitationSuccessTest(){
        DriveInvitationDto driveInvitationDto = new DriveInvitationDto();
        driveInvitationDto.setFirstLocation("Rumenacka, Novi Sad");
        driveInvitationDto.setDestination("Futoska, Novi Sad");
        driveInvitationDto.setEmailFrom("sasalukic@gmail.com");
        driveInvitationDto.setRideInviteStatus(RideInviteStatus.PENDING);
        driveInvitationDto.setEmailsTo(List.of("milicamatic@gmail.com", "strahinjapavlovic@gmail.com"));
        driveInvitationDto.setPriceToPay(10);

        clientService.createDriveInvitation(driveInvitationDto);
        verify(rideInviteRepository, times(2)).save(any(RideInvite.class));
    }
    @Test
    void createDriveInvitationNoEmailsTest(){
        DriveInvitationDto driveInvitationDto = new DriveInvitationDto();
        driveInvitationDto.setFirstLocation("Rumenacka, Novi Sad");
        driveInvitationDto.setDestination("Futoska, Novi Sad");
        driveInvitationDto.setEmailFrom("sasalukic@gmail.com");
        driveInvitationDto.setRideInviteStatus(RideInviteStatus.PENDING);
        driveInvitationDto.setEmailsTo(new ArrayList<>());
        driveInvitationDto.setPriceToPay(10);

        clientService.createDriveInvitation(driveInvitationDto);
        verify(rideInviteRepository, times(0)).save(any(RideInvite.class));
    }

    @Test
    void findAllRideInvitesSuccessTest(){
        List <RideInvite> rideInvites = List.of(
                new RideInvite(1L, "milicamatic@gmail.com", "sasalukic@gmail.com", "Rumenacka, Novi Sad", "Futoska, Novi Sad", 10, RideInviteStatus.PENDING),
                new RideInvite(1L, "strahinjapavlovic@gmail.com", "sasalukic@gmail.com", "Bul. oslobodjenja , Novi Sad", "Fruskogorska, Novi Sad", 10, RideInviteStatus.PENDING)
        );
        String email = "sasalukic@gmail.com";
        Mockito.when(rideInviteRepository.findAllRideInvites(email)).thenReturn(rideInvites);

        assertEquals(clientService.findAllRideInvites(email), rideInvites);
        verify(rideInviteRepository, times(1)).findAllRideInvites(email);
    }

    @Test
    void findAllRideInvitesEmptyTest(){
        List <RideInvite> rideInvites = new ArrayList<>();
        String email = "sasalukic@gmail.com";
        Mockito.when(rideInviteRepository.findAllRideInvites(email)).thenReturn(rideInvites);

        assertEquals(clientService.findAllRideInvites(email), rideInvites);
        verify(rideInviteRepository, times(1)).findAllRideInvites(email);
    }

    @Test
    void findAllRideInvitesNullTest(){
        String email = "sasalukic@gmail.com";
        Mockito.when(rideInviteRepository.findAllRideInvites(email)).thenReturn(null);

        assertNull(clientService.findAllRideInvites(email));
        verify(rideInviteRepository, times(1)).findAllRideInvites(email);
    }

    @Test
    void refundTokensAfterAcceptingSuccessTest(){
        Client initiator = new Client();
        initiator.setEmail("sasalukic@gmail.com");
        initiator.setTokens(20);

        Client passenger1 = new Client();
        passenger1.setEmail("milicamatic@gmail.com");
        passenger1.setTokens(8);
        Client passenger2 = new Client();
        passenger2.setEmail("strahinjapavlovic@gmail.com");
        passenger2.setTokens(5);

        Ride ride = new Ride();
        ride.setId(1L);
        ride.setPrice(10);
        ride.setPricePerPassenger(5);
        ride.setInitiator(initiator);
        ride.setClients(List.of(passenger1, passenger2));

        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.of(ride));

        clientService.refundTokensAfterAccepting(1L);
        verify(clientRepository, times(3)).save(any(Client.class));
        verify(rideRepository, times(1)).findById(anyLong());
    }
    @Test
    void refundTokensAfterAcceptingRideNotFoundExTest(){
        Mockito.when(rideRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RideNotFoundException.class ,()->clientService.refundTokensAfterAccepting(1L));
        verify(clientRepository, times(0)).save(any(Client.class));
        verify(rideRepository, times(1)).findById(anyLong());
    }

    @Test
    void getTokensByEmailTestSuccess()
    {
        String email = "sasalukic@gmail.com";
        double expectedValue = 10.0;
        Mockito.when(clientRepository.getTokensByEmail(email)).thenReturn(10.0);

        double actualValue = clientService.getTokensByEmail(email);
        Assertions.assertEquals(expectedValue, actualValue);
    }

    @Test
    void getTokensByEmailTestEmptyEmail()
    {
        String email = "";
        Assertions.assertThrows(EmptyStringException.class, () -> clientService.getTokensByEmail(email));
    }

    @Test
    void invitedHasTokensTestSuccessNoPeopleEmails()
    {
        CheckForEnoughTokens checkForEnoughTokens = new CheckForEnoughTokens();
        checkForEnoughTokens.setInitiatorEmail("sasalukic@gmail.com");
        checkForEnoughTokens.setPeopleEmails(new String[]{});
        checkForEnoughTokens.setPricePerPassenger(2);

        String expectedValue = "true";
        String actualValue = clientService.invitedHasTokens(checkForEnoughTokens);
        Assertions.assertEquals(expectedValue, actualValue);
        verify(clientRepository, times(0)).findByEmail(any(String.class));
    }

    @Test
    void invitedHasTokensTestSuccessEnoughTokens()
    {
        CheckForEnoughTokens checkForEnoughTokens = new CheckForEnoughTokens();
        checkForEnoughTokens.setInitiatorEmail("sasalukic@gmail.com");
        checkForEnoughTokens.setPeopleEmails(new String[]{
                "milicamatic@gmail.com"
        });
        checkForEnoughTokens.setPricePerPassenger(2);

        Client milica = new Client();
        milica.setTokens(3);
        Mockito.when(clientRepository.findByEmail(checkForEnoughTokens.getPeopleEmails()[0])).thenReturn(Optional.of(milica));

        String expectedValue = "true";
        String actualValue = clientService.invitedHasTokens(checkForEnoughTokens);
        Assertions.assertEquals(expectedValue, actualValue);
        verify(clientRepository, times(1)).findByEmail(any(String.class));
    }

    @Test
    void invitedHasTokensTestSuccessNotEnoughTokens()
    {
        CheckForEnoughTokens checkForEnoughTokens = new CheckForEnoughTokens();
        checkForEnoughTokens.setInitiatorEmail("sasalukic@gmail.com");
        checkForEnoughTokens.setPeopleEmails(new String[]{
                "milicamatic@gmail.com"
        });
        checkForEnoughTokens.setPricePerPassenger(2);

        Client milica = new Client();
        milica.setTokens(1);
        Mockito.when(clientRepository.findByEmail(checkForEnoughTokens.getPeopleEmails()[0])).thenReturn(Optional.of(milica));

        Assertions.assertThrows(NotEnoughTokensException.class, () -> clientService.invitedHasTokens(checkForEnoughTokens));
        verify(clientRepository, times(1)).findByEmail(any(String.class));
    }

    @Test
    void invitedHasTokensTestInvitedPersonNotExist()
    {
        CheckForEnoughTokens checkForEnoughTokens = new CheckForEnoughTokens();
        checkForEnoughTokens.setInitiatorEmail("sasalukic@gmail.com");
        checkForEnoughTokens.setPeopleEmails(new String[]{
                "milicamatic@gmail.com"
        });
        checkForEnoughTokens.setPricePerPassenger(2);
        Mockito.when(clientRepository.findByEmail(checkForEnoughTokens.getPeopleEmails()[0])).thenReturn(Optional.empty());

        Assertions.assertThrows(UserNotFoundException.class, () -> clientService.invitedHasTokens(checkForEnoughTokens));
        verify(clientRepository, times(1)).findByEmail(any(String.class));
    }

    @Test
    void changeDriveInvitationStatusTestSuccessAccepted()
    {
        InvitationStatusDto invitationStatusDto = new InvitationStatusDto();
        invitationStatusDto.setInvitationId(1L);
        invitationStatusDto.setAccepted(true);

        RideInvite rideInvite = new RideInvite();
        Mockito.when(rideInviteRepository.findById(invitationStatusDto.getInvitationId())).thenReturn(Optional.of(rideInvite));

        RideInviteStatus expectedValue = RideInviteStatus.ACCEPTED;

        clientService.changeDriveInvitationStatus(invitationStatusDto);
        Assertions.assertEquals(expectedValue, rideInvite.getRideInviteStatus());
        verify(rideInviteRepository, times(1)).save(any(RideInvite.class));
    }

    @Test
    void changeDriveInvitationStatusTestSuccessRejected()
    {
        InvitationStatusDto invitationStatusDto = new InvitationStatusDto();
        invitationStatusDto.setInvitationId(1L);
        invitationStatusDto.setAccepted(false);

        RideInvite rideInvite = new RideInvite();
        Mockito.when(rideInviteRepository.findById(invitationStatusDto.getInvitationId())).thenReturn(Optional.of(rideInvite));

        RideInviteStatus expectedValue = RideInviteStatus.REJECTED;

        clientService.changeDriveInvitationStatus(invitationStatusDto);
        Assertions.assertEquals(expectedValue, rideInvite.getRideInviteStatus());
        verify(rideInviteRepository, times(1)).save(any(RideInvite.class));
    }

    @Test
    void changeDriveInvitationStatusTestRideInviteNotFound()
    {
        InvitationStatusDto invitationStatusDto = new InvitationStatusDto();
        invitationStatusDto.setInvitationId(1L);
        invitationStatusDto.setAccepted(true);

        Mockito.when(rideInviteRepository.findById(invitationStatusDto.getInvitationId())).thenReturn(Optional.empty());
        Assertions.assertThrows(RideInviteNotFoundException.class, () -> clientService.changeDriveInvitationStatus(invitationStatusDto));
        verify(rideInviteRepository, times(0)).save(any(RideInvite.class));
    }

    @Test
    void refundTokensTestSuccessNoInvitedPeople()
    {
        //input
        Long requestId = 1L;
        DriveRequest request = new DriveRequest();

        Client initiator = new Client();
        initiator.setTokens(15);
        double expectedValue = initiator.getTokens();

        request.setInitiator(initiator);
        request.setPricePerPassenger(5);

        request.setPeople(new ArrayList<>());

        Mockito.when(driveRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        clientService.refundTokens(requestId);
        Assertions.assertNotEquals(expectedValue, initiator.getTokens());
    }

    @Test
    void refundTokensTestSuccessInvitedPeople()
    {
        //input
        Long requestId = 1L;
        DriveRequest request = new DriveRequest();

        Client initiator = new Client();
        initiator.setTokens(15);
        double expectedValueInitiator = initiator.getTokens();

        request.setInitiator(initiator);
        request.setPricePerPassenger(5);

        Client client1 = new Client();
        client1.setTokens(10);
        Client client2 = new Client();
        client1.setTokens(8);
        double expectedValueInvited = client2.getTokens();

        List<Client> clients = new ArrayList<>();
        clients.add(client1);
        clients.add(client2);
        request.setPeople(clients);

        Mockito.when(driveRequestRepository.findById(requestId)).thenReturn(Optional.of(request));
        clientService.refundTokens(requestId);
        Assertions.assertNotEquals(expectedValueInitiator, initiator.getTokens());
        Assertions.assertNotEquals(expectedValueInvited, client2.getTokens());
    }

    @Test
    void refundTokensTestDriveRequestNotFound()
    {
        //input
        Long requestId = 1L;

        Mockito.when(driveRequestRepository.findById(requestId)).thenReturn(Optional.empty());
        Assertions.assertThrows(DriveRequestNotFoundException.class, () -> clientService.refundTokens(requestId));
    }

    // GetFavoriteRoutes - SW-1-2019
    @Test
    public void givenEmptyString_whenGetFavoriteRoutesByEmail_returnEmptyList(){
        String email = "";
        Mockito.when(clientRepository.getFavoriteRoutesByEmail(email)).thenReturn(new ArrayList<>());

        List<FavoriteRouteDto> routes = clientService.getFavoriteRoutes(email);
        assertEquals(0, routes.size());
    }

    @Test
    public void givenNonexistentClientEmail_whenGetFavoriteRoutesByEmail_returnEmptyList(){
        String email = "client@gmail.com";
        Mockito.when(clientRepository.getFavoriteRoutesByEmail(email)).thenReturn(new ArrayList<>());

        List<FavoriteRouteDto> routes = clientService.getFavoriteRoutes(email);

        assertEquals(0, routes.size());
    }

    @Test
    public void givenExistentClientEmail_whenGetFavoriteRoutesByEmail_returnListOfSizeOne(){
        String email = "sasalukic@gmail.com";
        List<FavoriteRoute> retVal = new ArrayList<>();
        FavoriteRoute fr = new FavoriteRoute();
        Client c = new Client();
        c.setEmail("client@gmail.com");
        fr.setClient(c);

        retVal.add(fr);

        Mockito.when(clientRepository.getFavoriteRoutesByEmail(email)).thenReturn(retVal);

        List<FavoriteRouteDto> routes = clientService.getFavoriteRoutes(email);

        assertEquals(1, routes.size());
    }

    // AddFavoriteRoute - SW-1-2019
    @Test
    public void givenNonexistentClientEmail_whenAddFavoriteRoute_throwClientNotFoundException(){
        FavoriteRouteDto dto = new FavoriteRouteDto();
        dto.setLocations(new ArrayList<>());
        dto.setClientEmail("");

        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Assertions.assertThrows(ClientNotFoundException.class, () -> clientService.addFavoriteRoute(dto));
    }

    @Test
    public void givenExistentClientEmail_whenAddFavoriteRoute_returnFalse(){
        // Locations
        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto("Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto("Futoska", "45.11", "19.00")
        );

        // dto
        FavoriteRouteDto dto = new FavoriteRouteDto();
        dto.setLocations(locations);
        dto.setClientEmail("milan@gmail.com");

        // inner object
        List<FavoriteRoute> favoriteRoutes = new ArrayList<>();
        FavoriteRoute fr = new FavoriteRoute();
        Client c = new Client();
        c.setEmail("milan@gmail.com");
        fr.setClient(c);
        fr.setLocations(locations);
        favoriteRoutes.add(fr);
        c.setFavoriteRoutes(favoriteRoutes);

        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(Optional.of(c));

        boolean result = clientService.addFavoriteRoute(dto);
        assertFalse(result);
        verify(favoriteRouteRepository, times(0)).save(any(FavoriteRoute.class));
        verify(clientRepository, times(0)).save(any(Client.class));
    }

    @Test
    public void givenExistentClientEmail_whenAddFavoriteRoute_returnTrue(){
        // Locations
        List<MapSearchResultDto> locations = Arrays.asList(
                new MapSearchResultDto("Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto("Futoska", "45.11", "19.00")
        );

        List<MapSearchResultDto> locations1 = Arrays.asList(
                new MapSearchResultDto("Rumenacka", "45.11", "19.00"),
                new MapSearchResultDto("Futoska", "45.11", "19.00"),
                new MapSearchResultDto("Kisacka", "45.21", "19.20")
        );

        // dto
        FavoriteRouteDto dto = new FavoriteRouteDto();
        dto.setLocations(locations);
        dto.setClientEmail("milan@gmail.com");

        // inner object
        List<FavoriteRoute> favoriteRoutes = new ArrayList<>();
        FavoriteRoute fr = new FavoriteRoute();
        Client c = new Client();
        c.setEmail("milan@gmail.com");
        fr.setClient(c);
        fr.setLocations(locations1);
        favoriteRoutes.add(fr);
        c.setFavoriteRoutes(favoriteRoutes);

        Mockito.when(clientRepository.findByEmail(anyString())).thenReturn(Optional.of(c));

        boolean result = clientService.addFavoriteRoute(dto);
        assertTrue(result);
        verify(favoriteRouteRepository, times(1)).save(any(FavoriteRoute.class));
        verify(clientRepository, times(1)).save(any(Client.class));
    }
}
