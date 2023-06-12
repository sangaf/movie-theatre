package com.jpmc.theater.services;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.exceptions.TooManyMoviesToScheduleException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.jpmc.theater.util.MovieType.SPECIAL;
import static com.jpmc.theater.util.MovieType.USUAL;

class ScheduleServiceImplTest {

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    public void testPrintSchedule(){
        //given
        ScheduleServiceImpl service = new ScheduleServiceImpl();
        List<Movie> movies = getMovieList();
        service.buildSchedule(movies);

        //when
        service.printSchedule();

        //asserting json printout
        String json = outputStreamCaptor.toString().trim();
        JsonArray movieSchedule = new Gson().fromJson(json, JsonArray.class);
        Assertions.assertEquals(4, movieSchedule.size());
        Assertions.assertTrue(movieSchedule.get(0).isJsonObject());

        JsonObject movieObject = new Gson().fromJson(movieSchedule.get(0), JsonObject.class);
        Assertions.assertNotNull(movieObject.get("movie"));
        Assertions.assertNotNull(movieObject.get("sequenceOfTheDay"));
        Assertions.assertNotNull(movieObject.get("showStartTime"));
    }

    @Test
    public void testTooManyMoviesListException(){
        ScheduleServiceImpl service = new ScheduleServiceImpl();
        Exception exception = Assertions.assertThrows(TooManyMoviesToScheduleException.class, () -> service.buildSchedule(getMoreThanTenMovie()));

        Assertions.assertEquals("To many movies to schedule", exception.getMessage());
    }

    private List<Movie> getMovieList() {
        Movie batman = Movie.builder().title("Batman")
                                        .ticketPrice(21.00).type(SPECIAL)
                                        .description("Batman movie")
                                        .runningTime(Duration.ofMinutes(109)).build();
        Movie spiderMan = Movie.builder().title("SpiderMan")
                .ticketPrice(23.00).type(SPECIAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(95)).build();
        Movie ironMan = Movie.builder().title("IronMan")
                .ticketPrice(18.00).type(SPECIAL)
                .description("IronMan movie")
                .runningTime(Duration.ofMinutes(98)).build();
        Movie deadpool = Movie.builder().title("Deadpool")
                .ticketPrice(22.00).type(SPECIAL)
                .description("Deadpool movie")
                .runningTime(Duration.ofMinutes(121)).build();
        return List.of(batman, spiderMan, ironMan, deadpool);
    }

    private List<Movie> getMoreThanTenMovie(){
        List<Movie> movieList = new ArrayList<>(List.copyOf(getMovieList()));
        Movie darkKnight = Movie.builder().title("Dark Knight")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Batman movie")
                .runningTime(Duration.ofMinutes(109)).build();
        Movie nowayHome = Movie.builder().title("SpiderMan: No Way Home")
                .ticketPrice(15.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(120)).build();
        Movie superMan = Movie.builder().title("Super Man")
                .ticketPrice(21.00).type(USUAL)
                .description("Superman movie")
                .runningTime(Duration.ofMinutes(139)).build();
        Movie shrek = Movie.builder().title("Shrek")
                .ticketPrice(18.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(100)).build();
        Movie topCat = Movie.builder().title("Top Cat")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Top Cat movie")
                .runningTime(Duration.ofMinutes(109)).build();
        Movie spiderVerse = Movie.builder().title("SpiderMan: Spider Verse")
                .ticketPrice(15.00).type(USUAL)
                .description("SpiderMan movie")
                .runningTime(Duration.ofMinutes(120)).build();
        Movie inferno = Movie.builder().title("Inferno")
                .ticketPrice(15.00).type(USUAL)
                .description("Inferno movie")
                .runningTime(Duration.ofMinutes(120)).build();
        movieList.addAll(List.of(darkKnight, nowayHome, superMan, shrek, topCat, spiderVerse, inferno));

        return movieList;
    }

    private String schedulePrint(){
        String json = """
                [
                  {
                    "movie": {
                      "title": "Batman",
                      "description": "Batman movie",
                      "runningTime": "01 hour(s) 49 minute(s) 00 second(s)",
                      "ticketPrice": 21.0,
                      "type": "SPECIAL"
                    },
                    "sequenceOfTheDay": 1,
                    "showStartTime": "2023-06-09T10:00"
                  },
                  {
                    "movie": {
                      "title": "SpiderMan",
                      "description": "SpiderMan movie",
                      "runningTime": "01 hour(s) 35 minute(s) 00 second(s)",
                      "ticketPrice": 23.0,
                      "type": "SPECIAL"
                    },
                    "sequenceOfTheDay": 2,
                    "showStartTime": "2023-06-09T11:00"
                  },
                  {
                    "movie": {
                      "title": "IronMan",
                      "description": "IronMan movie",
                      "runningTime": "01 hour(s) 38 minute(s) 00 second(s)",
                      "ticketPrice": 18.0,
                      "type": "SPECIAL"
                    },
                    "sequenceOfTheDay": 3,
                    "showStartTime": "2023-06-09T12:00"
                  },
                  {
                    "movie": {
                      "title": "Deadpool",
                      "description": "Deadpool movie",
                      "runningTime": "02 hour(s) 01 minute(s) 00 second(s)",
                      "ticketPrice": 22.0,
                      "type": "SPECIAL"
                    },
                    "sequenceOfTheDay": 4,
                    "showStartTime": "2023-06-09T13:00"
                  }
                ]""";
        return json;
    }

}