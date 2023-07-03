package com.jpmc.theater;

import java.util.Objects;

/**
 * This class represents a customer object for the theater.
 * It holds the customer's name and id used for record keeping.
 * @author coder050519123
 */
public class Customer {
    private String name;
    private String id;

    /**
     * Constructs a new Customer object with name and id.
     * @param name - customer name
     * @param id - customer id
     */
    public Customer(String name, String id) {
        this.id = id;
        this.name = name;
    }

    /**
     * Method to compare two Customer objects and see if they are equal in all of their components/members.
     * Customers are equal if their name and id are the same.
     * @param o customer object to compare to
     * @return boolean true if customer is equal, false if customer is not equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(name, customer.name) && Objects.equals(id, customer.id);
    }

    /**
     * Equal customers will have the same hash code
     * @return hash code for customer object
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    /**
     * @return String representation of the customer displaying their id and name.
     */
    @Override
    public String toString() {
        return "Customer {" +
                "id=" + id +
                ", name='" + name +
                '\'' +
                '}';
    }
}
