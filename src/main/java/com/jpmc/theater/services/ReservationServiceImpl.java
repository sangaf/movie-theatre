package com.jpmc.theater.services;

import com.jpmc.theater.domain.Customer;
import com.jpmc.theater.domain.Reservation;
import com.jpmc.theater.domain.Showing;

public class ReservationServiceImpl implements ReservationService{

    DiscountService discountService;

    public ReservationServiceImpl(DiscountServiceImpl discountService) {
        this.discountService = discountService;
    }

    @Override
    public double calculateReservationFee(Showing showing, int audienceCount){
        double availableDiscount = discountService.calculateDiscountAmount(showing);
        double ticketPriceApplyingDiscount = (showing.getMovie().getTicketPrice() - availableDiscount);

        return ticketPriceApplyingDiscount * audienceCount;
    }

    @Override
    public Reservation settleReservation(Customer customer, Showing showing, int audienceCount){
        double totalFee = calculateReservationFee(showing, audienceCount);
        Reservation reservation = Reservation.builder()
                                    .customer(customer)
                                    .showing(showing).ticketCount(audienceCount)
                                    .totalFee(totalFee).build();

        return reservation;
    }

}
