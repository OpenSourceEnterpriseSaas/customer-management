package com.nocodenano.customermanagementboon;

import java.util.UUID;

public class MockCustomerData {

    static Customer createValid(UUID uuid,
                                String firstName,
                                String lastName,
                                String emailAddress) {
        return new Customer(uuid, firstName, lastName, emailAddress);
    }

}
