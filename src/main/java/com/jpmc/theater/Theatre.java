package com.jpmc.theater;

import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.services.ScheduleService;
import com.jpmc.theater.services.ScheduleServiceImpl;

import java.time.Duration;
import java.util.List;

import static com.jpmc.theater.util.MovieType.SPECIAL;
import static com.jpmc.theater.util.MovieType.USUAL;

public class Theatre {

    ScheduleService scheduleService;

    public Theatre(ScheduleServiceImpl scheduleService) {
        this.scheduleService = scheduleService;
    }

    public ScheduleService getScheduleService() {
        return scheduleService;
    }

    public static void main(String[] args) {
        Theatre theatre = new Theatre(new ScheduleServiceImpl());
        theatre.getScheduleService().buildSchedule(getMovieList());

        theatre.getScheduleService().printSchedule();
    }


    private static List<Movie> getMovieList() {
        var batman = Movie.builder().title("Batman")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Batman movie")
                .runningTime(Duration.ofMinutes(109)).build();
        var spiderMan = Movie.builder().title("SpiderMan")
                .ticketPrice(23.00).type(SPECIAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(95)).build();
        var ironMan = Movie.builder().title("IronMan")
                .ticketPrice(18.00).type(SPECIAL)
                .description("IronMan movie")
                .runningTime(Duration.ofMinutes(98)).build();
        var deadpool = Movie.builder().title("Deadpool")
                .ticketPrice(22.00).type(SPECIAL)
                .description("Deadpool movie")
                .runningTime(Duration.ofMinutes(121)).build();
        var darkKnight = Movie.builder().title("Dark Knight")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Batman movie")
                .runningTime(Duration.ofMinutes(109)).build();
        var nowayHome = Movie.builder().title("SpiderMan: No Way Home")
                .ticketPrice(15.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(120)).build();
        var superMan = Movie.builder().title("Super Man")
                .ticketPrice(21.00).type(USUAL)
                .description("Superman movie")
                .runningTime(Duration.ofMinutes(139)).build();
        var shrek = Movie.builder().title("Shrek")
                .ticketPrice(18.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(100)).build();
        var topCat = Movie.builder().title("Top Cat")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Top Cat movie")
                .runningTime(Duration.ofMinutes(109)).build();
        var spiderVerse = Movie.builder().title("SpiderMan: Spider Verse")
                .ticketPrice(15.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(120)).build();
        return List.of(batman, spiderMan, ironMan, deadpool, darkKnight, nowayHome, superMan, shrek, topCat, spiderVerse);
    }
    
}
