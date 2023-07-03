package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class is a "calculator" class used to calculate the discount of a movie's price depending on its codes and corresponding showing.
 * This class holds static values in order to determine what and how much discount a movie is eligible for.
 * The ticket discounts are based on special movie codes, the showing sequence of the day, and the time of the movie.
 * If a movie is eligible for multiple discounts, only the biggest will be chosen at the very end.
 *
 * @author coder050519123
 */
public class MovieDiscountCalculator {
    /**
     * Static integer constant representing a special movie.
     */
    private final static int SPECIAL_MOVIE_CODE = 1;
    /**
     * Percentage amount of discount applicable to special movies.
     */
    private final static BigDecimal SPECIAL_MOVIE_DISCOUNT_DECIMAL = new BigDecimal("0.2");
    /**
     * Dollar amount of discount applicable to movies shown first of the day.
     */
    private final static BigDecimal FIRST_SHOW_DISCOUNT_DOLLAR_AMT = new BigDecimal("3");
    /**
     * Dollar amount of discount applicable to movies shown second of the day.
     */
    private final static BigDecimal SECOND_SHOW_DISCOUNT_DOLLAR_AMT = new BigDecimal("2");
    /**
     * Dollar amount of discount applicable to movies shown seventh of the day.
     */
    private final static BigDecimal SEVENTH_SHOW_DISCOUNT_DOLLAR_AMT = new BigDecimal("1");
    /**
     * Percentage discount applicable to movies shown in the mid to early half of the day.
     */
    private final static BigDecimal EARLY_SHOWING_DISCOUNT_DECIMAL = new BigDecimal("0.25");
    /**
     * Start time to mark the start of the mid-showing discount segment of the day (applicable for 25% off)
     */
    private final static LocalTime EARLY_SHOWING_LOWER_BOUND_HOUR = LocalTime.of(11, 0);
    /**
     * End time to mark the end of the mid-showing discount segment of the day (applicable for 25% off)
     */
    private final static LocalTime EARLY_SHOWING_UPPER_BOUND_HOUR = LocalTime.of(16, 0);

    /**
     * Constructor for the MovieDiscountCalculator class.
     */
    public MovieDiscountCalculator() {
    }

    /**
     * This method calculates the biggest discount a movie is eligible for dependent on its showing entity.
     *
     * @param showing - the showing object that contains the movie it is showing at that specific time
     * @return the biggest discount the movie is eligible for
     * @throws IllegalArgumentException if the showing or movie object within the showing is null or has missing important values
     */
    public BigDecimal calculateTicketPriceDiscount(Showing showing) {
        validateShowingObject(showing);
        Movie movie = showing.getMovie();
        validateMovieObject(movie);

        BigDecimal finalDiscount = BigDecimal.ZERO;

        if (movie.getSpecialCode() == SPECIAL_MOVIE_CODE) {
            BigDecimal specialDiscount = movie.getTicketPrice().multiply(SPECIAL_MOVIE_DISCOUNT_DECIMAL);
            finalDiscount = specialDiscount.max(finalDiscount);  // 20% discount for special movie
        }

        int showingSequenceOfTheDay = showing.getSequenceOfTheDay();
        if (showingSequenceOfTheDay == 1) {
            finalDiscount = FIRST_SHOW_DISCOUNT_DOLLAR_AMT.max(finalDiscount); // $3 discount for 1st show of the day
        } else if (showingSequenceOfTheDay == 2) {
            finalDiscount = SECOND_SHOW_DISCOUNT_DOLLAR_AMT.max(finalDiscount); // $2 discount for 2nd show of the day
        } else if (showingSequenceOfTheDay == 7) {
            finalDiscount = SEVENTH_SHOW_DISCOUNT_DOLLAR_AMT.max(finalDiscount); // $1 discount for 7th show of the day
        }

        LocalDateTime movieStartTime = showing.getShowStartTime();
        LocalDateTime lowerBoundStartTime = LocalDateTime.of(LocalDate.now(), EARLY_SHOWING_LOWER_BOUND_HOUR);
        LocalDateTime upperBoundStartTime = LocalDateTime.of(LocalDate.now(), EARLY_SHOWING_UPPER_BOUND_HOUR);
        if (movieStartTime.isAfter(lowerBoundStartTime) && movieStartTime.isBefore(upperBoundStartTime)) {
            BigDecimal earlyShowingDiscount = movie.getTicketPrice().multiply(EARLY_SHOWING_DISCOUNT_DECIMAL); // 25% discount for movie shown in btwn 11am-4pm
            finalDiscount = earlyShowingDiscount.max(finalDiscount);
        }

        // biggest discount wins
        return finalDiscount;
    }

    /**
     * This method validates the Movie object and its values.
     *
     * @param movie - the Movie object to validate
     * @throws IllegalArgumentException if the movie object or its ticket price are invalid or null
     */
    private void validateMovieObject(Movie movie) {
        if (movie == null || movie.getTicketPrice() == null) {
            throw new IllegalArgumentException("Movie cannot be null or movie's ticket price cannot be null!");
        }
    }

    /**
     * This method validates the Showing object and its values.
     *
     * @param showing - the Showing object to validate
     * @throws IllegalArgumentException if the showing object or its sequence of the day value is invalid or null
     */
    private void validateShowingObject(Showing showing) {
        if (showing == null || showing.getSequenceOfTheDay() == 0) {
            throw new IllegalArgumentException("Showing object cannot be null or showing schedule cannot be 0!");
        }
    }
}
