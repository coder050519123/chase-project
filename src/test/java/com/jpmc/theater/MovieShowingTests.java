package com.jpmc.theater;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for the Movie and Showing classes.
 */
public class MovieShowingTests {
    /**
     * This is a test case to calculate the final showing price of a movie with no discounts.
     * Test data includes:
     * - Movie is a regular movie with ticket price of $12.5
     * - Showing is 5th of day and start time is 6 pm
     * Expected result:
     * - The final showing price of this movie should remain at $12.5
     */
    @Test
    public void testGetFinalShowingPrice_RegularMovie_NoDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(12.5), 0);
        Showing showing = new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        assertEquals(0, showing.getFinalShowingPrice().compareTo(BigDecimal.valueOf(12.5)));
    }

    /**
     * This is a test case to calculate the final showing price of a movie applied with the early-mid-day 25% discount.
     * Test data includes:
     * - Movie is a regular movie with ticket price of $12.5
     * - Showing is 4th of day but start time is 1 pm (inside the cut-off)
     * Expected result:
     * - The final showing price of this movie per unit is $9.38
     */
    @Test
    public void testGetFinalShowingPrice_RegularMovie_EarlyToMidDayShowingDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(12.5), 0);
        Showing showing = new Showing(spiderMan, 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)));

        assertEquals(0, showing.getFinalShowingPrice().compareTo(BigDecimal.valueOf(9.38)));
    }

    /**
     * This is a test case to calculate the final showing price of a movie applied with the second of day showing discount.
     * Test data includes:
     * - Movie is a regular movie with ticket price of $12.5
     * - Showing is 2nd of day and start time is 10 am.
     * Expected result:
     * - The final showing price of this movie per unit is $10.5
     */
    @Test
    public void testGetFinalShowingPrice_RegularMovie_SecondSequenceShowingDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(12.5), 0);
        Showing showing = new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));

        assertEquals(0, showing.getFinalShowingPrice().compareTo(BigDecimal.valueOf(10.5)));
    }

    /**
     * This is a test case to calculate the final showing price of a movie applied with the special movie discount.
     * Test data includes:
     * - Movie is a special movie with ticket price of $12.5
     * - Showing is 9th of day and start time is 8pm
     * Expected result:
     * - The final showing price of this movie per unit is $10
     */
    @Test
    public void testGetFinalShowingPrice_SpecialMovie_SpecialMovieDiscount() {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spiderman Movie Test Description", Duration.ofMinutes(90), BigDecimal.valueOf(12.5), 1);
        Showing showing = new Showing(spiderMan, 9, LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0)));

        assertEquals(0, showing.getFinalShowingPrice().compareTo(BigDecimal.valueOf(10)));
    }
}
