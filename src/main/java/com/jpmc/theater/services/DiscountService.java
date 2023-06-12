package com.jpmc.theater.services;

import com.jpmc.theater.domain.Showing;

public interface DiscountService {
    double calculateDiscountAmount(Showing showing);

}
