package com.jpmc.theater.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Showing;
import com.jpmc.theater.exceptions.TooManyMoviesToScheduleException;
import com.jpmc.theater.util.DurationSerializer;
import com.jpmc.theater.util.LocalDateTimeSerializer;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ScheduleServiceImpl implements ScheduleService{

    final Set<Showing> movieScheduling;

    public ScheduleServiceImpl() {
        this.movieScheduling = new TreeSet<>(new SequenceComparator());
    }

    @Override
    public void printSchedule(){
        if(movieScheduling.isEmpty()){
            System.out.println("No schedule to print");
        } else {
            Gson gson = getGsonForPrettyPrint();
            System.out.println(gson.toJson(movieScheduling));
        }
    }

    @Override
    public void buildSchedule(List<Movie> movies) throws TooManyMoviesToScheduleException{

        AtomicInteger index = new AtomicInteger(1);
        LocalDate localDate = LocalDate.now();
        List<LocalDateTime> movieTimes = getStartingTimeList(localDate, movies.size());

        List<Showing> showingList = movies.stream()
                                            .map(m -> new Showing(m, index.getAndIncrement(), movieTimes.get(movies.indexOf(m))))
                                            .toList();
        movieScheduling.addAll(showingList);
    }

    private List<LocalDateTime> getStartingTimeList(LocalDate time, int num) throws TooManyMoviesToScheduleException{

        if(num > 10) throw new TooManyMoviesToScheduleException("To many movies to schedule");

        return IntStream.range(0, num)
                    .mapToObj(i -> LocalDateTime.of(time, LocalTime.of((i + 10), 0))).collect(Collectors.toList());
    }


    private Gson getGsonForPrettyPrint(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Duration.class, new DurationSerializer());
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer());

        return gsonBuilder.setPrettyPrinting().create();
    }

    class SequenceComparator implements Comparator<Showing>{

        @Override
        public int compare(Showing showing1, Showing showing2) {
            return showing1.compareTo(showing2);
        }
    }



}
