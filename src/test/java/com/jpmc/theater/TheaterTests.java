package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Unit and Integration tests for Theater class.
 */
public class TheaterTests {
    /**
     * PrintStream and ByteArrayOutputStream are used to test the System.out.println methods called in the Theater class
     */
    private PrintStream originalSystemOut;
    private ByteArrayOutputStream outputStream;
    private Theater theater;
    private Customer customer;
    private List<Showing> schedule;

    /**
     * This method sets up the environment before each test case runs.
     */
    @BeforeEach
    public void setUp() {
        schedule = new ArrayList<>();
        Movie movie1 = new Movie("Test Movie 1", "Test Movie Desc 1", Duration.ofMinutes(100), BigDecimal.valueOf(20), 0);
        Movie movie2 = new Movie("Test Movie 2", "Test Movie Desc 2", Duration.ofMinutes(100), BigDecimal.valueOf(22), 1);

        schedule.add(new Showing(movie1, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0))));
        schedule.add(new Showing(movie2, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0))));

        theater = new Theater(schedule);
        customer = new Customer("John Doe", "customer-random-id");

        outputStream = new ByteArrayOutputStream();
        originalSystemOut = System.out;
        System.setOut(new PrintStream(outputStream));
    }

    /**
     * This is a case to see that the schedule getter returns the right object
     * Test data includes:
     * - Theater with 2 movies in the schedule
     * Expected result:
     * - The schedule returned should have 2 movies
     */
    @Test
    public void testGetTheaterSchedule() {
        Assertions.assertEquals(2, theater.getSchedule().size());
    }

    /**
     * This is a case to test a valid reservation being created for the Theater
     * Test data includes:
     * - Valid customer, sequence, and ticketAmount inputs
     * - Theater with 2 movies in schedule
     * Expected result:
     * - The correct reservation object being created that is non-null
     */
    @Test
    public void testCreateReservation_ValidReservation() {
        int sequence = 2;
        int ticketAmount = 3;
        Reservation reservation = theater.createReservation(customer, sequence, ticketAmount);
        Assertions.assertNotNull(reservation);
        Assertions.assertEquals(customer, reservation.getCustomer());
        Assertions.assertEquals(schedule.get(sequence - 1).getMovie(), reservation.getShowing().getMovie());
        Assertions.assertEquals(ticketAmount, reservation.getAudienceCount());
    }

    /**
     * This is a case to test an invalid reservation attempted to be created for the Theater
     * Test data includes:
     * - Invalid sequence input, and valid ticketAmount input
     * Expected result:
     * - The method throws an IllegalStateException due to the sequence being greater than the number of showings in the schedule
     */
    @Test
    public void testCreateReservation_InvalidReservation_IncorrectSequence_ThrowsIllegalStateException() {
        int sequence = 15;
        int ticketAmount = 3;
        Assertions.assertThrows(IllegalStateException.class, () -> theater.createReservation(customer, sequence, ticketAmount));
    }

    /**
     * This is a case to test an invalid reservation attempted to be created for the Theater
     * Test data includes:
     * - Invalid ticketAmount input, and valid sequence input
     * Expected result:
     * - The method throws an IllegalArgumentException due to the ticketAmount being less than 1
     */
    @Test
    public void testCreateReservation_InvalidReservation_IncorrectTicketAmount_ThrowsIllegalArgumentException() {
        int sequence = 1;
        int ticketAmount = -3;
        Assertions.assertThrows(IllegalArgumentException.class, () -> theater.createReservation(customer, sequence, ticketAmount));
    }

    /**
     * This is an integration test to create a reservation from the theater and calculate the total fee for the reservation
     * Test data includes:
     * - Valid customer, sequence, and ticketAmount inputs
     * - Theater with 2 movies in schedule
     * - Showing chosen is eligible for special movie discount
     * Expected result:
     * - The method should return the total reservation fee to be $70.4
     */
    @Test
    public void testIntegrationTheaterTest_ValidReservation_SpecialMovieDiscount() {
        int sequence = 2;
        int ticketAmount = 4;
        Reservation reservation = theater.createReservation(customer, sequence, ticketAmount);
        Assertions.assertEquals(0, reservation.calculateTotalReservationFee().compareTo(BigDecimal.valueOf(70.4)));
    }

    /**
     * This is a test to see if the schedule print method works as expected
     * This uses PrintStream and ByteArrayOutputStream
     * Test data includes:
     * - Theater with an empty schedule
     * Expected result:
     * - The method should return with a message saying no shows are scheduled.
     */
    @Test
    public void testPrintMovieSchedule_NoSchedule() {
        Theater newTheaterEmptySchedule = new Theater(Collections.emptyList());
        newTheaterEmptySchedule.printSchedule();
        String output = outputStream.toString();

        String expectedResult = "No shows scheduled.";
        Assertions.assertEquals(expectedResult, output.trim());
    }

    /**
     * This is another test to see if the schedule print method works as expected
     * This uses PrintStream and ByteArrayOutputStream
     * Test data includes:
     * - Theater with 2 movies in the schedule
     * Expected result:
     * - The method should return with the correct schedule printed with 2 movies
     */
    @Test
    public void testPrintMovieSchedule_ScheduleExists() {
        theater.printSchedule();
        String output = outputStream.toString();

        String expectedResult = LocalDate.now() + System.lineSeparator() +
                "===================================================" + System.lineSeparator() +
                "1: " + LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)) + " Test Movie 1 (1 hour 40 minutes) $20" + System.lineSeparator() +
                "2: " + LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)) + " Test Movie 2 (1 hour 40 minutes) $22" + System.lineSeparator() +
                "===================================================" + System.lineSeparator();
        Assertions.assertEquals(expectedResult, output);
    }

    /**
     * This is a test to see if the schedule print to JSON method works as expected
     * This uses PrintStream and ByteArrayOutputStream
     * Test data includes:
     * - Theater with 2 movies in the schedule
     * Expected result:
     * - The method should return with the correct schedule printed with 2 movies but in a JSON format and encapsulated by the date
     */
    @Test
    public void testPrintMovieScheduleToJson() {
        theater.printScheduleToJson();
        String output = outputStream.toString();

        String expectedResult = "{\"2023-06-23\":[{\"movie\":{\"title\":\"Test Movie 1\",\"description\":\"Test Movie Desc 1\",\"runningTime\":{\"seconds\":6000,\"nano\":0,\"negative\":false,\"zero\":false,\"units\":[\"SECONDS\",\"NANOS\"]},\"ticketPrice\":20,\"specialCode\":0},\"sequenceOfTheDay\":1,\"showStartTime\":{\"year\":2023,\"monthValue\":6,\"dayOfMonth\":23,\"hour\":8,\"minute\":0,\"second\":0,\"nano\":0,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":174,\"month\":\"JUNE\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"finalShowingPrice\":17.00},{\"movie\":{\"title\":\"Test Movie 2\",\"description\":\"Test Movie Desc 2\",\"runningTime\":{\"seconds\":6000,\"nano\":0,\"negative\":false,\"zero\":false,\"units\":[\"SECONDS\",\"NANOS\"]},\"ticketPrice\":22,\"specialCode\":1},\"sequenceOfTheDay\":2,\"showStartTime\":{\"year\":2023,\"monthValue\":6,\"dayOfMonth\":23,\"hour\":10,\"minute\":0,\"second\":0,\"nano\":0,\"dayOfWeek\":\"FRIDAY\",\"dayOfYear\":174,\"month\":\"JUNE\",\"chronology\":{\"id\":\"ISO\",\"calendarType\":\"iso8601\"}},\"finalShowingPrice\":17.60}]}";
        Assertions.assertEquals(expectedResult, output.trim());
    }
}
