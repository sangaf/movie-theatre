package com.jpmc.theater.services;

import com.jpmc.theater.domain.Movie;

import java.util.List;

public interface ScheduleService {

    void printSchedule();
    void buildSchedule(List<Movie> movies);
}
