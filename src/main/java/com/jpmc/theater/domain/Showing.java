package com.jpmc.theater.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@AllArgsConstructor
@Data
@Builder
public class Showing implements Comparable<Showing>{

    private Movie movie;
    private Integer sequenceOfTheDay;
    private LocalDateTime showStartTime;

    @Override
    public int compareTo(Showing o) {
        return this.getSequenceOfTheDay().compareTo(o.getSequenceOfTheDay());
    }
}
