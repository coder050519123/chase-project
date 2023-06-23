package com.jpmc.theater;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class represents the reservation to see a showing in the theater.
 * It holds information on the customer, the showing details,
 * and the number of audience in this specific reservation party.
 * This class also calculates the total reservation fee for the showing chosen.
 * @author natashamartinas
 */
public class Reservation {
    private final Customer customer;
    private Showing showing;
    private int audienceCount;

    /**
     * Constructs a new Reservation with the provided customer, showing, and audience count details.
     * @param customer - the customer details on who is making the reservation
     * @param showing - the showing chosen by the customer to watch (can be changed)
     * @param audienceCount - the total party size of this reservation (can be changed) - cannot be negative or 0
     */
    public Reservation(Customer customer, Showing showing, int audienceCount) {
        setAudienceCount(audienceCount);
        this.customer = customer;
        this.showing = showing;
    }

    /**
     * @return the customer details who made the reservation
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * @return the showing details for the reservation
     */
    public Showing getShowing() {
        return showing;
    }

    /**
     * @param showing - setter to change the showing of reservation
     */
    public void setShowing(Showing showing) {
        this.showing = showing;
    }

    /**
     * @return the size of the party of the reservation
     */
    public int getAudienceCount() {
        return audienceCount;
    }

    /**
     * @param audienceCount - setter to change the party size of reservation
     */
    public void setAudienceCount(int audienceCount) {
        if (audienceCount <= 0) {
            throw new IllegalArgumentException("Cannot have a reservation with negative or zero audience count!");
        }
        this.audienceCount = audienceCount;
    }

    /**
     * This method calculates the total reservation cost for the showing dependent on the ticket prices and the size of the party.
     * This method calls the showing class for the final showing cost after the discount, then multiplies with number of audience requested.
     * @return the final cost of the reservation
     */
    public BigDecimal calculateTotalReservationFee() {
        BigDecimal totalMovieFee = showing.getFinalShowingPrice().multiply(BigDecimal.valueOf(audienceCount));
        return totalMovieFee.setScale(2, RoundingMode.HALF_UP);
    }
}