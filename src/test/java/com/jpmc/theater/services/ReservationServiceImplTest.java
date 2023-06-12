package com.jpmc.theater.services;

import com.jpmc.theater.domain.Customer;
import com.jpmc.theater.domain.Movie;
import com.jpmc.theater.domain.Reservation;
import com.jpmc.theater.domain.Showing;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.jpmc.theater.util.MovieType.SPECIAL;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ReservationServiceImplTest {

    @Mock
    DiscountServiceImpl discountService;

    @Test
    public void testCalculateReservationFee(){
        //given
        Mockito.when(discountService.calculateDiscountAmount(getShowing())).thenReturn(5.0);
        ReservationServiceImpl service = new ReservationServiceImpl(discountService);

        //when
        double actualFee = service.calculateReservationFee(getShowing(), 7);

        //then
        assertEquals((21.0-5.0) * 7, actualFee);
    }

    @Test
    public void testUpdateCustomerReservation(){
        //given
        Customer customer = new Customer("John Smith");
        Mockito.when(discountService.calculateDiscountAmount(getShowing())).thenReturn(5.0);
        ReservationServiceImpl service = new ReservationServiceImpl(discountService);
        Reservation reservation = Reservation.builder()
                .customer(customer)
                .showing(getShowing()).ticketCount(5)
                .totalFee((21.0-5.0) * 5).build();
        //when
        Reservation settledReservation = service.settleReservation(customer, getShowing(), 5);

        //then
        assertEquals(reservation, settledReservation);

    }

    private Showing getShowing() {
        Movie batman = Movie.builder().title("Batman")
                .ticketPrice(21.00).type(SPECIAL)
                .description("Batman movie")
                .runningTime(Duration.ofMinutes(109)).build();
        return Showing.builder()
                .sequenceOfTheDay(1).showStartTime(LocalDateTime.of(LocalDate.now(), LocalTime.of(13,0)))
                .movie(batman).build();
    }


}