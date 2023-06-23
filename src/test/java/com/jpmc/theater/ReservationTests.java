package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationTests {
    private Customer customer;
    /**
     * This method sets up the environment before each test case runs.
     */
    @BeforeEach
    public void setUp() {
        customer = new Customer("John Doe", "customer-id-test");
    }

    /**
     * This is a test case to calculate the final reservation price of a special movie for a party size of 5 people.
     * Test data includes:
     * - Movie is a special movie with ticket price of $22.5
     * - Showing is 9th of day and start time is 8 pm
     * - Reservation has a standard customer and an audienceCount of 5
     * Expected result:
     * - The final reservation cost for this movie for 5 people is $90
     */
    @Test
    public void testCalculateTotalReservationFee_FivePartySize_SpecialMovie() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(22.5), 1);
        Showing showing = new Showing(spiderMan, 9, LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0)));

        Reservation reservation = new Reservation(customer, showing, 5);

        Assertions.assertEquals(0, reservation.calculateTotalReservationFee().compareTo(BigDecimal.valueOf(90)));
    }

    /**
     * This is a test case to calculate the final reservation price of a regular movie for a party size of 3 people.
     * Test data includes:
     * - Movie is a regular movie with ticket price of $22.5
     * - Showing is 5th of day and start time is 6 pm
     * - Reservation has a standard customer and an audienceCount of 3
     * Expected result:
     * - The final reservation cost for this movie for 5 people is $67.5
     */
    @Test
    public void testCalculateTotalReservationFee_ThreePartySize_RegularMovieNoDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(22.5), 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        Reservation reservation = new Reservation(customer, showing, 3);

        Assertions.assertEquals(0, reservation.calculateTotalReservationFee().compareTo(BigDecimal.valueOf(67.5)));
    }

    /**
     * This is a case to throw an exception if the audience count input in a reservation is zero.
     * Test data includes:
     * - Standard movie and standard showing
     * - Reservation has a standard customer but an audience count of 0 when creating the reservation
     * Expected result:
     * - Method should throw an Illegal Argument Exception warning that the audience count cannot be 0 or negative
     */
    @Test
    public void testZeroAudienceCount_MethodThrowsIllegalArgumentException() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(22.5), 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Reservation reservation = new Reservation(customer, showing, 0);
        });
    }

}
