package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Unit tests for the MovieDiscountCalculator class.
 */
public class MovieDiscountCalculatorTests {
    private MovieDiscountCalculator discountCalculator;

    /**
     * This method sets up the environment before each test case runs.
     */
    @BeforeEach
    public void setUp() {
        discountCalculator = new MovieDiscountCalculator();
    }

    /**
     * This is a case for a regular movie with no discount.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $22.5
     * - Movie is shown on the 5th slot of the day at 6 pm
     * Expected result:
     * - The calculated discount for this ticket price should be $0
     */
    @Test
    public void testCalculateTicketPriceDiscount_RegularMovie_NoDiscount() {
        Movie regularMovie = new Movie("Murder Mystery 2", "Starring Jennifer Anniston and Adam Sandler", Duration.ofMinutes(82), BigDecimal.valueOf(22.5), 0);
        Showing regularShowing = new Showing(regularMovie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(regularShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.ZERO));
    }

    /**
     * This is a case for a special movie with the special movie discount.
     * Test data includes:
     * - Movie is a special one with a special code of 1 and ticket price of $20
     * - Movie is shown on the 4th slot of the day at 6 pm
     * Expected result:
     * - The calculated discount for this ticket price should be $4
     */
    @Test
    public void testCalculateTicketPriceDiscount_SpecialMovie_SpecialDiscount() {
        Movie specialMovie = new Movie("The Simpsons", "A satirical depiction of American Life", Duration.ofMinutes(90), BigDecimal.valueOf(20), 1);
        Showing regularShowing = new Showing(specialMovie, 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(regularShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(4)));
    }

