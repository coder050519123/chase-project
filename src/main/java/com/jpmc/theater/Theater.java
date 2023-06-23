package com.jpmc.theater;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class represents the theater containing a schedule of movie showings in the day.
 * The theater object holds a list of showings containing the respective movies.
 *
 * @author natashamartinas
 */
public class Theater {
    private List<Showing> schedule;

    /**
     * Constructs a new Theater object with the provided schedule list of showings
     * @param schedule - List of movie showings for the day
     */
    public Theater(List<Showing> schedule) {
        this.schedule = schedule;
    }

    /**
     * @return the schedule list of showings for the theater for the day
     */
    public List<Showing> getSchedule() {
        return schedule;
    }

    /**
     * @param schedule - method to change the schedule list of the theater for the day
     */
    public void setSchedule(List<Showing> schedule) {
        this.schedule = schedule;
    }

    /**
     * Method to create a reservation in the theater based on the day's schedule
     * @param customer - customer initiating reservation request
     * @param sequence - the selected showing sequence
     * @param ticketAmount - the requested party size of the reservation
     * @return the reservation object if all the input parameters are valid
     */
    public Reservation createReservation(Customer customer, int sequence, int ticketAmount) {
        if (ticketAmount <= 0) {
            throw new IllegalArgumentException("Ticket amount cannot be less than 1!");
        }
        Showing showing;
        try {
            showing = schedule.get(sequence - 1);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalStateException("Not able to find any showing for given sequence " + sequence + " and exception is: " + ex.getMessage());
        }
        return new Reservation(customer, showing, ticketAmount);
    }

    /**
     * Method to print the theater schedule in a pretty format
     */
    public void printSchedule() {
        if (schedule.isEmpty()) {
            System.out.println("No shows scheduled.");
            return;
        }
        System.out.println(LocalDate.now());
        System.out.println("===================================================");
        schedule.forEach(s ->
                System.out.println(s.getSequenceOfTheDay() + ": " + s.getShowStartTime() + " " + s.getMovie().getTitle() + " " + humanReadableFormat(s.getMovie().getRunningTime()) + " $" + s.getMovie().getTicketPrice())
        );
        System.out.println("===================================================");
    }

    /**
     * Method to print the theater schedule in a JSON format
     */
    public void printScheduleToJson() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, List<Showing>> scheduleMap = new HashMap<>();
        scheduleMap.put(LocalDate.now().toString(), schedule);
        try {
            String scheduleJsonWithDate = mapper.writeValueAsString(scheduleMap);
            System.out.println(scheduleJsonWithDate);
        } catch (JsonProcessingException ex) {
            System.out.println("Could not convert schedule list with date to JSON: " + ex.getMessage());
        }
    }

    /**
     * Method to handle the movie's duration to be printed in a readable format
     */
    public String humanReadableFormat(Duration duration) {
        long hour = duration.toHours();
        long remainingMin = duration.toMinutes() - TimeUnit.HOURS.toMinutes(duration.toHours());

        return String.format("(%s hour%s %s minute%s)", hour, handlePlural(hour), remainingMin, handlePlural(remainingMin));
    }

    /**
     * Method to add plurality to the duration of the movie
     */
    private String handlePlural(long value) {
        if (value == 1) {
            return "";
        }
        else {
            return "s";
        }
    }

    /**
     * This is the main method that creates a Theater object and prints the schedule of the showings of the day.
     * The method creates instances of each Movie to be shown (all fields would be immutable once created).
     * Then each Movie created will be added to a specific Showing for the day (fields are also immutable).
     * Each Showing will be added to a list called the Schedule which will be used to instantiate the Theater object.
     * The printSchedule() method is called to display the schedule of the theater for the day.
     */
    public static void main(String[] args) {
        Movie spiderMan = new Movie("Spider-Man: No Way Home", "Spider-Man movie description.", Duration.ofMinutes(90), BigDecimal.valueOf(12.5), 1);
        Movie turningRed = new Movie("Turning Red", "This is a Disney movie.", Duration.ofMinutes(85), BigDecimal.valueOf(11), 0);
        Movie theBatMan = new Movie("The Batman", "This is a DC Comics movie", Duration.ofMinutes(95), BigDecimal.valueOf(9), 0);

        Theater theater = new Theater(List.of(
                new Showing(turningRed, 1, LocalDateTime.of(LocalDate.now(), LocalTime.of(9, 0))),
                new Showing(spiderMan, 2, LocalDateTime.of(LocalDate.now(), LocalTime.of(11, 0))),
                new Showing(theBatMan, 3, LocalDateTime.of(LocalDate.now(), LocalTime.of(12, 50))),
                new Showing(turningRed, 4, LocalDateTime.of(LocalDate.now(), LocalTime.of(14, 30))),
                new Showing(spiderMan, 5, LocalDateTime.of(LocalDate.now(), LocalTime.of(16, 10))),
                new Showing(theBatMan, 6, LocalDateTime.of(LocalDate.now(), LocalTime.of(17, 50))),
                new Showing(turningRed, 7, LocalDateTime.of(LocalDate.now(), LocalTime.of(19, 30))),
                new Showing(spiderMan, 8, LocalDateTime.of(LocalDate.now(), LocalTime.of(21, 10))),
                new Showing(theBatMan, 9, LocalDateTime.of(LocalDate.now(), LocalTime.of(23, 0)))
        ));

        theater.printSchedule();
    }
}
