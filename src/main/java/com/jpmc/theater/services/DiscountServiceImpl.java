package com.jpmc.theater.services;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Showing;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;

import static com.jpmc.theater.util.MovieType.SPECIAL;

public class DiscountServiceImpl implements DiscountService{

    @Override
    public double calculateDiscountAmount(Showing showing){
        double specialMovieDiscount = getDiscountForSpecialMove(showing.getMovie());
        double showTimeDiscount = getDiscountByShowtime(showing);
        double startingTimeDiscount = getDiscountByShowStartingTime(showing);

        return Collections.max(Arrays.asList(specialMovieDiscount, showTimeDiscount, startingTimeDiscount));
    }

    protected double getDiscountForSpecialMove(Movie movie){
        return (movie.getType() == SPECIAL) ?
                (movie.getTicketPrice()*(20.0/100.0)) : 0;
    }

    protected double getDiscountByShowtime(Showing showing){
        double discountByShowtime = 0.0;
        switch (showing.getSequenceOfTheDay()) {
            case 1:
                discountByShowtime = 3.0;
                break;
            case 2:
                discountByShowtime = 2.0;
                break;
            case 7:
                 discountByShowtime = 1.0;
                break;
            }
        return discountByShowtime;
    }

    protected double getDiscountByShowStartingTime(Showing showing){
        LocalDateTime startingTime = showing.getShowStartTime();
        double moviePrice = showing.getMovie().getTicketPrice();
        LocalTime lowerTime = LocalTime.of(11, 0);
        LocalTime higherTime = LocalTime.of(16, 0);

        return (startingTime.toLocalTime().compareTo(lowerTime) >=0  && startingTime.toLocalTime().compareTo(higherTime) <= 0) ?
                (moviePrice*(25.0/100.0)) : 0.0;
    }

}
