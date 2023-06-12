package com.jpmc.theater.domain;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@EqualsAndHashCode
public class Reservation {

    private Customer customer;
    private int ticketCount;
    private Showing showing;
    private double totalFee;
}
