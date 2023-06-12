package com.jpmc.theater.services;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Showing;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.jpmc.theater.util.MovieType.SPECIAL;

class DiscountServiceImplTest {

    @Test
    public void testSpecialMovieDiscount(){
        //given
        DiscountServiceImpl discountService = new DiscountServiceImpl();
        Movie movie = Movie.builder()
                            .title("Batman").type(SPECIAL).ticketPrice(21.00).build();
        //expected 20% discount
        double expectedTicketPrice = 21.00 * (20.0/100.0);
        //when
        double actualTicketPrice = discountService.getDiscountForSpecialMove(movie);
        //then
        Assertions.assertEquals(expectedTicketPrice, actualTicketPrice);
    }

    @Test
    public void testDiscountByShowtime(){
        //given
        Movie batman = Movie.builder().title("Batman").ticketPrice(21.00).build();
        Showing showingBatman = Showing.builder().movie(batman).sequenceOfTheDay(1).build();
        Movie spiderMan = Movie.builder().title("SpiderMan").ticketPrice(25.00).build();
        Showing showingSpiderMan = Showing.builder().movie(spiderMan).sequenceOfTheDay(2).build();
        Movie ironMan = Movie.builder().title("IronMan").ticketPrice(18.00).build();
        Showing showingIronMan = Showing.builder().movie(ironMan).sequenceOfTheDay(7).build();
        Movie deadpool = Movie.builder().title("Deadpool").ticketPrice(22.00).build();
        Showing showingDeadpool = Showing.builder().movie(deadpool).sequenceOfTheDay(5).build();
        DiscountServiceImpl discountService = new DiscountServiceImpl();

        //assert $3 discount for 1st show
        Assertions.assertEquals(3.0, discountService.getDiscountByShowtime(showingBatman));
        //assert $2 discount for 2nd show
        Assertions.assertEquals(2.0, discountService.getDiscountByShowtime(showingSpiderMan));
        //assert $1 discount for 7th show
        Assertions.assertEquals(1.0, discountService.getDiscountByShowtime(showingIronMan));
        //assert no discount for any show other than 1st, 2nd or 7th
        Assertions.assertEquals(0.0, discountService.getDiscountByShowtime(showingDeadpool));

    }

    @Test
    public void testDiscountByShowStartingTime(){
        Movie batman = Movie.builder().title("Batman").ticketPrice(21.00).build();
        Showing showingBatman = Showing.builder().movie(batman).sequenceOfTheDay(1).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(11, 0))).build();
        Movie spiderMan = Movie.builder().title("SpiderMan").ticketPrice(25.00).build();
        Showing showingSpiderMan = Showing.builder().movie(spiderMan).sequenceOfTheDay(2).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(10, 30))).build();
        Movie ironMan = Movie.builder().title("IronMan").ticketPrice(18.00).build();
        Showing showingIronMan = Showing.builder().movie(ironMan).sequenceOfTheDay(7).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(16, 0))).build();
        Movie deadpool = Movie.builder().title("Deadpool").ticketPrice(22.00).build();
        Showing showingDeadpool = Showing.builder().movie(deadpool).sequenceOfTheDay(5).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(16, 30))).build();
        DiscountServiceImpl discountService = new DiscountServiceImpl();

        //asserting 25% discount for movie starting at 11:00am
        Assertions.assertEquals(21.0 * (25.0/100.0), discountService.getDiscountByShowStartingTime(showingBatman));
        //asserting discount for movie starting before 11:00am
        Assertions.assertEquals(0.0, discountService.getDiscountByShowStartingTime(showingSpiderMan));
        //asserting discount for movie starting at 4:00pm
        Assertions.assertEquals(18.00 * (25.0/100.0), discountService.getDiscountByShowStartingTime(showingIronMan));
        //asserting discount for movie starting after 4:00pm
        Assertions.assertEquals(0.0, discountService.getDiscountByShowStartingTime(showingDeadpool));

    }


    @Test
    public void testCalculateDiscountAmount(){
        Movie batman = Movie.builder().title("Batman").ticketPrice(21.00).type(SPECIAL).build();
        Showing showingBatman = Showing.builder().movie(batman).sequenceOfTheDay(1).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(11,0))).build();
        Movie spiderMan = Movie.builder().title("SpiderMan").ticketPrice(25.00).build();
        Showing showingSpiderMan = Showing.builder().movie(spiderMan).sequenceOfTheDay(2).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(10, 30))).build();
        Movie ironMan = Movie.builder().title("IronMan").ticketPrice(18.00).build();
        Showing showingIronMan = Showing.builder().movie(ironMan).sequenceOfTheDay(7).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(17,0))).build();
        Movie deadpool = Movie.builder().title("Deadpool").ticketPrice(22.00).type(SPECIAL).build();
        Showing showingDeadpool = Showing.builder().movie(deadpool).sequenceOfTheDay(5).showStartTime(LocalDateTime.of(LocalDate.now(),LocalTime.of(19, 30))).build();
        DiscountServiceImpl discountService = new DiscountServiceImpl();

        //discount amount is 25% which is
        // maximum between special movie, showing at 1st and show start time between 11:00am~4pm:00pm
        Assertions.assertEquals(21.0 * (25.0/100.0), discountService.calculateDiscountAmount(showingBatman));

        //discount amount applied only for sequence of the day
        Assertions.assertEquals(2.0, discountService.calculateDiscountAmount(showingSpiderMan));
        Assertions.assertEquals(1.0, discountService.calculateDiscountAmount(showingIronMan));

        //discount amount applied only for special movie 20%
        Assertions.assertEquals(22.0 * (20.0/100.0), discountService.calculateDiscountAmount(showingDeadpool));
    }















}