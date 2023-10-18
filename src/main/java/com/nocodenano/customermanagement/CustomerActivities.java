package com.nocodenano.customermanagement;

import java.util.Set;
import java.util.UUID;

public interface CustomerActivities {
    UUID create(Customer customer);
    String updateFirstname(UUID customerId, UpdateFirstNameCustomer updateFirstNameCustomer);
    String updateLastname(UUID customerId, UpdateLastNameCustomer updateLastNameCustomer);
    String changeEmailAddress(UUID customerId, UpdateEmailCustomer updateEmailCustomer);
    String delete(UUID customerId);
    String findById(UUID customerId);
    Set<Customer> findAll();

}
