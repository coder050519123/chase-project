package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Customer class.
 */
public class CustomerTests {
    /**
     * This is a test to check the equals and hashCode method that was overwritten in the Customer class.
     * Test data includes:
     * - Two Customer objects pointed to two different references and then filled with similar values for its members.
     * Expected result:
     * - The equals method should return true when comparing the two Customer, and both movies should have the same hashcode.
     */
    @Test
    public void testCustomer_Equals_HashCode() {
        Customer customer1 = new Customer("John Doe", "customer-id");
        Customer customer2 = new Customer("John Doe", "customer-id");
        Assertions.assertTrue(customer1.equals(customer2) && customer2.equals(customer1));
        Assertions.assertEquals(customer1.hashCode(), customer2.hashCode());
    }

    /**
     * This is a test to check the toString method of the Customer class.
     * Test data includes:
     * - Customer object that is newly constructed with test values
     * Expected result:
     * - When you print the customer's values with toString it should print the id and name
     */
    @Test
    public void testCustomer_ToString() {
        Customer customer = new Customer("Sample Customer", "customer-id");
        String expectedString = "Customer {id=customer-id, name='Sample Customer'}";
        Assertions.assertEquals(expectedString, customer.toString());
    }
}
