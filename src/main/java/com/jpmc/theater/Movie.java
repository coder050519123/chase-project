package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Objects;

/**
 * This is a class representing the Movie entity in a theater.
 * This class holds the information about a movie, including its title, description,
 * running time, ticket price, and special code.
 * @author natashamartinas
 */
public class Movie {
    private final String title;
    private final String description;
    private final Duration runningTime;
    private final BigDecimal ticketPrice;
    private final int specialCode;

    /**
     * Constructs a new Movie object with the provided title, desc, running time, ticket price, and special code values.
     * @param title - the title of the movie
     * @param description - the description of the movie
     * @param runningTime - the duration of the movie
     * @param ticketPrice - the original cost of the movie
     * @param specialCode - the special code for the movie
     */
    public Movie(String title, String description, Duration runningTime, BigDecimal ticketPrice, int specialCode) {
        this.title = title;
        this.description = description;
        this.runningTime = runningTime;
        this.ticketPrice = ticketPrice;
        this.specialCode = specialCode;
    }

    /**
     * @return title of the movie i.e. "The Wizard of Oz"
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return description of the movie i.e. "A story about a girl from Kansas."
     */
    public String getDescription() {
        return description;
    }
    /**
     * @return total duration of the movie
     */
    public Duration getRunningTime() {
        return runningTime;
    }

    /**
     * @return original ticket price (before discount) of the movie i.e. $20.5
     */
    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    /**
     * @return special code of the movie to indicate whether movie is regular or special (special code = 1)
     */
    public int getSpecialCode() {
        return specialCode;
    }

    /**
     * Method to compare two movies and see if they are equal in all of their components/members.
     * Movies are equal if their title, description, ticketPrice, runningTime, and specialCode values are all the same.
     *
     * @param o movie object to compare to
     * @return boolean true if movie is equal, false if movie is not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Movie movie = (Movie) o;
        return ticketPrice.compareTo(movie.getTicketPrice()) == 0
                && Objects.equals(title, movie.title)
                && Objects.equals(description, movie.description)
                && Objects.equals(runningTime, movie.runningTime)
                && Objects.equals(specialCode, movie.specialCode);
    }

    /**
     * Equal movies will have the same hash code
     * @return hash code for movie object
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, description, runningTime, ticketPrice, specialCode);
    }
}