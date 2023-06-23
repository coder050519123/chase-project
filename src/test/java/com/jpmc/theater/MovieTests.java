package com.jpmc.theater;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

/**
 * Unit tests for the Movie class.
 */
public class MovieTests {
    /**
     * This is a test to check the constructor of the Movie object and to test its getter methods.
     * Test data includes:
     * - Movie object that is newly constructed with test values
     * Expected result:
     * - Each getter method tested should return the same value as what we constructed the object with
     */
    @Test
    public void testMovie_Construction() {
        Movie sampleMovie = new Movie("Sample Movie", "Sample movie description.", Duration.ofMinutes(85), BigDecimal.valueOf(25), 3);
        Assertions.assertEquals("Sample Movie", sampleMovie.getTitle());
        Assertions.assertEquals("Sample movie description.", sampleMovie.getDescription());
        Assertions.assertEquals(Duration.ofMinutes(85), sampleMovie.getRunningTime());
        Assertions.assertEquals(0, sampleMovie.getTicketPrice().compareTo(BigDecimal.valueOf(25)));
        Assertions.assertEquals(3, sampleMovie.getSpecialCode());
    }

    /**
     * This is a test to check the equals and hashCode method that was overwritten in the Movie class.
     * Test data includes:
     * - Two movie objects pointed to two different references and then filled with similar values for its members.
     * Expected result:
     * - The equals method should return true when comparing the two movies, and both movies should have the same hashcode.
     */
    @Test
    public void testMovie_Equals_HashCode() {
        Movie firstMovie = new Movie("Sample Movie", "Sample movie description.", Duration.ofMinutes(85), BigDecimal.valueOf(25), 3);
        Movie secondMovie = new Movie("Sample Movie", "Sample movie description.", Duration.ofMinutes(85), BigDecimal.valueOf(25), 3);
        Assertions.assertTrue(firstMovie.equals(secondMovie) && secondMovie.equals(firstMovie));
        Assertions.assertEquals(firstMovie.hashCode(), secondMovie.hashCode());
    }
}
