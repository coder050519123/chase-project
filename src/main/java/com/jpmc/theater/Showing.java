package com.jpmc.theater;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * This class represents the showing of a movie in a theater.
 * It holds the information about a specific movie being shown, including the movie itself,
 * the sequence of the showing within a day, and the start time of the showing.
 *
 * @author coder050519123
 */
public class Showing {
    private final Movie movie;
    private final int sequenceOfTheDay;
    private final LocalDateTime showStartTime;
    private final MovieDiscountCalculator movieDiscountCalculator;

    /**
     * Constructs a new Showing object with the provided movie, sequence of the day, and show start time.
     * This constructor also creates an instance of the MovieDiscountCalculator
     * @param movie - the movie the showing is about
     * @param sequenceOfTheDay - the showing's sequence of the day (when the movie is shown in the day)
     * @param showStartTime - the start time of the showing
     */
    public Showing(Movie movie, int sequenceOfTheDay, LocalDateTime showStartTime) {
        this.movie = movie;
        this.sequenceOfTheDay = sequenceOfTheDay;
        this.showStartTime = showStartTime;
        this.movieDiscountCalculator = new MovieDiscountCalculator();
    }

    /**
     * @return the movie object tied to the showing
     */
    public Movie getMovie() {
        return movie;
    }

    /**
     * @return the showing's start time
     */
    public LocalDateTime getShowStartTime() {
        return showStartTime;
    }

    /**
     * @return the showing's sequence of the day
     */
    public int getSequenceOfTheDay() {
        return sequenceOfTheDay;
    }

    /**
     * This method calculates the final ticket price of the movie dependent on the showing information of the movie.
     * This method invokes the MovieDiscountCalculator object to calculate the discount applicable for the showing & its movie.
     * @return the final movie ticket price (original price - the discount applicable due to the showing information)
     */
    public BigDecimal getFinalShowingPrice() {
        BigDecimal ticketDiscount = movieDiscountCalculator.calculateTicketPriceDiscount(this);
        return this.movie.getTicketPrice().subtract(ticketDiscount).setScale(2, RoundingMode.HALF_UP);
    }
}