    /**
     * This is a case for a regular movie shown on the first slot of the day.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $18
     * - Movie is shown on the 1st slot of the day at 8 am
     * Expected result:
     * - The calculated discount for this ticket price should be $3
     */
    @Test
    public void testCalculateTicketPriceDiscount_RegularMovie_FirstShowingDiscount() {
        Movie regularMovie = new Movie("The Wizard of Oz", "Kansas is home!", Duration.ofMinutes(100), BigDecimal.valueOf(18), 3);
        Showing firstShowing = new Showing(regularMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(8, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(firstShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(3)));
    }

    /**
     * This is a case for a regular movie shown on the second slot of the day.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $18
     * - Movie is shown on the 2nd slot of the day at 10 am
     * Expected result:
     * - The calculated discount for this ticket price should be $2
     */
    @Test
    public void testCalculateTicketPriceDiscount_RegularMovie_SecondShowingDiscount() {
        Movie regularMovie = new Movie("The Wizard of Oz", "Kansas is home!", Duration.ofMinutes(100), BigDecimal.valueOf(18), 3);
        Showing secondShowing = new Showing(regularMovie, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(10, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(secondShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(2)));
    }

    /**
     * This is a case for a regular movie shown on the seventh slot of the day.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $18
     * - Movie is shown on the 7th slot of the day at 6 pm
     * Expected result:
     * - The calculated discount for this ticket price should be $1
     */
    @Test
    public void testCalculateTicketPriceDiscount_RegularMovie_SeventhShowingDiscount() {
        Movie regularMovie = new Movie("The Wizard of Oz", "Kansas is home!", Duration.ofMinutes(100), BigDecimal.valueOf(18), 3);
        Showing seventhShowing = new Showing(regularMovie, 7, LocalDateTime.of(LocalDate.now(), LocalTime.of(18, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(seventhShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(1)));
    }

    /**
     * This is a case for a regular movie shown in between 11 am and 4 pm and getting 20% discount.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $18
     * - Movie is shown on the 5th slot of the day at 1pm
     * Expected result:
     * - The calculated discount for this ticket price should be $4.5 (25% discount)
     */
    @Test
    public void testCalculateTicketPriceDiscount_RegularMovie_MidDayCutOffDiscount() {
        Movie regularMovie = new Movie("The Wizard of Oz", "Kansas is home!", Duration.ofMinutes(100), BigDecimal.valueOf(18), 3);
        Showing midDayShowing = new Showing(regularMovie, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(13, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(midDayShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(4.5)));
    }

    /**
     * This is a case to check for the biggest discount if a movie hits multiple discount eligibility.
     * Test data includes:
     * - Movie is a regular one with a ticket price of $10
     * - Movie is shown on the 1st slot of the day at 11.30 am
     * - Movie is also eligible for the 25% discount for being shown between 11 am to 4 pm
     * Expected result:
     * - The calculated discount for this ticket price should be $3
     */
    @Test
    public void testCalculateTicketPriceDiscount_BiggestDiscountWins_FirstShowingDiscount() {
        Movie cheapMovie = new Movie("She's All That", "Romcoms are the best movies", Duration.ofMinutes(120), BigDecimal.valueOf(10), 3);
        Showing firstShowing = new Showing(cheapMovie, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 30)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(firstShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(3)));
    }

    /**
     * This is a case to check for the biggest discount if a movie hits multiple discount eligibility.
     * Test data includes:
     * - Movie is a special one with a special code of 1 and a ticket price of $20
     * - Movie is shown on the 2nd slot of the day at 3pm and eligible for $2 off
     * - Movie is also eligible for the 25% discount for being shown between 11 am to 4 pm
     * Expected result:
     * - The calculated discount for this ticket price should be $5
     */
    @Test
    public void testCalculateTicketPriceDiscount_BiggestDiscountWins_TwentyFivePercentDiscount() {
        Movie specialMovie = new Movie("Clueless", "Staple romcom from the 90's", Duration.ofMinutes(80), BigDecimal.valueOf(20), 1);
        Showing midDayShowing = new Showing(specialMovie, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));

        BigDecimal finalDiscount = discountCalculator.calculateTicketPriceDiscount(midDayShowing);
        Assertions.assertEquals(0, finalDiscount.compareTo(BigDecimal.valueOf(5)));
    }

    /**
     * This is a case to throw an exception if the movie object passed to calculator is null.
     * Test data includes:
     * - Null movie object
     * - Showing should be a complete object with null movie
     * Expected result:
     * - Method should throw an Illegal Argument Exception warning that the movie is null
     */
    @Test
    public void testCalculateTicketPriceDiscount_MethodThrowsIllegalArgumentException_MovieNull() {
        Movie nullMovie = null;
        Showing midDayShowing = new Showing(nullMovie, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> discountCalculator.calculateTicketPriceDiscount(midDayShowing));
    }

    /**
     * This is a case to throw an exception if the movie ticket price passed to calculator is null.
     * Test data includes:
     * - Null ticket price on movie object
     * - Showing should be a complete object with the movie
     * Expected result:
     * - Method should throw an Illegal Argument Exception warning that the movie ticket price cannot be null
     */
    @Test
    public void testCalculateTicketPriceDiscount_MethodThrowsIllegalArgumentException_MovieWithNoPrice() {
        Movie specialMovie = new Movie("Clueless", "Staple romcom from the 90's", Duration.ofMinutes(80), null, 1);
        Showing midDayShowing = new Showing(specialMovie, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> discountCalculator.calculateTicketPriceDiscount(midDayShowing));
    }

    /**
     * This is a case to throw an exception if the showing object passed to the calculator is null.
     * Test data includes:
     * - Null showing object
     * - Movie object can be passed normally
     * Expected result:
     * - Method should throw an Illegal Argument Exception warning that the showing object cannot be null
     */
    @Test
    public void testCalculateTicketPriceDiscount_MethodThrowsIllegalArgumentException_ShowingNull() {
        Showing nullShowing = null;

        Assertions.assertThrows(IllegalArgumentException.class, () -> discountCalculator.calculateTicketPriceDiscount(nullShowing));
    }

    /**
     * This is a case to throw an exception if the showing object for the movie has a ZERO sequence of the day field.
     * Test data includes:
     * - Showing object has 0 for the sequence of the day for this movie
     * Expected result:
     * - Method should throw an Illegal Argument Exception warning that the showing object cannot have a 0 for its sequence of the day for the movie
     */
    @Test
    public void testCalculateTicketPriceDiscount_MethodThrowsIllegalArgumentException_ShowingSequence0() {
        Movie specialMovie = new Movie("Clueless", "Staple romcom from the 90's", Duration.ofMinutes(80), null, 1);
        Showing wrongShowing = new Showing(specialMovie, 0, LocalDateTime.of(LocalDate.now(), LocalTime.of(15, 0)));

        Assertions.assertThrows(IllegalArgumentException.class, () -> discountCalculator.calculateTicketPriceDiscount(wrongShowing));
    }
}
