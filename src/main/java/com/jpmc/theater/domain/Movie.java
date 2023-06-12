package com.jpmc.theater.domain;

import com.jpmc.theater.util.MovieType;
import lombok.*;

import java.time.Duration;

@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
@Builder
public class Movie implements Comparable<Movie>{
    private String title;
    private String description;
    private Duration runningTime;
    private double ticketPrice;
    private MovieType type;

    @Override
    public String toString() {
        return "Movie[" +
                "title='" + title + '\'' +
                ", runningTime=" + runningTime +
                ", ticketPrice=" + ticketPrice +
                ", type=" + type +
                ']';
    }

    @Override
    public int compareTo(Movie o) {
        return this.getTitle().compareTo(o.getTitle());
    }
}
