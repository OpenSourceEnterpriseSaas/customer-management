package com.nocodenano.customermanagement;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.UUID;

public class MockCustomerData {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static Customer createValid(UUID uuid,
                                       String firstName,
                                       String lastName,
                                       String emailAddress) {
        return new Customer(uuid, firstName, lastName, emailAddress);
    }

    public static String createCustomerAsJson(UUID uuid,
                                              String firstName,
                                              String lastName,
                                              String emailAddress) throws JsonProcessingException {
        return mapper.writeValueAsString(
                createValid(uuid, firstName, lastName, emailAddress));
    }
    public static UpdateFirstNameCustomer createUpdateFirstnameCustomer(
            UUID customerIdentifier, String firstName) {
        return new UpdateFirstNameCustomer(customerIdentifier, firstName);
    }

    public static UpdateLastNameCustomer createUpdateLastnameCustomer(
            UUID customerIdentifier, String lastName) {
        return new UpdateLastNameCustomer(customerIdentifier, lastName);
    }

    public static UpdateEmailCustomer createUpdateEmailCustomer(
            UUID customerIdentifier, String email) {
        return new UpdateEmailCustomer(customerIdentifier, email);
    }
}
