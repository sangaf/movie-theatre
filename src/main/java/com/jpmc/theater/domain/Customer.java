package com.jpmc.theater.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class Customer {

    private static int id;
    private String name;

    public Customer(String name) {
        this.name = name;
        id++;
    }

    @Override
    public String toString() {
        return "Customer[" + name + "]";
    }
}
