package com.jpmc.theater.services;

import com.jpmc.theater.domain.Customer;
import com.jpmc.theater.domain.Reservation;
import com.jpmc.theater.domain.Showing;

public interface ReservationService {

    double calculateReservationFee(Showing showing, int audienceCount);
    Reservation settleReservation(Customer customer, Showing showing, int audienceCount);
}
